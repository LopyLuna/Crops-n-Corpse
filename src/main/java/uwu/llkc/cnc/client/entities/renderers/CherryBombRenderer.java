package uwu.llkc.cnc.client.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.CherryBombModel;
import uwu.llkc.cnc.common.entities.plants.CherryBomb;

public class CherryBombRenderer extends MobRenderer<CherryBomb, CherryBombModel> {
    public static final ResourceLocation NORMAL = CNCMod.rl("textures/entity/cherry_bomb.png");
    public static final ResourceLocation SLEEPING = CNCMod.rl("textures/entity/cherry_bomb_sleeping.png");

    public CherryBombRenderer(EntityRendererProvider.Context context) {
        super(context, new CherryBombModel(context.bakeLayer(CherryBombModel.MAIN_LAYER)), 0);
    }

    @Override
    protected float getFlipDegrees(CherryBomb livingEntity) {
        return 0f;
    }

    @Override
    protected void scale(CherryBomb livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(1.1f, 1.1f, 1.1f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CherryBomb cherryBomb) {
        return cherryBomb.getEntityData().get(CherryBomb.SLEEPING) ? SLEEPING : NORMAL;
    }
}
