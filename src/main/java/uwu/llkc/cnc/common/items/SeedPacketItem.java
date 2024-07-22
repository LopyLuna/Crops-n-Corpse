package uwu.llkc.cnc.common.items;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.Objects;

public class SeedPacketItem extends Item {
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");

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
                context.getItemInHand().remove(DataComponents.ENTITY_DATA);
                context.getItemInHand().remove(DataComponentRegistry.PLANTS.get());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
