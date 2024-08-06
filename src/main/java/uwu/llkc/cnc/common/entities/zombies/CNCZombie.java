package uwu.llkc.cnc.common.entities.zombies;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public abstract class CNCZombie extends Monster {
    protected CNCZombie(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public abstract ZombieCategory getZombieCategory();
}
