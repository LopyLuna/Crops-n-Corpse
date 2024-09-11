package uwu.llkc.cnc.client.entities.models;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.animations.WallNutAnimations;
import uwu.llkc.cnc.common.entities.plants.WallNut;

public class WallNutModel extends HierarchicalModel<WallNut> {
	public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("wallnut"), "main");

	private final ModelPart root;
	private final ModelPart rightEye;
	private final ModelPart leftEye;


	public WallNutModel(ModelPart root) {
		this.root = root.getChild("Head");
		this.leftEye = this.root.getChild("LeftEye");
		this.rightEye = this.root.getChild("RightEye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 32).addBox(-7.5F, -18.0F, -6.5F, 15.0F, 18.0F, 13.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-7.5F, -18.0F, -6.5F, 15.0F, 18.0F, 13.0F, new CubeDeformation(0.25F))
				.texOffs(1, 6).addBox(-5.5F, -13.0F, -6.48F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(1, 1).addBox(1.5F, -13.0F, -6.48F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(43, 0).addBox(-3.5F, -18.0F, -6.5F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition LeftEye = Head.addOrReplaceChild("LeftEye", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -1.0F, -0.09F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -10.5F, -6.4F));

		PartDefinition RightEye = Head.addOrReplaceChild("RightEye", CubeListBuilder.create().texOffs(4, 10).addBox(-1.0F, -1.0F, -0.09F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -10.5F, -6.4F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(WallNut entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.death, WallNutAnimations.DEATH, ageInTicks);
		this.animate(entity.glance, WallNutAnimations.GLANCE, ageInTicks);
		this.animate(entity.stage1, WallNutAnimations.IDLE_1, ageInTicks);
		this.animate(entity.stage2, WallNutAnimations.IDLE_2, ageInTicks);
		this.animate(entity.stage3, WallNutAnimations.IDLE_3, ageInTicks);


		Vec3 rightEyeOrigin = new Vec3(-2.5f, -10.5f, -4f);
		Vec3 leftEyeOrigin = new Vec3(2.5f, -10.5f, -4f);
		Vec3 lookTarget = Vec3.directionFromRotation(headPitch, netHeadYaw);

		rightEye.setPos(Math.clamp((float) (2.49f / lookTarget.z * lookTarget.x + rightEyeOrigin.x), -4.5f, -2.5f), Math.clamp((float) (-2.49f / lookTarget.z * lookTarget.y + rightEyeOrigin.y), -12, -9.5f), -6.49f);
		leftEye.setPos(Math.clamp((float) (2.49f / lookTarget.z * lookTarget.x + leftEyeOrigin.x), 2.5f, 4.5f), Math.clamp((float) (-2.49f / lookTarget.z * lookTarget.y + leftEyeOrigin.y), -12, -9.5f), -6.49f);
    }
}