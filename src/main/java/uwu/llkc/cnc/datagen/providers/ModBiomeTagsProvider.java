package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.Tags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagsProvider extends BiomeTagsProvider {
    public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.Biomes.SPAWNS_PEASHOOTER)
                .addTag(BiomeTags.IS_FOREST)
                .add(net.minecraft.world.level.biome.Biomes.PLAINS);
        tag(Tags.Biomes.SPAWNS_SUNFLOWER)
                .add(net.minecraft.world.level.biome.Biomes.PLAINS)
                .addTag(BiomeTags.IS_SAVANNA);
    }
}
