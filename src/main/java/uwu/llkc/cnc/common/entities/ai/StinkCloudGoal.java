package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.entities.zombies.FootSoldier;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;

import java.util.Comparator;

public class StinkCloudGoal extends Goal {
    private final float damageThreshold;
    private final FootSoldier zombie;
    private boolean used = false;

    public StinkCloudGoal(FootSoldier zombie, float damageThreshold) {
        this.zombie = zombie;
        this.damageThreshold = damageThreshold;
    }

    @Override
    public boolean canUse() {
        return zombie.totalDamage > damageThreshold && !used;
    }

    @Override
    public void start() {
        super.start();
        var plants = zombie.level().getEntitiesOfClass(CNCPlant.class, zombie.getBoundingBox().inflate(25, 5, 25), zombie::hasLineOfSight);
        var closestPlant = plants.stream().min(Comparator.comparingDouble(p -> p.distanceTo(zombie)));
        if (closestPlant.isEmpty()) return;
        var projectile = EntityTypeRegistry.ZOMBIE_STINK_BOMB.get().create(zombie.level());
        if (projectile == null) return;
        used = true;
        projectile.setPos(zombie.position());
        projectile.setOwner(zombie);
        projectile.shoot(closestPlant.get().getX() - zombie.getX(), closestPlant.get().getY() - zombie.getY(), closestPlant.get().getZ() - zombie.getZ(), 1, 0);
        zombie.level().addFreshEntity(projectile);
    }
}
