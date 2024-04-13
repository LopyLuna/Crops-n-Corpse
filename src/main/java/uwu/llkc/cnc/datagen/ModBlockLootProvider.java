package uwu.llkc.cnc.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import uwu.llkc.cnc.registry.ModBlocks;

import java.util.HashSet;
import java.util.Set;

public class ModBlockLootProvider extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public ModBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.EXAMPLE_BLOCK.get()
        );
    }

    protected void dropSelf(Block... block) {
        for (Block b : block) {
            dropSelf(b);
        }
    }

    @Override
    protected void add(Block block, LootTable.Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }
    protected void otherWhenSilkTouch(Block pBlock, Block pOther) {
        this.add(pBlock, createSilkTouchOnlyTable(pOther));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}
