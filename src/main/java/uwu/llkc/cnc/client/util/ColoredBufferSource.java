package uwu.llkc.cnc.client.util;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class ColoredBufferSource implements MultiBufferSource {
    private final MultiBufferSource wrapped;
    private final int[] color;

    public ColoredBufferSource(MultiBufferSource wrapped, int r, int g, int b, int a) {
        this.wrapped = wrapped;
        this.color = new int[]{r, g, b, a};
    }

    @Override
    public VertexConsumer getBuffer(RenderType renderType) {
        return new ColoredVertexConsumer(wrapped.getBuffer(renderType), color);
    }

    private record ColoredVertexConsumer(VertexConsumer wrapped, int[] color) implements VertexConsumer {
        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            return wrapped.addVertex(x, y, z).setColor(color[0], color[1], color[2], color[3]);
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            return wrapped.setColor(color[0], color[1], color[2], color[3]);
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            return wrapped.setUv(u, v);
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            return wrapped.setUv1(u, v);
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            return wrapped.setUv2(u, v);
        }

        @Override
        public VertexConsumer setNormal(float normalX, float normalY, float normalZ) {
            return wrapped.setNormal(normalX, normalY, normalZ);
        }
    }
}
