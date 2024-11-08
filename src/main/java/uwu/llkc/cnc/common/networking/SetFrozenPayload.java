package uwu.llkc.cnc.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;
import uwu.llkc.cnc.common.util.LayerDefinitionMixinHelper;

import java.util.Optional;
import java.util.stream.Collectors;

public record SetFrozenPayload(int entityId, boolean frozen) implements CustomPacketPayload {
    public static final Type<SetFrozenPayload> TYPE = new Type<>(CNCMod.rl("set_frozen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetFrozenPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            SetFrozenPayload::entityId,
            ByteBufCodecs.BOOL,
            SetFrozenPayload::frozen,
            SetFrozenPayload::new
    );

    public static void handleData(final SetFrozenPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();

            ClientLevel level = minecraft.level;
            if (level == null) return;

            Entity entity = Minecraft.getInstance().level.getEntity(data.entityId);
            if (entity == null) return;
            entity.setData(AttachmentTypeRegistry.FROZEN, data.frozen);

            if (data.frozen) {
                Optional<ModelLayerLocation> locations = minecraft.getEntityModels().roots.keySet().stream()
                        .filter(layer -> layer.getModel().equals(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType())))
                        .findFirst();

                locations.map(minecraft.getEntityModels().roots::get).ifPresent(model -> {
                    if (model instanceof LayerDefinitionMixinHelper helper) {
                        helper.cnc$getRoot().ifPresent(root -> entity.setData(
                                AttachmentTypeRegistry.MODEL_PARTS,
                                root.getAllParts().collect(Collectors.toMap(
                                        part -> part,
                                        ModelPart::storePose
                                ))
                        ));
                    }
                });
            }
        });
    }

    @Override
    public @NotNull Type<SetFrozenPayload> type() {
        return TYPE;
    }
}