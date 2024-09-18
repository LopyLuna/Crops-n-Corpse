package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.Tags;
import uwu.llkc.cnc.common.items.SeedPacketItem;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SeedPacketItem.getSeedPacket(EntityTypeRegistry.PEASHOOTER.get()))
                .pattern(" p ")
                .pattern("sef")
                .pattern(" p ")
                .define('p', ItemRegistry.PEA_POD.get())
                .define('s', ItemRegistry.SUN.get())
                .define('f', ItemRegistry.PLANT_FOOD.get())
                .define('e', ItemRegistry.EMPTY_SEED_PACKET.get())
                .unlockedBy("has_packet", has(ItemRegistry.EMPTY_SEED_PACKET.get()))
                .unlockedBy("has_pea", has(ItemRegistry.PEA_POD.get()))
                .save(recipeOutput, "seed_packet_peashooter");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SeedPacketItem.getSeedPacket(EntityTypeRegistry.WALLNUT.get()))
                .pattern(" n ")
                .pattern("sef")
                .pattern(" n ")
                .define('n', ItemRegistry.WALNUT.get())
                .define('s', ItemRegistry.SUN.get())
                .define('f', ItemRegistry.PLANT_FOOD.get())
                .define('e', ItemRegistry.EMPTY_SEED_PACKET.get())
                .unlockedBy("has_packet", has(ItemRegistry.EMPTY_SEED_PACKET.get()))
                .unlockedBy("has_pea", has(ItemRegistry.PEA_POD.get()))
                .save(recipeOutput, "seed_packet_wallnut");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ItemRegistry.PLANT_ARMOR.get())
                .pattern(" i ")
                .pattern("i i")
                .pattern(" i ")
                .define('i', Items.IRON_BLOCK)
                .unlockedBy("has_iron", has(Items.IRON_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, SeedPacketItem.getSeedPacket(EntityTypeRegistry.SUNFLOWER .get()))
                .pattern(" f ")
                .pattern("seF")
                .pattern(" f ")
                .define('f', ItemRegistry.SUNFLOWER_SEEDS.get())
                .define('F', ItemRegistry.PLANT_FOOD.get())
                .define('s', ItemRegistry.SUN.get())
                .define('e', DataComponentIngredient.of(true, new ItemStack(ItemRegistry.EMPTY_SEED_PACKET.get())))
                .unlockedBy("has_packet", has(ItemRegistry.EMPTY_SEED_PACKET.get()))
                .unlockedBy("has_sunflower_seeds", has(ItemRegistry.SUNFLOWER_SEEDS.get()))
                .save(recipeOutput, "seed_packet_sunflower");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, new ItemStack(ItemRegistry.SUNFLOWER_SEED_MUFFIN.get()))
                .requires(ItemRegistry.SUNFLOWER_SEEDS)
                .requires(ItemRegistry.WALNUT_FLOUR)
                .requires(Items.PAPER)
                .requires(Items.MILK_BUCKET)
                .unlockedBy("has_sunflower_seeds", has(ItemRegistry.SUNFLOWER_SEEDS))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, new ItemStack(ItemRegistry.CHOCOLATE_CHIP_MUFFIN.get()))
                .requires(Items.COCOA_BEANS)
                .requires(ItemRegistry.WALNUT_FLOUR)
                .requires(Items.PAPER)
                .requires(Items.MILK_BUCKET)
                .unlockedBy("has_sunflower_seeds", has(ItemRegistry.SUNFLOWER_SEEDS))
                .save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.FOOD, ItemRegistry.WALNUT_FLOUR, ItemRegistry.WALNUT, 3);

        oneToOneConversionRecipe(recipeOutput, ItemRegistry.RAW_PEA.get(), ItemRegistry.PEA_POD.get(), null, 3);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemRegistry.RAW_PEA.get()), RecipeCategory.FOOD, ItemRegistry.COOKED_PEA.get(), 0.1f, 200)
                .unlockedBy("has_pea", has(ItemRegistry.RAW_PEA.get()))
                .save(recipeOutput, "cooked_pea_smelting");

        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ItemRegistry.RAW_PEA.get()), RecipeCategory.FOOD, ItemRegistry.COOKED_PEA.get(), 0.1f, 600)
                .unlockedBy("has_pea", has(ItemRegistry.RAW_PEA.get()))
                .save(recipeOutput, "cooked_pea_campfire");

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ItemRegistry.RAW_PEA.get()), RecipeCategory.FOOD, ItemRegistry.COOKED_PEA.get(), 0.1f, 100)
                .unlockedBy("has_pea", has(ItemRegistry.RAW_PEA.get()))
                .save(recipeOutput, "cooked_pea_smoking");

        planksFromLogs(recipeOutput, ItemRegistry.WALNUT_PLANKS, Tags.Items.WALNUT_LOGS, 4);
        woodFromLogs(recipeOutput, ItemRegistry.WALNUT_WOOD, ItemRegistry.WALNUT_LOG);
        woodFromLogs(recipeOutput, ItemRegistry.STRIPPED_WALNUT_WOOD, ItemRegistry.STRIPPED_WALNUT_LOG);
        signBuilder(ItemRegistry.WALNUT_SIGN, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        hangingSign(recipeOutput, ItemRegistry.HANGING_WALNUT_SIGN.get(), ItemRegistry.WALNUT_PLANKS);
        trapdoorBuilder(ItemRegistry.WALNUT_TRAP_DOOR, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        doorBuilder(ItemRegistry.WALNUT_DOOR, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        buttonBuilder(ItemRegistry.WALNUT_BUTTON, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        pressurePlate(recipeOutput, ItemRegistry.WALNUT_PRESSURE_PLATE, ItemRegistry.WALNUT_PLANKS);
        fenceBuilder(ItemRegistry.WALNUT_FENCE, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        fenceGateBuilder(ItemRegistry.WALNUT_FENCE_GATE, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ItemRegistry.WALNUT_SLAB, ItemRegistry.WALNUT_PLANKS);
        stairBuilder(ItemRegistry.WALNUT_STAIRS, Ingredient.of(ItemRegistry.WALNUT_PLANKS))
                .unlockedBy("has_planks", has(ItemRegistry.WALNUT_PLANKS.get()))
                .save(recipeOutput);
        woodenBoat(recipeOutput, ItemRegistry.WALNUT_BOAT, ItemRegistry.WALNUT_PLANKS);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, ItemRegistry.WALNUT_CHEST_BOAT)
                .requires(Blocks.CHEST)
                .requires(ItemRegistry.WALNUT_PLANKS)
                .group("chest_boat")
                .unlockedBy("has_boat", has(ItemTags.BOATS))
                .save(recipeOutput, "walnut_chest_boat");
    }
}
