package uwu.llkc.cnc.common.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;
import uwu.llkc.cnc.common.init.EffectRegistry;
import uwu.llkc.cnc.common.networking.SetChillTimePayload;
import uwu.llkc.cnc.common.networking.SetChilledPayload;
import uwu.llkc.cnc.common.networking.SetFrozenPayload;

import java.util.List;


@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    private Level level;

    @Inject(method = "onAddedToLevel", at = @At("HEAD"))
    private void cnc$onAddedToLevel(CallbackInfo ci) {
        if ((Entity) (Object) this instanceof LivingEntity living && !level.isClientSide()) {
            if (living.hasEffect(EffectRegistry.CHILL)) {
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(living,
                        new SetChilledPayload(living.getId(), true, living.getData(AttachmentTypeRegistry.CHILL_DURATION), living.getEffect(EffectRegistry.CHILL).getAmplifier()),
                        new SetChillTimePayload(living.getId(), living.getEffect(EffectRegistry.CHILL).getDuration())
                );
            }
            PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, living.chunkPosition(), new SetFrozenPayload(living.getId(), living.getData(AttachmentTypeRegistry.FROZEN)));
        }
    }

    @Inject(method = "collectColliders", at = @At("RETURN"), cancellable = true)
    private static void cnc$collectColliders(Entity entity, Level level, List<VoxelShape> collisions, AABB boundingBox, CallbackInfoReturnable<List<VoxelShape>> cir) {
        if (entity instanceof ItemEntity item && item.getData(AttachmentTypeRegistry.ITEM_PULLED)) {
            cir.setReturnValue(List.of());
            cir.cancel();
        }
    }

    @Inject(method = "isFree(Lnet/minecraft/world/phys/AABB;)Z", at = @At("RETURN"), cancellable = true)
    private void cnc$isFree(AABB box, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof ItemEntity item && item.getData(AttachmentTypeRegistry.ITEM_PULLED)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
