package uwu.llkc.cnc.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.llkc.cnc.registry.ModBlocks;
import uwu.llkc.cnc.util.ModUtils;

import java.util.function.Supplier;

class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.simpleBlockAndItem(ModBlocks.EXAMPLE_BLOCK);
    }

    private void simpleBlockAndItem(Supplier<? extends Block> block) {
        this.simpleBlock(block.get());
        this.simpleBlockItem(block.get(), new ModelFile.UncheckedModelFile(ModUtils.location("block/" + this.name(block.get()))));
    }

    private String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }
}
