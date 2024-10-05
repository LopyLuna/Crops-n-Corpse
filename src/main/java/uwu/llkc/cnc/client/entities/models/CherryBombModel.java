package uwu.llkc.cnc.client.entities.models;


import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.CherryBomb;

public class CherryBombModel extends HierarchicalModel<CherryBomb> {
	public static final ModelLayerLocation MAIN_LAYER = new ModelLayerLocation(CNCMod.rl("cherry_bomb"), "main");
	private final ModelPart root;
	private final ModelPart headLeft;
	private final ModelPart headRight;

	public CherryBombModel(ModelPart root) {
		this.root = root.getChild("root");
		this.headLeft = this.root.getChild("head_left");
		this.headRight = this.root.getChild("head_right");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head_right = root.addOrReplaceChild("head_right", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -3.5F, 0.0F));

		PartDefinition head_left = root.addOrReplaceChild("head_left", CubeListBuilder.create().texOffs(0, 15).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(4.0F, -3.5F, 0.0F));

		PartDefinition stem_right = root.addOrReplaceChild("stem_right", CubeListBuilder.create().texOffs(29, 0).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -7.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

		PartDefinition leaf = stem_right.addOrReplaceChild("leaf", CubeListBuilder.create().texOffs(22, 15).addBox(0.0F, 0.0F, -3.0F, 6.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(39, 14).addBox(0.0F, 0.0F, 1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.01F, 0.0F));

		PartDefinition stem_left = root.addOrReplaceChild("stem_left", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(4.0F, -7.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(CherryBomb entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

	}

	@Override
	public @NotNull ModelPart root() {
		return root;
	}
}