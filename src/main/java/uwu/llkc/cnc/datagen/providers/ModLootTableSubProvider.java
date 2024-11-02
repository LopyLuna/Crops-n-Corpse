package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.function.BiConsumer;

public class ModLootTableSubProvider implements LootTableSubProvider {
    public static final ResourceKey<LootTable> CHERRIES_DROP =
            ResourceKey.create(Registries.LOOT_TABLE, CNCMod.rl("other/cherries_drop"));
    public static final ResourceKey<LootTable> HOUSE_TABLE =
            ResourceKey.create(Registries.LOOT_TABLE, CNCMod.rl("other/house_chest"));
    private final HolderLookup.Provider provider;

    public ModLootTableSubProvider(HolderLookup.Provider lookupProvider) {
        this.provider = lookupProvider;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(
                CHERRIES_DROP,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.CHERRIES))
                        )
        );
        output.accept(
                HOUSE_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.PEA_POD))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                .add(LootItem.lootTableItem(ItemRegistry.RAW_PEA))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6)))
                        )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.PLANT_FOOD))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9)))
                                .add(LootItem.lootTableItem(ItemRegistry.SUN))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(8, 18)))
                        )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.SUNFLOWER_SEEDS))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 12)))
                                .add(LootItem.lootTableItem(ItemRegistry.SUNFLOWER_SEED_MUFFIN))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 7)))
                        )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.EMPTY_SEED_PACKET).setWeight(3))
                                .add(LootItem.lootTableItem(ItemRegistry.SUNFLOWER_SEED_PACKET).setWeight(1))
                        )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.PLANT_ARMOR))
                                .when(LootItemRandomChanceCondition.randomChance(0.1f))
                        )
        );
    }
}
