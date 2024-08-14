package uwu.llkc.cnc.common.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import uwu.llkc.cnc.common.init.BlockRegistry;

public class TrafficConeItem extends BlockItem implements Equipable {
    public TrafficConeItem(Properties properties) {
        super(BlockRegistry.TRAFFIC_CONE.get(), properties);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return Math.max(super.getMaxDamage(stack), 30);
    }
}
