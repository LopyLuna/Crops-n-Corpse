package uwu.llkc.cnc.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.projectiles.ZpgProjectile;

public class ZpgModel extends EntityModel<ZpgProjectile> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CNCMod.rl("zpg"), "main");
    private final ModelPart ZPG;

    public ZpgModel(ModelPart root) {
        this.ZPG = root.getChild("ZPG");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ZPG = partdefinition.addOrReplaceChild("ZPG", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -14.0F, -2.5F, 5.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(15, 0).addBox(2.5F, -3.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition fin_r1 = ZPG.addOrReplaceChild("fin_r1", CubeListBuilder.create().texOffs(15, 0).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, -4.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition fin_r2 = ZPG.addOrReplaceChild("fin_r2", CubeListBuilder.create().texOffs(15, 0).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -1.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition fin_r3 = ZPG.addOrReplaceChild("fin_r3", CubeListBuilder.create().texOffs(15, 0).addBox(-1.5F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 4.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(ZpgProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        ZPG.render(poseStack, buffer, packedLight, packedOverlay);

    }
}
