package uwu.llkc.cnc.common.networking;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.client.particles.PhysicsModelParticle;

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
            Minecraft mc = Minecraft.getInstance();
            if (mc.level.getEntity(data.entityId) instanceof LivingEntity livingEntity) {
                boolean leftHanded = livingEntity instanceof Mob mob && mob.isLeftHanded();
                var pose = new PoseStack();
                var model = mc.getItemRenderer().getModel(data.equipment(), mc.level, livingEntity, 0).applyTransform(data.slot, pose, leftHanded);
                final var rotation = (float) Math.toRadians(livingEntity.getYHeadRot());
                final var rotPow = Vec3.directionFromRotation(0, livingEntity.getYHeadRot());
                final var y  = livingEntity.getY() + data.alignment.y();
                Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(mc.level, livingEntity.getX() - 0.5, y, livingEntity.getZ() - 0.5, Either.right(model), poseStack -> {
                    poseStack.translate(data.alignment.x,
                            0,
                            data.alignment.z);
                    poseStack.rotateAround(new Quaternionf().rotateY(rotation).invert(), data.alignment.x + 0.5f, (float) y, data.alignment.z + 0.5f);
                }, rotPow.x * -0.05 + livingEntity.getDeltaMovement().x, 0.2 + livingEntity.getDeltaMovement().y, rotPow.z * -0.05 + livingEntity.getDeltaMovement().z));
                pose.popPose();
            }
        });
    }
}
