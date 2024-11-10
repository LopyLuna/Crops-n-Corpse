package uwu.llkc.cnc.client.renderers.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.SnowPeaModel;
import uwu.llkc.cnc.common.entities.plants.SnowPea;

public class SnowPeaRenderer extends MobRenderer<SnowPea, SnowPeaModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/snow_pea.png");

    public SnowPeaRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowPeaModel(context.bakeLayer(SnowPeaModel.MAIN_LAYER)), 0);
    }

    @Override
    protected void scale(SnowPea livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }

    @Override
    protected float getFlipDegrees(SnowPea livingEntity) {
        return 0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull SnowPea peashooter) {
        return TEXTURE;
    }
}
