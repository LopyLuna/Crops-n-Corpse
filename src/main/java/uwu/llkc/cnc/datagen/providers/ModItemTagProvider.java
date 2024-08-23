package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.Tags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.LOGS_THAT_BURN)
                .addTag(Tags.Items.WALNUT_LOGS);
        tag(ItemTags.PLANKS)
                .add(ItemRegistry.WALNUT_PLANKS.get());
        tag(Tags.Items.WALNUT_LOGS)
                .add(ItemRegistry.WALNUT_LOG.get())
                .add(ItemRegistry.WALNUT_WOOD.get())
                .add(ItemRegistry.STRIPPED_WALNUT_WOOD.get())
                .add(ItemRegistry.STRIPPED_WALNUT_LOG.get());
        tag(ItemTags.SIGNS)
                .add(ItemRegistry.WALNUT_SIGN.get());
        tag(ItemTags.HANGING_SIGNS)
                .add(ItemRegistry.HANGING_WALNUT_SIGN.get());
        tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ItemRegistry.WALNUT_TRAP_DOOR.get());
        tag(ItemTags.WOODEN_DOORS)
                .add(ItemRegistry.WALNUT_DOOR.get());
        tag(ItemTags.WOODEN_BUTTONS)
                .add(ItemRegistry.WALNUT_BUTTON.get());
        tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ItemRegistry.WALNUT_PRESSURE_PLATE.get());
        tag(ItemTags.WOODEN_FENCES)
                .add(ItemRegistry.WALNUT_FENCE.get());
        tag(ItemTags.FENCE_GATES)
                .add(ItemRegistry.WALNUT_FENCE_GATE.get());
        tag(ItemTags.WOODEN_SLABS)
                .add(ItemRegistry.WALNUT_SLAB.get());
        tag(ItemTags.WOODEN_STAIRS)
                .add(ItemRegistry.WALNUT_STAIRS.get());
    }
}
