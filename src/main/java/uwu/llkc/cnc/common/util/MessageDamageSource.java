package uwu.llkc.cnc.common.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MessageDamageSource extends DamageSource {
    private final String messageId;

    public MessageDamageSource(DamageSource damageSource, String messageId) {
        super(damageSource.typeHolder(), damageSource.getDirectEntity(), damageSource.getEntity(), damageSource.sourcePositionRaw());
        this.messageId = messageId;
    }

    @Override
    public @NotNull String getMsgId() {
        return messageId;
    }

    @Override
    public Component getLocalizedDeathMessage(LivingEntity livingEntity) {
        String s = messageId;
        if (getEntity() == null && getDirectEntity() == null) {
            LivingEntity livingentity1 = livingEntity.getKillCredit();
            String s1 = s + ".player";
            return livingentity1 != null
                    ? Component.translatable(s1, livingEntity.getDisplayName(), livingentity1.getDisplayName())
                    : Component.translatable(s, livingEntity.getDisplayName());
        } else {
            Component component = getEntity() == null ? getDirectEntity().getDisplayName() : getEntity().getDisplayName();
            ItemStack itemstack = getEntity() instanceof LivingEntity livingentity ? livingentity.getMainHandItem() : ItemStack.EMPTY;
            return !itemstack.isEmpty() && itemstack.has(DataComponents.CUSTOM_NAME)
                    ? Component.translatable(s + ".item", livingEntity.getDisplayName(), component, itemstack.getDisplayName())
                    : Component.translatable(s, livingEntity.getDisplayName(), component);
        }
    }
}
