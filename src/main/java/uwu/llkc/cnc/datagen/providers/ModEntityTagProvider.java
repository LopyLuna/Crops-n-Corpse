package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.Tags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends EntityTypeTagsProvider {
    public ModEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, CNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tags.EntityTypes.OFFENSIVE_PLANTS)
                .add(EntityTypeRegistry.PEASHOOTER.get())
                .add(EntityTypeRegistry.SNOW_PEA.get())
                .add(EntityTypeRegistry.REPEATER.get());
        tag(Tags.EntityTypes.NOCTURNAL_PLANTS);
        tag(Tags.EntityTypes.DEFENSIVE_PLANTS)
                .add(EntityTypeRegistry.WALLNUT.get())
                .add(EntityTypeRegistry.POTATO_MINE.get());
        tag(Tags.EntityTypes.SUPPORT_PLANTS)
                .add(EntityTypeRegistry.CHERRY_BOMB.get())
                .add(EntityTypeRegistry.SUNFLOWER.get());
        tag(Tags.EntityTypes.SPECIAL_PLANTS);
        tag(Tags.EntityTypes.PLANTS)
                .addTag(Tags.EntityTypes.DEFENSIVE_PLANTS)
                .addTag(Tags.EntityTypes.NOCTURNAL_PLANTS)
                .addTag(Tags.EntityTypes.DEFENSIVE_PLANTS)
                .addTag(Tags.EntityTypes.SUPPORT_PLANTS)
                .addTag(Tags.EntityTypes.SPECIAL_PLANTS);
        tag(EntityTypeTags.UNDEAD)
                .add(EntityTypeRegistry.BROWNCOAT.get())
                .add(EntityTypeRegistry.IMP.get())
                .add(EntityTypeRegistry.FOOT_SOLDIER.get())
                .add(EntityTypeRegistry.MUMMIFIED_BROWNCOAT.get());
        tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
                .remove(EntityTypeRegistry.IMP.get())
                .remove(EntityTypeRegistry.BROWNCOAT.get());
        tag(Tags.EntityTypes.CHAMPION_ZOMBIE)
                .add(EntityTypeRegistry.FOOT_SOLDIER.get());
        tag(Tags.EntityTypes.ZOMBIE_SPAWNER);
        tag(Tags.EntityTypes.SPECIAL_ZOMBIE);
        tag(Tags.EntityTypes.MINI_BOSS_ZOMBIE);
        tag(Tags.EntityTypes.BASIC_ZOMBIE)
                .add(EntityTypeRegistry.BROWNCOAT.get())
                .add(EntityTypeRegistry.IMP.get())
                .add(EntityTypeRegistry.MUMMIFIED_BROWNCOAT.get());
        tag(Tags.EntityTypes.CNC_ZOMBIES)
                .addTag(Tags.EntityTypes.CHAMPION_ZOMBIE)
                .addTag(Tags.EntityTypes.ZOMBIE_SPAWNER)
                .addTag(Tags.EntityTypes.SPECIAL_ZOMBIE)
                .addTag(Tags.EntityTypes.MINI_BOSS_ZOMBIE)
                .addTag(Tags.EntityTypes.BASIC_ZOMBIE);
        tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)
                .add(EntityTypeRegistry.SNOW_PEA.get());
        tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER)
                .add(EntityTypeRegistry.PIRATE_BROWNCOAT.get());
    }
}
