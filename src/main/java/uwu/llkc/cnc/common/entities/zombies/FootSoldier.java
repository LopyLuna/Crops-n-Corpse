package uwu.llkc.cnc.common.entities.zombies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.client.util.ClientProxy;
import uwu.llkc.cnc.common.entities.ai.JumpToGoal;
import uwu.llkc.cnc.common.entities.ai.KeepDistanceGoal;
import uwu.llkc.cnc.common.entities.ai.StinkCloudGoal;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.entities.projectiles.FootSoldierProjectile;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;

public class FootSoldier extends CNCZombie implements RangedAttackMob {
    public static final EntityDataAccessor<Boolean> HAS_HEAD = SynchedEntityData.defineId(FootSoldier.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_ARM = SynchedEntityData.defineId(FootSoldier.class, EntityDataSerializers.BOOLEAN);

    private static final int HIT_TIME = 40;

    public float totalDamage;
    public boolean isSafeFall = false;

    public FootSoldier(EntityType<FootSoldier> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 100)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_SPEED, 1)
                .add(Attributes.FOLLOW_RANGE, 40);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_HEAD, true);
        builder.define(HAS_ARM, true);
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        if (getHealth() / getMaxHealth() > 0.5f) {
            super.actuallyHurt(damageSource, damageAmount);
            if (entityData.get(HAS_ARM) && getHealth() / getMaxHealth() < 0.5f) {
                entityData.set(HAS_ARM, false);
                level().broadcastEntityEvent(this, (byte) 0);
            }
        } else {
            super.actuallyHurt(damageSource, damageAmount);
        }
        totalDamage += damageAmount;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().getGameTime() % HIT_TIME == 3) {
            totalDamage = 0;
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        entityData.set(HAS_HEAD, false);
        if (level().isClientSide) {
            ClientProxy.createFootSoldierHead(this);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new StinkCloudGoal(this, 25));
        this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1, 50, 25));
        this.goalSelector.addGoal(5, new KeepDistanceGoal(this, 15));
        this.goalSelector.addGoal(2, new JumpToGoal(this, 25, 200, 1.2f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(FootSoldier.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            ClientProxy.createFootSoldierArm(this);
        }
    }


    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        if (isSafeFall) {
            isSafeFall = false;
            return 0;
        }
        return super.calculateFallDamage(fallDistance, damageMultiplier);
    }

    @Override
    public void setOnGroundWithMovement(boolean onGround, Vec3 movement) {
        super.setOnGroundWithMovement(onGround, movement);
        setDiscardFriction(false);
    }

    @Override
    public void setOnGround(boolean onGround) {
        super.setOnGround(onGround);
        setDiscardFriction(false);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float velocity) {
        FootSoldierProjectile projectile = EntityTypeRegistry.FOOT_SOLDIER_PROJECTILE.get().create(level());
        if (projectile == null) return;
        projectile.setOwner(this);

        float xOffset = -.5f;
        float yOffset = 1f;
        float zOffset = 1f;

        double x = Math.cos(Math.toRadians(getVisualRotationYInDegrees())) * xOffset + getX() - Math.sin(Math.toRadians(getVisualRotationYInDegrees())) * zOffset;
        double y = yOffset + getY();
        double z = Math.cos(Math.toRadians(getVisualRotationYInDegrees())) * zOffset + getZ() + Math.sin(Math.toRadians(getVisualRotationYInDegrees())) * xOffset;

        projectile.setPos(x, y, z);
        projectile.shoot(target.getX() - x, target.getEyeY() - y, target.getZ() - z, 1f, 0);
        level().addFreshEntity(projectile);
    }
}
