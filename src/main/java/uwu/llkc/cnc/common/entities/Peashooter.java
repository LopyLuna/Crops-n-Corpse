package uwu.llkc.cnc.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class Peashooter extends CNCPlant {
    public Peashooter(EntityType<Peashooter> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.FOLLOW_RANGE, 20);
    }

    @Override
    public void setPos(double x, double y, double z) {
        var pos = BlockPos.containing(x, y, z);
        super.setPos(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.5f);
    }

    @Override
    public PlantCategory getPlantCategory() {
        return PlantCategory.OFFENSIVE;
    }

    @Override
    public float getPlantPacketOverrideFloat() {
        return 0.01f;
    }
}
