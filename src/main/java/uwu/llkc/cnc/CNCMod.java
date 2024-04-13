package uwu.llkc.cnc;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import uwu.llkc.cnc.datagen.ModDataGenerators;
import uwu.llkc.cnc.registry.ModBlocks;
import uwu.llkc.cnc.registry.ModCreativeTab;
import uwu.llkc.cnc.registry.ModItems;

@Mod(CNCMod.MOD_ID)
public class CNCMod {

    private static CNCMod INSTANCE;
    public static final String MOD_ID = "cnc";
    public static final Logger LOGGER = LogUtils.getLogger();
    private final IEventBus modEventBus;

    public CNCMod(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
        INSTANCE = this;


        ModItems.staticInit();
        ModBlocks.staticInit();
        ModCreativeTab.staticInit();

        modEventBus.addListener(this::commonSetup);


        NeoForge.EVENT_BUS.register(this);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CNCConfig.SPEC);

        this.modEventBus.addListener(ModDataGenerators::gatherDataEvent);
        this.modEventBus.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }


    public static IEventBus getEventBus() {
        return INSTANCE.modEventBus;
    }
}
