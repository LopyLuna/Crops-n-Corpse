package uwu.llkc.cnc.common.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.blocks.PeashooterCropBlock;
import uwu.llkc.cnc.common.blocks.SunflowerCropBlock;
import uwu.llkc.cnc.common.blocks.TrafficConeBlock;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CNCMod.MOD_ID);

    public static final DeferredBlock<PeashooterCropBlock> PEASHOOTER_CROP = BLOCKS.registerBlock("peashooter_block",
            PeashooterCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));

    public static final DeferredBlock<SunflowerCropBlock> SUNFLOWER_CROP = BLOCKS.registerBlock("sunflower_block",
            SunflowerCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));

    public static final DeferredBlock<TrafficConeBlock> TRAFFIC_CONE = BLOCKS.registerBlock("traffic_cone",
            TrafficConeBlock::new, BlockBehaviour.Properties.of().instabreak().noOcclusion());
}
