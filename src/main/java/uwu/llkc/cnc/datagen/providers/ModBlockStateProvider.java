package uwu.llkc.cnc.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.blocks.PlantCropBlock;
import uwu.llkc.cnc.common.init.BlockRegistry;

public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CNCMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        crop(BlockRegistry.PEASHOOTER_CROP.get(),
                models().crop("pea_crop_stage0", CNCMod.rl("block/pea_crop_stage0")),
                models().crop("pea_crop_stage0", CNCMod.rl("block/pea_crop_stage0")),
                models().crop("pea_crop_stage1", CNCMod.rl("block/pea_crop_stage1")),
                models().crop("pea_crop_stage1", CNCMod.rl("block/pea_crop_stage1")),
                models().crop("pea_crop_stage2", CNCMod.rl("block/pea_crop_stage2")),
                models().crop("pea_crop_stage2", CNCMod.rl("block/pea_crop_stage2")),
                models().crop("pea_crop_stage2", CNCMod.rl("block/pea_crop_stage2")),
                models().crop("pea_crop_stage3", CNCMod.rl("block/pea_crop_stage3")));

        crop(BlockRegistry.SUNFLOWER_CROP.get(),
                models().crop("sunflower_crop_stage0", CNCMod.rl("block/sunflower_crop_stage0")),
                models().crop("sunflower_crop_stage0", CNCMod.rl("block/sunflower_crop_stage0")),
                models().crop("sunflower_crop_stage1", CNCMod.rl("block/sunflower_crop_stage1")),
                models().crop("sunflower_crop_stage1", CNCMod.rl("block/sunflower_crop_stage1")),
                models().crop("sunflower_crop_stage2", CNCMod.rl("block/sunflower_crop_stage2")),
                models().crop("sunflower_crop_stage2", CNCMod.rl("block/sunflower_crop_stage2")),
                models().crop("sunflower_crop_stage2", CNCMod.rl("block/sunflower_crop_stage2")),
                models().crop("sunflower_crop_stage3", CNCMod.rl("block/sunflower_crop_stage3")));
    }

    void crop(PlantCropBlock block, BlockModelBuilder... builders) {
        VariantBlockStateBuilder variantBlockStateBuilder = getVariantBuilder(block);
        variantBlockStateBuilder.forAllStates(blockState -> ConfiguredModel.builder().modelFile(builders[blockState.getValue(PlantCropBlock.AGE)].renderType("cutout")).build());
    }
}
