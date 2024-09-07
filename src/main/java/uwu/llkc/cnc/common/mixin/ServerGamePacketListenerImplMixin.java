package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uwu.llkc.cnc.common.util.ChunkMixinHelper;

@Mixin(ServerGamePacketListenerImpl.class)
@Debug(export = true)
public class ServerGamePacketListenerImplMixin {
    @Inject(method = "handleUseItemOn()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;useItemOn(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"))
    private void cnc$startUseItem(CallbackInfo ci, @Local InteractionResult result, @Local BlockHitResult blockHitResult, @Local ServerLevel level) {
        if (result.consumesAction()) {
            var state = ((ChunkMixinHelper) level.getChunk(blockHitResult.getBlockPos())).getBlockStateForDelayedPlace();
            level.setBlockAndUpdate(blockHitResult.getBlockPos(), state);
        }
    }
}
