package uwu.llkc.cnc.common.entities.zombies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import uwu.llkc.cnc.client.util.ClientProxy;

public class MummifiedImp extends Imp {
    public static final EntityDataAccessor<Boolean> HAS_SANDSTORM = SynchedEntityData.defineId(MummifiedImp.class, EntityDataSerializers.BOOLEAN);

    private static final int SANDSTORM_TIME = 200;
    private static final float SANDSTORM_CHANCE = 0.5f;


    public MummifiedImp(EntityType<MummifiedImp> entityType, Level level) {
        super(entityType, level);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasSandstorm", entityData.get(HAS_SANDSTORM));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(HAS_SANDSTORM, compound.getBoolean("hasSandstorm"));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HAS_SANDSTORM, false);
    }


    @Override
    public void tick() {
        super.tick();
        if (level().getBiome(blockPosition()).is(Tags.Biomes.IS_SANDY) && !entityData.get(HAS_SANDSTORM) && level().isThundering()) {
            if (level().getGameTime() % SANDSTORM_TIME == 5) {
                if (random.nextFloat() < SANDSTORM_CHANCE) {
                    entityData.set(HAS_SANDSTORM, true);
                    getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(MummifiedBrowncoat.SANDSTORM_MOD);
                }
            }
        }
        if (entityData.get(HAS_SANDSTORM)) {
            if (level().getBiome(blockPosition()).is(Tags.Biomes.IS_SANDY)) {
                if (level().getGameTime() % 280 == 5) {
                    entityData.set(HAS_SANDSTORM, false);
                    getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MummifiedBrowncoat.SANDSTORM_MOD);
                }
            } else {
                entityData.set(HAS_SANDSTORM, false);
                getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(MummifiedBrowncoat.SANDSTORM_MOD);
            }
        }
    }
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity) {
            float f = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int) f), this);
        }

        return flag;
    }

    @Override
    protected void createImpArm() {
        ClientProxy.createMummifiedImpArm(this);
    }

    @Override
    public void createImpHead() {
        ClientProxy.createMummifiedImpHead(this);
    }
}
