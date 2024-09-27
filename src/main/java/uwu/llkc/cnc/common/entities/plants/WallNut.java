package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.SoundRegistry;

public class WallNut extends CNCPlant {
    public static final EntityDataAccessor<Integer> STAGE = SynchedEntityData.defineId(WallNut.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> HAS_ARMOR = SynchedEntityData.defineId(WallNut.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState glance = new AnimationState();
    public final AnimationState stage1 = new AnimationState();
    public final AnimationState stage2 = new AnimationState();
    public final AnimationState stage3 = new AnimationState();
    public final AnimationState death = new AnimationState();

    public float armorHealth = 300;

    public WallNut(EntityType<WallNut> entityType, Level level) {
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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("armorHealth", armorHealth);
        compound.putInt("stage", entityData.get(STAGE));
        compound.putBoolean("hasArmor", entityData.get(HAS_ARMOR));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        armorHealth = compound.getFloat("armorHealth");
        entityData.set(STAGE, compound.getInt("stage"));
        entityData.set(HAS_ARMOR, compound.getBoolean("hasArmor"));
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BodyRotationControl(this) {
            @Override
            public void clientTick() {
            }
        };
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STAGE, 0);
        builder.define(HAS_ARMOR, false);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 8)
                .add(Attributes.MAX_HEALTH, 200);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6) {
            @Override
            public void stop() {
                super.stop();
                this.mob.yHeadRot = rotateTowards(this.mob.yHeadRot, this.mob.yBodyRot, 90.0F);
                this.mob.setXRot(0.0F);
                clampHeadRotationToBody();
            }
        });
    }

    protected float rotateTowards(float from, float to, float maxDelta) {
        float f = Mth.degreesDifference(from, to);
        float f1 = Mth.clamp(f, -maxDelta, maxDelta);
        return from + f1;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            stage1.animateWhen(entityData.get(STAGE) == 1, tickCount);
            stage2.animateWhen(entityData.get(STAGE) == 2, tickCount);
            stage3.animateWhen(entityData.get(STAGE) == 3, tickCount);
            if (getRandom().nextFloat() < 0.02f) {
                glance.startIfStopped(tickCount);
            } else {
                glance.stop();
            }
        }
    }

    @Override
    public void baseTick() {
        if (firstTick) {
            setYRot(Math.round(getYRot() / 90) * 90);
            setYHeadRot(Math.round(getYRot() / 90) * 90);
            setYBodyRot(Math.round(getYRot() / 90) * 90);
        }
        super.baseTick();
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        if (this.getOwner() != null && player.equals(this.getOwner())) {
            if (!getEntityData().get(HAS_ARMOR)) {
                if (player.getItemInHand(hand).getItem() == ItemRegistry.PLANT_ARMOR.get()) {
                    playSound(SoundEvents.ARMOR_EQUIP_IRON.value());
                    getEntityData().set(HAS_ARMOR, true);
                    player.getItemInHand(hand).shrink(1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void push(Entity entity) {
        if (entity.getBoundingBox().minY <= this.getBoundingBox().minY) {
            super.push(entity);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        level().broadcastEntityEvent(this, (byte) 1);
        super.die(damageSource);
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        if (entityData.get(HAS_ARMOR)) {
            armorHealth -= damageAmount;
            if (armorHealth <= 0) {
                playSound(SoundEvents.ITEM_BREAK);
                entityData.set(HAS_ARMOR, false);
            }
            return;
        }
        super.actuallyHurt(damageSource, damageAmount);
    }

    @Override
    public void setHealth(float health) {
        super.setHealth(health);
        if (getHealth() / getMaxHealth() < 0.25) {
            entityData.set(STAGE, 3);
        } else if (getHealth() / getMaxHealth() < 0.50) {
            entityData.set(STAGE, 2);
        } else if (getHealth() / getMaxHealth() < 0.75) {
            entityData.set(STAGE, 1);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 1) {
            death.start(tickCount);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.WALL_NUT_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return entityData.get(HAS_ARMOR) ? SoundRegistry.WALL_NUT_ARMOR_HURT.get() : SoundRegistry.WALL_NUT_HURT.get();
    }
}
