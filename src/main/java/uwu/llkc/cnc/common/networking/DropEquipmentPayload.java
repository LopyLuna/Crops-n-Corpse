package uwu.llkc.cnc.common.networking;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.util.ClientProxy;

public record DropEquipmentPayload(ItemStack equipment, Vector3f alignment, int entityId, ItemDisplayContext slot) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DropEquipmentPayload> TYPE = new CustomPacketPayload.Type<>(CNCMod.rl("drop_equipment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DropEquipmentPayload> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            DropEquipmentPayload::equipment,
            ByteBufCodecs.VECTOR3F,
            DropEquipmentPayload::alignment,
            ByteBufCodecs.INT,
            DropEquipmentPayload::entityId,
            NeoForgeStreamCodecs.enumCodec(ItemDisplayContext.class),
            DropEquipmentPayload::slot,
            DropEquipmentPayload::new
    );

    @Override
    public @NotNull Type<DropEquipmentPayload> type() {
        return TYPE;
    }

    public static void handleData(final DropEquipmentPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientProxy.dropEquipment(data);
        });
    }
}
