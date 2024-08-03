package uwu.llkc.cnc.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.Optional;

public class SunflowerCropBlock extends PlantCropBlock{
    public SunflowerCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    Optional<EntityType<?>> getEntityType(BlockState state, ServerLevel level, BlockPos pos) {
        if (level.getRandom().nextFloat() < 0.14f){
            return Optional.of(EntityTypeRegistry.SUNFLOWER.get());
        }
        return Optional.empty();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.SUNFLOWER_SEEDS.get();
    }
}
