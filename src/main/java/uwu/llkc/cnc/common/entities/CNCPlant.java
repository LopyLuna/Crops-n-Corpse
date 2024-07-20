package uwu.llkc.cnc.common.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public abstract class CNCPlant extends Mob {
    protected CNCPlant(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected boolean isImmobile() {
        return true;
    }

    public abstract PlantCategory getPlantCategory();

    public abstract float getPlantPacketOverrideFloat();
}
