package uwu.llkc.cnc.common.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import uwu.llkc.cnc.CNCMod;

public class PlacedFeatureInit {
    public static final ResourceKey<PlacedFeature> WALNUT_TREE = ResourceKey.create(Registries.PLACED_FEATURE, CNCMod.rl("walnut_tree"));

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        PlacementUtils.register(
                context,
                WALNUT_TREE,
                configuredFeatures.getOrThrow(ConfiguredFeatureInit.WALNUT_TREE),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(5),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(BlockRegistry.WALNUT_SAPLING.get().defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome());
    }
}
