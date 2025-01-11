package uwu.llkc.cnc.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.joml.Quaternionf;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.models.ChillModel;
import uwu.llkc.cnc.client.models.entity.*;
import uwu.llkc.cnc.client.renderers.FreezeLayer;
import uwu.llkc.cnc.client.renderers.entity.*;
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
        event.registerEntityRenderer(EntityTypeRegistry.SNOW_PEA.get(), SnowPeaRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.SUNFLOWER.get(), SunflowerRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.WALLNUT.get(), WallNutRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.POTATO_MINE.get(), PotatoMineRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.PEA.get(), PeaProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.FROZEN_PEA.get(), FrozenPeaProjectileRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.BROWNCOAT.get(), BrowncoatRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.FOOT_SOLDIER.get(), FootSoldierRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.IMP.get(), ImpRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.WALNUT_BOAT.get(), context -> new BoatRenderer(context, false));
        event.registerEntityRenderer(EntityTypeRegistry.WALNUT_CHEST_BOAT.get(), context -> new BoatRenderer(context, true));
        event.registerEntityRenderer(EntityTypeRegistry.CHERRY_BOMB.get(), CherryBombRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.REPEATER.get(), RepeaterRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.ZOMBIE_STINK_BOMB.get(), ZombieStinkBombRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityTypeRegistry.CUSTOM_SIGN.get(), SignRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityTypeRegistry.CUSTOM_HANGING_SIGN.get(), HangingSignRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PeashooterModel.MAIN_LAYER, PeashooterModel::createBodyLayer);
        event.registerLayerDefinition(SnowPeaModel.MAIN_LAYER, SnowPeaModel::createBodyLayer);
        event.registerLayerDefinition(SunflowerModel.MAIN_LAYER, SunflowerModel::createBodyLayer);
        event.registerLayerDefinition(BrowncoatModel.MAIN_LAYER, BrowncoatModel::createBodyLayer);
        event.registerLayerDefinition(WallNutModel.MAIN_LAYER, WallNutModel::createBodyLayer);
        event.registerLayerDefinition(WallNutArmorLayer.ARMOR, WallNutArmorLayer::createArmor);
        event.registerLayerDefinition(ImpModel.MAIN_LAYER, ImpModel::createBodyLayer);
        event.registerLayerDefinition(PotatoMineModel.MAIN_LAYER, PotatoMineModel::createBodyLayer);
        event.registerLayerDefinition(CherryBombModel.MAIN_LAYER, CherryBombModel::createBodyLayer);
        event.registerLayerDefinition(RepeaterModel.MAIN_LAYER, RepeaterModel::createBodyLayer);
        event.registerLayerDefinition(FootSoldierModel.MAIN_LAYER, FootSoldierModel::createBodyLayer);
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

    @SubscribeEvent
    public static void registerModels(final ModelEvent.RegisterAdditional event) {
        event.register(ChillModel.CHILL_CRYSTAL);
    }

    @SubscribeEvent
    public static void renderEvent(final RegisterGuiLayersEvent event) {
        event.registerBelowAll(FreezeLayer.FREEZE_LAYER, new FreezeLayer());
    }

    @SubscribeEvent
    public static void registerClientItemExtensions(final RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                if (player.getUseItem().equals(itemInHand) && player.getUseItemRemainingTicks() > 0) {
                    poseStack.translate(0.3, -0.3, -0.5);
                    poseStack.mulPose(new Quaternionf().fromAxisAngleDeg(1, 0, 0, -20));
                }
                return false;
            }
        }, ItemRegistry.SUN_WAND);
    }
}
