package uwu.llkc.cnc.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface ChunkMixinHelper {
    void setNextBlockPosDoBreak(BlockPos pos);
    void setNextPosForInteractionCheck(BlockPos pos);
    BlockState getBlockStateForDelayedPlace();
}
