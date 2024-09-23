package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import uwu.llkc.cnc.common.entities.plants.WallNut;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @WrapWithCondition(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"))
    private boolean skipIfProtected(Level level, BlockPos pos, ItemStack stack) {
        WallNut entity = level.getNearestEntity(WallNut.class, TargetingConditions.DEFAULT, null, pos.getX(), pos.getY(), pos.getZ(), AABB.ofSize(pos.getCenter(), 20, 20, 20));
        return entity == null || entity.getOwnerUUID() == null;
    }
}
