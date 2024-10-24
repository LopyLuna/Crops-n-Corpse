package uwu.llkc.cnc.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MilkshakeItem extends Item {
    public MilkshakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack result = super.finishUsingItem(stack, level, livingEntity);
        boolean success = result.item instanceof MilkshakeItem && result.getCount() == 0;
        if (success) {
            var slot = livingEntity.getEquipmentSlotForItem(result);
            result.setCount(1);
            livingEntity.setItemSlot(slot, result.hurtAndConvertOnBreak(1, Items.GLASS_BOTTLE, livingEntity, slot));
        }
        return result;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return super.getDrinkingSound();
    }

    @Override
    public SoundEvent getBreakingSound() {
        return SoundEvents.PLAYER_BURP;
    }
}
