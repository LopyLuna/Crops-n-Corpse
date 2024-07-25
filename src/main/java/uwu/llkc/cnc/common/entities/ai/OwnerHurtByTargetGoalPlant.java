package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

import java.util.EnumSet;

public class OwnerHurtByTargetGoalPlant extends TargetGoal {
    private final CNCPlant plant;
    private LivingEntity ownerLastHurtBy;
    private int timestamp;

    public OwnerHurtByTargetGoalPlant(CNCPlant plant) {
        super(plant, false);
        this.plant = plant;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if (this.plant.getOwner() != null) {
            LivingEntity livingentity = this.plant.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurtBy = livingentity.getLastHurtByMob();
                int i = livingentity.getLastHurtByMobTimestamp();
                return i != this.timestamp
                        && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        if (ownerLastHurtBy instanceof CNCPlant cncplant && cncplant.getOwnerUUID() != null && cncplant.getOwnerUUID().equals(plant.getOwnerUUID()) || plant.getOwnerUUID() != null && plant.getOwnerUUID().equals(ownerLastHurtBy.getUUID()) || plant.getOwnerUUID() != null && targetMob instanceof OwnableEntity ownable && plant.getOwnerUUID().equals(ownable.getOwnerUUID())) return;
        this.mob.setTarget(this.ownerLastHurtBy);
        LivingEntity livingentity = this.plant.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtByMobTimestamp();
        }

        super.start();
    }
}
