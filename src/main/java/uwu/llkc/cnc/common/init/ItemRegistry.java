package uwu.llkc.cnc.common.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.items.SeedPacketItem;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final DeferredHolder<Item, SeedPacketItem> SEED_PACKET = ITEMS.registerItem("seed_packet", SeedPacketItem::new);
    public static final Supplier<Item> PLANT_FOOD = ITEMS.registerSimpleItem("plant_food");
    public static final Supplier<Item> SUN = ITEMS.registerSimpleItem("sun");
}
