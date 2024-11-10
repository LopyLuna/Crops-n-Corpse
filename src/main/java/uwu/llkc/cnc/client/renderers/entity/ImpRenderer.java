package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.ImpModel;
import uwu.llkc.cnc.common.entities.zombies.Imp;

public class ImpRenderer extends MobRenderer<Imp, ImpModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/imp.png");

    public ImpRenderer(EntityRendererProvider.Context context) {
        super(context, new ImpModel(context.bakeLayer(ImpModel.MAIN_LAYER)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Imp imp) {
        return TEXTURE;
    }
}
