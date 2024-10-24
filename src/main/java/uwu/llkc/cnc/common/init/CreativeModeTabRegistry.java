package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;

import java.util.function.Supplier;

public class CreativeModeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, CNCMod.MOD_ID);

    public static final Supplier<CreativeModeTab> CNC_TAB = CREATIVE_MODE_TABS.register("cnc_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatableWithFallback("ítemGroup." + CNCMod.MOD_ID + ".cnc_tab", "Crops 'n' Corpse"))
                    .icon(() -> new ItemStack(ItemRegistry.PLANT_FOOD.get()))
                    .displayItems((params, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
                            output.accept(entry.get());
                        }
                    }).build());
}
