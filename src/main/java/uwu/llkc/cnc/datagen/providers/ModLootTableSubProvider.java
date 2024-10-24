package uwu.llkc.cnc.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.function.BiConsumer;

public class ModLootTableSubProvider implements LootTableSubProvider {
    private final HolderLookup.Provider provider;

    public static final ResourceKey<LootTable> CHERRIES_DROP =
            ResourceKey.create(Registries.LOOT_TABLE, CNCMod.rl("other/cherries_drop"));

    public ModLootTableSubProvider(HolderLookup.Provider lookupProvider) {
        this.provider = lookupProvider;
    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(CHERRIES_DROP, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ItemRegistry.CHERRIES))));
    }
}
