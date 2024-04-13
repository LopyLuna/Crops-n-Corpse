package uwu.llkc.cnc.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.util.NoTab;

import java.util.function.Consumer;

import static uwu.llkc.cnc.CNCMod.MOD_ID;

public class ModCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CNC_TAB;

    static {
        CNC_TAB = registerTabSearchBar("cnc_tab", ModItems.LOGO, output -> {

            for (DeferredHolder<Item, ? extends Item> registry : ModItems.getItems()) {
                if (registry.get() instanceof BlockItem || registry.get().getClass().isAnnotationPresent(NoTab.class)) continue;
                output.accept(registry.get());
            }
            for (DeferredHolder<Block, ? extends Block> registry : ModBlocks.getBlocks()) {
                if (registry.get().getClass().isAnnotationPresent(NoTab.class)) continue;
                output.accept(registry.get());
            }

        }, builder -> builder.withTabsBefore(CreativeModeTabs.COMBAT));
    }


    private static DeferredHolder<CreativeModeTab, CreativeModeTab> registerTabSearchBar(String name, Holder<Item> icon, Consumer<CreativeModeTab.Output> displayItems, Consumer<CreativeModeTab.Builder> additionalProperties) {
        return CREATIVE_MODE_TABS.register(name, id -> {
            final CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable(id.toLanguageKey("itemGroup")))
                    .icon(() -> new ItemStack(icon))
                    .withSearchBar()
                    .displayItems((pParameters, pOutput) -> displayItems.accept(pOutput));
            additionalProperties.accept(builder);
            return builder.build();
        });
    }


    public static void staticInit() {
    }
}
