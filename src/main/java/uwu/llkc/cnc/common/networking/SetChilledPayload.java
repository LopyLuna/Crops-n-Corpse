package uwu.llkc.cnc.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

public record SetChilledPayload(int entityId, boolean chilled) implements CustomPacketPayload {
    public static final Type<SetChilledPayload> TYPE = new Type<>(CNCMod.rl("set_chilled"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetChilledPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetChilledPayload::entityId,
            ByteBufCodecs.BOOL,
            SetChilledPayload::chilled,
            SetChilledPayload::new
    );

    public static void handleData(final SetChilledPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();

            ClientLevel level = minecraft.level;
            if (level == null) return;

            Entity entity = Minecraft.getInstance().level.getEntity(data.entityId);
            if (entity == null) return;

            entity.setData(AttachmentTypeRegistry.CHILLED, data.chilled);
        });
    }

    @Override
    public @NotNull Type<SetChilledPayload> type() {
        return TYPE;
    }
}