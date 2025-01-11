package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class KeepDistanceGoal extends Goal {
    private final Mob mob;
    private final float distance;

    public KeepDistanceGoal(Mob mob, float distance) {
        this.mob = mob;
        this.distance = distance;
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null && mob.distanceToSqr(mob.getTarget()) < distance * distance;
    }

    @Override
    public void tick() {
        if (mob.getNavigation().isDone()) {
            mob.lookAt(mob.getTarget(), 30.0f, 30.0f);
            mob.getMoveControl().strafe(-2f, 0.0f);
        }
    }
}
