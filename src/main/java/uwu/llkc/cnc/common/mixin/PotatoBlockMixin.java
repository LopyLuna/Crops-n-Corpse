package uwu.llkc.cnc.common.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.PotatoBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;

@Mixin(PotatoBlock.class)
public abstract class PotatoBlockMixin extends CropBlock {
    public PotatoBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (getAge(level.getBlockState(pos)) == getMaxAge()) {
            if (random.nextFloat() < 0.015) {
                EntityTypeRegistry.POTATO_MINE.get().spawn(level, pos, MobSpawnType.SPAWNER);
            }
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        super.performBonemeal(level, random, pos, state);
        if (getAge(level.getBlockState(pos)) == getMaxAge()) {
            if (random.nextFloat() < 0.015) {
                EntityTypeRegistry.POTATO_MINE.get().spawn(level, pos, MobSpawnType.SPAWNER);
            }
        }
    }
}
