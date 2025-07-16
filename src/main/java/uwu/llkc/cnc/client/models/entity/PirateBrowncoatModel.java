package uwu.llkc.cnc.client.models.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;

public class PirateBrowncoatModel<T extends Browncoat> extends AbstractZombieModel<T> implements HeadedModel, ArmedModel {
    public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("pirate_browncoat"), "main");
    public final ModelPart leftForeArm;
    private final ModelPart tie;

    public PirateBrowncoatModel(ModelPart root) {
        super(root);
        this.tie = root.getChild("tie");
        leftForeArm = this.leftArm.getChild("left_forearm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 1).addBox(-1.5F, -6.0F, 4.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(32, 3).addBox(0.0F, -8.0F, 5.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));

        //fixme: fix positioning
        PartDefinition Tie = partdefinition.addOrReplaceChild("tie", CubeListBuilder.create().texOffs(37, 1).addBox(-2.0F, -1.0F, 1.0F, 4.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -3.1F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 32).addBox(0.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition left_forearm = LeftArm.addOrReplaceChild("left_forearm", CubeListBuilder.create().texOffs(16, 42).addBox(3.0F, 3.6049F, -0.0694F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.3951F, -1.9306F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        var partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
        double d0 = Mth.lerp(partialTicks, entity.xTieO, entity.xTie) - Mth.lerp(partialTicks, entity.xo, entity.getX());
        double d1 = Mth.lerp(partialTicks, entity.yTieO, entity.yTie) - Mth.lerp(partialTicks, entity.yo, entity.getY());
        double d2 = Mth.lerp(partialTicks, entity.zTieO, entity.zTie) - Mth.lerp(partialTicks, entity.zo, entity.getZ());
        float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        double d4 = Mth.sin(f * (float) (Math.PI / 180.0));
        double d3 = -Mth.cos(f * (float) (Math.PI / 180.0));
        float f1 = (float) d1 - 10;
        f1 = Mth.clamp(f1, -32F, 0);
        float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
        f2 = Mth.clamp(f2, -150, 0);
        float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
        f3 = Mth.clamp(f3, -20.0F, 20.0F);
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        f1 += Mth.sin(Mth.lerp(partialTicks, entity.walkDistO, entity.walkDist) * 6.0F) * 32.0F;
        if (entity.isCrouching()) {
            f1 += 25.0F;
        }

        tie.xRot = (float) Math.toRadians(-(6.0F + f2 / 2.0F + f1));
        tie.yRot = (float) Math.toRadians(180 - f3 / 2.0F);
        tie.zRot = (float) Math.toRadians(f3 / 2.0F);

        head.visible = entity.getEntityData().get(Browncoat.HAS_HEAD);
        leftForeArm.visible = entity.getEntityData().get(Browncoat.HAS_ARM);
    }

    @Override
    public boolean isAggressive(T entity) {
        return entity.isAggressive();
    }

    @Override
    public ModelPart getHead() {
        return head;
    }
}