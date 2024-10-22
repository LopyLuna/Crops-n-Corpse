package uwu.llkc.cnc.common.entities.ai;

import org.joml.Vector3f;

public interface IMultiHeadEntity {
    int headCount();
    Vector3f getHeadPosition(int head);
    int getHeadRotSpeed(int head);
    int getMaxHeadXRot(int head);
    int getMaxHeadYRot(int head);
    void setHeadXRot(int head, float xRot);
    void setHeadYRot(int head, float yRot);
    float getHeadYRot(int head);
    float getHeadXRot(int head);
}
