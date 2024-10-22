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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
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

import java.util.function.BiConsumer;

public class CherryBomb extends CNCPlant implements VibrationSystem, IMultiHeadEntity {
    public static final EntityDataAccessor<Boolean> FLYING = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Vector3f> LEFT_HEAD_ROT = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> RIGHT_HEAD_ROT = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> LEFT_HEAD_ROT_O = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> RIGHT_HEAD_ROT_O = SynchedEntityData.defineId(CherryBomb.class, EntityDataSerializers.VECTOR3);


    private static final int DISTURBED_COOLDOWN = 200;

    public final AnimationState idleAwake = new AnimationState();
    public final AnimationState idleSleeping = new AnimationState();
    public final AnimationState flying = new AnimationState();
    public final AnimationState explode = new AnimationState();

    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
    private final VibrationSystem.User vibrationUser;

    private VibrationSystem.Data vibrationData;
    private int disturbedTimer = 0;
    private final MultiHeadLookControl<CherryBomb> randomLookControl = new MultiHeadLookControl<>(this);

    private static final Vector3f leftHeadOffset = new Vector3f(0, 0, 0);
    private static final Vector3f rightHeadOffset = new Vector3f(0, 0, 0);

    public CherryBomb(EntityType<CherryBomb> entityType, Level level) {
        super(entityType, level);
        this.vibrationUser = new CherryBomb.VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener<>(new VibrationSystem.Listener(this));
        this.lookControl = randomLookControl;
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
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ATTACK_DAMAGE, 65)
                .add(Attributes.FOLLOW_RANGE, 7);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        entityData.set(LEFT_HEAD_ROT_O, entityData.get(LEFT_HEAD_ROT));
        entityData.set(RIGHT_HEAD_ROT_O, entityData.get(RIGHT_HEAD_ROT));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6));
        goalSelector.addGoal(3, new CherryRandomLookAroundGoal(this));
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
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
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
        if (head == 0) {
            return new Vector3f(
                    (float) (getX() + leftHeadOffset.x * Math.sin(Math.toRadians(yBodyRot))),
                    (float) getY() + leftHeadOffset.y,
                    (float) (getZ() + leftHeadOffset.z * Math.cos(Math.toRadians(yBodyRot))));
        } else {
            return new Vector3f(
                    (float) (getX() + rightHeadOffset.x * Math.sin(Math.toRadians(yBodyRot))),
                    (float) getY() + rightHeadOffset.y,
                    (float) (getZ() + rightHeadOffset.z * Math.cos(Math.toRadians(yBodyRot))));
        }
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
                    && p_282574_.getWorldBorder().isWithinBounds(p_282323_)
            && CherryBomb.this.getEntityData().get(SLEEPING)) {
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

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return FixedBodyRotationControl.INSTANCE;
    }
}
