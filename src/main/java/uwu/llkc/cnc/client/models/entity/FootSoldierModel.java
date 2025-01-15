package uwu.llkc.cnc.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;
import uwu.llkc.cnc.common.entities.zombies.FootSoldier;

public class FootSoldierModel extends HierarchicalModel<FootSoldier> implements HeadedModel, ArmedModel {
    public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("foot_soldier"), "main");
    public final ModelPart leftForeArm;
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final HumanoidFootSoldier humanoid;


    public FootSoldierModel(ModelPart root) {
        leftForeArm = root.getChild("left_arm").getChild("forearm");
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.humanoid = new HumanoidFootSoldier(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition ZPG = Body.addOrReplaceChild("zpg", CubeListBuilder.create().texOffs(40, 42).addBox(-3.0F, 0, -4.25F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 51).addBox(-3.5F, -6.0F, -4.75F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(8, 40).addBox(-4.0F, 16.0F, -5.25F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.1F))
                .texOffs(56, 16).addBox(0.0F, 0.0F, 1.75F, 0.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 7.25F, -0.1309F, 0.0F, 0.0F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));

        PartDefinition Helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(32, 0).addBox(0, -1, 0, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(-4.0F, -7.0F, -4.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 6f, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition forearm = LeftArm.addOrReplaceChild("forearm", CubeListBuilder.create().texOffs(48, 32).addBox(-3.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 4f, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 2F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition Gun = RightArm.addOrReplaceChild("gun", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -3.0F, -5.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(21, 0).addBox(-2.5F, -3.0F, 4.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -2.5F, -13.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 7.5F, 0.0F, 1.5708F, 0.0F, 0.0873F));

        PartDefinition magazine = Gun.addOrReplaceChild("magazine", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(FootSoldier entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        humanoid.attackTime = attackTime;
        humanoid.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        copyFromDelegate();
        head.visible = entity.getEntityData().get(Browncoat.HAS_HEAD);
        leftForeArm.visible = entity.getEntityData().get(Browncoat.HAS_ARM);
    }

    private void copyFromDelegate() {
        this.head.loadPose(humanoid.head.storePose());
        this.body.loadPose(humanoid.body.storePose());
        this.rightArm.loadPose(humanoid.rightArm.storePose());
        this.leftArm.loadPose(humanoid.leftArm.storePose());
        this.rightLeg.loadPose(humanoid.rightLeg.storePose());
        this.leftLeg.loadPose(humanoid.leftLeg.storePose());
    }

    @Override
    public ModelPart getHead() {
        return head;
    }


    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        humanoid.translateToHand(side, poseStack);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    private static class HumanoidFootSoldier extends HumanoidModel<FootSoldier> {

        public HumanoidFootSoldier(ModelPart root) {
            super(root);
        }

        @Override
        public void setupAnim(FootSoldier entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            AnimationUtils.animateZombieArms(HumanoidFootSoldier.this.leftArm, HumanoidFootSoldier.this.rightArm, entity.isAggressive(), attackTime, ageInTicks);
        }
    }
}