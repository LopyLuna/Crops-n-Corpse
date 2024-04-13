package uwu.llkc.cnc.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;

import static uwu.llkc.cnc.CNCMod.MOD_ID;
import static uwu.llkc.cnc.registry.ModBlocks.EXAMPLE_BLOCK;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<Item> LOGO = ITEMS.registerSimpleItem("logo", new Item.Properties().fireResistant().stacksTo(1));

    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEat().nutrition(1).saturationMod(2f).build()));



    public static void staticInit() {
    }

    //private-package so block register class can use
    static void registerBlockItem(Holder<Block> blockHolder) {
        registerBlockItem(blockHolder, new Item.Properties());
    }

    //private-package so block register class can use
    static void registerBlockItem(Holder<Block> blockHolder, Item.Properties properties) {
        ITEMS.registerSimpleBlockItem(blockHolder, properties);
    }
    static void registerSpecialBlockItem(Holder<Block> blockHolder, Supplier<? extends BlockItem> sup) {
        ITEMS.register(blockHolder.unwrapKey().get().toString(), sup);
    }

    static void registerSimpleItem(String name) {
        ITEMS.registerSimpleItem(name);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getItems() {
        return ITEMS.getEntries();
    }
}
