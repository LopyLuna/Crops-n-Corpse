package uwu.llkc.cnc.common.blockentities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import uwu.llkc.cnc.common.init.BlockEntityTypeRegistry;

public class CustomHangingSignBlockEntity extends HangingSignBlockEntity {
    public CustomHangingSignBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BlockEntityTypeRegistry.CUSTOM_HANGING_SIGN.get();
    }
}
