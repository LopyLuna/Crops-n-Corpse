package uwu.llkc.cnc;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import uwu.llkc.cnc.common.init.CreativeModeTabRegistry;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

@Mod(CNCMod.MOD_ID)
public class CNCMod {
    public static final String MOD_ID = "cnc";

    public CNCMod(IEventBus modEventBus, ModContainer modContainer) {
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        DataComponentRegistry.DATA_COMPONENTS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static String rlStr(String path) {
        return MOD_ID + ":" + path;
    }
}
