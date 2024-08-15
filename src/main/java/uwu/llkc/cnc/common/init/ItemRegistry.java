package uwu.llkc.cnc.common.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.items.MultiEntitySpawnEgg;
import uwu.llkc.cnc.common.items.SeedPacketItem;
import uwu.llkc.cnc.common.items.TrafficConeItem;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final DeferredItem<SeedPacketItem<?>> EMPTY_SEED_PACKET = ITEMS.registerItem("empty_seed_packet", props -> new SeedPacketItem<>(props, 0, 0, null));
    public static final DeferredItem<SeedPacketItem<?>> PEASHOOTER_SEED_PACKET = ITEMS.registerItem("peashooter_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 16, 20, EntityTypeRegistry.PEASHOOTER));
    public static final DeferredItem<SeedPacketItem<?>> SUNFLOWER_SEED_PACKET = ITEMS.registerItem("sunflower_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 0, 20, EntityTypeRegistry.SUNFLOWER));

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
    public static final DeferredItem<Item> FLAG = ITEMS.registerSimpleItem("flag");
    public static final DeferredItem<MultiEntitySpawnEgg> BROWNCOAT_SPAWN_EGG = ITEMS.registerItem("browncoat_spawn_egg", props -> new MultiEntitySpawnEgg(props, List.of(
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> {
                mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                mob.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
            }),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> {
                mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FLAG.get()));
                mob.setItemSlot(EquipmentSlot.HEAD, ItemStack.EMPTY);
            }),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> {
                mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TRAFFIC_CONE.get()));
                mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> {
                mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.BUCKET));
                mob.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            })
    )));

    static class Foods {
        public static final FoodProperties PEA_POD = new FoodProperties.Builder().nutrition(9).saturationModifier(0.5f).build();
        public static final FoodProperties RAW_PEA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).fast().build();
        public static final FoodProperties COOKED_PEA = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).fast().build();
        public static final FoodProperties SUNFLOWER_SEEDS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
        public static final FoodProperties SUNFLOWER_SEED_MUFFIN = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties CHOCOLATE_CHIP_MUFFIN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.4f).usingConvertsTo(Items.PAPER).build();
    }
}
