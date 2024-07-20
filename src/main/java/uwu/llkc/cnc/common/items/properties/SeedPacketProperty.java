package uwu.llkc.cnc.common.items.properties;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.DataComponentRegistry;
import uwu.llkc.cnc.common.init.ItemRegistry;

public class SeedPacketProperty implements ClampedItemPropertyFunction {
    public static final ResourceLocation ID = CNCMod.rl("plant");
    public static final SeedPacketProperty INSTANCE = new SeedPacketProperty();

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return stack.is(ItemRegistry.SEED_PACKET.get()) && stack.has(DataComponentRegistry.PLANTS) ? stack.get(DataComponentRegistry.PLANTS) : 0;
    }
}
