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
                .save(recipeOutput, "sead_packet_peashooter");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, new ItemStack(ItemRegistry.SEED_PACKET.get()))
                .pattern("plp")
                .pattern("sss")
                .pattern("plp")
                .define('p', Items.PAPER)
                .define('l', Items.LIME_DYE)
                .define('s', ItemRegistry.SUN.get())
                .unlockedBy("has_sun", has(ItemRegistry.SUN.get()))
                .save(recipeOutput);

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
