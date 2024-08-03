package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import uwu.llkc.cnc.CNCMod;

public class Tags {
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> PLANTS = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), CNCMod.rl("plants"));
    }

    public static class BiomeTags {
        public static final TagKey<Biome> SPAWNS_PEASHOOTER = TagKey.create(Registries.BIOME, CNCMod.rl("spawns_peashooter"));
        public static final TagKey<Biome> SPAWNS_SUNFLOWER = TagKey.create(Registries.BIOME, CNCMod.rl("spawns_sunflower"));
    }
}
