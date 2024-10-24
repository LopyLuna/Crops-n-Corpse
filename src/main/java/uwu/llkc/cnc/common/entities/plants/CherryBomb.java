package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import uwu.llkc.cnc.common.entities.ai.CherryRandomLookAroundGoal;
import uwu.llkc.cnc.common.entities.ai.FixedBodyRotationControl;
import uwu.llkc.cnc.common.entities.ai.IMultiHeadEntity;
import uwu.llkc.cnc.common.entities.ai.MultiHeadLookControl;
import uwu.llkc.cnc.common.init.SoundRegistry;
import uwu.llkc.cnc.common.init.Tags;
import uwu.llkc.cnc.common.util.MessageDamageSource;

import java.util.Optional;
import java.util.function.BiConsumer;

public class CherryBomb extends CNCPlant implements VibrationSystem, IMultiHeadEntity {
    public static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Vector3f> LEFT_HEAD_ROT = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> RIGHT_HEAD_ROT = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> LEFT_HEAD_ROT_O = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> RIGHT_HEAD_ROT_O = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);

    private static final int DISTURBED_COOLDOWN = 12000;

    public final AnimationState idleAwake = new AnimationState();
    public final AnimationState idleSleeping = new AnimationState();
    public final AnimationState flying = new AnimationState();
    public final AnimationState explode = new AnimationState();

    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
    private final VibrationSystem.User vibrationUser;
    private final MultiHeadLookControl<CherryBomb> randomLookControl = new MultiHeadLookControl<>(this);
    private VibrationSystem.Data vibrationData;
    private int disturbedTimer = 0;
    private int explosionCountDown = 0;
    private boolean isExploding = false;

    public CherryBomb(EntityType<CherryBomb> entityType, Level level) {
        super(entityType, level);
        this.vibrationUser = new CherryBomb.VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));
        this.lookControl = randomLookControl;
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ATTACK_DAMAGE, 65)
                .add(Attributes.FOLLOW_RANGE, 7);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FLYING, false);
        builder.define(SLEEPING, false);
        builder.define(LEFT_HEAD_ROT, new Vector3f(getXRot(), getYHeadRot(), 0));
        builder.define(RIGHT_HEAD_ROT, new Vector3f(getXRot(), getYHeadRot(), 0));
        builder.define(LEFT_HEAD_ROT_O, new Vector3f(getXRot(), getYHeadRot(), 0));
        builder.define(RIGHT_HEAD_ROT_O, new Vector3f(getXRot(), getYHeadRot(), 0));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
        VibrationSystem.Data.CODEC
                .encodeStart(registryops, this.vibrationData)
                .resultOrPartial(result -> LogManager.getLogger().error("Failed to encode vibration listener for Warden: '{}'", result))
                .ifPresent(tag -> compound.put("listener", tag));
        compound.putBoolean("flying", entityData.get(FLYING));
        compound.putBoolean("sleeping", entityData.get(SLEEPING));
        compound.putBoolean("exploding", isExploding);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
        if (compound.contains("listener", 10)) {
            VibrationSystem.Data.CODEC
                    .parse(registryops, compound.getCompound("listener"))
                    .resultOrPartial(result -> LogManager.getLogger().error("Failed to parse vibration listener for Warden: '{}'", result))
                    .ifPresent(tag -> this.vibrationData = tag);
        }
        entityData.set(FLYING, compound.getBoolean("flying"));
        entityData.set(SLEEPING, compound.getBoolean("sleeping"));
        isExploding = compound.getBoolean("exploding");
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6) {
            @Override
            public boolean canUse() {
                return super.canUse() && !entityData.get(SLEEPING);
            }
        });
        goalSelector.addGoal(3, new CherryRandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && !entityData.get(SLEEPING);
            }
        });
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel serverLevel) {
            VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
        }

        super.tick();

        if (!level().isClientSide() && !entityData.get(SLEEPING)) {
            disturbedTimer++;
            if (disturbedTimer >= DISTURBED_COOLDOWN) {
                entityData.set(SLEEPING, true);
            }
        }

        if (level().isClientSide()) {
            idleAwake.animateWhen(!entityData.get(SLEEPING) && !entityData.get(FLYING), tickCount);
            idleSleeping.animateWhen(entityData.get(SLEEPING) && !entityData.get(FLYING), tickCount);
            flying.animateWhen(entityData.get(FLYING), tickCount);
        }

        if (entityData.get(FLYING) && onGround()) {
            entityData.set(FLYING, false);
        }

        if (!getEntityData().get(SLEEPING) && !isExploding && !getEntityData().get(FLYING) && !level().isClientSide()) {
            var player = level().getNearestPlayer(this, 3.5);
            if (player != null && !player.getUUID().equals(getOwnerUUID())) {
                isExploding = true;
            } else {
                var entity = level().getNearestEntity(Monster.class, TargetingConditions.DEFAULT, this, getX(), getY(), getZ(), AABB.ofSize(position(), 3.5, 3.5, 3.5));
                if (entity != null) {
                    isExploding = true;
                } else if (getOwner() != null) {
                    var mob = getOwner().getLastHurtMob();
                    if (mob != null && mob.distanceToSqr(this) < 12.25) {
                        isExploding = true;
                    } else {
                        mob = getOwner().getLastHurtByMob();
                        if (mob != null && mob.distanceToSqr(this) < 12.25) {
                            isExploding = true;
                        }
                    }
                }
            }
        }

        if (isExploding) {
            if (explosionCountDown == 0) {
                level().broadcastEntityEvent(this, (byte) -2);
            } else if (explosionCountDown >= 24 && !level().isClientSide()) {
                explode();
            }
            explosionCountDown++;
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        entityData.set(LEFT_HEAD_ROT_O, entityData.get(LEFT_HEAD_ROT));
        entityData.set(RIGHT_HEAD_ROT_O, entityData.get(RIGHT_HEAD_ROT));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL) && entityData.get(FLYING)) return false;
        if (!entityData.get(SLEEPING)) {
            isExploding = true;
            level().broadcastEntityEvent(this, (byte) -1);
        }
        return super.hurt(source, amount);
    }

    public Vec3 getMovementToShoot(double x, double y, double z, float velocity, float inaccuracy) {
        return new Vec3(x, y, z)
                .normalize()
                .add(
                        this.random.triangle(0.0, 0.0172275 * (double) inaccuracy),
                        this.random.triangle(0.0, 0.0172275 * (double) inaccuracy),
                        this.random.triangle(0.0, 0.0172275 * (double) inaccuracy)
                )
                .scale((double) velocity);
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3 vec3 = this.getMovementToShoot(x, y, z, velocity, inaccuracy);
        this.setDeltaMovement(vec3);
        this.hasImpulse = true;
        double d0 = vec3.horizontalDistance();
        this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * 180.0F / (float) Math.PI));
        this.setXRot((float) (Mth.atan2(vec3.y, d0) * 180.0F / (float) Math.PI));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    public void shootFromRotation(Entity shooter, float x, float y, float z, float velocity, float inaccuracy) {
        float f = -Mth.sin(y * (float) (Math.PI / 180.0)) * Mth.cos(x * (float) (Math.PI / 180.0));
        float f1 = -Mth.sin((x + z) * (float) (Math.PI / 180.0));
        float f2 = Mth.cos(y * (float) (Math.PI / 180.0)) * Mth.cos(x * (float) (Math.PI / 180.0));
        this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
        Vec3 vec3 = shooter.getKnownMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, shooter.onGround() ? 0.0 : vec3.y, vec3.z));
    }

    private void explode() {
        level().explode(this, new MessageDamageSource(damageSources().explosion(this, this), "death.attack.cherry_bomb"), new ExplosionDamageCalculator() {
            @Override
            public float getEntityDamageAmount(Explosion explosion, Entity entity) {
                if (entity instanceof CherryBomb) {return 0;}
                return ((float) getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            }

            @Override
            public Optional<Float> getBlockExplosionResistance(Explosion explosion, BlockGetter reader, BlockPos pos, BlockState state, FluidState fluid) {
                return super.getBlockExplosionResistance(explosion, reader, pos, state, fluid).map(f -> f + 2.5f);
            }

            @Override
            public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
                if (entity instanceof CNCPlant plant) {
                    if (plant instanceof CherryBomb) {
                        return super.shouldDamageEntity(explosion, entity);
                    }
                    if (plant.getOwnerUUID() != null && plant.getOwnerUUID().equals(getOwnerUUID())) {
                        return false;
                    }
                }
                return super.shouldDamageEntity(explosion, entity);
            }
        }, getX(), getY(), getZ(), 2f, false, Level.ExplosionInteraction.MOB);
        this.remove(RemovalReason.KILLED);
    }

    @Override
    public void updateDynamicGameEventListener(@NotNull BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        if (this.level() instanceof ServerLevel serverLevel) {
            listenerConsumer.accept(this.dynamicGameEventListener, serverLevel);
        }
    }

    @Override
    public @NotNull Data getVibrationData() {
        return vibrationData;
    }

    @Override
    public @NotNull User getVibrationUser() {
        return vibrationUser;
    }

    @Override
    public @NotNull MultiHeadLookControl<CherryBomb> getLookControl() {
        return randomLookControl;
    }

    @Override
    public int headCount() {
        return 2;
    }

    @Override
    public Vector3f getHeadPosition(int head) {
        return new Vector3f(
                (float) getX(),
                (float) getY(),
                (float) getZ());
    }

    @Override
    public int getHeadRotSpeed(int head) {
        return getHeadRotSpeed();
    }

    @Override
    public int getMaxHeadXRot(int head) {
        return getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot(int head) {
        return getMaxHeadYRot();
    }

    @Override
    public void setHeadXRot(int head, float xRot) {
        if (head == 0) {
            var oldVector = entityData.get(LEFT_HEAD_ROT);
            var vector = new Vector3f(xRot, oldVector.y, oldVector.z);
            entityData.set(LEFT_HEAD_ROT, vector);
        } else {
            var oldVector = entityData.get(RIGHT_HEAD_ROT);
            var vector = new Vector3f(xRot, oldVector.y, oldVector.z);
            entityData.set(RIGHT_HEAD_ROT, vector);
        }
    }

    @Override
    public void setHeadYRot(int head, float yRot) {
        if (head == 0) {
            var oldVector = entityData.get(LEFT_HEAD_ROT);
            var vector = new Vector3f(oldVector.x, yRot, oldVector.z);
            entityData.set(LEFT_HEAD_ROT, vector);
        } else {
            var oldVector = entityData.get(RIGHT_HEAD_ROT);
            var vector = new Vector3f(oldVector.x, yRot, oldVector.z);
            entityData.set(RIGHT_HEAD_ROT, vector);
        }
    }

    @Override
    public float getHeadYRot(int head) {
        if (head == 0) {
            return entityData.get(LEFT_HEAD_ROT).y;
        } else {
            return entityData.get(RIGHT_HEAD_ROT).y;
        }
    }

    @Override
    public float getHeadXRot(int head) {
        if (head == 0) {
            return entityData.get(LEFT_HEAD_ROT).x;
        } else {
            return entityData.get(RIGHT_HEAD_ROT).x;
        }
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return FixedBodyRotationControl.INSTANCE;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.PEASHOOTER_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundRegistry.PEASHOOTER_HURT.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case -1 -> isExploding = true;
            case -2 -> explode.start(tickCount);
            default -> super.handleEntityEvent(id);
        }
    }


    class VibrationUser implements VibrationSystem.User {
        private final PositionSource positionSource = new EntityPositionSource(CherryBomb.this, CherryBomb.this.getEyeHeight());

        @Override
        public int getListenerRadius() {
            return 10;
        }

        @Override
        public @NotNull PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public @NotNull TagKey<GameEvent> getListenableEvents() {
            return Tags.gameEvents.CAN_CHERRY_BOMB_LISTEN;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel p_282574_, BlockPos p_282323_, Holder<GameEvent> p_316784_, GameEvent.Context p_282515_) {
            if (!CherryBomb.this.isNoAi()
                    && !CherryBomb.this.isDeadOrDying()
                    && p_282574_.getWorldBorder().isWithinBounds(p_282323_)) {
                return p_282515_.sourceEntity() instanceof LivingEntity;
            } else {
                return false;
            }
        }

        @Override
        public void onReceiveVibration(
                ServerLevel p_281325_, BlockPos p_282386_, Holder<GameEvent> p_316139_, @javax.annotation.Nullable Entity p_281438_, @javax.annotation.Nullable Entity p_282582_, float p_283699_
        ) {
            disturbedTimer = 0;
            entityData.set(SLEEPING, false);
        }
    }
}
