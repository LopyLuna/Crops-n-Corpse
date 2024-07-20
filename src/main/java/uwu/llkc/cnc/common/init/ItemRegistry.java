package uwu.llkc.cnc.common.init;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.items.SeedPacketItem;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final Supplier<SeedPacketItem> SEED_PACKET = ITEMS.register("seed_packet",
            () -> new SeedPacketItem(new Item.Properties()));
}
