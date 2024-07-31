package uwu.llkc.cnc.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.Optional;

public class PeashooterCropBlock extends PlantCropBlock{
    public PeashooterCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    Optional<EntityType<?>> getEntityType(BlockState state, ServerLevel level, BlockPos pos) {
        if (level.getRandom().nextFloat() < 0.125f){
            return Optional.of(EntityTypeRegistry.PEASHOOTER.get());
        }
        if (level.getRandom().nextFloat() < 0.04f){
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.RAW_PEA.get();
    }
}
