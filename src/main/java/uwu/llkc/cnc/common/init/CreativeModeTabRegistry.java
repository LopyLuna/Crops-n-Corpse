package uwu.llkc.cnc.common.init;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.items.SeedPacketItem;

import java.util.function.Supplier;

public class CreativeModeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, CNCMod.MOD_ID);

    public static final Supplier<CreativeModeTab> CNC_TAB = CREATIVE_MODE_TABS.register("cnc_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatableWithFallback("ítemGroup." + CNCMod.MOD_ID + ".cnc_tab", "Crops 'n' Corpse"))
                    .icon(() -> new ItemStack(ItemRegistry.PLANT_FOOD.get()))
                    .displayItems((params, output) -> {
                        for (DeferredHolder<Item, ? extends Item> entry : ItemRegistry.ITEMS.getEntries()) {
                            output.accept(entry.get());
                        }
                        output.accept(getSeedPacket(0.1f, EntityTypeRegistry.PEASHOOTER.get(), 16));
                        output.accept(getSeedPacket(0.2f, EntityTypeRegistry.SUNFLOWER.get(), 8));
                    }).build());

    public static ItemStack getSeedPacket(float override, EntityType<?> type, int sunCost) {
        var tag = SeedPacketItem.ENTITY_TYPE_FIELD_CODEC.codec().encodeStart(NbtOps.INSTANCE, type).getOrThrow();
        if (tag.getType() == CompoundTag.TYPE) {
            return new ItemStack(ItemRegistry.SEED_PACKET, 1, DataComponentPatch.builder()
                    .set(DataComponentRegistry.PLANTS.get(), override)
                    .set(DataComponentRegistry.SUN_COST.get(), sunCost)
                    .set(DataComponents.ENTITY_DATA, CustomData.of((CompoundTag) tag)).build());
        } else return ItemStack.EMPTY;
    }
}
