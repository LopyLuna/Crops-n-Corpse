package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.client.entities.animations.PotatoMineAnimations;
import uwu.llkc.cnc.common.entities.ai.FixedBodyRotationControl;
import uwu.llkc.cnc.common.init.SoundRegistry;
import uwu.llkc.cnc.common.util.MessageDamageSource;

public class PotatoMine extends CNCPlant {
    public static final EntityDataAccessor<Boolean> ARMED = SynchedEntityData.defineId(PotatoMine.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState inActive = new AnimationState();
    public final AnimationState arming = new AnimationState();
    public final AnimationState armed = new AnimationState();
    public final AnimationState bulbBlink = new AnimationState();
    public final AnimationState explode = new AnimationState();

    private int armingCooldown = 600;
    private int explosionCountdown = 0;
    private boolean isExploding = false;

    public PotatoMine(EntityType<PotatoMine> entityType, Level level) {
        super(entityType, level);
        lookControl = new LookControl(this) {
            @Override
            public void tick() {
                if (this.resetXRotOnTick()) {
                    this.mob.setXRot(0.0F);
                }

                if (this.lookAtCooldown > 0) {
                    this.lookAtCooldown--;
                    this.getYRotD().ifPresent(p_287447_ -> this.mob.yHeadRot = this.rotateTowards(this.mob.yHeadRot, p_287447_, this.yMaxRotSpeed));
                    this.getXRotD().ifPresent(p_352768_ -> this.mob.setXRot(this.rotateTowards(this.mob.getXRot(), p_352768_, this.xMaxRotAngle)));
                }
            }

            @Override
            protected boolean resetXRotOnTick() {
                return false;
            }
        };
        if (level().isClientSide()) {
            inActive.animateWhen(!entityData.get(ARMED) && !arming.isStarted(), tickCount);
        }
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.ATTACK_DAMAGE, 40)
                .add(Attributes.MAX_HEALTH, 6);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("armed", entityData.get(ARMED));
        compound.putInt("armingCooldown", armingCooldown);
        compound.putInt("explosionCountdown", explosionCountdown);
        compound.putBoolean("isExploding", isExploding);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(ARMED, compound.getBoolean("armed"));
        armingCooldown = compound.getInt("armingCooldown");
        explosionCountdown = compound.getInt("explosionCountdown");
        isExploding = compound.getBoolean("isExploding");
    }

    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return FixedBodyRotationControl.INSTANCE;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ARMED, false);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(2, new RandomLookAroundGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && entityData.get(ARMED);
            }
        });
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6, 0.001f) {
            @Override
            public boolean canUse() {
                return super.canUse() && entityData.get(ARMED);
            }
        });
    }


    void explode() {
        level().explode(this, new MessageDamageSource(damageSources().explosion(this, this), "death.attack.potato_mine"), new ExplosionDamageCalculator() {
            @Override
            public float getEntityDamageAmount(Explosion explosion, Entity entity) {
                return ((float) getAttribute(Attributes.ATTACK_DAMAGE).getValue());
            }

            @Override
            public boolean shouldDamageEntity(Explosion explosion, Entity entity) {
                if (entity instanceof CNCPlant plant) {
                    if (plant.getOwnerUUID() != null && plant.getOwnerUUID().equals(getOwnerUUID())) {
                        return false;
                    }
                }
                return super.shouldDamageEntity(explosion, entity);
            }
        }, getX(), getY(), getZ(), 1.5f, false, Level.ExplosionInteraction.MOB);
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void doPush(Entity entity) {
        if (entity instanceof LivingEntity && entityData.get(ARMED) && !(entity instanceof Player player && (player.isCreative() || player.isSpectator()))) {
            isExploding = true;
        }
    }

    @Override
    public void push(double x, double y, double z) {
        if (entityData.get(ARMED)) {
            isExploding = true;
        }
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (armingCooldown <= 0) {
            if (!level().isClientSide()) {
                level().broadcastEntityEvent(this, (byte) -2);
                entityData.set(ARMED, true);
            }
        } else {
            armingCooldown--;
        }
        if (level().isClientSide()) {
            armed.animateWhen(entityData.get(ARMED) && !arming.isStarted(), tickCount);
            inActive.animateWhen(!entityData.get(ARMED) && !arming.isStarted(), tickCount);
        }
        if (isExploding) {
            if (explosionCountdown == 0) {
                level().broadcastEntityEvent(this, (byte) -1);
            }
            if (explosionCountdown >= 7 && !level().isClientSide) {
                explode();
            } else {
                explosionCountdown++;
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (entityData.get(ARMED)) {
            isExploding = true;
            level().broadcastEntityEvent(this, (byte) -3);
        }
        return super.hurt(source, amount);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.POTATO_MINE_DEATH.get();
    }

    @Override
    public void handleEntityEvent(byte id) {
        switch (id) {
            case -1 -> explode.start(tickCount);
            case -2 -> {
                if (!entityData.get(ARMED)) {
                    arming.startIfStopped(tickCount);
                }
                if (arming.getAccumulatedTime() >= PotatoMineAnimations.ARMING.lengthInSeconds() * 1000) {
                    arming.stop();
                }
            }
            case -3 -> isExploding = true;
            default -> super.handleEntityEvent(id);
        }
    }
}
