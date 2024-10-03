package uwu.llkc.cnc.client.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.PotatoMineModel;
import uwu.llkc.cnc.common.entities.plants.PotatoMine;

public class PotatoMineRenderer extends MobRenderer<PotatoMine, PotatoMineModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/potato_mine.png");

    public PotatoMineRenderer(EntityRendererProvider.Context context) {
        super(context, new PotatoMineModel(context.bakeLayer(PotatoMineModel.MAIN_LAYER)), 0);
    }

    @Override
    protected float getFlipDegrees(PotatoMine livingEntity) {
        return 0f;
    }

    @Override
    protected void scale(PotatoMine livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull PotatoMine nut) {
        return TEXTURE;
    }
}
