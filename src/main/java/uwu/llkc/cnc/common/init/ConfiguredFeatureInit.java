package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import uwu.llkc.cnc.CNCMod;

import java.util.OptionalInt;

public class ConfiguredFeatureInit {
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALNUT_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, CNCMod.rl("walnut_tree"));

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        FeatureUtils.register(
                context,
                WALNUT_TREE,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(BlockRegistry.WALNUT_LOG.get()),
                        new FancyTrunkPlacer(3, 11, 0),
                        BlockStateProvider.simple(BlockRegistry.WALNUT_LEAVES.get()),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                ).ignoreVines().build()
        );
    }
}
