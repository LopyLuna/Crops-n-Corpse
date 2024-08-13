package uwu.llkc.cnc.common.entities.zombies;

import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import uwu.llkc.cnc.client.entities.models.BrowncoatModel;
import uwu.llkc.cnc.client.particles.PhysicsModelParticle;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_HEAD, true);
        builder.define(HAS_ARM, true);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 3)
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
        if (d0 > 10.0) {
            this.xTie = this.getX();
            this.xTieO = this.xTie;
        }

        if (d2 > 10.0) {
            this.zTie = this.getZ();
            this.zTieO = this.zTie;
        }

        if (d1 > 10.0) {
            this.yTie = this.getY();
            this.yTieO = this.yTie;
        }

        if (d0 < -10.0) {
            this.xTie = this.getX();
            this.xTieO = this.xTie;
        }

        if (d2 < -10.0) {
            this.zTie = this.getZ();
            this.zTieO = this.zTie;
        }

        if (d1 < -10.0) {
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
            if (getHealth() / getMaxHealth() < 0.5f) {
                entityData.set(HAS_ARM, false);
                level().broadcastEntityEvent(this, (byte)0);
            }
        } else {
            super.actuallyHurt(damageSource, damageAmount);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        entityData.set(HAS_HEAD, false);
        if (level().isClientSide) {
            var model = BrowncoatModel.createBodyLayer().bakeRoot().getChild("root").getChild("head");
            Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(((ClientLevel) level()), this.getX(), this.getY() + 1.5, this.getZ(), model, poseStack -> {
                poseStack.mulPose(Axis.YN.rotationDegrees(getYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(180));
            }, Vec3.directionFromRotation(0, getYRot()).x * -0.05, 0.2, Vec3.directionFromRotation(0, getYRot()).z * -0.05));
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers(ZombifiedPiglin.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public ZombieCategory getZombieCategory() {
        return ZombieCategory.BASIC;
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            var model = BrowncoatModel.createBodyLayer().bakeRoot().getChild("root").getChild("left_arm").getChild("left_forearm");
            Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(((ClientLevel) level()), this.getX(), this.getY() + 1.25, this.getZ(), model, poseStack -> {
                poseStack.translate(Vec3.directionFromRotation(0, getYRot()).z *.32, 0, Vec3.directionFromRotation(0, getYRot()).x *-.32);
                poseStack.mulPose(Axis.YP.rotationDegrees(-getYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
            }, Vec3.directionFromRotation(0, getYRot()).x * -0.05, 0, Vec3.directionFromRotation(0, getYRot()).z * -0.05));
        }
    }
}
