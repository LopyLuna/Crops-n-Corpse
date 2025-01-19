package uwu.llkc.cnc.common.entities.projectiles;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;
import uwu.llkc.cnc.common.init.DamageTypeInit;

public class FootSoldierProjectile extends AbstractHurtingProjectile {
    public FootSoldierProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected @Nullable ParticleOptions getTrailParticle() {
        return null;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            Entity entity = result.getEntity();
            if (entity instanceof CNCPlant shooter && shooter.getOwner() != null && this.getOwner() instanceof CNCPlant shot && shooter.getOwner().getUUID().equals(shot.getOwnerUUID()) || this.getOwner() instanceof CNCPlant shott && shott.getOwnerUUID() != null && shott.getOwnerUUID().equals(entity.getUUID()))
                return;
            if (getOwner() != null && getOwner() instanceof LivingEntity owner) {
                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypeInit.PEA_SHOT), this, owner), 2);
            } else {
                entity.hurt(new DamageSource(level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypeInit.PEA_SHOT), this), 2);
            }
            discard();
        }
    }
}
