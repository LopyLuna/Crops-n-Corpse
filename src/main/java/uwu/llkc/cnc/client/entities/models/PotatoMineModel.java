package uwu.llkc.cnc.client.entities.models;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.animations.PotatoMineAnimations;
import uwu.llkc.cnc.client.entities.animations.WallNutAnimations;
import uwu.llkc.cnc.common.entities.plants.PotatoMine;
import uwu.llkc.cnc.common.entities.plants.WallNut;

public class PotatoMineModel extends HierarchicalModel<PotatoMine> {
	public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("potato_mine"), "main");

	private final ModelPart root;
	private final ModelPart head;


	public PotatoMineModel(ModelPart root) {
		super(RenderType::entityTranslucent);
		this.root = root;
		this.head = root.getChild("Head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.5F, -4.5F, 8.0F, 5.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 21.75F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition Antenna = Head.addOrReplaceChild("Antenna", CubeListBuilder.create().texOffs(0, 3).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, 0.0F));

		PartDefinition Bulb = Antenna.addOrReplaceChild("Bulb", CubeListBuilder.create().texOffs(27, 15).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(14, 15).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, 0.0F));

		PartDefinition Bulb_Glow = Bulb.addOrReplaceChild("Bulb_Glow", CubeListBuilder.create().texOffs(1, 15).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -1.5F, 0.0F));

		PartDefinition dirts = partdefinition.addOrReplaceChild("dirts", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition outer_dirt = dirts.addOrReplaceChild("outer_dirt", CubeListBuilder.create().texOffs(23, 22).addBox(-2.0F, -1.0F, -12.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.5F));

		PartDefinition dirt_r1 = outer_dirt.addOrReplaceChild("dirt_r1", CubeListBuilder.create().texOffs(23, 22).addBox(-2.0F, -1.0F, -7.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.5F, 0.0F, 1.5708F, 0.0F));

		PartDefinition dirt_r2 = outer_dirt.addOrReplaceChild("dirt_r2", CubeListBuilder.create().texOffs(23, 22).addBox(-2.0F, -1.0F, -7.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -5.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition dirt_r3 = outer_dirt.addOrReplaceChild("dirt_r3", CubeListBuilder.create().texOffs(23, 22).addBox(-2.0F, -1.0F, -7.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, -5.5F, 0.0F, -1.5708F, 0.0F));

		PartDefinition corner_outer_dirt = dirts.addOrReplaceChild("corner_outer_dirt", CubeListBuilder.create().texOffs(10, 22).addBox(-1.0F, -0.75F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 4.5F));

		PartDefinition dirt_r4 = corner_outer_dirt.addOrReplaceChild("dirt_r4", CubeListBuilder.create().texOffs(10, 22).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition dirt_r5 = corner_outer_dirt.addOrReplaceChild("dirt_r5", CubeListBuilder.create().texOffs(10, 22).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.25F, -9.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition dirt_r6 = corner_outer_dirt.addOrReplaceChild("dirt_r6", CubeListBuilder.create().texOffs(10, 22).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.5F, -0.5F, -9.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition dirt_layer = dirts.addOrReplaceChild("dirt_layer", CubeListBuilder.create().texOffs(-14, 28).addBox(-7.0F, 12.49F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.5F, 0.0F));

		PartDefinition inner_dirt = partdefinition.addOrReplaceChild("inner_dirt", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition dirt_r7 = inner_dirt.addOrReplaceChild("dirt_r7", CubeListBuilder.create().texOffs(1, 22).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7925F, 1.0472F, 3.1416F));

		PartDefinition dirt_r8 = inner_dirt.addOrReplaceChild("dirt_r8", CubeListBuilder.create().texOffs(1, 22).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition dirt_r9 = inner_dirt.addOrReplaceChild("dirt_r9", CubeListBuilder.create().texOffs(1, 22).addBox(-1.0F, -1.0F, 0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7925F, -1.0472F, 3.1416F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(PotatoMine entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.inActive, PotatoMineAnimations.INACTIVE, ageInTicks);
		this.animate(entity.arming, PotatoMineAnimations.ARMING, ageInTicks);
		this.animate(entity.armed, PotatoMineAnimations.ARMED, ageInTicks);
		this.animate(entity.bulbBlink, PotatoMineAnimations.BULB_BLINK, ageInTicks);
		this.animate(entity.explode, PotatoMineAnimations.EXPLODE, ageInTicks);

		head.yRot = (float) Math.toRadians(netHeadYaw);
    }
}