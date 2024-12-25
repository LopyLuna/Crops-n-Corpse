package uwu.llkc.cnc.client.renderers.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.entity.FootSoldierModel;
import uwu.llkc.cnc.common.entities.zombies.FootSoldier;

public class FootSoldierRenderer extends HumanoidMobRenderer<FootSoldier, FootSoldierModel> {
    public static final ResourceLocation TEXTURE = CNCMod.rl("textures/entity/foot_soldier.png");

    public FootSoldierRenderer(EntityRendererProvider.Context context) {
        super(context, new FootSoldierModel(context.bakeLayer(FootSoldierModel.MAIN_LAYER)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull FootSoldier footSoldier) {
        return TEXTURE;
    }
}
