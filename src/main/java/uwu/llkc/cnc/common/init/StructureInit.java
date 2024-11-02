package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.neoforged.neoforge.common.Tags;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.worldgen.structures.FlatGroundStructure;

public class StructureInit {
    public static final ResourceKey<Structure> HOUSE_GRILL = ResourceKey.create(Registries.STRUCTURE,
            CNCMod.rl("house_grill"));

    public static final ResourceKey<Structure> HOUSE_MAILBOX = ResourceKey.create(Registries.STRUCTURE,
            CNCMod.rl("house_mailbox"));

    public static final ResourceKey<Structure> HOUSE_POOL = ResourceKey.create(Registries.STRUCTURE,
            CNCMod.rl("house_pool"));

    public static void bootstrap(BootstrapContext<Structure> context) {
        var biomeLookup = context.lookup(Registries.BIOME);
        var templatePoolLookup = context.lookup(Registries.TEMPLATE_POOL);

        context.register(HOUSE_GRILL, new FlatGroundStructure(
                new Structure.StructureSettings.Builder(biomeLookup.getOrThrow(Tags.Biomes.IS_FOREST))
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN).build(),
                templatePoolLookup.getOrThrow(StructureTemplatePoolInit.HOUSE_GRILL),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG,
                9,
                5
        ));
        context.register(HOUSE_MAILBOX, new FlatGroundStructure(
                new Structure.StructureSettings.Builder(biomeLookup.getOrThrow(Tags.Biomes.IS_FOREST))
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN).build(),
                templatePoolLookup.getOrThrow(StructureTemplatePoolInit.HOUSE_MAILBOX),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG,
                9,
                5
        ));
        context.register(HOUSE_POOL, new FlatGroundStructure(
                new Structure.StructureSettings.Builder(biomeLookup.getOrThrow(Tags.Biomes.IS_FOREST))
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN).build(),
                templatePoolLookup.getOrThrow(StructureTemplatePoolInit.HOUSE_POOL),
                1,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.WORLD_SURFACE_WG,
                9,
                5
        ));
    }
}
