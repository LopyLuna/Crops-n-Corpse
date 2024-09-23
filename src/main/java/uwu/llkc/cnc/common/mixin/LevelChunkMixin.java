package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.llkc.cnc.common.entities.plants.WallNut;
import uwu.llkc.cnc.common.util.ChunkMixinHelper;

@Mixin(value = {LevelChunk.class, ProtoChunk.class})
@Debug(export = true)
public abstract class LevelChunkMixin extends ChunkAccess implements ChunkMixinHelper {
    private BlockPos nextBlockPosDoBreak = null;
    private BlockPos nextPosForInteractionCheck = null;
    private BlockState blockStateForDelayedPlace = null;

    public LevelChunkMixin(ChunkPos chunkPos, UpgradeData upgradeData, LevelHeightAccessor levelHeightAccessor, Registry<Biome> biomeRegistry, long inhabitedTime, @Nullable LevelChunkSection[] sections, @Nullable BlendingData blendingData) {
        super(chunkPos, upgradeData, levelHeightAccessor, biomeRegistry, inhabitedTime, sections, blendingData);
    }

    @WrapOperation(method = "setBlockState(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/level/block/state/BlockState;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunkSection;setBlockState(IIILnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState setBlockState(LevelChunkSection chunk, int x, int y, int z, BlockState state, Operation<BlockState> original, BlockPos pos, BlockState otherState, boolean isMoving) {
        if (getLevel() != null &&  (!pos.equals(nextBlockPosDoBreak) && (state.isAir() && !getLevel().getBlockState(pos).isAir() && !isMoving)) && !pos.equals(nextPosForInteractionCheck)) {
            if (StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(f -> f.limit(10).anyMatch(cls -> Block.class.isAssignableFrom(cls.getDeclaringClass())))) return original.call(chunk, x, y, z, state);
            WallNut entity = getLevel().getNearestEntity(WallNut.class, TargetingConditions.DEFAULT, null, pos.getX(), pos.getY(), pos.getZ(), AABB.ofSize(pos.getCenter(), 20, 20, 20));
            if (entity != null && entity.getOwnerUUID() != null) {
                int damage = 0;
                float resistance = getLevel().getBlockState(pos).getBlock().getExplosionResistance();
                if (resistance > 0.1f && resistance <= 5) damage = 1;
                if (resistance > 5f && resistance <= 9) damage = 2;
                if (resistance > 9f && resistance <= 50) damage = 5;
                if (resistance > 50f) damage = 15;
                entity.hurt(getLevel().damageSources().generic(), damage);
                getLevel().sendBlockUpdated(pos, Blocks.AIR.defaultBlockState(), getLevel().getBlockState(pos), 50);
                return getLevel().getBlockState(pos);
            }
        }
        if (!pos.equals(nextPosForInteractionCheck)) {
            nextPosForInteractionCheck = null;
        }
        nextBlockPosDoBreak = null;
        return original.call(chunk, x, y, z, state);
    }

    @Override
    public void setNextBlockPosDoBreak(BlockPos pos) {
        nextBlockPosDoBreak = pos;
    }

    @Override
    public void setNextPosForInteractionCheck(BlockPos nextPosForInteractionCheck) {
        this.nextPosForInteractionCheck = nextPosForInteractionCheck;
    }

    @Override
    public BlockState getBlockStateForDelayedPlace() {
        return blockStateForDelayedPlace;
    }

    @Override
    public BlockPos getNextPosForInteractionCheck() {
        return nextPosForInteractionCheck;
    }
}
