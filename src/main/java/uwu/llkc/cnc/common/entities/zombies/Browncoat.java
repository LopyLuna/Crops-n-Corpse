package uwu.llkc.cnc.common.entities.zombies;

import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.client.entities.models.BrowncoatModel;
import uwu.llkc.cnc.client.particles.PhysicsModelParticle;
import uwu.llkc.cnc.client.util.ClientProxy;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.SoundRegistry;

public class Browncoat extends CNCZombie{
    public static final EntityDataAccessor<Boolean> HAS_HEAD = SynchedEntityData.defineId(Browncoat.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> HAS_ARM = SynchedEntityData.defineId(Browncoat.class, EntityDataSerializers.BOOLEAN);

    public double xTieO;
    public double yTieO;
    public double zTieO;
    public double xTie;
    public double yTie;
    public double zTie;


    public Browncoat(EntityType<Browncoat> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if(spawnType != MobSpawnType.SPAWN_EGG) {
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
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_SPEED, 1);
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
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        if (getHealth() / getMaxHealth() > 0.5f) {
            super.actuallyHurt(damageSource, damageAmount);
            if (entityData.get(HAS_ARM) && getHealth() / getMaxHealth() < 0.5f) {
                entityData.set(HAS_ARM, false);
                level().broadcastEntityEvent(this, (byte)0);
            }
        } else {
            super.actuallyHurt(damageSource, damageAmount);
        }
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
            ClientProxy.createBrowncoatHead(this);
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(Browncoat.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            ClientProxy.createBrowncoatArm(this);
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundRegistry.BROWNCOAT_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.BROWNCOAT_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundRegistry.BROWNCOAT_AMBIANCE.get();
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Override
    protected void playAttackSound() {
        playSound(SoundRegistry.BROWNCOAT_ATTACK.get());
    }
}
