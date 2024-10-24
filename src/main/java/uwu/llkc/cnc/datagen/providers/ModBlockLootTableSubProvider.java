package uwu.llkc.cnc.datagen.providers;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        this.add(BlockRegistry.SUNFLOWER_CROP.get(), createCropDrops(BlockRegistry.SUNFLOWER_CROP.get(), ItemRegistry.SUNFLOWER_SEEDS.get(), ItemRegistry.SUNFLOWER_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SUNFLOWER_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SunflowerCropBlock.AGE, 7))));
        this.add(BlockRegistry.PEASHOOTER_CROP.get(),
                this.applyExplosionDecay(BlockRegistry.PEASHOOTER_CROP.get(), LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ItemRegistry.PEA_POD.get())
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PEASHOOTER_CROP.get())
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PeashooterCropBlock.AGE, 7)))
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                                        .apply(ApplyBonusCount.addBonusBinomialDistributionCount(registrylookup.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 1)
                                        ).otherwise(LootItem.lootTableItem(ItemRegistry.RAW_PEA.get()))))));
        dropSelf(BlockRegistry.TRAFFIC_CONE.get());
        dropSelf(BlockRegistry.STRIPPED_WALNUT_LOG.get());
        dropSelf(BlockRegistry.WALNUT_LOG.get());
        dropSelf(BlockRegistry.WALNUT_WOOD.get());
        dropSelf(BlockRegistry.STRIPPED_WALNUT_WOOD.get());
        dropSelf(BlockRegistry.WALNUT_SAPLING.get());
        dropSelf(BlockRegistry.WALNUT_PLANKS.get());
        dropSelf(BlockRegistry.WALNUT_WALL_SIGN.get());
        dropSelf(BlockRegistry.STANDING_WALNUT_SIGN.get());
        dropSelf(BlockRegistry.CEILING_HANGING_WALNUT_SIGN.get());
        dropSelf(BlockRegistry.WALL_HANGING_WALNUT_SIGN.get());
        dropSelf(BlockRegistry.WALNUT_TRAPDOOR.get());
        dropSelf(BlockRegistry.WALNUT_DOOR.get());
        dropSelf(BlockRegistry.WALNUT_TRAPDOOR.get());
        add(BlockRegistry.WALNUT_DOOR.get(), createDoorTable(BlockRegistry.WALNUT_DOOR.get()));
        dropSelf(BlockRegistry.WALNUT_BUTTON.get());
        dropSelf(BlockRegistry.WALNUT_FENCE_GATE.get());
        dropSelf(BlockRegistry.WALNUT_FENCE.get());
        dropSelf(BlockRegistry.WALNUT_PRESSURE_PLATE.get());
        dropSelf(BlockRegistry.WALNUT_STAIRS.get());
        dropSelf(BlockRegistry.WALNUT_SLAB.get());
        this.add(BlockRegistry.WALNUT_LEAVES.get(), createLeavesDrops(BlockRegistry.WALNUT_LEAVES.get(), BlockRegistry.WALNUT_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(HAS_SHEARS.or(this.hasSilkTouch()).invert())
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>) this.applyExplosionCondition(BlockRegistry.WALNUT_LEAVES, LootItem.lootTableItem(ItemRegistry.WALNUT)))
                                                .when(
                                                        BonusLevelTableCondition.bonusLevelFlatChance(
                                                                registrylookup.getOrThrow(Enchantments.FORTUNE), 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F
                                                        )
                                                )
                                )
                )
        );
    }
}
