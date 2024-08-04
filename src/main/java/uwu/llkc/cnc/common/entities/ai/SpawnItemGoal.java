package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

public class SpawnItemGoal extends Goal {
    private final ItemStack item;
    private final float range;
    private final CNCPlant plant;
    private final boolean separateItems;
    private final int minCooldown;
    private final int maxCooldown;
    private int cooldown;
    private int animTiming;

    public SpawnItemGoal(ItemStack stack, float range, CNCPlant plant, boolean separateItems, int minCooldown, int maxCooldown, int animTiming) {
        item = stack;
        this.range = range;
        this.plant = plant;
        this.separateItems = separateItems;
        this.minCooldown = minCooldown;
        this.maxCooldown = maxCooldown;
        this.animTiming = animTiming;
    }

    @Override
    public boolean canUse() {
        return plant.getOwner() != null && plant.distanceTo(plant.getOwner()) < range;
    }

    @Override
    public void start() {
        cooldown = plant.getRandom().nextIntBetweenInclusive(minCooldown, maxCooldown);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        if (cooldown-- == animTiming) {
            plant.level().broadcastEntityEvent(plant, (byte) 0);
        }
        if (cooldown <= 0) {
            cooldown = plant.getRandom().nextIntBetweenInclusive(minCooldown, maxCooldown);
            if (separateItems) {
                for (int i = 0; i < item.getCount(); i++) {
                    plant.spawnAtLocation(item.copyWithCount(1), 0.5f);
                }
            } else {
                plant.spawnAtLocation(item);
            }
        }
    }
}
