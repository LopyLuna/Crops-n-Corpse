package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;

import java.util.function.Supplier;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CNCMod.MOD_ID);

    public static final Supplier<SoundEvent> PEASHOOTER_DEATH = SOUNDS.register("peashooter_death",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.peashooter.death")));

    public static final Supplier<SoundEvent> PEASHOOTER_HURT = SOUNDS.register("peashooter_hurt",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.peashooter.hurt")));

    public static final Supplier<SoundEvent> PLANT_SPAWN = SOUNDS.register("plant_spawn",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("item.seed_packet.spawn")));

    public static final Supplier<SoundEvent> PEASHOOTER_SHOOT = SOUNDS.register("peashooter_shoot",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.peashooter.shoot")));

    public static final Supplier<SoundEvent> SUNFLOWER_DEATH = SOUNDS.register("sunflower_death",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.sunflower.death")));

    public static final Supplier<SoundEvent> SUNFLOWER_HURT = SOUNDS.register("sunflower_hurt",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.sunflower.hurt")));

    public static final Supplier<SoundEvent> SUNFLOWER_PRODUCE = SOUNDS.register("sunflower_produce",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.sunflower.produce")));

    public static final Supplier<SoundEvent> BROWNCOAT_AMBIANCE = SOUNDS.register("browncoat_ambiance",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.browncoat.ambiance")));

    public static final Supplier<SoundEvent> BROWNCOAT_ATTACK = SOUNDS.register("browncoat_attack",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.browncoat.attack")));

    public static final Supplier<SoundEvent> BROWNCOAT_DEATH = SOUNDS.register("browncoat_death",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.browncoat.death")));

    public static final Supplier<SoundEvent> BROWNCOAT_HURT = SOUNDS.register("browncoat_hurt",
            () -> SoundEvent.createVariableRangeEvent(CNCMod.rl("entity.browncoat.hurt")));
}
