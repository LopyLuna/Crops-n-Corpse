package uwu.llkc.cnc.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public abstract class PlantCropBlock extends CropBlock {
    public PlantCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.randomTick(state, level, pos, random);
        if (getAge(level.getBlockState(pos)) == getMaxAge()) {
            getEntityType(state, level, pos).ifPresent(entityType -> entityType.spawn(level, pos, MobSpawnType.SPAWNER));
        }
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        super.performBonemeal(level, random, pos, state);
        if (getAge(level.getBlockState(pos)) == getMaxAge()) {
            getEntityType(state, level, pos).ifPresent(entityType -> entityType.spawn(level, pos, MobSpawnType.SPAWNER));
        }
    }

    abstract Optional<EntityType<?>> getEntityType(BlockState state, ServerLevel level, BlockPos pos);
}
