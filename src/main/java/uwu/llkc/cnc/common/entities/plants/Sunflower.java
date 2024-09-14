package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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
        goalSelector.addGoal(3, new SpawnItemGoal(new ItemStack(ItemRegistry.SUN.asItem(), 1), 8, this, true, 1120, 1200, 40));
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
    public PlantCategory getPlantCategory() {
        return PlantCategory.SUPPORT;
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
