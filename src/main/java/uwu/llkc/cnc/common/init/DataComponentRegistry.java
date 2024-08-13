package uwu.llkc.cnc.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;

import java.util.function.Supplier;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(CNCMod.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> SUN_COST = DATA_COMPONENTS.registerComponentType("sun_cost",
            builder -> builder.networkSynchronized(ByteBufCodecs.INT).persistent(Codec.INT));

    public static final Supplier<DataComponentType<Integer>> COOLDOWN = DATA_COMPONENTS.registerComponentType("cooldown",
            builder -> builder.networkSynchronized(ByteBufCodecs.INT).persistent(Codec.INT));
}
