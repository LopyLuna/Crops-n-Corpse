package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.MummifiedBrowncoatModel;
import uwu.llkc.cnc.client.models.entity.SandstormLayer;
import uwu.llkc.cnc.common.entities.zombies.MummifiedBrowncoat;

public class MummifiedBrowncoatRenderer extends HumanoidMobRenderer<MummifiedBrowncoat, MummifiedBrowncoatModel<MummifiedBrowncoat>> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/mummified_browncoat.png");

    public MummifiedBrowncoatRenderer(EntityRendererProvider.Context context) {
        super(context, new MummifiedBrowncoatModel<>(context.bakeLayer(MummifiedBrowncoatModel.MAIN_LAYER)), 0.5f);
        addLayer(new SandstormLayer<>(context, this, (browncoat) -> browncoat.getEntityData().get(MummifiedBrowncoat.HAS_SANDSTORM)));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull MummifiedBrowncoat browncoat) {
        return TEXTURE;
    }
}
