package uwu.llkc.cnc.common.entities.zombies;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.multiplayer.ClientLevel;
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
import org.joml.Quaternionf;
import uwu.llkc.cnc.client.entities.models.BrowncoatModel;
import uwu.llkc.cnc.client.entities.renderers.BrowncoatRenderer;
import uwu.llkc.cnc.client.particles.PhysicsModelParticle;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

public class Browncoat extends CNCZombie{
    public double xTieO;
    public double yTieO;
    public double zTieO;
    public double xTie;
    public double yTie;
    public double zTie;


    public Browncoat(EntityType<Browncoat> entityType, Level level) {
        super(entityType, level);

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
        if (level().isClientSide && level().getGameTime() % 10 == 3) {
            var model = BrowncoatModel.createBodyLayer().bakeRoot().getChild("root").getChild("head");
            Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(((ClientLevel) level()), this.getX(), this.getY(), this.getZ(), model, poseStack -> poseStack.mulPose(Axis.ZP.rotationDegrees(180))));
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
}
