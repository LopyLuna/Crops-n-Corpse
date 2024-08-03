package uwu.llkc.cnc.client.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.PeashooterModel;
import uwu.llkc.cnc.client.entities.models.SunflowerModel;
import uwu.llkc.cnc.common.entities.plants.Peashooter;
import uwu.llkc.cnc.common.entities.plants.Sunflower;

public class SunflowerRenderer extends MobRenderer<Sunflower, SunflowerModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/sunflower.png");

    public SunflowerRenderer(EntityRendererProvider.Context context) {
        super(context, new SunflowerModel(context.bakeLayer(SunflowerModel.MAIN_LAYER)), 0);
    }

    @Override
    protected void scale(Sunflower livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }

    @Override
    protected float getFlipDegrees(Sunflower livingEntity) {
        return 0f;
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Sunflower sunflower) {
        return TEXTURE;
    }
}
