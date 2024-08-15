package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uwu.llkc.cnc.CNCMod;

public class BiomeModifierInit {
    public static final ResourceKey<BiomeModifier> PEASHOOTER_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("peashooter_spawns"));
    public static final ResourceKey<BiomeModifier> SUNFLOWER_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("sunflower_spawns"));
    public static final ResourceKey<BiomeModifier> BROWNCOAT_SPAWNS = ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, CNCMod.rl("browncoat_spawns"));

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        context.register(PEASHOOTER_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                context.lookup(Registries.BIOME).getOrThrow(Tags.BiomeTags.SPAWNS_PEASHOOTER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.PEASHOOTER.get(), 50, 1, 2)));

        context.register(SUNFLOWER_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                context.lookup(Registries.BIOME).getOrThrow(Tags.BiomeTags.SPAWNS_SUNFLOWER),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.SUNFLOWER.get(), 50, 2, 3)));

        context.register(BROWNCOAT_SPAWNS, BiomeModifiers.AddSpawnsBiomeModifier.singleSpawn(
                context.lookup(Registries.BIOME).getOrThrow(BiomeTags.IS_OVERWORLD),
                new MobSpawnSettings.SpawnerData(EntityTypeRegistry.BROWNCOAT.get(), 30, 3, 7)));
    }
}
