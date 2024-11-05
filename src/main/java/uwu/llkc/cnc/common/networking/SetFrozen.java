
package uwu.llkc.cnc.common.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

import java.util.function.Consumer;

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
            //todo fix possible NPE
            Entity entity = Minecraft.getInstance().level.getEntity(data.entityId);
            entity.setData(AttachmentTypeRegistry.FROZEN, data.frozen);
            EntityRenderer<? super Entity> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
            ifRenderer(renderer, livingEntityRenderer -> {
            });

        });
    }

    @Override
    public @NotNull Type<SetFrozen> type() {
        return TYPE;
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void ifRenderer(EntityRenderer<? super Entity> renderer, Consumer<LivingEntityRenderer<? super T, M>> consumer) {
        if (renderer instanceof EntityRenderer<? super LivingEntity> entityRenderer) {
            if (entityRenderer instanceof LivingEntityRenderer<? super T, M> livingEntityRenderer) {
                consumer.accept(livingEntityRenderer);
            }
        }
    }
}
