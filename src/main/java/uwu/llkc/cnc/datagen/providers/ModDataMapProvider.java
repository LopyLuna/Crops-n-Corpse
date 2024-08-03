package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    public ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ItemRegistry.RAW_PEA, new Compostable(0.1f), false)
                .add(ItemRegistry.PEA_POD, new Compostable(0.3f), false);

        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ItemRegistry.SUN, new FurnaceFuel(100), false);
    }
}
