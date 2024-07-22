package uwu.llkc.cnc.client.events;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.PeashooterModel;
import uwu.llkc.cnc.client.entities.renderers.PeaProjectileRenderer;
import uwu.llkc.cnc.client.entities.renderers.PeashooterRenderer;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.items.properties.SeedPacketProperty;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ItemRegistry.SEED_PACKET.get(), SeedPacketProperty.ID, SeedPacketProperty.INSTANCE);
        });
    }

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.PEASHOOTER.get(), PeashooterRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.PEA.get(), PeaProjectileRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PeashooterModel.MAIN_LAYER, PeashooterModel::createBodyLayer);
    }
}
