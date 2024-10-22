package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.ai.control.BodyRotationControl;

public class FixedBodyRotationControl extends BodyRotationControl {
    public static final FixedBodyRotationControl INSTANCE = new FixedBodyRotationControl();

    @SuppressWarnings("DataFlowIssue")
    private FixedBodyRotationControl() {
        super(null);
    }

    @Override
    public void clientTick() {}
}
