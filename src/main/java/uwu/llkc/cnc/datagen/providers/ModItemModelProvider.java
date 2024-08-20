package uwu.llkc.cnc.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.items.properties.MultiEntitySpawnEggProperty;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CNCMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ItemRegistry.PLANT_FOOD.get());
        basicItem(ItemRegistry.SUN.get());
        basicItem(ItemRegistry.PEA_POD.get());
        basicItem(ItemRegistry.COOKED_PEA.get());
        basicItem(ItemRegistry.RAW_PEA.get());
        basicItem(ItemRegistry.SUNFLOWER_SEEDS.get());
        basicItem(ItemRegistry.SUNFLOWER_SEED_MUFFIN.get());
        basicItem(ItemRegistry.CHOCOLATE_CHIP_MUFFIN.get());
        basicItem(ItemRegistry.WALNUT_FLOUR.get());
        basicItem(ItemRegistry.WALNUT.get());
        basicItem(ItemRegistry.EMPTY_SEED_PACKET.get());
        basicItem(ItemRegistry.PEASHOOTER_SEED_PACKET.get());
        basicItem(ItemRegistry.SUNFLOWER_SEED_PACKET.get());

        withExistingParent(CNCMod.rlStr("traffic_cone"), "neoforge:item/default")
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(nested().parent(basicItem(CNCMod.rl("traffic_cone_item"))))
                .perspective(ItemDisplayContext.HEAD, nested().parent(getExistingFile(CNCMod.rl("block/traffic_cone")))
                        .transforms().transform(ItemDisplayContext.HEAD).scale(1.6f).translation(0, 17, 0).end().end())
                .end();

        withExistingParent(ResourceLocation.withDefaultNamespace("bucket").toString(), "neoforge:item/default")
                .customLoader(SeparateTransformsModelBuilder::begin)
                .base(nested().parent(basicItem(CNCMod.rl("bucket_item"))))
                .perspective(ItemDisplayContext.HEAD, nested().parent(getExistingFile(CNCMod.rl("item/bucket"))))
                .end();

        withExistingParent("browncoat_spawn_egg", "item/generated")
                .texture("layer0", CNCMod.rl("item/browncoat_spawn_egg")).override()
                .model(basicItem(CNCMod.rl("flag_spawn_egg")))
                .predicate(MultiEntitySpawnEggProperty.ID, 0.01f).end().override()
                .model(basicItem(CNCMod.rl("conehead_spawn_egg")))
                .predicate(MultiEntitySpawnEggProperty.ID, 0.02f).end().override()
                .model(basicItem(CNCMod.rl("buckethead_spawn_egg")))
                .predicate(MultiEntitySpawnEggProperty.ID, 0.03f).end();
    }
}