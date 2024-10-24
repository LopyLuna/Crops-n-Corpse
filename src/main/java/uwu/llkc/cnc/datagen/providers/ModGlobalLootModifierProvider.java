package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import uwu.llkc.cnc.CNCMod;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CNCMod.MOD_ID);
    }

    @Override
    protected void start() {
        add(
                "add_cherry_to_leaves",
                new AddTableLootModifier(new LootItemCondition[]{
                        LootItemRandomChanceCondition.randomChance(0.2f).build(),
                        LootTableIdCondition.builder(ResourceLocation.withDefaultNamespace("cherry_leaves")).build()
                }, ModLootTableSubProvider.CHERRIES_DROP)
        );
    }
}
