package uwu.llkc.cnc.client.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.WallNutArmorLayer;
import uwu.llkc.cnc.client.entities.models.WallNutModel;
import uwu.llkc.cnc.common.entities.plants.WallNut;

public class WallNutRenderer extends MobRenderer<WallNut, WallNutModel> {
    public static final ResourceLocation STAGE_0 = CNCMod.rl("textures/entity/wallnut_stage0.png");
    public static final ResourceLocation STAGE_1 = CNCMod.rl("textures/entity/wallnut_stage1.png");
    public static final ResourceLocation STAGE_2 = CNCMod.rl("textures/entity/peashooter.png");
    public static final ResourceLocation STAGE_3 = CNCMod.rl("textures/entity/peashooter.png");
    public static final ResourceLocation ARMOR = CNCMod.rl("textures/entity/wallnut_armor.png");

    public WallNutRenderer(EntityRendererProvider.Context context) {
        super(context, new WallNutModel(context.bakeLayer(WallNutModel.MAIN_LAYER)), 0);
        addLayer(new WallNutArmorLayer(this, context.bakeLayer(WallNutArmorLayer.ARMOR)));
    }

    @Override
    protected void scale(WallNut livingEntity, PoseStack poseStack, float partialTickTime) {
        super.scale(livingEntity, poseStack, partialTickTime);
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }

    @Override
    protected float getFlipDegrees(WallNut livingEntity) {
        return 0f;
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull WallNut nut) {
        return switch (nut.getEntityData().get(WallNut.STAGE)) {
            case 0 -> STAGE_0;
            case 1 -> STAGE_1;
            case 2 -> STAGE_2;
            case 3 -> STAGE_3;
            default -> throw new IllegalStateException("Unexpected value: " + nut.getEntityData().get(WallNut.STAGE));
        };
    }
}
