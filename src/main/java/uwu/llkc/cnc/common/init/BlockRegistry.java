package uwu.llkc.cnc.common.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.blockentities.CustomHangingSignBlockEntity;
import uwu.llkc.cnc.common.blockentities.CustomSignBlockEntity;
import uwu.llkc.cnc.common.blocks.*;

public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(CNCMod.MOD_ID);

    public static final DeferredBlock<PeashooterCropBlock> PEASHOOTER_CROP = BLOCKS.registerBlock("peashooter_block",
            PeashooterCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));

    public static final DeferredBlock<SunflowerCropBlock> SUNFLOWER_CROP = BLOCKS.registerBlock("sunflower_block",
            SunflowerCropBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT));

    public static final DeferredBlock<TrafficConeBlock> TRAFFIC_CONE = BLOCKS.registerBlock("traffic_cone",
            TrafficConeBlock::new, BlockBehaviour.Properties.of().instabreak().noOcclusion());

    public static final DeferredBlock<LogBlock> STRIPPED_WALNUT_LOG = BLOCKS.registerBlock("stripped_walnut_log",
            props -> new LogBlock(props, null), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_LOG));

    public static final DeferredBlock<LogBlock> WALNUT_LOG = BLOCKS.registerBlock("walnut_log",
            props -> new LogBlock(props, STRIPPED_WALNUT_LOG), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LOG));

    public static final DeferredBlock<SaplingBlock> WALNUT_SAPLING = BLOCKS.registerBlock("walnut_sapling",
            props -> new SaplingBlock(TreeGrowers.WALNUT_TREE, props), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SAPLING));

    public static final DeferredBlock<WalnutLeavesBlock> WALNUT_LEAVES = BLOCKS.registerBlock("walnut_leaves",
            WalnutLeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES));

    public static final DeferredBlock<LogBlock> STRIPPED_WALNUT_WOOD = BLOCKS.registerBlock("stripped_walnut_wood",
            props -> new LogBlock(props, null), BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_SPRUCE_WOOD));

    public static final DeferredBlock<LogBlock> WALNUT_WOOD = BLOCKS.registerBlock("walnut_wood",
            props -> new LogBlock(props, STRIPPED_WALNUT_WOOD), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD));

    public static final DeferredBlock<Block> WALNUT_PLANKS = BLOCKS.registerBlock("walnut_planks",
            props -> new Block(props) {
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
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)
    );

    public static final DeferredBlock<StandingSignBlock> STANDING_WALNUT_SIGN = BLOCKS.registerBlock("standing_walnut_sign",
            props -> new StandingSignBlock(WoodTypes.WALNUT, props) {
                @Override
                public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                    return new CustomSignBlockEntity(pos, state);
                }
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SIGN));

    public static final DeferredBlock<WallSignBlock> WALNUT_WALL_SIGN = BLOCKS.registerBlock("walnut_wall_sign",
            props -> new WallSignBlock(WoodTypes.WALNUT, props) {
                @Override
                public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                    return new CustomSignBlockEntity(pos, state);
                }
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WALL_SIGN));

    public static final DeferredBlock<CeilingHangingSignBlock> CEILING_HANGING_WALNUT_SIGN = BLOCKS.registerBlock("ceiling_hanging_walnut_sign",
            props -> new CeilingHangingSignBlock(WoodTypes.WALNUT, props) {
                @Override
                public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                    return new CustomHangingSignBlockEntity(pos, state);
                }
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_HANGING_SIGN));

    public static final DeferredBlock<WallHangingSignBlock> WALL_HANGING_WALNUT_SIGN = BLOCKS.registerBlock("wall_hanging_walnut_sign",
            props -> new WallHangingSignBlock(WoodTypes.WALNUT, props) {
                @Override
                public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
                    return new CustomHangingSignBlockEntity(pos, state);
                }
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WALL_HANGING_SIGN));

    public static final DeferredBlock<TrapDoorBlock> WALNUT_TRAPDOOR = BLOCKS.registerBlock("walnut_trapdoor",
            props -> new TrapDoorBlock(BlockSetType.SPRUCE, props) {
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
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_TRAPDOOR));

    public static final DeferredBlock<DoorBlock> WALNUT_DOOR = BLOCKS.registerBlock("walnut_door",
            props -> new DoorBlock(BlockSetType.SPRUCE, props) {
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
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_DOOR));

    public static final DeferredBlock<ButtonBlock> WALNUT_BUTTON = BLOCKS.registerBlock("walnut_button",
            props -> new ButtonBlock(BlockSetType.SPRUCE, 30, props), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_BUTTON));

    public static final DeferredBlock<FenceBlock> WALNUT_FENCE = BLOCKS.registerBlock("walnut_fence",
            props -> new FenceBlock(props) {
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
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_FENCE));

    public static final DeferredBlock<FenceGateBlock> WALNUT_FENCE_GATE = BLOCKS.registerBlock("walnut_fence_gate",
            props -> new FenceGateBlock(WoodTypes.WALNUT, props), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_FENCE_GATE));

    public static final DeferredBlock<PressurePlateBlock> WALNUT_PRESSURE_PLATE = BLOCKS.registerBlock("walnut_pressure_plate",
            props -> new PressurePlateBlock(BlockSetType.SPRUCE, props), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_PRESSURE_PLATE));
    public static final DeferredBlock<SlabBlock> WALNUT_SLAB = BLOCKS.registerBlock("walnut_slab",
            props -> new SlabBlock(props) {
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
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_SLAB));

    public static final DeferredBlock<StairBlock> WALNUT_STAIRS = BLOCKS.registerBlock("walnut_stairs",
            props -> new StairBlock(WALNUT_PLANKS.get().defaultBlockState(), props) {
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
            }, BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_STAIRS));

    public static final DeferredBlock<CustomCakeBlock> CHOCOLATE_CHERRY_CAKE = BLOCKS.registerBlock("chocolate_cherry_cake",
            props -> new CustomCakeBlock(props, 5, 10, 4, 0.6f), BlockBehaviour.Properties.ofFullCopy(Blocks.CAKE));

    public static class WoodTypes {
        public static final WoodType WALNUT = WoodType.register(new WoodType(CNCMod.rlStr("walnut"), BlockSetType.SPRUCE));
    }
}
