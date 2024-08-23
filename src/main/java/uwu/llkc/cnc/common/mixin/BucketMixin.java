package uwu.llkc.cnc.common.mixin;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BucketItem.class)
public abstract class BucketMixin extends Item implements Equipable {
    public BucketMixin(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        return super.canEquip(stack, armorType, entity) && stack.getItem() == Items.BUCKET;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
