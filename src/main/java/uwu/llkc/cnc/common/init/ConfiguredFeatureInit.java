package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import uwu.llkc.cnc.CNCMod;

public class ConfiguredFeatureInit {
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALNUT_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, CNCMod.rl("walnut_tree"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(
                context,
                WALNUT_TREE,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockRegistry.WALNUT_LOG.get()),
                        new StraightTrunkPlacer(5, 2, 6),
                        BlockStateProvider.simple(BlockRegistry.WALNUT_LEAVES.get()),
                        new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)).build()
        );
    }
}
