package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

import java.util.Map;

@Mixin(LivingEntityRenderer.class)
@Debug(export = true)
public class LivingEntityRendererMixin {
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
}
