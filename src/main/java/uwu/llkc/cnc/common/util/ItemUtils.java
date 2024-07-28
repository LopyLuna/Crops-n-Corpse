package uwu.llkc.cnc.common.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtils {
    public static boolean tryTakeItems(Player player, ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack item : player.getInventory().items) {
            if (ItemStack.isSameItem(item, stack)) {
                stacks.add(item);
            }
        }
        if (stacks.stream().mapToInt(ItemStack::getCount).sum() >= stack.getCount()) {
            int count = stack.getCount();
            int iteration = 0;
            while (count > 0) {
                count -= stacks.get(iteration).getCount();
                player.getInventory().removeItem(player.getInventory().findSlotMatchingItem(stacks.get(iteration)), Math.min(stack.getCount(), stacks.get(iteration).getCount()));
                iteration++;
            }
            return true;
        }
        return false;
    }
}
