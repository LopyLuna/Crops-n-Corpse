package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import uwu.llkc.cnc.common.entities.plants.CherryBomb;

import java.util.EnumSet;

public class CherryRandomLookAroundGoal extends Goal {
    private final CherryBomb mob;
    private double relX;
    private double relZ;
    private int lookTime;
    private int index;

    public CherryRandomLookAroundGoal(CherryBomb mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.mob.getRandom().nextFloat() < 0.02F;
    }

    @Override
    public boolean canContinueToUse() {
        return this.lookTime >= 0;
    }

    @Override
    public void start() {
        double d0 = (Math.PI * 2) * this.mob.getRandom().nextDouble();
        this.relX = Math.cos(d0);
        this.relZ = Math.sin(d0);
        this.lookTime = 20 + this.mob.getRandom().nextInt(20);
        index = mob.getRandom().nextInt(2);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        this.lookTime--;
        this.mob.getLookControl().setLookAt(this.mob.getX() + this.relX, this.mob.getEyeY(), this.mob.getZ() + this.relZ, index);
    }
}
