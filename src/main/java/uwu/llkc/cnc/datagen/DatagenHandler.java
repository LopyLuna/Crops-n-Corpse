package uwu.llkc.cnc.datagen;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.info.BlockListReport;
import net.minecraft.data.info.ItemListReport;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.entity.EntityTickList;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.BiomeModifierInit;
import uwu.llkc.cnc.common.init.ConfiguredFeatureInit;
import uwu.llkc.cnc.common.init.DamageTypeInit;
import uwu.llkc.cnc.common.init.PlacedFeatureInit;
import uwu.llkc.cnc.datagen.providers.*;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DatagenHandler {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, DamageTypeInit::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureInit::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatureInit::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifierInit::bootstrap);

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModSoundProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        var blockTags = new ModBlockTagProvider(output, event.getLookupProvider(), existingFileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ModItemTagProvider(output, event.getLookupProvider(), blockTags.contentsGetter()));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTagProvider(output, event.getLookupProvider(), existingFileHelper));
        var provider = generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), BUILDER, Set.of(CNCMod.MOD_ID))).getRegistryProvider();
        generator.addProvider(event.includeServer(), new ModDamageTypeTagProvider(output, provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModGameEventTagProvider(output, provider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, provider));
        generator.addProvider(event.includeServer(), new ModDataMapProvider(output, provider));
        generator.addProvider(event.includeServer(),
                new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                        ModBlockLootTableSubProvider::new, LootContextParamSets.BLOCK
                ), new LootTableProvider.SubProviderEntry(
                        ModEntityLootTableSubProvider::new, LootContextParamSets.ENTITY
                )), provider));
        generator.addProvider(event.includeReports(), new ItemListReport(output, event.getLookupProvider()));
    }
}
