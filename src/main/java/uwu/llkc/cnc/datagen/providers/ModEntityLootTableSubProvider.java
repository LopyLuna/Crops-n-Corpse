package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.stream.Stream;

public class ModEntityLootTableSubProvider extends EntityLootSubProvider {
    public ModEntityLootTableSubProvider(HolderLookup.Provider lookupProvider) {
        super(FeatureFlags.DEFAULT_FLAGS, lookupProvider);

    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityTypeRegistry.ENTITY_TYPES.getEntries()
                .stream().map(DeferredHolder::value);
    }

    @Override
    public void generate() {
        add(EntityTypeRegistry.PEASHOOTER.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.RAW_PEA)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        add(EntityTypeRegistry.REPEATER.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.RAW_PEA)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        add(EntityTypeRegistry.SUNFLOWER.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.SUNFLOWER_SEEDS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))));

        add(EntityTypeRegistry.BROWNCOAT.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, ConstantValue.exactly(1))))));

        add(EntityTypeRegistry.IMP.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(Items.ROTTEN_FLESH)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, ConstantValue.exactly(1))))));

        add(EntityTypeRegistry.WALLNUT.get(), new LootTable.Builder());

        add(EntityTypeRegistry.CHERRY_BOMB.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.CHERRIES)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2))))));

        add(EntityTypeRegistry.POTATO_MINE.get(), new LootTable.Builder()
                .withPool(LootPool.lootPool()
                        .when(LootItemRandomChanceCondition.randomChance(0.4f))
                        .add(LootItem.lootTableItem(Items.BAKED_POTATO))));
    }
}
