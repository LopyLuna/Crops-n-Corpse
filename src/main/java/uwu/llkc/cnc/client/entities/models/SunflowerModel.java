package uwu.llkc.cnc.client.entities.models;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.animations.SunflowerAnimations;
import uwu.llkc.cnc.common.entities.plants.Sunflower;

public class SunflowerModel extends HierarchicalModel<Sunflower> {
	public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("sunflower"), "main");

	private final ModelPart root;
	private final ModelPart rootStem;
	private final ModelPart rootHead;

	public SunflowerModel(ModelPart root) {
		this.root = root.getChild("root");
		this.rootStem = this.root.getChild("rootStem");
		this.rootHead = rootStem.getChild("rootHead");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create().texOffs(17, 11).addBox(-2.52F, -1.5F, -2.51F, 5.02F, 2.0F, 5.02F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition rootStem = root.addOrReplaceChild("rootStem", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Stem = rootStem.addOrReplaceChild("Stem", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Stem_r1 = Stem.addOrReplaceChild("Stem_r1", CubeListBuilder.create().texOffs(20, 21).addBox(-1.0F, -10.5F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition StemLeafLeft = Stem.addOrReplaceChild("StemLeafLeft", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -3.0F, 0.5F, 0.0F, 0.0F, -0.3927F));

		PartDefinition StemLeafRight = Stem.addOrReplaceChild("StemLeafRight", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, 0.5F, 0.0F, 0.0F, 0.3927F));

		PartDefinition rootHead = rootStem.addOrReplaceChild("rootHead", CubeListBuilder.create(), PartPose.offset(-0.5F, -10.0F, 1.15F));

		PartDefinition Head = rootHead.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 28).addBox(-3.0F, -3.0F, -3.0F, 7.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0, 0, 0));

		PartDefinition Petals = Head.addOrReplaceChild("Petals", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, -1.65F));

		PartDefinition PetalTop = Petals.addOrReplaceChild("PetalTop", CubeListBuilder.create().texOffs(32, 21).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.5F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition PetalTopRight = Petals.addOrReplaceChild("PetalTopRight", CubeListBuilder.create().texOffs(32, 21).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8971F, -2.25F, 0.0F, 0.1745F, 0.0F, -1.0472F));

		PartDefinition PetalBottomRight = Petals.addOrReplaceChild("PetalBottomRight", CubeListBuilder.create().texOffs(32, 21).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8971F, 2.25F, 0.0F, 0.1745F, 0.0F, -2.0944F));

		PartDefinition PetalBottom = Petals.addOrReplaceChild("PetalBottom", CubeListBuilder.create().texOffs(32, 21).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.5F, 0.0F, 0.1745F, 0.0F, -3.1416F));

		PartDefinition PetalBottomLeft = Petals.addOrReplaceChild("PetalBottomLeft", CubeListBuilder.create().texOffs(32, 21).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8971F, 2.25F, 0.0F, 0.1745F, 0.0F, 2.0944F));

		PartDefinition PetalTopLeft = Petals.addOrReplaceChild("PetalTopLeft", CubeListBuilder.create().texOffs(32, 21).addBox(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8971F, -2.25F, 0.0F, 0.1745F, 0.0F, 1.0472F));

		PartDefinition Leaves = root.addOrReplaceChild("Leaves", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition LeafEast = Leaves.addOrReplaceChild("LeafEast", CubeListBuilder.create().texOffs(0, 16).addBox(-6.0F, -1.0F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition LeafSouth = Leaves.addOrReplaceChild("LeafSouth", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5F, -1.0F, 0.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, 1.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition LeafNorth = Leaves.addOrReplaceChild("LeafNorth", CubeListBuilder.create().texOffs(0, 8).addBox(-2.5F, -1.0F, -6.0F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -1.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition LeafWest = Leaves.addOrReplaceChild("LeafWest", CubeListBuilder.create().texOffs(17, 3).addBox(0.0F, -1.0F, -2.5F, 6.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.5F, 0.0F, 0.0F, 0.0F, -0.2618F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public ModelPart root() {
		return root;
	}

	@Override
	public void setupAnim(Sunflower entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(entity.produce, SunflowerAnimations.PRODUCTION, ageInTicks);
		this.animate(entity.idle, SunflowerAnimations.IDLE, ageInTicks);
		this.animate(entity.die, SunflowerAnimations.DEATH, ageInTicks);

		rootStem.yRot = (float) Math.toRadians(netHeadYaw);
		rootHead.xRot = ((float) Math.toRadians(headPitch));
	}
}