package uwu.llkc.cnc.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.List;

public class SunWandItem extends Item {
    private static final float ITEM_SPEED = .4f;
    private static final int RANGE = 15;

    public SunWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return Integer.MAX_VALUE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (player.isShiftKeyDown()) return super.use(level, player, usedHand);
        player.startUsingItem(usedHand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(usedHand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        var entities = level.getEntities(livingEntity, AABB.ofSize(livingEntity.position(), RANGE, RANGE, RANGE), entity -> entity instanceof ItemEntity item && item.getItem().is(ItemRegistry.SUN));
        for (Entity entity : entities) {
            if (entity instanceof ItemEntity item) {
                item.setData(AttachmentTypeRegistry.ITEM_PULLED, true);
                var direction = livingEntity.getEyePosition().add(livingEntity.position()).scale(0.5).subtract(entity.position()).normalize();
                entity.setDeltaMovement(direction.x * ITEM_SPEED, direction.y * ITEM_SPEED, direction.z * ITEM_SPEED);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
        var entities = level.getEntities(livingEntity, AABB.ofSize(livingEntity.position(), RANGE, RANGE, RANGE), entity -> entity instanceof ItemEntity item && item.getItem().is(ItemRegistry.SUN));
        for (Entity entity : entities) {
            if (entity instanceof ItemEntity item) {
                item.setData(AttachmentTypeRegistry.ITEM_PULLED, false);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.cnc.sun_wand.sun", stack.get(DataComponentRegistry.SUN_WAND_SUNS.get()) != null ? stack.get(DataComponentRegistry.SUN_WAND_SUNS.get()) : 0).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        if (action == ClickAction.SECONDARY) {
            if (slot.getItem().isEmpty()) {
                var capability = stack.getCapability(Capabilities.ItemHandler.ITEM);
                if (capability == null) return false;
                var count = capability.getStackInSlot(0).getCount();
                if (count > 0) {
                    slot.set(capability.extractItem(0, Math.min(count, 64), false));
                    return true;
                }
            } else if (slot.getItem().is(ItemRegistry.SUN.get())) {
                var capability = stack.getCapability(Capabilities.ItemHandler.ITEM);
                if (capability == null) return false;
                var count = capability.getSlotLimit(0) - capability.getStackInSlot(0).getCount();
                if (count >= slot.getItem().getCount()) {
                    capability.insertItem(0, slot.getItem(), false);
                    slot.getItem().setCount(0);
                    return true;
                } else if (count < slot.getItem().getCount()) {
                    var decrease = slot.getItem().getCount() - count;
                    capability.insertItem(0, new ItemStack(ItemRegistry.SUN.get(), decrease), false);
                    slot.getItem().setCount(slot.getItem().getCount() - decrease);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
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
            if (isItemValid(slot, stack)) {
                setCount(getCount() + stack.getCount());
                return ItemStack.EMPTY;
            }
            return stack;
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
