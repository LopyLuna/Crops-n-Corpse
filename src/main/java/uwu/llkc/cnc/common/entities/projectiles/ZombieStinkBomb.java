package uwu.llkc.cnc.common.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import uwu.llkc.cnc.common.entities.plants.CNCPlant;

public class ZombieStinkBomb extends AbstractArrow {
    public static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(ZombieStinkBomb.class, EntityDataSerializers.BOOLEAN);
    private static final float RANGE = 3;
    private int timer = 200;

    public ZombieStinkBomb(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ACTIVATED, false);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        entityData.set(ACTIVATED, true);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        //todo custom sound
        return SoundEvents.EMPTY;
    }

    @Override
    public void tick() {
        super.tick();
        if (entityData.get(ACTIVATED)) {
            if (timer <= 0) {
                remove(RemovalReason.DISCARDED);
                return;
            }
            timer--;
            if (level().isClientSide()) {
                double x = Mth.lerp(random.nextFloat(), getX() - RANGE, getX() + RANGE);
                double y = Mth.lerp(random.nextFloat(), getY() - RANGE, getY() + RANGE);
                double z = Mth.lerp(random.nextFloat(), getZ() - RANGE, getZ() + RANGE);

                level().addParticle(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 0, 0, 0);
            } else {
                var entities = level().getEntities(this, getBoundingBox().inflate(RANGE));
                for (var entity : entities) {
                    if (entity == getOwner()) continue;
                    //todo custom damage type
                    entity.hurt(damageSources().source(DamageTypes.MAGIC), 1);
                    if (entity instanceof CNCPlant plant) {
                        plant.setTarget(null);
                    }
                }
            }
        }
    }


    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }
}
