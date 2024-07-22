package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.entities.projectiles.PeaProjectile;
import uwu.llkc.cnc.common.init.EntityTypeRegistry;

public class Peashooter extends CNCPlant {
    public final AnimationState idle = new AnimationState();
    public final AnimationState attack = new AnimationState();
    public final AnimationState die = new AnimationState();

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
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new RangedAttackGoal(this, 1, 20, 30) {
            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && distanceTo(getTarget()) < 30;
            }
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            idle.animateWhen(!attack.isStarted() && !die.isStarted(), tickCount);
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        level().broadcastEntityEvent(this, (byte)1);
        super.die(damageSource);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
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

    @Override
    public int sunCost() {
        return 16;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity target, float velocity) {
        PeaProjectile projectile = EntityTypeRegistry.PEA.get().create(level());
        if (projectile == null) return;
        projectile.damage = 3;
        projectile.setPos(this.getX(), this.getEyeY(), this.getZ());
        projectile.shoot(target.getX() - getX(), target.getEyeY() - getEyeY(), target.getZ() - getZ(), velocity, 0);
        level().addFreshEntity(projectile);
        level().broadcastEntityEvent(this, (byte) 0);
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            attack.start(tickCount);
        } else if (id == 1) {
            die.start(tickCount);
        }
    }
}
