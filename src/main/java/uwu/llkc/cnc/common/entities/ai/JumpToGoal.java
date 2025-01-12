package uwu.llkc.cnc.common.entities.ai;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.LongJumpUtil;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;
import uwu.llkc.cnc.common.entities.zombies.FootSoldier;

import java.util.EnumSet;
import java.util.Optional;

public class JumpToGoal extends Goal {
    private static final ObjectArrayList<Integer> ALLOWED_ANGLES = new ObjectArrayList<>(Lists.newArrayList(30, 40, 55, 60));

    private final FootSoldier mob;
    private final float distance;
    private final int cooldown;
    private final float maxJumpVelocity;

    private int cooldownCounter = 0;

    public JumpToGoal(FootSoldier mob, float distance, int cooldown, float maxJumpVelocity) {
        this.mob = mob;
        this.distance = distance;
        this.cooldown = cooldown;
        this.maxJumpVelocity = maxJumpVelocity;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return cooldownCounter++ > cooldown && mob.getTarget() != null && mob.distanceToSqr(mob.getTarget()) > distance * distance;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        var pos = mob.getTarget().position().subtract(mob.position()).normalize().scale(distance);
        Optional<Vec3> optional = calculateOptimalJumpVector(mob, mob.getRandom(), mob.position().add(pos));
        optional.ifPresent(vec3 -> {
                    mob.setDiscardFriction(true);
                    mob.setDeltaMovement(vec3);
                    mob.isSafeFall = true;
                    cooldownCounter = 0;
                }
        );
    }

    private Optional<Vec3> calculateOptimalJumpVector(Mob mob, RandomSource random, Vec3 target) {
        for (int i : Util.shuffledCopy(ALLOWED_ANGLES, random)) {
            Optional<Vec3> optional = LongJumpUtil.calculateJumpVectorForAngle(mob, target, 4, i, false);
            if (optional.isPresent()) {
                return optional;
            }
        }

        return Optional.empty();
    }
}