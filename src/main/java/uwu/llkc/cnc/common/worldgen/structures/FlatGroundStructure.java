package uwu.llkc.cnc.common.worldgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.common.init.StructureTypeRegistry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class FlatGroundStructure extends JigsawStructure {
    public static final MapCodec<FlatGroundStructure> CODEC = RecordCodecBuilder.<FlatGroundStructure>mapCodec(
                    instance -> instance.group(
                                    settingsCodec(instance),
                                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                                    ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
                                    Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.maxDepth),
                                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                                    Codec.BOOL.fieldOf("use_expansion_hack").forGetter(structure -> structure.useExpansionHack),
                                    Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
                                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter),
                                    Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(structure -> structure.poolAliases),
                                    DimensionPadding.CODEC
                                            .optionalFieldOf("dimension_padding", DEFAULT_DIMENSION_PADDING)
                                            .forGetter(structure -> structure.dimensionPadding),
                                    LiquidSettings.CODEC.optionalFieldOf("liquid_settings", DEFAULT_LIQUID_SETTINGS).forGetter(structure -> structure.liquidSettings),
                                    Codec.INT.fieldOf("width").forGetter(structure -> structure.width),
                                    Codec.INT.fieldOf("max_difference").forGetter(structure -> structure.maxDifference)
                            )
                            .apply(instance, FlatGroundStructure::new)
            )
            .validate(FlatGroundStructure::verifyRange);

    private final int width;
    private final int maxDifference;
    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final List<PoolAliasBinding> poolAliases;
    private final DimensionPadding dimensionPadding;
    private final LiquidSettings liquidSettings;


    public FlatGroundStructure(
            StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            Optional<ResourceLocation> startJigsawName,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Optional<Heightmap.Types> projectStartToHeightmap,
            int maxDistanceFromCenter,
            List<PoolAliasBinding> poolAliases,
            DimensionPadding dimensionPadding,
            LiquidSettings liquidSettings,
            int width,
            int maxDifference
    ) {
        super(
                settings,
                startPool,
                startJigsawName,
                maxDepth,
                startHeight,
                useExpansionHack,
                projectStartToHeightmap,
                maxDistanceFromCenter,
                poolAliases,
                dimensionPadding,
                liquidSettings
        );
        this.width = width;
        this.maxDifference = maxDifference;
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.poolAliases = poolAliases;
        this.dimensionPadding = dimensionPadding;
        this.liquidSettings = liquidSettings;
    }

    public FlatGroundStructure(
            Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight, boolean useExpansionHack, int width, int maxDifference
    ) {
        this(
                settings,
                startPool,
                Optional.empty(),
                maxDepth,
                startHeight,
                useExpansionHack,
                Optional.empty(),
                80,
                List.of(),
                DEFAULT_DIMENSION_PADDING,
                DEFAULT_LIQUID_SETTINGS,
                width,
                maxDifference
        );
    }

    public FlatGroundStructure(
            Structure.StructureSettings settings,
            Holder<StructureTemplatePool> startPool,
            int maxDepth,
            HeightProvider startHeight,
            boolean useExpansionHack,
            Heightmap.Types projectStartToHeightmap,
            int width,
            int maxDifference
    ) {
        this(
                settings,
                startPool,
                Optional.empty(),
                maxDepth,
                startHeight,
                useExpansionHack,
                Optional.of(projectStartToHeightmap),
                80,
                List.of(),
                DEFAULT_DIMENSION_PADDING,
                DEFAULT_LIQUID_SETTINGS,
                width,
                maxDifference
        );
    }

    private static DataResult<FlatGroundStructure> verifyRange(FlatGroundStructure structure) {
        int i = switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX, ENCAPSULATE -> 12;
        };
        return structure.maxDistanceFromCenter + i > 128
                ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128")
                : DataResult.success(structure);
    }

    @Override
    public @NotNull Optional<GenerationStub> findValidGenerationPoint(@NotNull GenerationContext context) {
        return super.findValidGenerationPoint(context).filter(stub -> isGroundFlat(stub, context));
    }

    private boolean isGroundFlat(GenerationStub stub, GenerationContext context) {
        BlockPos[] positions = new BlockPos[9];

        int spread = width / 3;
        for (int i = 0; i < 9; i++) {
            var pos = stub.position().offset((i / 3) * (spread + 1), 0, (i / 3) * (spread + 1));
            positions[i] = pos.atY(context.chunkGenerator().getFirstOccupiedHeight(
                    pos.getX(),
                    pos.getZ(),
                    Heightmap.Types.WORLD_SURFACE_WG,
                    context.heightAccessor(),
                    context.randomState()));
        }

        var min = Arrays.stream(positions).min(Comparator.comparingInt(BlockPos::getY)).orElse(BlockPos.ZERO);
        var max = Arrays.stream(positions).max(Comparator.comparingInt(BlockPos::getY)).orElse(BlockPos.ZERO);

        return max.getY() - min.getY() <= maxDifference;
    }

    @Override
    public StructureType<?> type() {
        return StructureTypeRegistry.FLAT_GROUND.get();
    }
}
