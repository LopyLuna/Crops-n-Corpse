package uwu.llkc.cnc.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.ZpgModel;
import uwu.llkc.cnc.common.entities.projectiles.ZpgProjectile;

public class ZpgRenderer extends EntityRenderer<ZpgProjectile> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/zpg.png");
    private final ZpgModel model;

    public ZpgRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new ZpgModel(context.bakeLayer(ZpgModel.LAYER_LOCATION));
    }

    @Override
    public void render(ZpgProjectile entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Vec3 motion = entity.getDeltaMovement().normalize();
        Vec3 up = new Vec3(0, 1, 0);
        if (!motion.equals(Vec3.ZERO)) {
            poseStack.mulPose(up.toVector3f().rotationTo(motion.toVector3f(), new Quaternionf()).rotateX(Mth.PI));
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY);
    }

    @Override
    public ResourceLocation getTextureLocation(ZpgProjectile entity) {
        return TEXTURE;
    }
}
