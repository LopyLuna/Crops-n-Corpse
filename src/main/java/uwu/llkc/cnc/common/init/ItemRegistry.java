package uwu.llkc.cnc.common.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.*;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;
import uwu.llkc.cnc.common.items.MultiEntitySpawnEgg;
import uwu.llkc.cnc.common.items.PlantArmorItem;
import uwu.llkc.cnc.common.items.SeedPacketItem;
import uwu.llkc.cnc.common.items.TrafficConeItem;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final DeferredItem<SeedPacketItem<Entity>> EMPTY_SEED_PACKET = ITEMS.registerItem("empty_seed_packet", props -> new SeedPacketItem<>(props, 0, 0, null));
    public static final DeferredItem<SeedPacketItem<Peashooter>> PEASHOOTER_SEED_PACKET = ITEMS.registerItem("peashooter_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 16, 40, EntityTypeRegistry.PEASHOOTER));
    public static final DeferredItem<SeedPacketItem<Sunflower>> SUNFLOWER_SEED_PACKET = ITEMS.registerItem("sunflower_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 0, 200, EntityTypeRegistry.SUNFLOWER));
    public static final DeferredItem<SeedPacketItem<WallNut>> WALLNUT_SEED_PACKET = ITEMS.registerItem("wallnut_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 8, 320, EntityTypeRegistry.WALLNUT));
    public static final DeferredItem<SeedPacketItem<PotatoMine>> POTATO_MINE_SEED_PACKET = ITEMS.registerItem("potato_mine_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 4, 320, EntityTypeRegistry.POTATO_MINE));
    public static final DeferredItem<SeedPacketItem<CherryBomb>> CHERRY_BOMB_SEED_PACKET = ITEMS.registerItem("cherry_bomb_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 480, 45, EntityTypeRegistry.CHERRY_BOMB));

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
    public static final DeferredItem<Item> WALNUT = ITEMS.registerSimpleItem("walnut", new Item.Properties().food(Foods.WALNUT));
    public static final DeferredItem<TrafficConeItem> TRAFFIC_CONE = ITEMS.registerItem("traffic_cone", TrafficConeItem::new, new Item.Properties().durability(15));
    public static final DeferredItem<Item> FLAG = ITEMS.registerSimpleItem("flag");
    public static final DeferredItem<Item> PLANT_ARMOR = ITEMS.registerItem("plant_armor",
            PlantArmorItem::new, new Item.Properties().stacksTo(1));
    @SuppressWarnings("unchecked")
    public static final DeferredItem<MultiEntitySpawnEgg> BROWNCOAT_SPAWN_EGG = ITEMS.registerItem("browncoat_spawn_egg", props -> new MultiEntitySpawnEgg(props, List.of(
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> {}),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FLAG.get()))),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TRAFFIC_CONE.get()))),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>)(Object) EntityTypeRegistry.BROWNCOAT, mob -> mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.BUCKET)))
    )));
    public static final DeferredItem<Item> IMP_SPAWN_EGG = ITEMS.registerItem("imp_spawn_egg",
            props -> new DeferredSpawnEggItem(EntityTypeRegistry.IMP, 0xFFFFFF, 0xFFFFFF, props));

    public static final DeferredItem<BlockItem> STRIPPED_WALNUT_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_WALNUT_LOG);
    public static final DeferredItem<BlockItem> WALNUT_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_LOG);
    public static final DeferredItem<BlockItem> WALNUT_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_LEAVES);
    public static final DeferredItem<BlockItem> WALNUT_SAPLING = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_SAPLING);
    public static final DeferredItem<BlockItem> WALNUT_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_WALNUT_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_WALNUT_WOOD);
    public static final DeferredItem<BlockItem> WALNUT_PLANKS = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_PLANKS);
    public static final DeferredItem<BlockItem> WALNUT_SIGN = ITEMS.registerItem("walnut_sign",
            props -> new SignItem(props.stacksTo(16), BlockRegistry.STANDING_WALNUT_SIGN.get(), BlockRegistry.WALNUT_WALL_SIGN.get()));
    public static final DeferredItem<BlockItem> HANGING_WALNUT_SIGN = ITEMS.registerItem("hanging_walnut_sign",
            props -> new HangingSignItem(BlockRegistry.CEILING_HANGING_WALNUT_SIGN.get(), BlockRegistry.WALL_HANGING_WALNUT_SIGN.get(), props.stacksTo(16)));
    public static final DeferredItem<BlockItem> WALNUT_TRAP_DOOR = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_TRAPDOOR);
    public static final DeferredItem<BlockItem> WALNUT_DOOR = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_DOOR);
    public static final DeferredItem<BlockItem> WALNUT_BUTTON = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_BUTTON);
    public static final DeferredItem<BlockItem> WALNUT_FENCE = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_FENCE);
    public static final DeferredItem<BlockItem> WALNUT_FENCE_GATE = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_FENCE_GATE);
    public static final DeferredItem<BlockItem> WALNUT_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> WALNUT_STAIRS = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_STAIRS);
    public static final DeferredItem<BlockItem> WALNUT_SLAB = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_SLAB);
    public static final DeferredItem<BoatItem> WALNUT_BOAT = ITEMS.registerItem("walnut_boat",
            props -> new BoatItem(false, Boat.Type.valueOf("CNC_WALNUT"), props), new Item.Properties().stacksTo(1));
    public static final DeferredItem<BoatItem> WALNUT_CHEST_BOAT = ITEMS.registerItem("walnut_chest_boat",
            props -> new BoatItem(true, Boat.Type.valueOf("CNC_WALNUT"), props), new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> MASHED_POTATOES = ITEMS.registerSimpleItem("mashed_potatoes",
            new Item.Properties().food(Foods.MASHED_POTATOES));

    static class Foods {
        public static final FoodProperties PEA_POD = new FoodProperties.Builder().nutrition(9).saturationModifier(0.5f).build();
        public static final FoodProperties RAW_PEA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.2f).fast().build();
        public static final FoodProperties COOKED_PEA = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).fast().build();
        public static final FoodProperties SUNFLOWER_SEEDS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build();
        public static final FoodProperties SUNFLOWER_SEED_MUFFIN = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties CHOCOLATE_CHIP_MUFFIN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.4f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties WALNUT = Util.make(() -> {
            var food = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f);
            food.eatSeconds *= 3;
            return food.build();
        });
        public static final FoodProperties MASHED_POTATOES = new FoodProperties.Builder().nutrition(7).saturationModifier(0.23f).usingConvertsTo(Items.BOWL).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 40), 1).build();
    }
}
