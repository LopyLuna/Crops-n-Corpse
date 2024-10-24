package uwu.llkc.cnc.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import uwu.llkc.cnc.common.entities.plants.WallNut;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;

public class WalnutLeavesBlock extends LeavesBlock {
    public WalnutLeavesBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
        if (level.getRandom().nextFloat() < 0.005f) {
            WallNut wallNut = EntityTypeRegistry.WALLNUT.get().create(level);
            if (wallNut != null) {
                wallNut.moveTo((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, 0.0F, 0.0F);
                level.addFreshEntity(wallNut);
                wallNut.spawnAnim();
            }
        }
    }
}
