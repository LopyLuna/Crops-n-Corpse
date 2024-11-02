package uwu.llkc.cnc.common.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import uwu.llkc.cnc.common.entities.plants.CherryBomb;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.init.GameRuleInit;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.SoundRegistry;
import uwu.llkc.cnc.common.util.ItemUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.Map.entry;

public class SeedPacketItem<T extends Entity> extends Item {
    public static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    public static final MapCodec<UUID> UUID_FIELD_CODEC = UUIDUtil.CODEC.fieldOf("owner");
    public static final MapCodec<Float> HEALTH_FIELD_CODEC = Codec.FLOAT.fieldOf("Health");

    private static final Supplier<Map<EntityType<?>, SeedPacketItem<?>>> SEED_PACKET_ITEM_MAP = () -> Map.ofEntries(
            entry(EntityTypeRegistry.PEASHOOTER.get(), ItemRegistry.PEASHOOTER_SEED_PACKET.get()),
            entry(EntityTypeRegistry.SUNFLOWER.get(), ItemRegistry.SUNFLOWER_SEED_PACKET.get()),
            entry(EntityTypeRegistry.WALLNUT.get(), ItemRegistry.WALLNUT_SEED_PACKET.get()),
            entry(EntityTypeRegistry.POTATO_MINE.get(), ItemRegistry.POTATO_MINE_SEED_PACKET.get()),
            entry(EntityTypeRegistry.CHERRY_BOMB.get(), ItemRegistry.CHERRY_BOMB_SEED_PACKET.get()),
            entry(EntityTypeRegistry.REPEATER.get(), ItemRegistry.REPEATER_SEED_PACKET.get())
    );

    private final int sunCost;
    private final int cooldown;
    private final Supplier<EntityType<T>> fallbackEntityType;
    private final boolean canBeLaunched;

    public SeedPacketItem(Properties properties, int sunCost, int cooldown, Supplier<EntityType<T>> fallbackEntityType, boolean canBeLaunched) {
        super(properties);
        this.sunCost = sunCost;
        this.cooldown = cooldown;
        this.fallbackEntityType = fallbackEntityType;
        this.canBeLaunched = canBeLaunched;
    }

    public static ItemStack getSeedPacket(EntityType<?> entityType) {
        return new ItemStack(SEED_PACKET_ITEM_MAP.get().get(entityType));
    }

    protected <F extends Entity> Consumer<F> getConsumer(ServerLevel level, ItemStack stack, Player player, InteractionHand hand) {
        return EntityType.<F>createDefaultStackConfig(level, stack, player).andThen(mob -> {
            mob.setYRot(player.getYHeadRot());
            mob.setYHeadRot(player.getYHeadRot());
            mob.setYBodyRot(player.getYHeadRot());
        });
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getClickedFace() == Direction.UP && !context.getLevel().isClientSide) {
            var data = context.getItemInHand().getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!data.isEmpty() || fallbackEntityType != null) {
                EntityType<?> entity = data.isEmpty() ? getFallbackEntityType() : data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(getFallbackEntityType());
                if (context.getPlayer() != null && (context.getPlayer().hasInfiniteMaterials() || ItemUtils.tryTakeItems(context.getPlayer(), new ItemStack(ItemRegistry.SUN.get(), getSunCost())))) {
                    entity.spawn((ServerLevel) context.getLevel(), getConsumer(((ServerLevel) context.getLevel()), context.getItemInHand(), context.getPlayer(), context.getHand()), context.getClickedPos(), MobSpawnType.SPAWN_EGG, true, true);
                    if (context.getPlayer() != null && !context.getPlayer().hasInfiniteMaterials()) {
                        context.getPlayer().setItemInHand(context.getHand(), new ItemStack(ItemRegistry.EMPTY_SEED_PACKET.get(), 1));
                        if (context.getPlayer().level().getGameRules().getBoolean(GameRuleInit.RULE_SEED_PACKET_COOLDOWN)) {
                            context.getPlayer().getCooldowns().addCooldown(this, getCooldown());
                        }
                    }
                    context.getLevel().playSound(null, context.getClickedPos(), SoundRegistry.PLANT_SPAWN.get(), SoundSource.PLAYERS);
                    return InteractionResult.SUCCESS;
                }
                if (context.getPlayer() != null) {
                    context.getPlayer().displayClientMessage(Component.translatableWithFallback("item.seed_packet.sun", "Insufficient Sun").withStyle(ChatFormatting.RED), true);
                    ((ServerPlayer) context.getPlayer()).connection.send(new ClientboundSoundPacket(SoundEvents.NOTE_BLOCK_DIDGERIDOO, SoundSource.PLAYERS, context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ(), 1, 0, context.getPlayer().getRandom().nextLong()));
                }
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (canBeLaunched && !level.isClientSide) {
            var data = player.getItemInHand(usedHand).getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
            if (!data.isEmpty() || fallbackEntityType != null) {
                EntityType<?> entity = data.isEmpty() ? getFallbackEntityType() : data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(getFallbackEntityType());
                if (entity != null && (player.hasInfiniteMaterials() || ItemUtils.tryTakeItems(player, new ItemStack(ItemRegistry.SUN.get(), getSunCost())))) {
                    var cherryEntity = entity.create((ServerLevel) level, getConsumer((ServerLevel) level, player.getItemInHand(usedHand), player, usedHand), player.blockPosition(), MobSpawnType.SPAWN_EGG, true, true);
                    if (cherryEntity instanceof CherryBomb cherry) {
                        cherry.getEntityData().set(CherryBomb.FLYING, true);
                        cherry.setPos(player.position());
                        cherry.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 1F, 1.0F);
                        level.addFreshEntity(cherry);

                        if (!player.hasInfiniteMaterials()) {
                            player.setItemInHand(usedHand, new ItemStack(ItemRegistry.EMPTY_SEED_PACKET.get(), 1));
                            if (player.level().getGameRules().getBoolean(GameRuleInit.RULE_SEED_PACKET_COOLDOWN)) {
                                player.getCooldowns().addCooldown(this, getCooldown());
                            }
                        }
                        level.playSound(null, player.blockPosition(), SoundRegistry.PLANT_SPAWN.get(), SoundSource.PLAYERS);
                        return InteractionResultHolder.success(player.getItemInHand(usedHand));
                    } else {
                        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
                    }
                }
                player.displayClientMessage(Component.translatable("item.seed_packet.insufficient_sun").withStyle(ChatFormatting.RED), true);
                ((ServerPlayer) player).connection.send(new ClientboundSoundPacket(SoundEvents.NOTE_BLOCK_DIDGERIDOO, SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 1, 0, player.getRandom().nextLong()));
                return InteractionResultHolder.fail(player.getItemInHand(usedHand));
            }
        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        var entity = data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(getFallbackEntityType());

        if (entity != null) {

            tooltipComponents.add(Component.translatable("item.cnc.seed_packet.cost").withStyle(ChatFormatting.BLUE));

            tooltipComponents.add(Component.literal(getSunCost() + " ").append(Component.translatable("item.cnc.sun")).withStyle(ChatFormatting.GRAY));

            tooltipComponents.add(Component.translatable("item.cnc.seed_packet.cooldown").withStyle(ChatFormatting.BLUE));

            tooltipComponents.add(Component.literal(getCooldown() / 20f + " ").append(Component.translatable("item.cnc.seed_packet.seconds")).withStyle(ChatFormatting.GRAY));


            tooltipComponents.add(Component.translatable("item.cnc.seed_packet.hp").withStyle(ChatFormatting.BLUE));
            var maxHealth = DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) entity).getValue(Attributes.MAX_HEALTH);
            data.read(HEALTH_FIELD_CODEC).result().ifPresentOrElse(hp -> {
                tooltipComponents.add(Component.literal(Math.round(hp / maxHealth * 100) + "%").withStyle(ChatFormatting.GRAY));
            }, () -> tooltipComponents.add(Component.literal("100%").withStyle(ChatFormatting.GRAY)));

            tooltipComponents.add(Component.translatable("item.cnc.seed_packet.owner").withStyle(ChatFormatting.BLUE));

            data.read(UUID_FIELD_CODEC).result().ifPresentOrElse(uuid -> {
                tooltipComponents.add(Component.literal(Minecraft.getInstance().level.getPlayerByUUID(uuid).getName().getString()).withStyle(ChatFormatting.GRAY));
            }, () -> {
                tooltipComponents.add(Component.translatable("item.cnc.seed_packet.none").withStyle(ChatFormatting.GRAY));
            });
        }
    }

    public EntityType<T> getFallbackEntityType() {
        if (fallbackEntityType == null) return null;
        return fallbackEntityType.get();
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getSunCost() {
        return sunCost;
    }
}
