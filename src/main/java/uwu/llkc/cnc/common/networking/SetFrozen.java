
package uwu.llkc.cnc.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

public record SetFrozen(int entityId, boolean frozen) implements CustomPacketPayload {
    public static final Type<SetFrozen> TYPE = new Type<>(CNCMod.rl("set_frozen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetFrozen> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetFrozen::entityId,
            ByteBufCodecs.BOOL,
            SetFrozen::frozen,
            SetFrozen::new
    );

    public static void handleData(final SetFrozen data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().level.getEntity(data.entityId).setData(AttachmentTypeRegistry.FROZEN, data.frozen);
        });
    }

    @Override
    public @NotNull Type<SetFrozen> type() {
        return TYPE;
    }
}
