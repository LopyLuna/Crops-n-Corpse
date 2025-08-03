package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.PirateBrowncoatModel;
import uwu.llkc.cnc.common.entities.zombies.PirateBrowncoat;

public class PirateBrowncoatRenderer extends MobRenderer<PirateBrowncoat, PirateBrowncoatModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/pirate_browncoat.png");

    public PirateBrowncoatRenderer(EntityRendererProvider.Context context) {
        super(context, new PirateBrowncoatModel(context.bakeLayer(PirateBrowncoatModel.MAIN_LAYER)), 0.5f);
        addLayer(new CustomHeadLayer<>(this, context.getModelSet(), 1, 1, 1, context.getItemInHandRenderer()));
        addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(PirateBrowncoat pirateBrowncoat) {
        return TEXTURE;
    }
}
