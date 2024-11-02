package uwu.llkc.cnc.common.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uwu.llkc.cnc.CNCMod;

public class BiomeModifierInit {
    public static final ResourceKey<BiomeModifier> PEASHOOTER_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("peashooter_spawns"));
    public static final ResourceKey<BiomeModifier> REPEATER_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("repeater_spawns"));
    public static final ResourceKey<BiomeModifier> SUNFLOWER_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("sunflower_spawns"));
    public static final ResourceKey<BiomeModifier> BROWNCOAT_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("browncoat_spawns"));
    public static final ResourceKey<BiomeModifier> WALL_NUT_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("wall_nut_spawns"));
    public static final ResourceKey<BiomeModifier> IMP_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("imp_spawns"));
    public static final ResourceKey<BiomeModifier> CHERRY_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("cherry_spawns"));

    public static final ResourceKey<BiomeModifier> WALNUT_TREE_PLACEMENT = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("walnut_tree_placement"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(PEASHOOTER_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(Tags.Biomes.SPAWNS_PEASHOOTER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.PEASHOOTER.get(), 50, 1, 2))
        );
        context.register(REPEATER_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(Tags.Biomes.SPAWNS_PEASHOOTER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.REPEATER.get(), 25, 1, 2))
        );
        context.register(WALL_NUT_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(Tags.Biomes.SPAWNS_PEASHOOTER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.WALLNUT.get(), 20, 1, 1))
        );
        context.register(SUNFLOWER_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(Tags.Biomes.SPAWNS_SUNFLOWER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SUNFLOWER.get(), 50, 2, 3))
        );
        context.register(BROWNCOAT_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BROWNCOAT.get(), 30, 3, 7))
        );
        context.register(IMP_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.IMP.get(), 20, 5, 7))
        );
        context.register(CHERRY_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                HolderSet.direct(biomes.getOrThrow(Biomes.CHERRY_GROVE)),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.CHERRY_BOMB.get(), 10, 1, 1)
        ));
        context.register(WALNUT_TREE_PLACEMENT, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(net.minecraft.world.level.biome.Biomes.FOREST)),
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(PlacedFeatureInit.WALNUT_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
    }
}
