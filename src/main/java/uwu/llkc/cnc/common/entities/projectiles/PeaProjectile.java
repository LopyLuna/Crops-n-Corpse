package uwu.llkc.cnc.common.entities.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.entities.plants.Peashooter;
import uwu.llkc.cnc.common.init.DamageTypeInit;

public class PeaProjectile extends AbstractHurtingProjectile {
    public int damage;

    public PeaProjectile(EntityType<PeaProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Nullable
    @Override
    protected ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("damage", damage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        damage = compound.getInt("damage");
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide && result.getType() != HitResult.Type.ENTITY) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        applyGravity();
    }

    @Override
    public double getDefaultGravity() {
        return 0.01d;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            if (entity instanceof CNCPlant shooter && shooter.getOwner() != null && this.getOwner() instanceof CNCPlant shot && shooter.getOwner().getUUID().equals(shot.getOwnerUUID()) || this.getOwner() instanceof CNCPlant shott && shott.getOwnerUUID() != null && shott.getOwnerUUID().equals(entity.getUUID())) return;
            if (getOwner() != null && getOwner() instanceof LivingEntity owner) {
                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypeInit.PEA_SHOT), this, owner), damage);
            } else {
                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypeInit.PEA_SHOT), this), damage);
            }
            discard();
        }
    }
}
