package uwu.llkc.cnc.common.util;

import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.function.Supplier;

public class EnumParams {
    public static final EnumProxy<Boat.Type> BOAT_TYPE = new EnumProxy<>(Boat.Type.class, (Supplier<Block>) (() -> BlockRegistry.WALNUT_PLANKS.get()), "cnc:walnut", (Supplier<Item>) (() -> ItemRegistry.WALNUT_BOAT.get()), (Supplier<Item>) (() -> ItemRegistry.WALNUT_CHEST_BOAT.get()), (Supplier<Item>) () -> Items.STICK, false);
}
