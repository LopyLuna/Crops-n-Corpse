package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.util.ColoredBufferSource;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

import java.util.Map;

@Mixin(LivingEntityRenderer.class)
@Debug(export = true)
public abstract class LivingEntityRendererMixin {

    @WrapWithCondition(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V"))
    private <T extends Entity> boolean onlySetupIfNotFrozen(EntityModel<T> instance, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof LivingEntity livingEntity) {
            var result = !livingEntity.getData(AttachmentTypeRegistry.FROZEN.get());
            if (!result) {
                Map<ModelPart, PartPose> parts = entity.getData(AttachmentTypeRegistry.MODEL_PARTS);
                if (parts.isEmpty()) return true;
                parts.forEach(ModelPart::loadPose);
                return false;
            }
        }
        return true;
    }


    @ModifyVariable(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), argsOnly = true)
    private <T extends LivingEntity> MultiBufferSource cnc$render(MultiBufferSource buffer, @Local(argsOnly = true) T entity) {
        if (entity.getData(AttachmentTypeRegistry.CHILLED.get())) {
            return new ColoredBufferSource(buffer, 54, 139, 193, 255);
        }
        return buffer;
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"))
    private void cnc$render(LivingEntity entity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (entity.getData(AttachmentTypeRegistry.FROZEN.get())) {
            poseStack.pushPose();
            poseStack.translate(-0.5, 0, -0.5);
            var model = Minecraft.getInstance().getModelManager().getModel(CNCMod.CHILL_CRYSTAL);
            for (RenderType renderType : model.getRenderTypes(Blocks.AIR.defaultBlockState(), entity.getRandom(), ModelData.EMPTY)) {
                VertexConsumer vertexConsumer = multiBufferSource.getBuffer(renderType);
                Minecraft.getInstance().getItemRenderer().renderModelLists(model, ItemStack.EMPTY, i, LivingEntityRenderer.getOverlayCoords(entity, OverlayTexture.NO_OVERLAY), poseStack, vertexConsumer);
            }
            poseStack.popPose();
        }
    }
}
