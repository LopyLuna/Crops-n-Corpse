package uwu.llkc.cnc.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.projectiles.FootSoldierProjectile;

public class FootSoliderProjectileRenderer extends EntityRenderer<FootSoldierProjectile> {
    private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
    private static final ResourceLocation TEXTURE = CNCMod.rl("textures/other/foot_soldier_bullet.png");
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean fullBright;

    public FootSoliderProjectileRenderer(EntityRendererProvider.Context context, float scale, boolean fullBright) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.scale = scale;
        this.fullBright = fullBright;
    }

    public FootSoliderProjectileRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    @Override
    protected int getBlockLightLevel(FootSoldierProjectile entity, BlockPos pos) {
        return this.fullBright ? 15 : super.getBlockLightLevel(entity, pos);
    }

    @Override
    public void render(FootSoldierProjectile entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25)) {
            poseStack.pushPose();
            //poseStack.scale(this.scale, this.scale, this.scale);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));


            consumer.addVertex(poseStack.last(), -.5f, -.5f, 0)
                    .setUv(8, 7).setColor(-1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(1, 0, 0);
            consumer.addVertex(poseStack.last(), .5f, -.5f, 0)
                    .setUv(7, 7).setColor(-1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(1, 0, 0);
            consumer.addVertex(poseStack.last(), .5f, .5f, 0)
                    .setUv(7, 8).setColor(-1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(1, 0, 0);
            consumer.addVertex(poseStack.last(), -.5f, .5f, 0)
                    .setUv(8, 8).setColor(-1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(1, 0, 0);

            poseStack.popPose();
            super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    @Override
    public ResourceLocation getTextureLocation(FootSoldierProjectile entity) {
        return TEXTURE;
    }
}
