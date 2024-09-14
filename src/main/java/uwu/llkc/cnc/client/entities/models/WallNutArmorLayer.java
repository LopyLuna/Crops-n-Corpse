package uwu.llkc.cnc.client.entities.models;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.renderers.WallNutRenderer;
import uwu.llkc.cnc.common.entities.plants.WallNut;

public class WallNutArmorLayer extends RenderLayer<WallNut, WallNutModel> {
    public static final ModelLayerLocation ARMOR = new ModelLayerLocation(CNCMod.rl("wallnut"), "armor");

    private final ModelPart root;
    private final RenderType type;

    public WallNutArmorLayer(RenderLayerParent<WallNut, WallNutModel> renderer, ModelPart root) {
        super(renderer);
        this.root = root;
        type = RenderType.entityCutoutNoCull(WallNutRenderer.ARMOR);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, WallNut livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        var vertexConsumer = bufferSource.getBuffer(type);
        if (livingEntity.getEntityData().get(WallNut.HAS_ARMOR)) {
            root.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    public static LayerDefinition createArmor() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition Armor = partdefinition.addOrReplaceChild("Armor", CubeListBuilder.create().texOffs(0, 32).addBox(-7.5F, 6, -6.5F, 15.0F, 18.0F, 13.0F, new CubeDeformation(0.5F))
                .texOffs(0, 0).addBox(-7.5F, 6, -6.5F, 15.0F, 18.0F, 13.0F, new CubeDeformation(1.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    protected ResourceLocation getTextureLocation(WallNut entity) {
        return WallNutRenderer.ARMOR;
    }
}
