package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.Peashooter;
import uwu.llkc.cnc.common.entities.plants.Sunflower;
import uwu.llkc.cnc.common.entities.plants.Wallnut;
import uwu.llkc.cnc.common.entities.projectiles.PeaProjectile;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;

import java.util.function.Supplier;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CNCMod.MOD_ID);

    public static final Supplier<EntityType<Peashooter>> PEASHOOTER = ENTITY_TYPES.register("peashooter",
            () -> EntityType.Builder.of(Peashooter::new, MobCategory.CREATURE).sized(0.5f, 1).eyeHeight(0.7f).build(CNCMod.rlStr("peashooter")));

    public static final Supplier<EntityType<Sunflower>> SUNFLOWER = ENTITY_TYPES.register("sunflower",
            () -> EntityType.Builder.of(Sunflower::new, MobCategory.CREATURE).sized(0.5f, 1).eyeHeight(0.7f).build(CNCMod.rlStr("sunflower")));

    public static final Supplier<EntityType<Wallnut>> WALLNUT = ENTITY_TYPES.register("wallnut",
            () -> EntityType.Builder.of(Wallnut::new, MobCategory.CREATURE).sized(0.5f, 1).eyeHeight(0.7f).build(CNCMod.rlStr("wallnut")));

    public static final Supplier<EntityType<Browncoat>> BROWNCOAT = ENTITY_TYPES.register("browncoat",
            () -> EntityType.Builder.of(Browncoat::new, MobCategory.MONSTER).sized(0.6F, 1.95F).eyeHeight(1.74F).build(CNCMod.rlStr("browncoat")));

    public static final Supplier<EntityType<PeaProjectile>> PEA = ENTITY_TYPES.register("pea",
            () -> EntityType.Builder.of(PeaProjectile::new, MobCategory.MISC).sized(.5f, .5f).build(CNCMod.rlStr("pea")));

    public static final Supplier<EntityType<Boat>> WALNUT_BOAT = ENTITY_TYPES.register("walnut_boat",
            () -> EntityType.Builder.of((EntityType<Boat> type, Level level) ->
                    new Boat(type, level), MobCategory.MISC).build("walnut_boat"));

    public static final Supplier<EntityType<ChestBoat>> WALNUT_CHEST_BOAT = ENTITY_TYPES.register("walnut_chest_boat",
            () -> EntityType.Builder.of((EntityType<ChestBoat> type, Level level) ->
                    new ChestBoat(type, level), MobCategory.MISC).build("walnut_chest_boat"));
}
