package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import uwu.llkc.cnc.common.entities.zombies.FootSoldier;

public class StinkCloudGoal extends Goal {
    private final float damageThreshold;
    private final FootSoldier zombie;

    public StinkCloudGoal(FootSoldier zombie, float damageThreshold) {
        this.zombie = zombie;
        this.damageThreshold = damageThreshold;
    }

    @Override
    public boolean canUse() {
        return zombie.totalDamage > damageThreshold;
    }

    @Override
    public void start() {
        super.start();
        zombie.level().explode(zombie, zombie.getX(), zombie.getY(), zombie.getZ(), 3, Level.ExplosionInteraction.MOB);
    }
}
