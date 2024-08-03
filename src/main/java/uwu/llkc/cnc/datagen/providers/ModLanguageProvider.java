package uwu.llkc.cnc.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, CNCMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        for (DeferredHolder<EntityType<?>, ? extends EntityType<?>> entry : EntityTypeRegistry.ENTITY_TYPES.getEntries()) {
            addEntityType(entry, toEnglishTranslation(entry.getId()));
        }

        for (DeferredHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
            addItem(entry, toEnglishTranslation(entry.getId()));
        }

        for (DeferredHolder<Block, ? extends Block> entry : BlockRegistry.BLOCKS.getEntries()) {
            addBlock(entry, toEnglishTranslation(entry.getId()));
        }

        add("itemGroup.cnc.cnc_tab", "Crops 'n' Corpse");
        add("damage_type.cnc.pea_shot", "Pea Shot");
        add("subtitles.cnc.peashooter_death", "Peashooter Died");
        add("subtitles.cnc.peashooter_hurt", "Peashooter Hurt");
        add("subtitles.cnc.peashooter_shoot", "Peashooter Shoot");
        add("subtitles.cnc.plant_spawn", "Plant Spawned");
    }

    private String toEnglishTranslation(ResourceLocation rl) {
        return Arrays.stream(rl.getPath().split("_"))
                .map(word -> Character.toTitleCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
