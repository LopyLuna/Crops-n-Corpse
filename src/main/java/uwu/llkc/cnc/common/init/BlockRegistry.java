package uwu.llkc.cnc.common.init;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.blocks.PeashooterCropBlock;

import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CNCMod.MOD_ID);

    public static final Supplier<PeashooterCropBlock> PEASHOOTER_CROP = BLOCKS.registerBlock("peashooter_block",
            PeashooterCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));
}
