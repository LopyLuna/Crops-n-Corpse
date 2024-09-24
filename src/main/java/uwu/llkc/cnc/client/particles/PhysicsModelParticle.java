package uwu.llkc.cnc.client.particles;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.level.LightLayer;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.renderable.BakedModelRenderable;

import java.util.function.Consumer;

public class PhysicsModelParticle extends Particle {
    ParticleRenderType MODEL = new ParticleRenderType() {
        @Override
        public BufferBuilder begin(Tesselator p_350910_, TextureManager p_107470_) {
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            return p_350910_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public String toString() {
            return "MODEL";
        }
    };

    private final Either<ModelPart, BakedModel> bodyPart;
    private final Consumer<PoseStack> transformation;
    private final ResourceLocation texture;

    public PhysicsModelParticle(ClientLevel level, double x, double y, double z, Either<ModelPart, BakedModel> part, Consumer<PoseStack> transformation, double xPower, double yPower, double zPower, ResourceLocation texture) {
        super(level, x, y, z);
        this.bodyPart = part;
        this.transformation = transformation;
        gravity = 0.9f;
        lifetime = 300;
        xd = xPower;
        yd = yPower;
        zd = zPower;
        this.texture = texture;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        var vec3 = camera.getPosition();
        var pose = new PoseStack();
        float f = (float)(Mth.lerp(partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp(partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp(partialTicks, this.zo, this.z) - vec3.z());
        pose.translate(f, f1, f2);
        transformation.accept(pose);
        RenderSystem.getShaderColor();
        var source = Minecraft.getInstance().renderBuffers().bufferSource();
        bodyPart.ifLeft(modelPart -> {
                    RenderSystem.setShaderTexture(0, texture);
                    var consumer = source.getBuffer(RenderType.entityCutoutNoCull(texture));
                    modelPart.render(pose, consumer, LightTexture.pack(level.getBrightness(LightLayer.BLOCK, BlockPos.containing(getPos())), level.getBrightness(LightLayer.SKY, BlockPos.containing(getPos()))), OverlayTexture.NO_OVERLAY);
                })
                .ifRight(bakedModel -> BakedModelRenderable.of(bakedModel).withContext(ModelData.EMPTY).render(
                                pose,
                                source,
                                RenderType::entityTranslucent,
                                LightTexture.pack(level.getBrightness(LightLayer.BLOCK, BlockPos.containing(getPos())), level.getBrightness(LightLayer.SKY, BlockPos.containing(getPos()))),
                                OverlayTexture.NO_OVERLAY,
                                partialTicks,
                                Unit.INSTANCE
                        ));
        source.endBatch();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return MODEL;
    }
}
