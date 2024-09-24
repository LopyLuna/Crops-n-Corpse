package uwu.llkc.cnc.client.entities.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.BrowncoatModel;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;

public class BrowncoatRenderer extends MobRenderer<Browncoat, BrowncoatModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/browncoats/browncoat.png");

    public BrowncoatRenderer(EntityRendererProvider.Context context) {
        super(context, new BrowncoatModel(context.bakeLayer(BrowncoatModel.MAIN_LAYER)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), 1, 1, 1, context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Browncoat browncoat) {
        return TEXTURE;
    }
}
