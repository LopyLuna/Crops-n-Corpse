package uwu.llkc.cnc.util;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModUtils {
    private static final Consumer<?> NO_ACTION = (a) -> {};

    public static ResourceLocation location(String path) {
        return new ResourceLocation(CNCMod.MOD_ID, path);
    }

    public static <DR extends DeferredRegister<T>, T> DR createRegister(Function<String, DR> factory) {
        return registerToBus(factory.apply(CNCMod.MOD_ID));
    }

    public static <T> DeferredRegister<T> createRegister(ResourceKey<Registry<T>> registry) {
        return registerToBus(DeferredRegister.create(registry, CNCMod.MOD_ID));
    }

    private static <DR extends DeferredRegister<T>, T> DR registerToBus(DR deferredRegister) {
        deferredRegister.register(CNCMod.getEventBus());
        return deferredRegister;
    }

    @SuppressWarnings("unchecked")
    public static <T> Consumer<T> noAction() {
        return ((Consumer<T>) NO_ACTION);
    }

    public static int secondsToTicks(int seconds) {
        return seconds * 20;
    }

    public static float secondsToTicks(float seconds) {
        return seconds * 20.0F;
    }

    public static int roundThat(Float n)
    {
        return (int)(n % 1 > 0.5 ? Math.ceil(n) : Math.floor(n));
    }
    public static float flightMeterMax = 100.0F;
}