package uwu.llkc.cnc.common.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.Peashooter;
import uwu.llkc.cnc.common.entities.plants.Sunflower;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(EntityTypeRegistry.PEASHOOTER.get(), Peashooter.attributes().build());
        event.put(EntityTypeRegistry.SUNFLOWER.get(), Sunflower.attributes().build());
        event.put(EntityTypeRegistry.BROWNCOAT.get(), Browncoat.attributes().build());
    }
}
