package uwu.llkc.cnc.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.util.ChunkMixinHelper;

public record SyncBlockActuallyBrokenPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Type<SyncBlockActuallyBrokenPayload> TYPE = new Type<>(CNCMod.rl("block_actually_broken"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncBlockActuallyBrokenPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SyncBlockActuallyBrokenPayload::pos,
            SyncBlockActuallyBrokenPayload::new
    );

    @Override
    public @NotNull Type<SyncBlockActuallyBrokenPayload> type() {
        return TYPE;
    }

    public static void handleData(final SyncBlockActuallyBrokenPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null) {
                ((ChunkMixinHelper) Minecraft.getInstance().level.getChunk(data.pos)).setNextBlockPosDoBreak(data.pos);
            }
        });
    }
}
