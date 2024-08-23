package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import uwu.llkc.cnc.CNCMod;

public class Tags {
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> PLANTS = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), CNCMod.rl("plants"));
    }

    public static class Biomes {
        public static final TagKey<Biome> SPAWNS_PEASHOOTER = TagKey.create(Registries.BIOME, CNCMod.rl("spawns_peashooter"));
        public static final TagKey<Biome> SPAWNS_SUNFLOWER = TagKey.create(Registries.BIOME, CNCMod.rl("spawns_sunflower"));
    }

    public static class Blocks {
        public static final TagKey<Block> WALNUT_LOGS = TagKey.create(Registries.BLOCK, CNCMod.rl("walnut_logs"));
    }

    public static class Items {
        public static final TagKey<Item> WALNUT_LOGS = TagKey.create(Registries.ITEM, CNCMod.rl("walnut_logs"));
    }
}
