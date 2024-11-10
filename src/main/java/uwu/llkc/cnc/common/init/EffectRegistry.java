package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.effects.ChillEffect;

public class EffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, CNCMod.MOD_ID);

    public static final DeferredHolder<MobEffect, ChillEffect> CHILL = EFFECTS.register("chill", () ->
            new ChillEffect(MobEffectCategory.HARMFUL, 0x368BC1));


}
