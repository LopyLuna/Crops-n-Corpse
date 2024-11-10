package uwu.llkc.cnc.client.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.RepeaterModel;
import uwu.llkc.cnc.common.entities.plants.Repeater;

public class RepeaterRenderer extends MobRenderer<Repeater, RepeaterModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/repeater.png");

    public RepeaterRenderer(EntityRendererProvider.Context context) {
        super(context, new RepeaterModel(context.bakeLayer(RepeaterModel.MAIN_LAYER)), 0);
    }

    @Override
    protected void scale(Repeater livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }

    @Override
    protected float getFlipDegrees(Repeater livingEntity) {
        return 0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Repeater peashooter) {
        return TEXTURE;
    }
}
