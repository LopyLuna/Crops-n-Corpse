package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.entities.ai.OwnerHurtByTargetGoalPlant;
import uwu.llkc.cnc.common.entities.ai.OwnerHurtTargetGoalPlant;
import uwu.llkc.cnc.common.init.ItemRegistry;
import uwu.llkc.cnc.common.init.Tags;
import uwu.llkc.cnc.common.items.SeedPacketItem;

import java.util.UUID;

public abstract class CNCPlant extends Mob implements OwnableEntity {
    private UUID owner;

    protected CNCPlant(EntityType<? extends Mob> entityType, Level level) {
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
            if (i % 2 != 1 && this.tickCount > 1) {
                profilerfiller.push("targetSelector");
                this.targetSelector.tickRunningGoals(false);
                if (level().getGameTime() % 40 == 0) {
                    for (Monster nearbyEntity : level().getNearbyEntities(Monster.class, TargetingConditions.DEFAULT, this, AABB.ofSize(this.position(), 30, 10, 30))) {
                        if (nearbyEntity.getType().is(EntityTypeTags.UNDEAD)) {
                            if (this.getType().is(Tags.EntityTypes.DEFENSIVE_PLANTS)) {
                                if (nearbyEntity.getTarget() == null || nearbyEntity.getTarget() instanceof Player || nearbyEntity.getTarget() instanceof CNCPlant plant && !plant.getType().is(Tags.EntityTypes.DEFENSIVE_PLANTS)) {
                                    nearbyEntity.setTarget(this);
                                }
                            } else if (nearbyEntity.getTarget() == null) {
                                nearbyEntity.setTarget(this);
                            }
                        }
                    }
                }
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
        targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, entity -> entity instanceof Enemy && !(entity instanceof Creeper) && !(this.getOwnerUUID() != null && entity instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null && ownable.getOwnerUUID().equals(getOwnerUUID()))) {
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
        } else if (player.getItemInHand(hand).is(ItemRegistry.EMPTY_SEED_PACKET.get()) && getOwner() != null && getOwner().equals(player)){
            if (player.getItemInHand(hand).getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).isEmpty()) {
                var itemStack = SeedPacketItem.getSeedPacket(getType());
                CompoundTag entityData = new CompoundTag();
                this.save(entityData);
                this.discard();
                entityData.remove("Pos");
                CustomData customData = CustomData.of(entityData);
                itemStack.set(DataComponents.ENTITY_DATA, customData);
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
            setPersistenceRequired();
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

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return SeedPacketItem.getSeedPacket(getType());
    }
}
