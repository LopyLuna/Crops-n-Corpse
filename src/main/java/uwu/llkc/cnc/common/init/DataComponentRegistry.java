package uwu.llkc.cnc.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;

import java.util.function.Supplier;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, CNCMod.MOD_ID);

    public static final Supplier<DataComponentType<Integer>> SELECTED_ENTITY = DATA_COMPONENTS.registerComponentType("selected_entity",
            builder -> builder.networkSynchronized(ByteBufCodecs.INT).persistent(Codec.INT));

    public static final Supplier<DataComponentType<Integer>> SUN_WAND_SUNS = DATA_COMPONENTS.registerComponentType("sun_wand_suns",
            builder -> builder.networkSynchronized(ByteBufCodecs.INT).persistent(Codec.INT));

}
