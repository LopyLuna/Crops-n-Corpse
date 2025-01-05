package uwu.llkc.cnc.common.items;

import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

public class SunWandItem extends Item {
    public SunWandItem(Properties properties) {
        super(properties);
    }


    public static class ItemHandler implements IItemHandler {
        private final ItemStack itemStack;

        public ItemHandler(ItemStack itemStack) {
            this.itemStack = itemStack;
        }

        public int getCount() {
            var count = itemStack.get(DataComponentRegistry.SUN_WAND_SUNS.get());
            if (count == null) return 0;
            return count;
        }

        public void setCount(int count) {
            itemStack.set(DataComponentRegistry.SUN_WAND_SUNS.get(), count);
        }

        @Override
        public int getSlots() {
            return Mth.ceil(getCount() / 64f);
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return new ItemStack(ItemRegistry.SUN.get(), getCount());
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return null;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (amount <= 0) return ItemStack.EMPTY;
            var stack = new ItemStack(ItemRegistry.SUN.get(), Math.min(amount, getCount()));
            setCount(Math.max(0, getCount() - amount));
            return stack;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1024;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(ItemRegistry.SUN.get()) && getCount() + stack.getCount() <= 1024;
        }
    }
}
