package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

import java.util.EnumSet;

public class OwnerHurtTargetGoalPlant extends TargetGoal {
    private final CNCPlant plant;
    private LivingEntity ownerLastHurt;
    private int timestamp;

    public OwnerHurtTargetGoalPlant(CNCPlant plant) {
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
                this.ownerLastHurt = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp
                        && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        if (ownerLastHurt instanceof CNCPlant cncplant && cncplant.getOwnerUUID() != null && cncplant.getOwnerUUID().equals(plant.getOwnerUUID()) || plant.getOwnerUUID() != null && plant.getOwnerUUID().equals(ownerLastHurt.getUUID()) || plant.getOwnerUUID() != null &&  ownerLastHurt instanceof OwnableEntity ownable && plant.getOwnerUUID().equals(ownable.getOwnerUUID())) return;
        this.mob.setTarget(this.ownerLastHurt);
        LivingEntity livingentity = this.plant.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastHurtMobTimestamp();
        }

        super.start();
    }
}
