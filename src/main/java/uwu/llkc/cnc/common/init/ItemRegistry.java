package uwu.llkc.cnc.common.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.plants.*;
import uwu.llkc.cnc.common.items.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CNCMod.MOD_ID);

    public static final DeferredItem<SeedPacketItem<Peashooter>> PEASHOOTER_SEED_PACKET = ITEMS.registerItem("peashooter_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 16, 40, EntityTypeRegistry.PEASHOOTER, false));
    public static final DeferredItem<SeedPacketItem<Sunflower>> SUNFLOWER_SEED_PACKET = ITEMS.registerItem("sunflower_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 0, 200, EntityTypeRegistry.SUNFLOWER, false));
    public static final DeferredItem<SeedPacketItem<WallNut>> WALLNUT_SEED_PACKET = ITEMS.registerItem("wallnut_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 8, 320, EntityTypeRegistry.WALLNUT, false));
    public static final DeferredItem<SeedPacketItem<PotatoMine>> POTATO_MINE_SEED_PACKET = ITEMS.registerItem("potato_mine_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 4, 320, EntityTypeRegistry.POTATO_MINE, false));
    public static final DeferredItem<SeedPacketItem<Repeater>> REPEATER_SEED_PACKET = ITEMS.registerItem("repeater_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 32, 60, EntityTypeRegistry.REPEATER, false));
    public static final DeferredItem<SeedPacketItem<CherryBomb>> CHERRY_BOMB_SEED_PACKET = ITEMS.registerItem("cherry_bomb_seed_packet", props -> new SeedPacketItem<>(props.stacksTo(1), 24, 900, EntityTypeRegistry.CHERRY_BOMB, true) {
        @Override
        protected <F extends Entity> Consumer<F> getConsumer(ServerLevel level, ItemStack stack, Player player, InteractionHand hand) {
            return super.<F>getConsumer(level, stack, player, hand).andThen(entity -> {
                if (hand == InteractionHand.MAIN_HAND) {
                    if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.FLINT_AND_STEEL)) {
                        entity.getEntityData().set(CherryBomb.IGNITED, true);
                    }
                } else {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.FLINT_AND_STEEL)) {
                        entity.getEntityData().set(CherryBomb.IGNITED, true);
                    }
                }
            });
        }
    });
    public static final DeferredItem<SeedPacketItem<Entity>> EMPTY_SEED_PACKET = ITEMS.registerItem("empty_seed_packet", props -> new SeedPacketItem<>(props, 0, 0, null, false));

    public static final DeferredItem<Item> PLANT_FOOD = ITEMS.registerSimpleItem("plant_food");
    public static final DeferredItem<Item> SUN = ITEMS.registerSimpleItem("sun");
    public static final DeferredItem<Item> PLANT_ARMOR = ITEMS.registerItem("plant_armor",
            PlantArmorItem::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<Item> IMP_SPAWN_EGG = ITEMS.registerItem("imp_spawn_egg",
            props -> new DeferredSpawnEggItem(EntityTypeRegistry.IMP, 0xFFFFFF, 0xFFFFFF, props));
    public static final DeferredItem<TrafficConeItem> TRAFFIC_CONE = ITEMS.registerItem("traffic_cone", TrafficConeItem::new, new Item.Properties().durability(15));
    public static final DeferredItem<Item> FLAG = ITEMS.registerSimpleItem("flag");
    @SuppressWarnings("unchecked")
    public static final DeferredItem<MultiEntitySpawnEggItem> BROWNCOAT_SPAWN_EGG = ITEMS.registerItem("browncoat_spawn_egg", props -> new MultiEntitySpawnEggItem(props, List.of(
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>) (Object) EntityTypeRegistry.BROWNCOAT, mob -> {
            }),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>) (Object) EntityTypeRegistry.BROWNCOAT, mob -> mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(FLAG.get()))),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>) (Object) EntityTypeRegistry.BROWNCOAT, mob -> mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(TRAFFIC_CONE.get()))),
            new Pair<Supplier<EntityType<? extends Mob>>, Consumer<Mob>>((Supplier<EntityType<? extends Mob>>) (Object) EntityTypeRegistry.BROWNCOAT, mob -> mob.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.BUCKET)))
    )));
    public static final DeferredItem<Item> PEA_POD = ITEMS.registerSimpleItem("pea_pod", new Item.Properties().food(Foods.PEA_POD));
    public static final DeferredItem<BlockItem> RAW_PEA = ITEMS.registerItem("raw_pea",
            props -> new ItemNameBlockItem(BlockRegistry.PEASHOOTER_CROP.get(), props), new Item.Properties().food(Foods.RAW_PEA));
    public static final DeferredItem<Item> COOKED_PEA = ITEMS.registerSimpleItem("cooked_pea", new Item.Properties().food(Foods.COOKED_PEA));
    public static final DeferredItem<Item> SUNFLOWER_SEEDS = ITEMS.registerItem("sunflower_seeds",
            props -> new ItemNameBlockItem(BlockRegistry.SUNFLOWER_CROP.get(), props), new Item.Properties().food(Foods.SUNFLOWER_SEEDS));
    public static final DeferredItem<Item> WALNUT = ITEMS.registerSimpleItem("walnut", new Item.Properties().food(Foods.WALNUT));
    public static final DeferredItem<Item> WALNUT_FLOUR = ITEMS.registerSimpleItem("walnut_flour");
    public static final DeferredItem<Item> CHOCOLATE_CHIP_MUFFIN = ITEMS.registerSimpleItem("chocolate_chip_muffin", new Item.Properties().food(Foods.CHOCOLATE_CHIP_MUFFIN));
    public static final DeferredItem<Item> SUNFLOWER_SEED_MUFFIN = ITEMS.registerSimpleItem("sunflower_seed_muffin", new Item.Properties().food(Foods.SUNFLOWER_SEED_MUFFIN));
    public static final DeferredItem<Item> CHERRY_MUFFIN = ITEMS.registerSimpleItem("cherry_muffin",
            new Item.Properties().food(Foods.CHERRY_MUFFIN));
    public static final DeferredItem<Item> MASHED_POTATOES = ITEMS.registerSimpleItem("mashed_potatoes",
            new Item.Properties().food(Foods.MASHED_POTATOES));
    public static final DeferredItem<Item> CHERRIES = ITEMS.registerSimpleItem("cherries",
            new Item.Properties().food(Foods.CHERRIES));
    public static final DeferredItem<Item> GOLDEN_CHERRIES = ITEMS.registerSimpleItem("golden_cherries",
            new Item.Properties().food(Foods.GOLDEN_CHERRIES));
    public static final DeferredItem<Item> CHERRY_PIE = ITEMS.registerSimpleItem("cherry_pie",
            new Item.Properties().food(Foods.CHERRY_PIE));
    public static final DeferredItem<BlockItem> CHOCOLATE_CHERRY_CAKE = ITEMS.registerSimpleBlockItem(BlockRegistry.CHOCOLATE_CHERRY_CAKE);
    public static final DeferredItem<MilkshakeItem> VANILLA_MILKSHAKE = ITEMS.registerItem("vanilla_milkshake",
            MilkshakeItem::new, new Item.Properties().durability(32).food(Foods.VANILLA_MILKSHAKE));
    public static final DeferredItem<MilkshakeItem> CHOCOLATE_MILKSHAKE = ITEMS.registerItem("chocolate_milkshake",
            MilkshakeItem::new, new Item.Properties().durability(32).food(Foods.CHOCOLATE_MILKSHAKE));
    public static final DeferredItem<MilkshakeItem> CHERRY_MILKSHAKE = ITEMS.registerItem("cherry_milkshake",
            MilkshakeItem::new, new Item.Properties().durability(32).food(Foods.CHERRY_MILKSHAKE));

    public static final DeferredItem<BlockItem> WALNUT_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_LOG);
    public static final DeferredItem<BlockItem> WALNUT_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_WALNUT_LOG = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_WALNUT_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_WALNUT_WOOD = ITEMS.registerSimpleBlockItem(BlockRegistry.STRIPPED_WALNUT_WOOD);
    public static final DeferredItem<BlockItem> WALNUT_PLANKS = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_PLANKS);
    public static final DeferredItem<BlockItem> WALNUT_STAIRS = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_STAIRS);
    public static final DeferredItem<BlockItem> WALNUT_SLAB = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_SLAB);
    public static final DeferredItem<BlockItem> WALNUT_FENCE = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_FENCE);
    public static final DeferredItem<BlockItem> WALNUT_FENCE_GATE = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_FENCE_GATE);
    public static final DeferredItem<BlockItem> WALNUT_DOOR = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_DOOR);
    public static final DeferredItem<BlockItem> WALNUT_TRAP_DOOR = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_TRAPDOOR);
    public static final DeferredItem<BlockItem> WALNUT_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> WALNUT_BUTTON = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_BUTTON);
    public static final DeferredItem<BlockItem> WALNUT_SIGN = ITEMS.registerItem("walnut_sign",
            props -> new SignItem(props.stacksTo(16), BlockRegistry.STANDING_WALNUT_SIGN.get(), BlockRegistry.WALNUT_WALL_SIGN.get()));
    public static final DeferredItem<BlockItem> HANGING_WALNUT_SIGN = ITEMS.registerItem("hanging_walnut_sign",
            props -> new HangingSignItem(BlockRegistry.CEILING_HANGING_WALNUT_SIGN.get(), BlockRegistry.WALL_HANGING_WALNUT_SIGN.get(), props.stacksTo(16)));
    public static final DeferredItem<BoatItem> WALNUT_BOAT = ITEMS.registerItem("walnut_boat",
            props -> new BoatItem(false, Boat.Type.valueOf("CNC_WALNUT"), props), new Item.Properties().stacksTo(1));
    public static final DeferredItem<BoatItem> WALNUT_CHEST_BOAT = ITEMS.registerItem("walnut_chest_boat",
            props -> new BoatItem(true, Boat.Type.valueOf("CNC_WALNUT"), props), new Item.Properties().stacksTo(1));
    public static final DeferredItem<BlockItem> WALNUT_LEAVES = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_LEAVES);
    public static final DeferredItem<BlockItem> WALNUT_SAPLING = ITEMS.registerSimpleBlockItem(BlockRegistry.WALNUT_SAPLING);


    static class Foods {
        public static final FoodProperties PEA_POD = new FoodProperties.Builder().nutrition(9).saturationModifier(0.25f).build();
        public static final FoodProperties RAW_PEA = new FoodProperties.Builder().nutrition(3).saturationModifier(0.1f).fast().build();
        public static final FoodProperties COOKED_PEA = new FoodProperties.Builder().nutrition(4).saturationModifier(0.2f).fast().build();
        public static final FoodProperties SUNFLOWER_SEEDS = new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build();
        public static final FoodProperties SUNFLOWER_SEED_MUFFIN = new FoodProperties.Builder().nutrition(6).saturationModifier(0.15f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties CHOCOLATE_CHIP_MUFFIN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties WALNUT = Util.make(() -> {
            var food = new FoodProperties.Builder().nutrition(2).saturationModifier(0.05f);
            food.eatSeconds *= 3;
            return food.build();
        });
        public static final FoodProperties MASHED_POTATOES = new FoodProperties.Builder().nutrition(7).saturationModifier(0.115f).usingConvertsTo(Items.BOWL).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 40), 1).build();
        public static final FoodProperties GOLDEN_CHERRIES = new FoodProperties.Builder().nutrition(3).saturationModifier(0.8f).alwaysEdible().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 60), 0.5f).build();
        public static final FoodProperties CHERRY_PIE = new FoodProperties.Builder().nutrition(6).saturationModifier(0.3f).build();
        public static final FoodProperties VANILLA_MILKSHAKE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).alwaysEdible().effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1).build();
        public static final FoodProperties CHOCOLATE_MILKSHAKE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).alwaysEdible().effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 500, 1), 1).build();
        public static final FoodProperties CHERRY_MILKSHAKE = new FoodProperties.Builder().nutrition(4).saturationModifier(0.8f).alwaysEdible().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1).build();
        public static final FoodProperties CHERRY_MUFFIN = new FoodProperties.Builder().nutrition(5).saturationModifier(0.2f).usingConvertsTo(Items.PAPER).build();
        public static final FoodProperties CHERRIES = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2f).build();
    }
}
