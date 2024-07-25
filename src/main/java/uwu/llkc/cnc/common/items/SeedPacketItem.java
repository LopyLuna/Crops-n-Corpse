package uwu.llkc.cnc.common.items;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.SoundRegistry;

import java.util.List;

public class SeedPacketItem extends Item {
    public static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");

    public SeedPacketItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() == Direction.UP && !context.getLevel().isClientSide) {
            var data = context.getItemInHand().getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!data.isEmpty()) {
                data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(EntityTypeRegistry.PEASHOOTER.get())
                        .spawn((ServerLevel) context.getLevel(), context.getItemInHand(), context.getPlayer(), context.getClickedPos(), MobSpawnType.SPAWN_EGG, true, true);
                if (context.getPlayer() != null && !context.getPlayer().hasInfiniteMaterials()) {
                    context.getItemInHand().remove(DataComponents.ENTITY_DATA);
                    context.getItemInHand().remove(DataComponentRegistry.PLANTS.get());
                }
                context.getLevel().playSound(null, context.getClickedPos(), SoundRegistry.PLANT_SPAWN.get(), SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        if (data.isEmpty()) {
            tooltipComponents.add(Component.translatableWithFallback("item.cnc.seed_packet.empty", "Empty").withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(EntityTypeRegistry.PEASHOOTER.get())
                    .getDescription().plainCopy().withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return stack.has(DataComponents.ENTITY_DATA) ? 1 : getDefaultMaxStackSize();
    }
}
