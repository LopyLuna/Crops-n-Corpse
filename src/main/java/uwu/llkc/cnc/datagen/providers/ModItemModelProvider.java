package uwu.llkc.cnc.datagen.providers;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.ItemRegistry;

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
    }
}
