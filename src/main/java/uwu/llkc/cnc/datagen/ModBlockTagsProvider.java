package uwu.llkc.cnc.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.registry.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.registerBlockMineables();
        this.registerMinecraftTags();
    }

    protected void registerBlockMineables() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.EXAMPLE_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.EXAMPLE_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.EXAMPLE_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.EXAMPLE_BLOCK.get());
    }


    protected void registerMinecraftTags() {
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.EXAMPLE_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.EXAMPLE_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.EXAMPLE_BLOCK.get());
    }
}
