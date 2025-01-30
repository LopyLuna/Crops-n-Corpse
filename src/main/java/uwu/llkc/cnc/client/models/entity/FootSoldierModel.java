package uwu.llkc.cnc.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.animations.FootSoldierAnimations;
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


    public FootSoldierModel(ModelPart rootPart) {
        this.root = rootPart.getChild("root");
        this.body = root.getChild("body");

        this.head = body.getChild("Head1");
        this.rightArm = body.getChild("RightArm");
        this.leftArm = body.getChild("LeftArm");
        this.rightLeg = root.getChild("RightLeg1");
        leftForeArm = leftArm.getChild("forearm");

        this.leftLeg = root.getChild("LeftLeg1");
        this.humanoid = new HumanoidFootSoldier(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24, 0.0F));

        PartDefinition Body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition ZPG = Body.addOrReplaceChild("ZPG", CubeListBuilder.create().texOffs(40, 42).addBox(-3.0F, -7.0F, -4.25F, 6.0F, 16.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(12, 51).addBox(-3.5F, -13.0F, -4.75F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(8, 40).addBox(-4.0F, 9.0F, -5.25F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.1F))
                .texOffs(56, 16).addBox(0.0F, -7.0F, 1.75F, 0.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 7.25F, -0.1309F, 0.0F, 0.0F));

        PartDefinition LeftArm = Body.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(32, 32).addBox(0.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -10.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition Forearm = LeftArm.addOrReplaceChild("forearm", CubeListBuilder.create().texOffs(48, 32).addBox(0.0F, -1.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.0F, 0.0F));

        PartDefinition RightArm = Body.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -10.0F, 0.0F, -1.309F, 0.0F, 0.0F));

        PartDefinition Gun = RightArm.addOrReplaceChild("Gun", CubeListBuilder.create().texOffs(64, 0).addBox(-2.0F, -3.0F, -5.0F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 11).addBox(-2.5F, -3.0F, 4.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-1.5F, -2.5F, -13.0F, 2.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 7.5F, 0.0F, 1.5708F, 0.0F, 0.0873F));

        PartDefinition magazine = Gun.addOrReplaceChild("magazine", CubeListBuilder.create().texOffs(24, 0).addBox(-2.5F, -4.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -3.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition Head1 = Body.addOrReplaceChild("Head1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition Helmet = Head1.addOrReplaceChild("Helmet", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -5.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition RightLeg1 = root.addOrReplaceChild("RightLeg1", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -12.0F, 0.0F));

        PartDefinition LeftLeg1 = root.addOrReplaceChild("LeftLeg1", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -12.0F, 0.0F));

        PartDefinition Head = root.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);


        PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition RightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(FootSoldier entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);


        humanoid.attackTime = attackTime;
        humanoid.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        copyFromDelegate();

        head.xRot = (float) Math.toRadians(headPitch);
        head.yRot = (float) Math.toRadians(netHeadYaw);
        head.visible = entity.getEntityData().get(Browncoat.HAS_HEAD);
        leftForeArm.visible = entity.getEntityData().get(Browncoat.HAS_ARM);

        if (entity.zpgState.isStarted()) {
            this.animate(entity.zpgState, FootSoldierAnimations.ZPG, ageInTicks);
        }



    }

    private void copyFromDelegate() {
        body.setRotation(humanoid.body.xRot, humanoid.body.yRot, humanoid.body.zRot);
        rightArm.setRotation(humanoid.rightArm.xRot, humanoid.rightArm.yRot, humanoid.rightArm.zRot);
        leftArm.setRotation(humanoid.leftArm.xRot, humanoid.leftArm.yRot, humanoid.leftArm.zRot);
        rightLeg.setRotation(humanoid.rightLeg.xRot, humanoid.rightLeg.yRot, humanoid.rightLeg.zRot);
        leftLeg.setRotation(humanoid.leftLeg.xRot, humanoid.leftLeg.yRot, humanoid.leftLeg.zRot);
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

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        root().render(poseStack, buffer, packedLight, packedOverlay, color);
    }

    private static class HumanoidFootSoldier extends HumanoidModel<FootSoldier> {

        public HumanoidFootSoldier(ModelPart root) {
            super(root);
        }

        @Override
        public void setupAnim(FootSoldier entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
            boolean flag = entity.getFallFlyingTicks() > 4;
            boolean flag1 = entity.isVisuallySwimming();
            this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
            if (flag) {
                this.head.xRot = (float) (-Math.PI / 4);
            } else if (this.swimAmount > 0.0F) {
                if (flag1) {
                    this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, (float) (-Math.PI / 4));
                } else {
                    this.head.xRot = this.rotlerpRad(this.swimAmount, this.head.xRot, headPitch * (float) (Math.PI / 180.0));
                }
            } else {
                this.head.xRot = headPitch * (float) (Math.PI / 180.0);
            }

            this.body.yRot = 0.0F;

            float f = 1.0F;
            if (flag) {
                f = (float) entity.getDeltaMovement().lengthSqr();
                f /= 0.2F;
                f *= f * f;
            }

            if (f < 1.0F) {
                f = 1.0F;
            }

            this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
            this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
            this.rightArm.zRot = 0.0F;
            this.leftArm.zRot = 0.0F;
            this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
            this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount / f;
            this.rightLeg.yRot = 0.005F;
            this.leftLeg.yRot = -0.005F;
            this.rightLeg.zRot = 0.005F;
            this.leftLeg.zRot = -0.005F;
            if (this.riding) {
                this.rightArm.xRot += (float) (-Math.PI / 5);
                this.leftArm.xRot += (float) (-Math.PI / 5);
                this.rightLeg.xRot = -1.4137167F;
                this.rightLeg.yRot = (float) (Math.PI / 10);
                this.rightLeg.zRot = 0.07853982F;
                this.leftLeg.xRot = -1.4137167F;
                this.leftLeg.yRot = (float) (-Math.PI / 10);
                this.leftLeg.zRot = -0.07853982F;
            }

            this.rightArm.yRot = 0.0F;
            this.leftArm.yRot = 0.0F;
            boolean flag2 = entity.getMainArm() == HumanoidArm.RIGHT;
            if (entity.isUsingItem()) {
                boolean flag3 = entity.getUsedItemHand() == InteractionHand.MAIN_HAND;
                if (flag3 == flag2) {
                    this.poseRightArm(entity);
                } else {
                    this.poseLeftArm(entity);
                }
            } else {
                boolean flag4 = flag2 ? this.leftArmPose.isTwoHanded() : this.rightArmPose.isTwoHanded();
                if (flag2 != flag4) {
                    this.poseLeftArm(entity);
                    this.poseRightArm(entity);
                } else {
                    this.poseRightArm(entity);
                    this.poseLeftArm(entity);
                }
            }

            this.setupAttackAnimation(entity, ageInTicks);

            if (this.rightArmPose != HumanoidModel.ArmPose.SPYGLASS) {
                AnimationUtils.bobModelPart(this.rightArm, ageInTicks, 1.0F);
            }

            if (this.leftArmPose != HumanoidModel.ArmPose.SPYGLASS) {
                AnimationUtils.bobModelPart(this.leftArm, ageInTicks, -1.0F);
            }

            if (this.swimAmount > 0.0F) {
                float f5 = limbSwing % 26.0F;
                HumanoidArm humanoidarm = getAttackArm(entity);
                float f1 = humanoidarm == HumanoidArm.RIGHT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
                float f2 = humanoidarm == HumanoidArm.LEFT && this.attackTime > 0.0F ? 0.0F : this.swimAmount;
                if (!entity.isUsingItem()) {
                    if (f5 < 14.0F) {
                        this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, 0.0F);
                        this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, 0.0F);
                        this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float) Math.PI);
                        this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
                        this.leftArm.zRot = this.rotlerpRad(
                                f2, this.leftArm.zRot, (float) Math.PI + 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F)
                        );
                        this.rightArm.zRot = Mth.lerp(
                                f1, this.rightArm.zRot, (float) Math.PI - 1.8707964F * this.quadraticArmUpdate(f5) / this.quadraticArmUpdate(14.0F)
                        );
                    } else if (f5 >= 14.0F && f5 < 22.0F) {
                        float f6 = (f5 - 14.0F) / 8.0F;
                        this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, (float) (Math.PI / 2) * f6);
                        this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, (float) (Math.PI / 2) * f6);
                        this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float) Math.PI);
                        this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
                        this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, 5.012389F - 1.8707964F * f6);
                        this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, 1.2707963F + 1.8707964F * f6);
                    } else if (f5 >= 22.0F && f5 < 26.0F) {
                        float f3 = (f5 - 22.0F) / 4.0F;
                        this.leftArm.xRot = this.rotlerpRad(f2, this.leftArm.xRot, (float) (Math.PI / 2) - (float) (Math.PI / 2) * f3);
                        this.rightArm.xRot = Mth.lerp(f1, this.rightArm.xRot, (float) (Math.PI / 2) - (float) (Math.PI / 2) * f3);
                        this.leftArm.yRot = this.rotlerpRad(f2, this.leftArm.yRot, (float) Math.PI);
                        this.rightArm.yRot = Mth.lerp(f1, this.rightArm.yRot, (float) Math.PI);
                        this.leftArm.zRot = this.rotlerpRad(f2, this.leftArm.zRot, (float) Math.PI);
                        this.rightArm.zRot = Mth.lerp(f1, this.rightArm.zRot, (float) Math.PI);
                    }
                }

                float f7 = 0.3F;
                float f4 = 0.33333334F;
                this.leftLeg.xRot = Mth.lerp(this.swimAmount, this.leftLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F + (float) Math.PI));
                this.rightLeg.xRot = Mth.lerp(this.swimAmount, this.rightLeg.xRot, 0.3F * Mth.cos(limbSwing * 0.33333334F));
            }

            AnimationUtils.animateZombieArms(HumanoidFootSoldier.this.leftArm, HumanoidFootSoldier.this.rightArm, entity.isAggressive(), attackTime, ageInTicks);
        }
    }
}