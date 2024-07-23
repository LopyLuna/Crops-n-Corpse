package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import uwu.llkc.cnc.CNCMod;

public class Tags {
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> PLANTS = TagKey.create(BuiltInRegistries.ENTITY_TYPE.key(), CNCMod.rl("plants"));
    }
}
