package uwu.llkc.cnc.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import uwu.llkc.cnc.CNCMod;

public class DamageTypeInit {
    public static final ResourceKey<DamageType> PEA_SHOT = ResourceKey.create(Registries.DAMAGE_TYPE, CNCMod.rl("pea_shot"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(PEA_SHOT, new DamageType("damage_type.cnc.pea_shot", 0.1f));
    }
}
