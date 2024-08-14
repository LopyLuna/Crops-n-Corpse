package uwu.llkc.cnc.common.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.items.SeedPacketItem;
import uwu.llkc.cnc.common.items.TrafficConeItem;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final DeferredItem<SeedPacketItem<?>> EMPTY_SEED_PACKET = ITEMS.registerItem("empty_seed_packet", props -> new SeedPacketItem<>(props, 0, 0, null));
    public static final DeferredItem<SeedPacketItem<?>> PEASHOOTER_SEED_PACKET = ITEMS.registerItem("peashooter_seed_packet", props -> new SeedPacketItem<>(props, 16, 20, EntityTypeRegistry.PEASHOOTER));
    public static final DeferredItem<SeedPacketItem<?>> SUNFLOWER_SEED_PACKET = ITEMS.registerItem("sunflower_seed_packet", props -> new SeedPacketItem<>(props, 0, 20, EntityTypeRegistry.SUNFLOWER));

    public static final DeferredItem<Item> PLANT_FOOD = ITEMS.registerSimpleItem("plant_food");
    public static final DeferredItem<Item> SUN = ITEMS.registerSimpleItem("sun");
    public static final DeferredItem<BlockItem> RAW_PEA = ITEMS.registerItem("raw_pea",
            props -> new ItemNameBlockItem(BlockRegistry.PEASHOOTER_CROP.get(), props), new Item.Properties().food(Foods.RAW_PEA));
    public static final DeferredItem<Item> COOKED_PEA = ITEMS.registerSimpleItem("cooked_pea", new Item.Properties().food(Foods.COOKED_PEA));
    public static final DeferredItem<Item> PEA_POD = ITEMS.registerSimpleItem("pea_pod", new Item.Properties().food(Foods.PEA_POD));
    public static final DeferredItem<Item> SUNFLOWER_SEED_MUFFIN = ITEMS.registerSimpleItem("sunflower_seed_muffin", new Item.Properties().food(Foods.SUNFLOWER_SEED_MUFFIN));
    public static final DeferredItem<Item> CHOCOLATE_CHIP_MUFFIN = ITEMS.registerSimpleItem("chocolate_chip_muffin", new Item.Properties().food(Foods.CHOCOLATE_CHIP_MUFFIN));
    public static final DeferredItem<BlockItem> SUNFLOWER_SEEDS = ITEMS.registerItem("sunflower_seeds",
            props -> new ItemNameBlockItem(BlockRegistry.SUNFLOWER_CROP.get(), props), new Item.Properties().food(Foods.SUNFLOWER_SEEDS));
    public static final DeferredItem<Item> WALNUT_FLOUR = ITEMS.registerSimpleItem("walnut_flour");
    public static final DeferredItem<Item> WALNUT = ITEMS.registerSimpleItem("walnut");
    public static final DeferredItem<TrafficConeItem> TRAFFIC_CONE = ITEMS.registerItem("traffic_cone", TrafficConeItem::new, new Item.Properties().durability(15));

    static class Foods {
        public static final FoodProperties PEA_POD = new FoodProperties.Builder().nutrition(9).saturationModifier(0.5f).build();
        public static final FoodProperties RAW_PEA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).fast().build();
        public static final FoodProperties COOKED_PEA = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).fast().build();
        public static final FoodProperties SUNFLOWER_SEEDS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
        public static final FoodProperties SUNFLOWER_SEED_MUFFIN = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties CHOCOLATE_CHIP_MUFFIN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.4f).usingConvertsTo(Items.PAPER).build();
    }
}
