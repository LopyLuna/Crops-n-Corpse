package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.Peashooter;
import uwu.llkc.cnc.common.entities.projectiles.PeaProjectile;

import java.util.function.Supplier;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CNCMod.MOD_ID);

    public static final Supplier<EntityType<Peashooter>> PEASHOOTER = ENTITY_TYPES.register("peashooter",
            () -> EntityType.Builder.of(Peashooter::new, MobCategory.MISC).sized(0.5f, 1).eyeHeight(0.7f).build(CNCMod.rlStr("peashooter")));

    public static final Supplier<EntityType<PeaProjectile>> PEA = ENTITY_TYPES.register("pea",
            () -> EntityType.Builder.of(PeaProjectile::new, MobCategory.MISC).sized(.5f, .5f).build(CNCMod.rlStr("pea")));
}
