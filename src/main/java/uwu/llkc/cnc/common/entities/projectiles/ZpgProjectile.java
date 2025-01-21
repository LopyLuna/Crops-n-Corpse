package uwu.llkc.cnc.common.entities.projectiles;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

public class ZpgProjectile extends AbstractHurtingProjectile {
    public ZpgProjectile(EntityType<? extends ZpgProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3, false, Level.ExplosionInteraction.NONE);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount > 100) {
            this.discard();
        }
    }

    /**
     * Called when the arrow hits an entity
     */
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level() instanceof ServerLevel serverlevel) {
            Entity entity1 = result.getEntity();
            //todo: damage source
            DamageSource $$5 = this.damageSources().magic();
            if (entity1 instanceof CNCPlant) {
                entity1.hurt($$5, 160.0F);
            } else {
                entity1.hurt($$5, 16.0F);
            }
            EnchantmentHelper.doPostAttackEffects(serverlevel, entity1, $$5);
        }
    }
}
