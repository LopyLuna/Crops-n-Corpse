package uwu.llkc.cnc.common.entities.zombies;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import uwu.llkc.cnc.client.util.ClientProxy;

public class PirateBrowncoat extends Browncoat {
    protected final WaterBoundPathNavigation waterNavigation;
    protected final GroundPathNavigation groundNavigation;
    boolean searchingForLand;

    public PirateBrowncoat(EntityType<PirateBrowncoat> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new PirateBrowncoatMoveControl(this);
        this.waterNavigation = new WaterBoundPathNavigation(this, level);
        this.groundNavigation = new GroundPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(6, new PirateBrowncoatSwimUpGoal(this, 1.0, this.level().getSeaLevel()));
    }

    @Override
    public void createBrowncoatArm() {
        ClientProxy.createPirateBrowncoatArm(this);
    }

    @Override
    protected void createBrowncoatHead() {
        ClientProxy.createPirateBrowncoatHead(this);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && this.isInWater() && this.wantsToSwim()) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }
    }

    @Override
    public boolean isVisuallySwimming() {
        return this.isSwimming();
    }

    boolean wantsToSwim() {
        if (this.searchingForLand) {
            return true;
        } else {
            LivingEntity livingentity = this.getTarget();
            return livingentity != null && livingentity.isInWater();
        }
    }

    protected boolean closeToNextPos() {
        Path path = this.getNavigation().getPath();
        if (path != null) {
            BlockPos blockpos = path.getTarget();
            if (blockpos != null) {
                double d0 = this.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ());
                return d0 < 4.0;
            }
        }

        return false;
    }

    public void setSearchingForLand(boolean searchingForLand) {
        this.searchingForLand = searchingForLand;
    }

    static class PirateBrowncoatMoveControl extends MoveControl {
        private final PirateBrowncoat pirateBrowncoat;

        public PirateBrowncoatMoveControl(PirateBrowncoat pirateBrowncoat) {
            super(pirateBrowncoat);
            this.pirateBrowncoat = pirateBrowncoat;
        }

        public void tick() {
            LivingEntity livingentity = this.pirateBrowncoat.getTarget();
            if (this.pirateBrowncoat.wantsToSwim() && this.pirateBrowncoat.isInWater()) {
                if (livingentity != null && livingentity.getY() > this.pirateBrowncoat.getY() || this.pirateBrowncoat.searchingForLand) {
                    this.pirateBrowncoat.setDeltaMovement(this.pirateBrowncoat.getDeltaMovement().add(0.0, 0.002, 0.0));
                }

                if (this.operation != Operation.MOVE_TO || this.pirateBrowncoat.getNavigation().isDone()) {
                    this.pirateBrowncoat.setSpeed(0.0F);
                    return;
                }

                double d0 = this.wantedX - this.pirateBrowncoat.getX();
                double d1 = this.wantedY - this.pirateBrowncoat.getY();
                double d2 = this.wantedZ - this.pirateBrowncoat.getZ();
                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float) (Mth.atan2(d2, d0) * 180.0 / 3.1415927410125732) - 90.0F;
                this.pirateBrowncoat.setYRot(this.rotlerp(this.pirateBrowncoat.getYRot(), f, 90.0F));
                this.pirateBrowncoat.yBodyRot = this.pirateBrowncoat.getYRot();
                float f1 = (float) (this.speedModifier * this.pirateBrowncoat.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float f2 = Mth.lerp(0.125F, this.pirateBrowncoat.getSpeed(), f1);
                this.pirateBrowncoat.setSpeed(f2);
                this.pirateBrowncoat.setDeltaMovement(this.pirateBrowncoat.getDeltaMovement().add((double) f2 * d0 * 0.005, (double) f2 * d1 * 0.1, (double) f2 * d2 * 0.005));
            } else {
                if (!this.pirateBrowncoat.onGround()) {
                    this.pirateBrowncoat.setDeltaMovement(this.pirateBrowncoat.getDeltaMovement().add(0.0, -0.008, 0.0));
                }

                super.tick();
            }

        }
    }

    static class PirateBrowncoatSwimUpGoal extends Goal {
        private final PirateBrowncoat browncoat;
        private final double speedModifier;
        private final int seaLevel;
        private boolean stuck;

        public PirateBrowncoatSwimUpGoal(PirateBrowncoat browncoat, double speedModifier, int sealevel) {
            this.browncoat = browncoat;
            this.speedModifier = speedModifier;
            this.seaLevel = sealevel;
        }

        @Override
        public boolean canUse() {
            return this.browncoat.isInWater() && this.browncoat.getY() < (double) (this.seaLevel - 2);
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse() && !this.stuck;
        }

        @Override
        public void tick() {
            if (this.browncoat.getY() < (double) (this.seaLevel - 1) && (this.browncoat.getNavigation().isDone() || this.browncoat.closeToNextPos())) {
                Vec3 vec3 = DefaultRandomPos.getPosTowards(
                        this.browncoat, 4, 8, new Vec3(this.browncoat.getX(), this.seaLevel - 1, this.browncoat.getZ()), (float) (Math.PI / 2)
                );
                if (vec3 == null) {
                    this.stuck = true;
                    return;
                }

                this.browncoat.getNavigation().moveTo(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }
        }

        @Override
        public void start() {
            this.browncoat.setSearchingForLand(true);
            this.stuck = false;
        }

        @Override
        public void stop() {
            this.browncoat.setSearchingForLand(false);
        }
    }
}
