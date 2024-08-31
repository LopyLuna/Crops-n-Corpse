package uwu.llkc.cnc.common.events;

import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.Peashooter;
import uwu.llkc.cnc.common.entities.plants.Sunflower;
import uwu.llkc.cnc.common.entities.plants.Wallnut;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.items.MultiEntitySpawnEgg;
import uwu.llkc.cnc.common.networking.DropEquipmentPayload;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void entityAttributeCreation(final EntityAttributeCreationEvent event) {
        event.put(EntityTypeRegistry.PEASHOOTER.get(), Peashooter.attributes().build());
        event.put(EntityTypeRegistry.SUNFLOWER.get(), Sunflower.attributes().build());
        event.put(EntityTypeRegistry.WALLNUT.get(), Wallnut.attributes().build());
        event.put(EntityTypeRegistry.BROWNCOAT.get(), Browncoat.attributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(final RegisterSpawnPlacementsEvent event) {
        event.register(EntityTypeRegistry.PEASHOOTER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Peashooter::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityTypeRegistry.SUNFLOWER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Sunflower::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityTypeRegistry.BROWNCOAT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Browncoat::checkAnyLightMonsterSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(EntityTypeRegistry.WALLNUT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Wallnut::checkMobSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }

    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                DropEquipmentPayload.TYPE,
                DropEquipmentPayload.STREAM_CODEC,
                DropEquipmentPayload::handleData);
    }

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (MultiEntitySpawnEgg egg : MultiEntitySpawnEgg.EGGS) {
                DispenseItemBehavior dispenseBehavior = egg.createDispenseBehavior();
                if (dispenseBehavior != null) {
                    DispenserBlock.registerBehavior(egg, dispenseBehavior);
                }
            }
            WoodType.register(BlockRegistry.WoodTypes.WALNUT);
        });
    }
}
