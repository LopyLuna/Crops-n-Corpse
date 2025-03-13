package uwu.llkc.cnc.client.models.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AbstractZombieModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Monster;

import java.util.function.Predicate;

public class SandstormLayer<E extends Monster> extends RenderLayer<E, AbstractZombieModel<E>> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/breeze/breeze_wind.png");
    private final WindModel<E> model;
    private final Predicate<E> shouldRender;

    public SandstormLayer(EntityRendererProvider.Context context, RenderLayerParent<E, AbstractZombieModel<E>> renderer, Predicate<E> shouldRender) {
        super(renderer);
        this.model = new WindModel<>(context.bakeLayer(ModelLayers.BREEZE_WIND));
        this.shouldRender = shouldRender;
    }

    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            E livingEntity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        if (shouldRender.test(livingEntity)) {
            float f = (float) livingEntity.tickCount + partialTick;
            VertexConsumer vertexconsumer = bufferSource.getBuffer(RenderType.breezeWind(TEXTURE_LOCATION, this.xOffset(f) % 1.0F, 0.0F));
            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFCCA6B);
        }
    }

    private float xOffset(float tickCount) {
        return tickCount * 0.02F;
    }
}
