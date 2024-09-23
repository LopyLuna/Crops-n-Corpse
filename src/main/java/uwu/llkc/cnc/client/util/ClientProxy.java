package uwu.llkc.cnc.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import uwu.llkc.cnc.client.entities.models.BrowncoatModel;
import uwu.llkc.cnc.client.particles.PhysicsModelParticle;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;
import uwu.llkc.cnc.common.networking.DropEquipmentPayload;
import uwu.llkc.cnc.common.networking.SyncBlockActuallyBrokenPayload;
import uwu.llkc.cnc.common.util.ChunkMixinHelper;

public class ClientProxy {
    public static void createBrowncoatHead(Browncoat browncoat) {
        var model = BrowncoatModel.createBodyLayer().bakeRoot().getChild("root").getChild("head");

        Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(((ClientLevel) browncoat.level()), browncoat.getX(), browncoat.getY() + 1.5, browncoat.getZ(), Either.left(model), poseStack -> {
            poseStack.mulPose(Axis.YN.rotationDegrees(browncoat.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
        }, Vec3.directionFromRotation(0, browncoat.getYRot()).x * -0.05, 0.2, Vec3.directionFromRotation(0, browncoat.getYRot()).z * -0.05));
    }

    public static void createBrowncoatArm(Browncoat browncoat) {
        var model = BrowncoatModel.createBodyLayer().bakeRoot().getChild("root").getChild("left_arm").getChild("left_forearm");


        Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(((ClientLevel) browncoat.level()), browncoat.getX(), browncoat.getY() + 1.25, browncoat.getZ(), Either.left(model), poseStack -> {
            poseStack.translate(Vec3.directionFromRotation(0, browncoat.getYRot()).z * .32, 0, Vec3.directionFromRotation(0, browncoat.getYRot()).x * -.32);
            poseStack.mulPose(Axis.YP.rotationDegrees(-browncoat.getYRot()));
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
        }, Vec3.directionFromRotation(0, browncoat.getYRot()).x * -0.05, 0, Vec3.directionFromRotation(0, browncoat.getYRot()).z * -0.05));
    }

    public static void actuallyBroken(SyncBlockActuallyBrokenPayload data) {
        if (Minecraft.getInstance().level != null) {
            ((ChunkMixinHelper) Minecraft.getInstance().level.getChunk(data.pos())).setNextBlockPosDoBreak(data.pos());
        }
    }

    public static void dropEquipment(DropEquipmentPayload data) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level.getEntity(data.entityId()) instanceof LivingEntity livingEntity) {
            boolean leftHanded = livingEntity instanceof Mob mob && mob.isLeftHanded();
            var pose = new PoseStack();
            var model = mc.getItemRenderer().getModel(data.equipment(), mc.level, livingEntity, 0).applyTransform(data.slot(), pose, leftHanded);
            final var rotation = (float) Math.toRadians(livingEntity.getYHeadRot());
            final var rotPow = Vec3.directionFromRotation(0, livingEntity.getYHeadRot());
            final var y = livingEntity.getY() + data.alignment().y();
            Minecraft.getInstance().particleEngine.add(new PhysicsModelParticle(mc.level, livingEntity.getX() - 0.5, y, livingEntity.getZ() - 0.5, Either.right(model), poseStack -> {
                poseStack.translate(data.alignment().x,
                        0,
                        data.alignment().z);
                poseStack.rotateAround(new Quaternionf().rotateY(rotation).invert(), data.alignment().x + 0.5f, (float) y, data.alignment().z + 0.5f);
            }, rotPow.x * -0.05 + livingEntity.getDeltaMovement().x, 0.2 + livingEntity.getDeltaMovement().y, rotPow.z * -0.05 + livingEntity.getDeltaMovement().z));
            pose.popPose();
        }
    }
}
