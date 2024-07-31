package uwu.llkc.cnc.client.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.projectiles.PeaProjectile;

public class PeaProjectileRenderer extends EntityRenderer<PeaProjectile> {
    private static final ResourceLocation PEA_TEXTURE = CNCMod.rl("textures/item/raw_pea.png");
    private static final ResourceLocation COOKED_PEA_TEXTURE = CNCMod.rl("textures/item/cooked_pea.png");
    private static final ResourceLocation FROZEN_PEA_TEXTURE = CNCMod.rl    ("textures/item/frozen_pea.png");
    private static final RenderType RENDER_TYPE_PEA = RenderType.entityCutoutNoCull(PEA_TEXTURE);
    private static final RenderType RENDER_TYPE_COOKED_PEA = RenderType.entityCutoutNoCull(COOKED_PEA_TEXTURE);
    private static final RenderType RENDER_TYPE_FROZEN_PEA = RenderType.entityCutoutNoCull(FROZEN_PEA_TEXTURE);

    public PeaProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(PeaProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(.5F, .5F, .5F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        PoseStack.Pose posestack$pose = poseStack.last();
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE_PEA);
        vertex(vertexconsumer, posestack$pose, packedLight, 0.0F, 0, 0, 1);
        vertex(vertexconsumer, posestack$pose, packedLight, 1.0F, 0, 1, 1);
        vertex(vertexconsumer, posestack$pose, packedLight, 1.0F, 1, 1, 0);
        vertex(vertexconsumer, posestack$pose, packedLight, 0.0F, 1, 0, 0);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, int y, int u, int v) {
        consumer.addVertex(pose, x - 0.5F, (float)y - 0.25F, 0.0F)
                .setColor(-1)
                .setUv((float)u, (float)v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PeaProjectile entity) {
        return PEA_TEXTURE;
    }
}
