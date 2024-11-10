package uwu.llkc.cnc.common.util;

import net.minecraft.client.model.geom.ModelPart;

public record ModelPartData(float x, float y, float z, float xRot, float yRot, float zRot, float xScale, float yScale,
                            float zScale, boolean visible, boolean skipDraw) {
    public static ModelPartData fromModelPart(ModelPart modelPart) {
        return new ModelPartData(modelPart.x, modelPart.y, modelPart.z, modelPart.xRot, modelPart.yRot, modelPart.zRot, modelPart.xScale, modelPart.yScale, modelPart.zScale, modelPart.visible, modelPart.skipDraw);
    }

    public void toModelPart(ModelPart modelPart) {
        modelPart.x = x;
        modelPart.y = y;
        modelPart.z = z;
        modelPart.xRot = xRot;
        modelPart.yRot = yRot;
        modelPart.zRot = zRot;
        modelPart.xScale = xScale;
        modelPart.yScale = yScale;
        modelPart.zScale = zScale;
        modelPart.visible = visible;
        modelPart.skipDraw = skipDraw;
    }
}
