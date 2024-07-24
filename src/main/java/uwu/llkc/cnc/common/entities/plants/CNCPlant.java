package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.entities.ai.OwnerHurtByTargetGoalPlant;
import uwu.llkc.cnc.common.entities.ai.OwnerHurtTargetGoalPlant;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

import java.util.UUID;

public abstract class CNCPlant extends Mob implements OwnableEntity, RangedAttackMob {
    private UUID owner;

    protected CNCPlant(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (owner != null) {
            compound.putUUID("owner", owner);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("owner")) {
            owner = compound.getUUID("owner");
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide) {
            ProfilerFiller profilerfiller = this.level().getProfiler();
            int i = this.tickCount + this.getId();
            if (i % 2 != 0 && this.tickCount > 1) {
                profilerfiller.push("targetSelector");
                this.targetSelector.tickRunningGoals(false);
                profilerfiller.pop();
                profilerfiller.push("goalSelector");
                this.goalSelector.tickRunningGoals(false);
                profilerfiller.pop();
            } else {
                profilerfiller.push("targetSelector");
                this.targetSelector.tick();
                profilerfiller.pop();
                profilerfiller.push("goalSelector");
                this.goalSelector.tick();
                profilerfiller.pop();
            }
            this.lookControl.tick();
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        targetSelector.addGoal(0, new OwnerHurtByTargetGoalPlant(this));
        targetSelector.addGoal(0, new OwnerHurtTargetGoalPlant(this));
        targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, entity -> !entity.getUUID().equals(owner)) {
            @Override
            public void stop() {}
        });
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, entity -> entity instanceof Enemy && !(entity instanceof Creeper)) {
            @Override
            public void stop() {}
        });
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return owner;
    }

    @Override
    protected float getKnockback(Entity attacker, DamageSource damageSource) {
        return 0;
    }

    @Override
    public void knockback(double strength, double x, double z) {}

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {}

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        if (player.getItemInHand(hand).is(ItemRegistry.PLANT_FOOD.get())) {
            if (!level().isClientSide && getOwnerUUID() == null) {
                tryToTame(player);
                player.getItemInHand(hand).consume(1, player);
                return InteractionResult.SUCCESS;
            } else if (this.getHealth() < this.getMaxHealth()) {
                this.heal(2);
                player.getItemInHand(hand).consume(1, player);
                this.gameEvent(GameEvent.EAT); // Neo: add EAT game event
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        } else if (player.getItemInHand(hand).is(ItemRegistry.SEED_PACKET.get()) && getOwner() != null && getOwner().equals(player)){
            if (player.getItemInHand(hand).getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).isEmpty()) {
                var itemStack = new ItemStack(ItemRegistry.SEED_PACKET.get(), 1);
                CompoundTag entityData = new CompoundTag();
                this.save(entityData);
                this.discard();
                entityData.remove("Pos");
                CustomData customData = CustomData.of(entityData);
                itemStack.set(DataComponents.ENTITY_DATA, customData);
                itemStack.set(DataComponentRegistry.PLANTS.get(), getPlantPacketOverrideFloat());
                player.setItemInHand(hand, ItemUtils.createFilledResult(player.getItemInHand(hand), player, itemStack, true));
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0) {
            owner = player.getUUID();
            this.navigation.stop();
            this.setTarget(null);
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    public int getMaxAirSupply() {
        return 20;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 7) {
            this.spawnTamingParticles(true);
        } else if (id == 6) {
            this.spawnTamingParticles(false);
        } else {
            super.handleEntityEvent(id);
        }
    }

    protected void spawnTamingParticles(boolean tamed) {
        ParticleOptions particleoptions = ParticleTypes.HEART;
        if (!tamed) {
            particleoptions = ParticleTypes.SMOKE;
        }

        for (int i = 0; i < 7; i++) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(particleoptions, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), d0, d1, d2);
        }
    }

    public abstract PlantCategory getPlantCategory();

    public abstract float getPlantPacketOverrideFloat();

    public abstract int sunCost();
}
