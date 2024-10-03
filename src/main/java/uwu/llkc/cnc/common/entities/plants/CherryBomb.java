package uwu.llkc.cnc.common.entities.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.DynamicGameEventListener;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.common.init.SoundRegistry;
import uwu.llkc.cnc.common.init.Tags;

import java.util.function.BiConsumer;

public class CherryBomb extends CNCPlant implements VibrationSystem {
    public final AnimationState idle = new AnimationState();
    public final AnimationState attack = new AnimationState();
    public final AnimationState die = new AnimationState();
    private final DynamicGameEventListener<VibrationSystem.Listener> dynamicGameEventListener;
    private final VibrationSystem.User vibrationUser;
    private VibrationSystem.Data vibrationData;


    public CherryBomb(EntityType<CherryBomb> entityType, Level level) {
        super(entityType, level);
        this.vibrationUser = new CherryBomb.VibrationUser();
        this.vibrationData = new VibrationSystem.Data();
        this.dynamicGameEventListener = new DynamicGameEventListener<VibrationSystem.Listener>(new VibrationSystem.Listener(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
        VibrationSystem.Data.CODEC
                .encodeStart(registryops, this.vibrationData)
                .resultOrPartial(result -> LogManager.getLogger().error("Failed to encode vibration listener for Warden: '{}'", result))
                .ifPresent(tag -> compound.put("listener", tag));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        RegistryOps<Tag> registryops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
        if (compound.contains("listener", 10)) {
            VibrationSystem.Data.CODEC
                    .parse(registryops, compound.getCompound("listener"))
                    .resultOrPartial(result -> LogManager.getLogger().error("Failed to parse vibration listener for Warden: '{}'", result))
                    .ifPresent(tag -> this.vibrationData = tag);
        }
    }

    public static AttributeSupplier.Builder attributes() {
        return CNCPlant.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

    }

    @Override
    public void tick() {
        if (this.level() instanceof ServerLevel serverLevel) {
            VibrationSystem.Ticker.tick(serverLevel, this.vibrationData, this.vibrationUser);
        }

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
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 0) {
            attack.start(tickCount);
        } else if (id == 1) {
            die.start(tickCount);
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundRegistry.PEASHOOTER_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundRegistry.PEASHOOTER_HURT.get();
    }

    @Override
    public void updateDynamicGameEventListener(BiConsumer<DynamicGameEventListener<?>, ServerLevel> listenerConsumer) {
        if (this.level() instanceof ServerLevel serverLevel) {
            listenerConsumer.accept(this.dynamicGameEventListener, serverLevel);
        }
    }

    @Override
    public Data getVibrationData() {
        return vibrationData;
    }

    @Override
    public User getVibrationUser() {
        return vibrationUser;
    }

    class VibrationUser implements VibrationSystem.User {
        private final PositionSource positionSource = new EntityPositionSource(CherryBomb.this, CherryBomb.this.getEyeHeight());

        @Override
        public int getListenerRadius() {
            return 7;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public TagKey<GameEvent> getListenableEvents() {
            return Tags.gameEvents.CAN_CHERRY_BOMB_LISTEN;
        }

        @Override
        public boolean canTriggerAvoidVibration() {
            return true;
        }

        @Override
        public boolean canReceiveVibration(ServerLevel p_282574_, BlockPos p_282323_, Holder<GameEvent> p_316784_, GameEvent.Context p_282515_) {
            if (!CherryBomb.this.isNoAi()
                    && !CherryBomb.this.isDeadOrDying()
                    && p_282574_.getWorldBorder().isWithinBounds(p_282323_)) {
                return !(p_282515_.sourceEntity() instanceof LivingEntity);
            } else {
                return false;
            }
        }

        @Override
        public void onReceiveVibration(
                ServerLevel p_281325_, BlockPos p_282386_, Holder<GameEvent> p_316139_, @javax.annotation.Nullable Entity p_281438_, @javax.annotation.Nullable Entity p_282582_, float p_283699_
        ) {
            if (!CherryBomb.this.isDeadOrDying()) {
                BlockPos blockpos = p_282386_;
                //todo explode
            }
        }
    }
}
