package uwu.llkc.cnc.common.entities.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.Optional;

public class MultiHeadLookControl<T extends Mob & IMultiHeadEntity> extends LookControl {
    private final LookControl[] lookControls;

    public MultiHeadLookControl(T mob) {
        super(mob);

        lookControls = new LookControl[mob.headCount()];
        for (int i = 0; i < mob.headCount(); i++) {
            lookControls[i] = new HeadLookControl(mob, i);
        }
    }

    @Override
    public void setLookAt(@NotNull Vec3 lookVector) {
        for (LookControl lookControl : lookControls) {
            lookControl.setLookAt(lookVector);
        }
    }

    public void setRandomLookAt(Vec3 lookVector) {
        lookControls[mob.getRandom().nextInt(lookControls.length)].setLookAt(lookVector);
    }

    public void setLookAt(Vec3 lookVector, int index) {
        lookControls[index].setLookAt(lookVector);
    }

    @Override
    public void setLookAt(@NotNull Entity entity) {
        for (LookControl lookControl : lookControls) {
            lookControl.setLookAt(entity);
        }
    }

    public void setRandomLookAt(Entity entity) {
        lookControls[mob.getRandom().nextInt(lookControls.length)].setLookAt(entity);
    }

    public void setLookAt(Entity entity, int index) {
        lookControls[index].setLookAt(entity);
    }

    @Override
    public void setLookAt(@NotNull Entity entity, float deltaYaw, float deltaPitch) {
        for (LookControl lookControl : lookControls) {
            lookControl.setLookAt(entity, deltaYaw, deltaPitch);
        }
    }

    public void setRandomLookAt(@NotNull Entity entity, float deltaYaw, float deltaPitch) {
        lookControls[mob.getRandom().nextInt(lookControls.length)].setLookAt(entity, deltaYaw, deltaPitch);
    }

    public void setLookAt(Entity entity, float deltaYaw, float deltaPitch, int index) {
        lookControls[index].setLookAt(entity, deltaYaw, deltaPitch);
    }

    @Override
    public void setLookAt(double x, double y, double z) {
        for (LookControl lookControl : lookControls) {
            lookControl.setLookAt(x, y, z);
        }
    }

    public void setRandomLookAt(double x, double y, double z) {
        lookControls[mob.getRandom().nextInt(lookControls.length)].setLookAt(x, y, z);
    }

    public void setLookAt(double x, double y, double z, int index) {
        lookControls[index].setLookAt(x, y, z);
    }

    @Override
    public void setLookAt(double x, double y, double z, float deltaYaw, float deltaPitch) {
        for (LookControl lookControl : lookControls) {
            lookControl.setLookAt(x, y, z, deltaYaw, deltaPitch);
        }
    }

    public void setRandomLookAt(double x, double y, double z, float deltaYaw, float deltaPitch) {
        lookControls[mob.getRandom().nextInt(lookControls.length)].setLookAt(x, y, z, deltaYaw, deltaPitch);
    }

    public void setLookAt(double x, double y, double z, float deltaYaw, float deltaPitch, int index) {
        lookControls[index].setLookAt(x, y, z, deltaYaw, deltaPitch);
    }

    @Override
    public void tick() {
        for (LookControl lookControl : lookControls) {
            lookControl.tick();
        }
    }

    @Override
    public boolean isLookingAtTarget() {
        return Arrays.stream(lookControls).allMatch(LookControl::isLookingAtTarget);
    }

    public boolean isEitherLookingAtTarget() {
        return Arrays.stream(lookControls).anyMatch(LookControl::isLookingAtTarget);
    }

    public boolean isLookingAtTarget(int index) {
        return lookControls[index].isLookingAtTarget();
    }

    @Override
    public double getWantedX() {
        return Arrays.stream(lookControls).mapToDouble(LookControl::getWantedX).average().orElse(0);
    }

    public double getWantedX(int index) {
        return lookControls[index].getWantedX();
    }

    @Override
    public double getWantedY() {
        return Arrays.stream(lookControls).mapToDouble(LookControl::getWantedY).average().orElse(0);
    }

    public double getWantedY(int index) {
        return lookControls[index].getWantedY();
    }

    @Override
    public double getWantedZ() {
        return Arrays.stream(lookControls).mapToDouble(LookControl::getWantedZ).average().orElse(0);
    }

    public double getWantedZ(int index) {
        return lookControls[index].getWantedZ();
    }

    private class HeadLookControl extends LookControl {
        private final int index;

        public HeadLookControl(T mob, int index) {
            super(mob);
            this.index = index;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected @NotNull Optional<Float> getXRotD() {
            var headPos = ((T) mob).getHeadPosition(index);

            double d0 = this.wantedX - headPos.x;
            double d1 = this.wantedY - headPos.y;
            double d2 = this.wantedZ - headPos.z;
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            return !(Math.abs(d1) > 1.0E-5F) && !(Math.abs(d3) > 1.0E-5F) ? Optional.empty() : Optional.of((float) (-(Mth.atan2(d1, d3) * 180.0F / (float) Math.PI)));
        }

        @SuppressWarnings("unchecked")
        @Override
        protected @NotNull Optional<Float> getYRotD() {
            var headPos = ((T) mob).getHeadPosition(index);

            double d0 = this.wantedX - headPos.x;
            double d1 = this.wantedZ - headPos.z;
            return !(Math.abs(d1) > 1.0E-5F) && !(Math.abs(d0) > 1.0E-5F)
                    ? Optional.empty()
                    : Optional.of((float) (Mth.atan2(d1, d0) * 180.0F / (float) Math.PI) - 90.0F);
        }

        @SuppressWarnings("unchecked")
        public void setLookAt(double x, double y, double z) {
            this.setLookAt(x, y, z, ((T) mob).getHeadRotSpeed(index), ((T) mob).getMaxHeadXRot(index));
        }

        @SuppressWarnings("unchecked")
        public void tick() {
            if (this.resetXRotOnTick()) {
                ((T) mob).setHeadXRot(index, 0.0F);
            }

            if (this.lookAtCooldown > 0) {
                this.lookAtCooldown--;
                this.getYRotD().ifPresent(p_287447_ -> ((T) mob).setHeadYRot(index, this.rotateTowards(((T) mob).getHeadYRot(index), p_287447_, this.yMaxRotSpeed)));
                this.getXRotD().ifPresent(p_352768_ -> ((T) mob).setHeadXRot(index, this.rotateTowards(((T) mob).getHeadXRot(index), p_352768_, this.xMaxRotAngle)));
            }
        }

        @SuppressWarnings("unchecked")
        protected void clampHeadRotationToBody() {
            if (!this.mob.getNavigation().isDone()) {
                ((T) mob).setHeadYRot(index, Mth.rotateIfNecessary(((T) mob).getHeadYRot(index), this.mob.yBodyRot, ((T) mob).getMaxHeadYRot(index)));
            }
        }
    }
}
