package uwu.llkc.cnc.common.items;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.entities.zombies.Browncoat;

import javax.annotation.Nullable;

public class ZombieVariantProperty implements ClampedItemPropertyFunction {
    public static final ResourceLocation ID = CNCMod.rl("zombie_variant_property");
    public static final ZombieVariantProperty INSTANCE = new ZombieVariantProperty();

    private ZombieVariantProperty() {
    }

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return switch (entity) {
            case Browncoat browncoat -> 0.1f;
            case null, default -> 0.0f;
        };
    }
}
