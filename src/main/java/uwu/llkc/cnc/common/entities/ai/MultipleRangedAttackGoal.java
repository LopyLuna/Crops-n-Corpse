package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class MultipleRangedAttackGoal extends Goal {
    private final Mob mob;
    private final RangedAttackMob rangedAttackMob;
    private final double speedModifier;
    private final int attackIntervalMin;
    private final int attackIntervalMax;
    private final float attackRadius;
    private final float attackRadiusSqr;
    private final int bulletCooldown;
    private final int bulletCount;
    @Nullable
    private LivingEntity target;
    private int attackTime = -1;
    private int seeTime;
    private int bulletsToFire;

    public MultipleRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackInterval, float attackRadius, int bulletCooldown, int bulletCount) {
        this(rangedAttackMob, speedModifier, attackInterval, attackInterval, attackRadius, bulletCooldown, bulletCount);
    }

    public MultipleRangedAttackGoal(RangedAttackMob rangedAttackMob, double speedModifier, int attackIntervalMin, int attackIntervalMax, float attackRadius, int bulletCooldown, int bulletCount) {
        this.bulletCooldown = bulletCooldown;
        this.bulletCount = bulletCount;
        bulletsToFire = bulletCount;
        if (!(rangedAttackMob instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.rangedAttackMob = rangedAttackMob;
            this.mob = (Mob) rangedAttackMob;
            this.speedModifier = speedModifier;
            this.attackIntervalMin = attackIntervalMin;
            this.attackIntervalMax = attackIntervalMax;
            this.attackRadius = attackRadius;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.target = livingentity;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() || this.target.isAlive() && !this.mob.getNavigation().isDone();
    }

    @Override
    public void stop() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = -1;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean flag = this.mob.getSensing().hasLineOfSight(this.target);
        if (flag) {
            this.seeTime++;
        } else {
            this.seeTime = 0;
        }

        if (!(d0 > (double) this.attackRadiusSqr) && this.seeTime >= 5) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().moveTo(this.target, this.speedModifier);
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        if (--this.attackTime == 0 && bulletsToFire > 0) {
            if (!flag) {
                return;
            }

            float f = (float) Math.sqrt(d0) / this.attackRadius;
            float f1 = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.target, f1);
            this.attackTime = bulletCooldown;
            bulletsToFire--;
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt(d0) / (double) this.attackRadius, this.attackIntervalMin, this.attackIntervalMax));
            bulletsToFire = bulletCount;
        }
    }
}
