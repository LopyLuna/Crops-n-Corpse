package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.init.SoundRegistry;

public class WallNut extends CNCPlant {
    public static final EntityDataAccessor<Integer> STAGE = SynchedEntityData.defineId(WallNut.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> HAS_ARMOR = SynchedEntityData.defineId(WallNut.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState glance = new AnimationState();
    public final AnimationState stage1 = new AnimationState();
    public final AnimationState stage2 = new AnimationState();
    public final AnimationState stage3 = new AnimationState();
    public final AnimationState death = new AnimationState();

    public WallNut(EntityType<WallNut> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STAGE, 0);
        builder.define(HAS_ARMOR, false);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 8)
                .add(Attributes.MAX_HEALTH, 200);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6, 0.001f));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            stage1.animateWhen(entityData.get(STAGE) == 1, tickCount);
            stage2.animateWhen(entityData.get(STAGE) == 2, tickCount);
            stage3.animateWhen(entityData.get(STAGE) == 3, tickCount);
            if (getRandom().nextInt(2) < 0.02) {
                glance.startIfStopped(tickCount);
            } else {
                glance.stop();
            }
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        level().broadcastEntityEvent(this, (byte)1);
        super.die(damageSource);
    }

    @Override
    public PlantCategory getPlantCategory() {
        return PlantCategory.DEFENSIVE;
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 1) {
            death.start(tickCount);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        //todo
        return SoundRegistry.SUNFLOWER_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        //todo
        return SoundRegistry.SUNFLOWER_HURT.get();
    }
}
