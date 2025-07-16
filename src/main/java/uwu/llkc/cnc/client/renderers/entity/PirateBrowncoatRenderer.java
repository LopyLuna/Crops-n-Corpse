package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.PirateBrowncoatModel;
import uwu.llkc.cnc.common.entities.zombies.PirateBrowncoat;

public class PirateBrowncoatRenderer extends HumanoidMobRenderer<PirateBrowncoat, PirateBrowncoatModel<PirateBrowncoat>> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/pirate_browncoat.png");

    public PirateBrowncoatRenderer(EntityRendererProvider.Context context) {
        super(context, new PirateBrowncoatModel<>(context.bakeLayer(PirateBrowncoatModel.MAIN_LAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(PirateBrowncoat pirateBrowncoat) {
        return TEXTURE;
    }
}
