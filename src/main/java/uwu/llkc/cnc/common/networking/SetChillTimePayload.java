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

public record SetChillTimePayload(int entityId, int time) implements CustomPacketPayload {
    public static final Type<SetChillTimePayload> TYPE = new Type<>(CNCMod.rl("set_chill_time"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetChillTimePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetChillTimePayload::entityId,
            ByteBufCodecs.INT,
            SetChillTimePayload::time,
            SetChillTimePayload::new
    );

    public static void handleData(final SetChillTimePayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();

            ClientLevel level = minecraft.level;
            if (level == null) return;

            Entity entity = level.getEntity(data.entityId);
            if (entity == null) return;

            entity.setData(AttachmentTypeRegistry.CHILL_TIME, data.time);
        });
    }

    @Override
    public @NotNull Type<SetChillTimePayload> type() {
        return TYPE;
    }
}