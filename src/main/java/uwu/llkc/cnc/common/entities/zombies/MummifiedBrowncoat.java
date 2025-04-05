package uwu.llkc.cnc.common.entities.zombies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.util.ClientProxy;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.init.ItemRegistry;

public class MummifiedBrowncoat extends CNCZombie {
    public static final EntityDataAccessor<Boolean> HAS_HEAD = SynchedEntityData.defineId(MummifiedBrowncoat.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_ARM = SynchedEntityData.defineId(MummifiedBrowncoat.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_SANDSTORM = SynchedEntityData.defineId(MummifiedBrowncoat.class, EntityDataSerializers.BOOLEAN);

    private static final int SANDSTORM_TIME = 200;
    private static final float SANDSTORM_CHANCE = 0.5f;
    public static final AttributeModifier SANDSTORM_MOD = new AttributeModifier(CNCMod.rl("sandstorm"), 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    public double xTieO;
    public double yTieO;
    public double zTieO;
    public double xTie;
    public double yTie;
    public double zTie;

    public MummifiedBrowncoat(EntityType<MummifiedBrowncoat> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_SPEED, 1);
    }

    @Override
    protected void doHurtEquipment(DamageSource damageSource, float damageAmount, EquipmentSlot... slots) {
        ItemStack oldHat = null;
        ItemStack newHat = null;
        for (EquipmentSlot slot : slots) {
            if (slot == EquipmentSlot.HEAD) {
                oldHat = getItemBySlot(EquipmentSlot.HEAD);
            }
        }
        super.doHurtEquipment(damageSource, damageAmount, slots);
        for (EquipmentSlot slot : slots) {
            if (slot == EquipmentSlot.HEAD) {
                newHat = getItemBySlot(EquipmentSlot.HEAD);
            }
        }

        if (oldHat != null && newHat != null) {
            if (oldHat.is(Items.BUCKET) && newHat.isEmpty()) {
                if (random.nextFloat() < 0.085) {
                    spawnAtLocation(Items.BUCKET);
                }
            } else if (oldHat.is(ItemRegistry.TRAFFIC_CONE) && newHat.isEmpty()) {
                if (random.nextFloat() < 0.085) {
                    spawnAtLocation(ItemRegistry.TRAFFIC_CONE);
                }
            }
        }
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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (spawnType != MobSpawnType.SPAWN_EGG) {
            if (random.nextFloat() < 0.30f) {
                setItemSlot(EquipmentSlot.HEAD, new ItemStack(ItemRegistry.TRAFFIC_CONE.get()));
            } else if (random.nextFloat() < 0.15) {
                setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.BUCKET));
            } else if (random.nextFloat() < 0.05f) {
                setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.FLAG.get()));
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_HEAD, true);
        builder.define(HAS_ARM, true);
        builder.define(HAS_SANDSTORM, false);
    }

    private void moveTie() {
        this.xTieO = this.xTie;
        this.yTieO = this.yTie;
        this.zTieO = this.zTie;
        double d0 = this.getX() - this.xTie;
        double d1 = this.getY() - this.yTie;
        double d2 = this.getZ() - this.zTie;
        double d3 = 10.0;
        if (d0 > d3) {
            this.xTie = this.getX();
            this.xTieO = this.xTie;
        }

        if (d2 > d3) {
            this.zTie = this.getZ();
            this.zTieO = this.zTie;
        }

        if (d1 > d3) {
            this.yTie = this.getY();
            this.yTieO = this.yTie;
        }

        if (d0 < -d3) {
            this.xTie = this.getX();
            this.xTieO = this.xTie;
        }

        if (d2 < -d3) {
            this.zTie = this.getZ();
            this.zTieO = this.zTie;
        }

        if (d1 < -d3) {
            this.yTie = this.getY();
            this.yTieO = this.yTie;
        }

        this.xTie += d0 * 0.25;
        this.zTie += d2 * 0.25;
        this.yTie += d1 * 0.25;
    }

    @Override
    public void tick() {
        super.tick();
        moveTie();
        if (level().getBiome(blockPosition()).is(Tags.Biomes.IS_SANDY) && !entityData.get(HAS_SANDSTORM) && level().isThundering()) {
            if (level().getGameTime() % SANDSTORM_TIME == 5) {
                if (random.nextFloat() < SANDSTORM_CHANCE) {
                    entityData.set(HAS_SANDSTORM, true);
                    getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SANDSTORM_MOD);
                }
            }
        }
        if (entityData.get(HAS_SANDSTORM)) {
            if (level().getBiome(blockPosition()).is(Tags.Biomes.IS_SANDY)) {
                if (level().getGameTime() % 280 == 5) {
                    entityData.set(HAS_SANDSTORM, false);
                    getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SANDSTORM_MOD);
                }
            } else {
                entityData.set(HAS_SANDSTORM, false);
                getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SANDSTORM_MOD);
            }
        }
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
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int) f), this);
        }

        return flag;
    }

    @Override
    protected int getBaseExperienceReward() {
        if (this.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.TRAFFIC_CONE)) {
            return 7;
        }
        if (this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BUCKET)) {
            return 10;
        }
        if (this.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistry.FLAG)) {
            return 6;
        }
        return 5;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        entityData.set(HAS_HEAD, false);
        if (level().isClientSide) {
            ClientProxy.createMummifiedBrowncoatHead(this);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(MummifiedBrowncoat.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            ClientProxy.createMummifiedBrowncoatArm(this);
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
