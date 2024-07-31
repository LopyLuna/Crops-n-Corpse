package uwu.llkc.cnc.common.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.items.SeedPacketItem;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final DeferredHolder<Item, SeedPacketItem> SEED_PACKET = ITEMS.registerItem("seed_packet", SeedPacketItem::new);
    public static final Supplier<Item> PLANT_FOOD = ITEMS.registerSimpleItem("plant_food");
    public static final Supplier<Item> SUN = ITEMS.registerSimpleItem("sun");
    public static final DeferredHolder<Item, BlockItem> RAW_PEA = ITEMS.registerSimpleBlockItem("raw_pea", BlockRegistry.PEASHOOTER_CROP, new Item.Properties().food(Foods.RAW_PEA));
    public static final Supplier<Item> COOKED_PEA = ITEMS.registerSimpleItem("cooked_pea", new Item.Properties().food(Foods.COOKED_PEA));
    public static final DeferredHolder<Item, Item> PEA_POD = ITEMS.registerSimpleItem("pea_pod", new Item.Properties().food(Foods.PEA_POD));

    static class Foods {
        public static final FoodProperties PEA_POD = new FoodProperties.Builder().nutrition(9).saturationModifier(0.5f).build();
        public static final FoodProperties RAW_PEA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).fast().build();
        public static final FoodProperties COOKED_PEA = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build();
    }
}
