package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.FootSoldierModel;
import uwu.llkc.cnc.common.entities.zombies.FootSoldier;

public class FootSoldierRenderer extends MobRenderer<FootSoldier, FootSoldierModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/foot_soldier.png");

    public FootSoldierRenderer(EntityRendererProvider.Context context) {
        super(context, new FootSoldierModel(context.bakeLayer(FootSoldierModel.MAIN_LAYER)), 0.5f);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), 1, 1, 1, context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FootSoldier footSoldier) {
        return TEXTURE;
    }
}
