package uwu.llkc.cnc.datagen.providers;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.common.blocks.PeashooterCropBlock;
import uwu.llkc.cnc.common.blocks.SunflowerCropBlock;
import uwu.llkc.cnc.common.init.BlockRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.Set;

public class ModBlockLootTableSubProvider extends BlockLootSubProvider {
    public ModBlockLootTableSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);

    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries()
                .stream().map(e -> ((Block) e.value())).toList();
    }

    @Override
    protected void generate() {
        this.add(BlockRegistry.SUNFLOWER_CROP.get(), createCropDrops(BlockRegistry.SUNFLOWER_CROP.get(), ItemRegistry.SUNFLOWER_SEEDS.get(), ItemRegistry.SUNFLOWER_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SUNFLOWER_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SunflowerCropBlock.AGE, 7))));
        this.add(BlockRegistry.PEASHOOTER_CROP.get(),
                this.applyExplosionDecay(BlockRegistry.PEASHOOTER_CROP.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.PEA_POD.get())
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PEASHOOTER_CROP.get())
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PeashooterCropBlock.AGE, 7)))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 1)
                                ).otherwise(LootItem.lootTableItem(ItemRegistry.RAW_PEA.get()))))));
        dropSelf(BlockRegistry.TRAFFIC_CONE.get());
    }
}
