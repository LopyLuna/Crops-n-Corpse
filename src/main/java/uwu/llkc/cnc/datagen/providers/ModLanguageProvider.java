package uwu.llkc.cnc.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
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
            if (entry == EntityTypeRegistry.WALLNUT) continue;
            addEntityType(entry, toEnglishTranslation(entry.getId()));
        }

        addEntityType(EntityTypeRegistry.WALLNUT, "Wall-Nut");

        for (DeferredHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
            if (entry == ItemRegistry.WALLNUT_SEED_PACKET) continue;
            if (entry.get() instanceof BlockItem) continue;
            addItem(entry, toEnglishTranslation(entry.getId()));
        }

        addItem(ItemRegistry.RAW_PEA, toEnglishTranslation(ItemRegistry.RAW_PEA.getId()));
        addItem(ItemRegistry.WALLNUT_SEED_PACKET, "Wall-Nut Seed Packet");
        addItem(ItemRegistry.SUNFLOWER_SEEDS, toEnglishTranslation(ItemRegistry.SUNFLOWER_SEEDS.getId()));

        for (DeferredHolder<Block, ? extends Block> entry : BlockRegistry.BLOCKS.getEntries()) {
            if (entry.get() instanceof SignBlock) continue;
            addBlock(entry, toEnglishTranslation(entry.getId()));
        }
        add("block.cnc.standing_walnut_sign", "Walnut Sign");
        add("block.cnc.walnut_wall_sign", "Walnut Wall Sign");
        add("block.cnc.ceiling_hanging_walnut_sign", "Hanging Walnut Sign");
        add("block.cnc.hanging_walnut_wall_sign", "Hanging Walnut Sign");

        add("itemGroup.cnc.cnc_tab", "Crops 'n' Corpses");
        add("damage_type.cnc.pea_shot", "Pea shot");
        add("subtitles.cnc.peashooter_death", "Peashooter died");
        add("subtitles.cnc.sunflower_death", "Sunflower died");
        add("subtitles.cnc.peashooter_hurt", "Peashooter hurt");
        add("subtitles.cnc.sunflower_hurt", "Sunflower hurt");
        add("subtitles.cnc.peashooter_shoot", "Peashooter shoot");
        add("subtitles.cnc.sunflower_action", "Sunflower action");
        add("subtitles.cnc.browncoat_ambiance", "Browncoat ambiance");
        add("subtitles.cnc.browncoat_attack", "Browncoat attack");
        add("subtitles.cnc.browncoat_death", "Browncoat died");
        add("subtitles.cnc.browncoat_hurt", "Browncoat hurt");
        add("subtitles.cnc.plant_spawn", "Plant spawned");
        add("subtitles.cnc.wall_nut_armor_hurt", "Wall-Nut armor hurt");
        add("subtitles.cnc.wall_nut_hurt", "Wall-Nut hurt");
        add("subtitles.cnc.wall_nut_death", "Wall-Nut died");
        add("subtitles.cnc.imp_attack", "Imp attacked");
        add("subtitles.cnc.imp_death", "Imp died");
        add("subtitles.cnc.imp_hurt", "Imp hurt");
        add("subtitles.cnc.imp_idle", "Imp idle");

        add("death.attack.pea_shot", "%1$s fed %2$s their vegetables");
        add("death.attack.potato_mine", "%1$s went SPUDOW! by %2$s");
        add("death.attack.cherry_bomb", "%1$s was explodonated by %2$s");
        add("death.attack.cnc_zombie", "%1$s ate %2$s's brainz");

        add("item.multi_spawn_egg.tooltip", "Shift right-click to cycle");
    }

    private String toEnglishTranslation(ResourceLocation rl) {
        return Arrays.stream(rl.getPath().split("_"))
                .map(word -> Character.toTitleCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
