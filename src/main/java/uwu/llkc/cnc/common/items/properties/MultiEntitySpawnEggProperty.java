package uwu.llkc.cnc.common.items.properties;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.DataComponentRegistry;

import javax.annotation.Nullable;

public class MultiEntitySpawnEggProperty implements ClampedItemPropertyFunction {
    public static final ResourceLocation ID = CNCMod.rl("multi_entity_spawn_egg_property");
    public static final MultiEntitySpawnEggProperty INSTANCE = new MultiEntitySpawnEggProperty();

    private MultiEntitySpawnEggProperty() {}

    @Override
    public float unclampedCall(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        return stack.getOrDefault(DataComponentRegistry.SELECTED_ENTITY, 0) / 100f;
    }

}
