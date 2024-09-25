package uwu.llkc.cnc.client.entities.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;
import uwu.llkc.cnc.common.entities.zombies.Imp;

public class ImpModel extends HierarchicalModel<Imp> implements HeadedModel, ArmedModel {
	public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("imp"), "main");
	private final ModelPart root;
	public final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart hat;
	private final HumanoidImp delegateImp;

	public ImpModel(ModelPart root) {
		this.root = root.getChild("root");
		this.head = this.root.getChild("head");
		this.body = this.root.getChild("body");
		leftArm = this.root.getChild("left_arm");
		rightArm = this.root.getChild("right_arm");
		leftLeg = this.root.getChild("left_leg");
		rightLeg = this.root.getChild("right_leg");
		hat = this.root.getChild("hat");
		delegateImp = new HumanoidImp(this.root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -1.0F, -2.5F, 8.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F - 24, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -12.5F + 4, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 17.5F, 0.0F));

		PartDefinition leftLeg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(34, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 20.0F - 24, 0.0F));

		PartDefinition rightLeg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 25).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 20.0F - 24, 0.0F));

		PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(34, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 14.0F - 24, -0.5F, -1.309F, 0.0F, 0.0F));

		PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(26, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 14.0F - 24, -0.5F, -1.309F, 0.0F, 0.0F));
		PartDefinition hat = root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(Imp entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		delegateImp.attackTime = this.attackTime;
		delegateImp.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		copyFromDelegate();

		head.visible = entity.getEntityData().get(Browncoat.HAS_HEAD);
		leftArm.visible = entity.getEntityData().get(Browncoat.HAS_ARM);
	}

	private void copyFromDelegate() {
		this.head.loadPose(delegateImp.head.storePose());
		this.body.loadPose(delegateImp.body.storePose());
		this.rightArm.loadPose(delegateImp.rightArm.storePose());
		this.leftArm.loadPose(delegateImp.leftArm.storePose());
		this.rightLeg.loadPose(delegateImp.rightLeg.storePose());
		this.leftLeg.loadPose(delegateImp.leftLeg.storePose());
	}

	@Override
	public ModelPart getHead() {
		return head;
	}

	@Override
	public void translateToHand(HumanoidArm side, PoseStack poseStack) {
		delegateImp.translateToHand(side, poseStack);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		poseStack.pushPose();

		poseStack.translate(0, 0.5, 0);
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay, color);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay, color);

		poseStack.translate(0, 0.375, 0);
		body.render(poseStack, buffer, packedLight, packedOverlay, color);

		poseStack.pushPose();
		poseStack.translate(0, -0.1, 0);
		leftArm.render(poseStack, buffer, packedLight, packedOverlay, color);
		rightArm.render(poseStack, buffer, packedLight, packedOverlay, color);
		poseStack.popPose();

		poseStack.translate(0, -0.03, 0);
		head.render(poseStack, buffer, packedLight, packedOverlay, color);



		poseStack.popPose();
	}

	private static class HumanoidImp extends HumanoidModel<Imp> {
		public HumanoidImp(ModelPart root) {
			super(root);
		}

		@Override
		public void setupAnim(Imp entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			AnimationUtils.animateZombieArms(HumanoidImp.this.leftArm, HumanoidImp.this.rightArm, entity.isAggressive(), attackTime, ageInTicks);
		}
	}
}