package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.BrowncoatModel;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;

public class BrowncoatRenderer extends HumanoidMobRenderer<Browncoat, BrowncoatModel<Browncoat>> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/browncoat.png");

    public BrowncoatRenderer(EntityRendererProvider.Context context) {
        super(context, new BrowncoatModel<>(context.bakeLayer(BrowncoatModel.MAIN_LAYER)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Browncoat browncoat) {
        return TEXTURE;
    }
}
