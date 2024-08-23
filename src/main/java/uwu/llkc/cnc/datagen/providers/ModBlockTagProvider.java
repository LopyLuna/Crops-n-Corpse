package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.Tags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.CROPS)
                .add(BlockRegistry.PEASHOOTER_CROP.get())
                .add(BlockRegistry.SUNFLOWER_CROP.get());
        tag(Tags.Blocks.WALNUT_LOGS)
                .add(BlockRegistry.STRIPPED_WALNUT_WOOD.get())
                .add(BlockRegistry.WALNUT_WOOD.get())
                .add(BlockRegistry.WALNUT_LOG.get())
                .add(BlockRegistry.STRIPPED_WALNUT_LOG.get());
        tag(BlockTags.LOGS_THAT_BURN)
                .addTag(Tags.Blocks.WALNUT_LOGS);
        tag(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(BlockRegistry.WALNUT_LOG.get());
        tag(BlockTags.LEAVES)
                .add(BlockRegistry.WALNUT_LEAVES.get());
        tag(BlockTags.WALL_HANGING_SIGNS)
                .add(BlockRegistry.WALL_HANGING_WALNUT_SIGN.get());
        tag(BlockTags.STANDING_SIGNS)
                .add(BlockRegistry.STANDING_WALNUT_SIGN.get());
        tag(BlockTags.CEILING_HANGING_SIGNS)
                .add(BlockRegistry.CEILING_HANGING_WALNUT_SIGN.get());
        tag(BlockTags.WALL_SIGNS)
                .add(BlockRegistry.WALNUT_WALL_SIGN.get());
        tag(BlockTags.WOODEN_TRAPDOORS)
                .add(BlockRegistry.WALNUT_TRAPDOOR.get());
        tag(BlockTags.WOODEN_DOORS)
                .add(BlockRegistry.WALNUT_DOOR.get());
        tag(BlockTags.WOODEN_BUTTONS)
                .add(BlockRegistry.WALNUT_BUTTON.get());
        tag(BlockTags.WOODEN_FENCES)
                .add(BlockRegistry.WALNUT_FENCE.get());
        tag(BlockTags.FENCE_GATES)
                .add(BlockRegistry.WALNUT_FENCE_GATE.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(BlockRegistry.WALNUT_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_STAIRS)
                .add(BlockRegistry.WALNUT_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS)
                .add(BlockRegistry.WALNUT_SLAB.get());
    }
}
