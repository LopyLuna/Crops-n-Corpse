package uwu.llkc.cnc.client.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

public class FreezeLayer implements LayeredDraw.Layer {
    public static final ResourceLocation FREEZE_LAYER = CNCMod.rl("freeze_layer");
    private static final ResourceLocation CHILL_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/nausea.png");
    private static final ResourceLocation FROZEN_LOCATION = ResourceLocation.withDefaultNamespace("textures/misc/white.png");


    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (Minecraft.getInstance().player == null) return;
        if (Minecraft.getInstance().level == null) return;

        boolean frozen = Minecraft.getInstance().player.getData(AttachmentTypeRegistry.FROZEN);
        boolean chilled = Minecraft.getInstance().player.getData(AttachmentTypeRegistry.CHILLED);

        if (!frozen && !chilled) {
            return;
        }

        int duration = Minecraft.getInstance().player.getData(AttachmentTypeRegistry.CHILL_DURATION);
        if (duration == 0) return;

        int time = Minecraft.getInstance().player.getData(AttachmentTypeRegistry.CHILL_TIME);

        if (time == 0) return;
        int strength = Minecraft.getInstance().player.getData(AttachmentTypeRegistry.CHILL_STRENGTH);
        double freezePercentage = 1 - Math.min(0.99f, (strength == 0 ? 0 : (15 + strength * 5)) / 100d);
        double freezeDuration = duration * freezePercentage;
        float percentagePercentage = Math.max(0, (float) ((time - freezeDuration) / (duration - freezeDuration)));
        float freezeTime = (float) (freezePercentage * duration);

        float intensity = frozen ? Mth.lerp(percentagePercentage, 1, 0) :
                Mth.lerp(time / freezeTime, 1, 0);

        float scalar = frozen ? 0 : intensity;

        int i = guiGraphics.guiWidth();
        int j = guiGraphics.guiHeight();
        guiGraphics.pose().pushPose();
        float f = Mth.lerp(scalar, 1.0F, 2.0F);
        guiGraphics.pose().translate((float) i / 2.0F, (float) j / 2.0F, 0.0F);
        guiGraphics.pose().scale(f, f, f);
        guiGraphics.pose().translate((float) (-i) / 2.0F, (float) (-j) / 2.0F, 0.0F);
        float f1 = 0.012f * (1 - intensity);
        float f2 = 0.345f * (1 - intensity);
        float f3 = 0.557f * (1 - intensity);
        float f4 = 0.012f * (frozen ? intensity : (1 - intensity));
        float f5 = 0.345f * (frozen ? intensity : (1 - intensity));
        float f6 = 0.557f * (frozen ? intensity : (1 - intensity));
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE
        );
        if (frozen) {
            guiGraphics.setColor(f1, f2, f3, intensity);
            guiGraphics.blit(FROZEN_LOCATION, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);
        }
        guiGraphics.setColor(f4, f5, f6, 1 - intensity);
        guiGraphics.blit(CHILL_LOCATION, 0, 0, -90, 0.0F, 0.0F, i, j, i, j);

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.pose().popPose();
    }
}
