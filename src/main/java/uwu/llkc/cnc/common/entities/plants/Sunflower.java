package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.entities.ai.SpawnItemGoal;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.SoundRegistry;

public class Sunflower extends CNCPlant {
    public final AnimationState idle = new AnimationState();
    public final AnimationState produce = new AnimationState();
    public final AnimationState die = new AnimationState();

    public Sunflower(EntityType<Sunflower> entityType, Level level) {
        super(entityType, level);
        moveControl = new MoveControl(this) {
            @Override
            public void tick() {}
        };

        lookControl = new LookControl(this) {
            @Override
            protected boolean resetXRotOnTick() {
                return false;
            }
        };
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        var pos = BlockPos.containing(getX(), getY(), getZ());
        super.setPos(pos.getX() + 0.5, getY(), pos.getZ() + 0.5);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 6, 0.001f));
        goalSelector.addGoal(3, new SpawnItemGoal(new ItemStack(ItemRegistry.SUN.asItem(), 4), 8, this, true, 1120, 1200, 40));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            idle.startIfStopped(tickCount);
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
        super.setPos(x, y, z);
    }

    @Override
    public PlantCategory getPlantCategory() {
        return PlantCategory.SUPPORT;
    }

    @Override
    public int getSunCost() {
        return 0;
    }

    @Override
    public int getCooldown() {
        return 20;
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            produce.start(tickCount);
            playSound(SoundRegistry.SUNFLOWER_PRODUCE.get());
        } else if (id == 1) {
            die.start(tickCount);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.SUNFLOWER_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundRegistry.SUNFLOWER_HURT.get();
    }
}
