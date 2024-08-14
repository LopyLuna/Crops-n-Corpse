package uwu.llkc.cnc.client.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.BrowncoatModel;
import uwu.llkc.cnc.client.entities.models.PeashooterModel;
import uwu.llkc.cnc.client.entities.models.SunflowerModel;
import uwu.llkc.cnc.client.entities.renderers.BrowncoatRenderer;
import uwu.llkc.cnc.client.entities.renderers.PeaProjectileRenderer;
import uwu.llkc.cnc.client.entities.renderers.PeashooterRenderer;
import uwu.llkc.cnc.client.entities.renderers.SunflowerRenderer;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.items.TrafficConeItem;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.PEASHOOTER.get(), PeashooterRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.SUNFLOWER.get(), SunflowerRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.PEA.get(), PeaProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.BROWNCOAT.get(), BrowncoatRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PeashooterModel.MAIN_LAYER, PeashooterModel::createBodyLayer);
        event.registerLayerDefinition(SunflowerModel.MAIN_LAYER, SunflowerModel::createBodyLayer);
        event.registerLayerDefinition(BrowncoatModel.MAIN_LAYER, BrowncoatModel::createBodyLayer);
    }
}
