package uwu.llkc.cnc.common.entities.zombies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.client.util.ClientProxy;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

public class MummifiedImp extends CNCZombie {
    public static final EntityDataAccessor<Boolean> HAS_HEAD = SynchedEntityData.defineId(MummifiedImp.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_ARM = SynchedEntityData.defineId(MummifiedImp.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_SANDSTORM = SynchedEntityData.defineId(MummifiedBrowncoat.class, EntityDataSerializers.BOOLEAN);

    private static final int SANDSTORM_TIME = 200;
    private static final float SANDSTORM_CHANCE = 0.5f;


    public MummifiedImp(EntityType<MummifiedImp> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_SPEED, 1.2);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasHead", entityData.get(HAS_HEAD));
        compound.putBoolean("hasArm", entityData.get(HAS_ARM));
        compound.putBoolean("hasSandstorm", entityData.get(HAS_SANDSTORM));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(HAS_HEAD, !compound.contains("hasHead") || compound.getBoolean("hasHead"));
        entityData.set(HAS_ARM, !compound.contains("hasHead") || compound.getBoolean("hasArm"));
        entityData.set(HAS_SANDSTORM, compound.getBoolean("hasSandstorm"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_HEAD, true);
        builder.define(HAS_ARM, true);
        builder.define(HAS_SANDSTORM, false);
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
    }

    @Override
    public void tick() {
        super.tick();
        if (level().getBiome(blockPosition()).is(Tags.Biomes.IS_SANDY) && !entityData.get(HAS_SANDSTORM) && level().isThundering()) {
            if (level().getGameTime() % SANDSTORM_TIME == 5) {
                if (random.nextFloat() < SANDSTORM_CHANCE) {
                    entityData.set(HAS_SANDSTORM, true);
                    getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(MummifiedBrowncoat.SANDSTORM_MOD);
                }
            }
        }
        if (entityData.get(HAS_SANDSTORM)) {
            if (level().getBiome(blockPosition()).is(Tags.Biomes.IS_SANDY)) {
                if (level(.getGameTime() % 280 == 5) {
                    entityData.set(HAS_SANDSTORM, false);
                    getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MummifiedBrowncoat.SANDSTORM_MOD);
                }
            } else {
                entityData.set(HAS_SANDSTORM, false);
                getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MummifiedBrowncoat.SANDSTORM_MOD);
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        entityData.set(HAS_HEAD, false);
        if (level().isClientSide) {
            ClientProxy.createMummifiedImpHead(this);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(MummifiedImp.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int) f), this);
        }

        return flag;
    }



    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            ClientProxy.createMummfiedImpArm(this);
        }
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

}
