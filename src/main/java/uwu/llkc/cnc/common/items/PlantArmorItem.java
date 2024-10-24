package uwu.llkc.cnc.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import uwu.llkc.cnc.common.entities.plants.WallNut;

import java.util.List;

public class PlantArmorItem extends Item {
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Override
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            return dispenseArmor(source, stack) ? stack : super.execute(source, stack);
        }
    };

    public PlantArmorItem(Properties properties) {
        super(properties);
    }

    public static boolean dispenseArmor(BlockSource blockSource, ItemStack armorItem) {
        BlockPos blockpos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
        List<WallNut> list = blockSource.level()
                .getEntitiesOfClass(
                        WallNut.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS);
        if (list.isEmpty()) {
            return false;
        } else {
            WallNut nut = list.getFirst();
            if (!nut.getEntityData().get(WallNut.HAS_ARMOR)) {
                armorItem.shrink(1);
                nut.getEntityData().set(WallNut.HAS_ARMOR, true);
                nut.armorHealth = 300;
            }

            return true;
        }
    }
}
