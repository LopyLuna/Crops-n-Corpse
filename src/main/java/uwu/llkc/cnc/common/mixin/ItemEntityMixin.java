package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.llkc.cnc.common.init.ItemRegistry;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @WrapOperation(method = "playerTouch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;add(Lnet/minecraft/world/item/ItemStack;)Z"))
    private boolean cnc$addItem(Inventory instance, ItemStack stack, Operation<Boolean> original, Player player) {
        if (stack.is(ItemRegistry.SUN.asItem())) {
            for (ItemStack item : player.getHandSlots()) {
                if (item.is(ItemRegistry.SUN_WAND.get())) {
                    var capability = item.getCapability(Capabilities.ItemHandler.ITEM);
                    if (capability == null) return original.call(instance, stack);
                    var count = capability.getSlotLimit(0) - capability.getStackInSlot(0).getCount();
                    if (count >= stack.getCount()) {
                        var itemCount = stack.getCount();
                        stack.setCount(0);
                        capability.insertItem(0, new ItemStack(ItemRegistry.SUN.get(), itemCount), false);
                    } else {
                        var decrease = stack.getCount() - count;
                        stack.setCount(stack.getCount() - decrease);
                        capability.insertItem(0, new ItemStack(ItemRegistry.SUN.get(), count), false);
                    }
                    return true;
                }
            }
        }
        return original.call(instance, stack);
    }

    @WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;noCollision(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Z"))
    private boolean cnc$onTick(Level instance, Entity entity, AABB aabb, Operation<Boolean> original) {
        return true;
    }
}
