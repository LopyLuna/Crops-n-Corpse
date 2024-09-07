package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uwu.llkc.cnc.common.util.ChunkMixinHelper;

@Mixin(Minecraft.class)
@Debug(export = true)
public class MinecraftMixin {
    @Inject(method = "startUseItem()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;useItemOn(Lnet/minecraft/client/player/LocalPlayer;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"))
    private void cnc$startUseItem(CallbackInfo ci, @Local(ordinal = 1) InteractionResult interactionResult, @Local BlockHitResult blockHitResult) {
        if (interactionResult.consumesAction()) {
            var state = ((ChunkMixinHelper) ((Minecraft) (Object) this).level.getChunk(blockHitResult.getBlockPos())).getBlockStateForDelayedPlace();
            ((Minecraft) (Object) this).level.setBlockAndUpdate(blockHitResult.getBlockPos(), state);
        }
    }
}
