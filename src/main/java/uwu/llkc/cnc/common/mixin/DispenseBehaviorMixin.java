package uwu.llkc.cnc.common.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/core/dispenser/DispenseItemBehavior$7")
public abstract class DispenseBehaviorMixin extends DefaultDispenseItemBehavior {
    @Inject(method = "execute", at = @At("HEAD"), cancellable = true)
    private void cnc$lambda$static$0(BlockSource source, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
        if (stack.is(Items.BUCKET) && stack.has(DataComponents.DAMAGE) && stack.get(DataComponents.DAMAGE) > 0) {
            ci.setReturnValue(super.execute(source, stack));
        }
    }
}
