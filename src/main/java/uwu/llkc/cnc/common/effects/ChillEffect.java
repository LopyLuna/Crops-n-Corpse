package uwu.llkc.cnc.common.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;
import uwu.llkc.cnc.common.init.EffectRegistry;
import uwu.llkc.cnc.common.networking.SetFrozenPayload;

public class ChillEffect extends MobEffect {
    public ChillEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.withDefaultNamespace("effect.chill_effect"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, level -> -0.5);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        double freezePercentage = Math.min(0.99f, (amplifier == 0 ? 0 : (15 + amplifier * 5)) / 100d);
        double currentPercentage = 1 - (livingEntity.getEffect(EffectRegistry.CHILL).getDuration() /
                ((double) livingEntity.getData(AttachmentTypeRegistry.CHILL_TIME)));

        if (currentPercentage < freezePercentage) {
            freeze(livingEntity);
            if (!livingEntity.getData(AttachmentTypeRegistry.FROZEN)) {
                livingEntity.setData(AttachmentTypeRegistry.FROZEN, true);
                PacketDistributor.sendToPlayersTrackingEntity(livingEntity, new SetFrozenPayload(livingEntity.getId(), true));
            }
        } else {
            unFreeze(livingEntity);
            if (livingEntity.getData(AttachmentTypeRegistry.FROZEN)) {
                livingEntity.setData(AttachmentTypeRegistry.FROZEN, false);
                PacketDistributor.sendToPlayersTrackingEntity(livingEntity, new SetFrozenPayload(livingEntity.getId(), false));
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        super.onEffectAdded(livingEntity, amplifier);
        var effect = livingEntity.getEffect(EffectRegistry.CHILL);
        if (effect != null) {
            livingEntity.setData(AttachmentTypeRegistry.CHILL_TIME, effect.getDuration());
        }
    }

    private void freeze(LivingEntity livingEntity) {
        if (livingEntity instanceof Mob mob) {
            mob.yHeadRot = mob.yBodyRot;
            mob.setNoAi(true);
        } else if (livingEntity instanceof Player player) {

        }
    }

    private void unFreeze(LivingEntity livingEntity) {
        if (livingEntity instanceof Mob mob) {
            mob.setNoAi(false);
        } else if (livingEntity instanceof Player player) {

        }
    }
}
