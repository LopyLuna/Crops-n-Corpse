package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.blockentities.CustomHangingSignBlockEntity;
import uwu.llkc.cnc.common.blockentities.CustomSignBlockEntity;

import java.util.function.Supplier;

public class BlockEntityTypeRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CNCMod.MOD_ID);

    public static final Supplier<BlockEntityType<CustomHangingSignBlockEntity>> CUSTOM_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("custom_hanging_sign",
            () -> BlockEntityType.Builder.of(CustomHangingSignBlockEntity::new, BlockRegistry.CEILING_HANGING_WALNUT_SIGN.get(), BlockRegistry.WALL_HANGING_WALNUT_SIGN.get())
                    .build(null));

    public static final Supplier<BlockEntityType<CustomSignBlockEntity>> CUSTOM_SIGN = BLOCK_ENTITY_TYPES.register("custom_sign",
            () -> BlockEntityType.Builder.of(CustomSignBlockEntity::new, BlockRegistry.WALNUT_WALL_SIGN.get(), BlockRegistry.STANDING_WALNUT_SIGN.get())
                    .build(null));
}
