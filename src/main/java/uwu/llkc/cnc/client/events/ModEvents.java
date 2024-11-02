package uwu.llkc.cnc.client.events;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.entities.models.*;
import uwu.llkc.cnc.client.entities.renderers.*;
import uwu.llkc.cnc.common.init.BlockEntityTypeRegistry;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.items.properties.MultiEntitySpawnEggProperty;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.PEASHOOTER.get(), PeashooterRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.SUNFLOWER.get(), SunflowerRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.WALLNUT.get(), WallNutRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.POTATO_MINE.get(), PotatoMineRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.PEA.get(), PeaProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.BROWNCOAT.get(), BrowncoatRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.IMP.get(), ImpRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.WALNUT_BOAT.get(), context -> new BoatRenderer(context, false));
        event.registerEntityRenderer(EntityTypeRegistry.WALNUT_CHEST_BOAT.get(), context -> new BoatRenderer(context, true));
        event.registerEntityRenderer(EntityTypeRegistry.CHERRY_BOMB.get(), CherryBombRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.REPEATER.get(), RepeaterRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityTypeRegistry.CUSTOM_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityTypeRegistry.CUSTOM_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PeashooterModel.MAIN_LAYER, PeashooterModel::createBodyLayer);
        event.registerLayerDefinition(SunflowerModel.MAIN_LAYER, SunflowerModel::createBodyLayer);
        event.registerLayerDefinition(BrowncoatModel.MAIN_LAYER, BrowncoatModel::createBodyLayer);
        event.registerLayerDefinition(WallNutModel.MAIN_LAYER, WallNutModel::createBodyLayer);
        event.registerLayerDefinition(WallNutArmorLayer.ARMOR, WallNutArmorLayer::createArmor);
        event.registerLayerDefinition(ImpModel.MAIN_LAYER, ImpModel::createBodyLayer);
        event.registerLayerDefinition(PotatoMineModel.MAIN_LAYER, PotatoMineModel::createBodyLayer);
        event.registerLayerDefinition(CherryBombModel.MAIN_LAYER, CherryBombModel::createBodyLayer);
        event.registerLayerDefinition(RepeaterModel.MAIN_LAYER, RepeaterModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ItemRegistry.BROWNCOAT_SPAWN_EGG.get(), MultiEntitySpawnEggProperty.ID, MultiEntitySpawnEggProperty.INSTANCE);
            Sheets.addWoodType(BlockRegistry.WoodTypes.WALNUT);
        });
    }

    @SubscribeEvent
    public static void colorBlocks(final RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, index) -> level != null && pos != null
                ? BiomeColors.getAverageFoliageColor(level, pos)
                : FoliageColor.getDefaultColor(), BlockRegistry.WALNUT_LEAVES.get());
    }

    @SubscribeEvent
    public static void colorItems(final RegisterColorHandlersEvent.Item event) {
        event.register((stack, index) -> {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return event.getBlockColors().getColor(blockstate, null, null, index);
        }, ItemRegistry.WALNUT_LEAVES);
    }
}
