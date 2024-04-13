package uwu.llkc.cnc.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.util.ModUtils;

import java.util.Collection;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = ModUtils.createRegister(DeferredRegister::createBlocks);


    public static final DeferredBlock<Block> EXAMPLE_BLOCK = register("example_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            ), new Item.Properties());

    public static final DeferredBlock<Block> EXAMPLE_BLOCK_2 = register("example_block_2",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
            ), new Item.Properties());

    public static void staticInit() {
    }

    private static <T extends Block> DeferredBlock<T> register(String id, Supplier<T> block, Item.Properties pIProp) {
        DeferredBlock<T> toReturn = BLOCKS.register(id.toLowerCase(), block);
        makeBlockItem(toReturn, pIProp);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerNoItem(String id, Supplier<T> block) {
        return BLOCKS.register(id.toLowerCase(), block);
    }
    private static <T extends Block> void makeBlockItem(DeferredBlock<T> block, Item.Properties pIProp) {
        ModItems.registerBlockItem(block, pIProp);
    }
    private static <T extends Block> void makeSpecialBlockItem(DeferredBlock<T> block, Item.Properties pIProp, Supplier<? extends BlockItem> sup) {
        ModItems.registerSpecialBlockItem(block, sup);
    }

    public static Collection<DeferredHolder<Block, ? extends Block>> getBlocks() {
        return BLOCKS.getEntries();
    }
}
