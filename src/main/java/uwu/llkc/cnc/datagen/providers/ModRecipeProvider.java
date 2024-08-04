package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import uwu.llkc.cnc.common.init.CreativeModeTabRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CreativeModeTabRegistry.getSeedPacket(0.1f, EntityTypeRegistry.PEASHOOTER.get(), 16))
                .pattern(" p ")
                .pattern("ses")
                .pattern(" p ")
                .define('p', ItemRegistry.PEA_POD.get())
                .define('s', ItemRegistry.SUN.get())
                .define('e', DataComponentIngredient.of(true, new ItemStack(ItemRegistry.SEED_PACKET.get())))
                .unlockedBy("has_packet", has(ItemRegistry.SEED_PACKET.get()))
                .unlockedBy("has_pea", has(ItemRegistry.PEA_POD.get()))
                .save(recipeOutput, "seed_packet_peashooter");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, CreativeModeTabRegistry.getSeedPacket(0.2f, EntityTypeRegistry.SUNFLOWER.get(), 0))
                .pattern(" f ")
                .pattern("ses")
                .pattern(" f ")
                .define('f', ItemRegistry.SUNFLOWER_SEEDS.get())
                .define('s', ItemRegistry.SUN.get())
                .define('e', DataComponentIngredient.of(true, new ItemStack(ItemRegistry.SEED_PACKET.get())))
                .unlockedBy("has_packet", has(ItemRegistry.SEED_PACKET.get()))
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
    }
}
