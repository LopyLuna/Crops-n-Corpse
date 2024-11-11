package uwu.llkc.cnc.common.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;
import uwu.llkc.cnc.common.init.EffectRegistry;
import uwu.llkc.cnc.common.networking.SetChillTimePayload;
import uwu.llkc.cnc.common.networking.SetChilledPayload;
import uwu.llkc.cnc.common.networking.SetFrozenPayload;
import uwu.llkc.cnc.common.util.ModelPartData;
import uwu.llkc.cnc.common.util.ModelSetMixinHelper;

import java.util.Optional;
import java.util.stream.Collectors;

public class ChillEffect extends MobEffect {
    public ChillEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.withDefaultNamespace("effect.chill_effect"), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL, level -> -0.5);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {

        double freezePercentage = Math.min(0.99f, (amplifier == 0 ? 0 : (15 + amplifier * 5)) / 100d);
        double currentPercentage = 1 - (livingEntity.getEffect(EffectRegistry.CHILL).getDuration() /
                ((double) livingEntity.getData(AttachmentTypeRegistry.CHILL_DURATION)));

        if (currentPercentage < freezePercentage) {
            freeze(livingEntity);
            //fixme change to player check
            if (!livingEntity.getData(AttachmentTypeRegistry.FROZEN)) {
                livingEntity.setData(AttachmentTypeRegistry.FROZEN, true);
                if (livingEntity.level().isClientSide()) {
                    Optional<ModelLayerLocation> locations = Minecraft.getInstance().getEntityModels().roots.keySet().stream()
                            .filter(layer -> layer.getModel().equals(BuiltInRegistries.ENTITY_TYPE.getKey(livingEntity.getType())))
                            .findFirst();

                    locations.ifPresent(loc -> {
                        if (Minecraft.getInstance().getEntityModels() instanceof ModelSetMixinHelper helper) {
                            helper.cnc$getRoot(loc).ifPresent(root -> livingEntity.setData(
                                    AttachmentTypeRegistry.MODEL_PARTS,
                                    root.getAllParts().collect(Collectors.toMap(
                                            part -> part,
                                            ModelPartData::fromModelPart
                                    ))
                            ));
                        }
                    });
                } else {
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity, new SetFrozenPayload(livingEntity.getId(), true));
                }
            }
        } else {
            unFreeze(livingEntity);
            if (livingEntity.getData(AttachmentTypeRegistry.FROZEN)) {
                if (livingEntity.level().isClientSide()) {
                    livingEntity.setData(AttachmentTypeRegistry.FROZEN, false);
                } else {
                    livingEntity.setData(AttachmentTypeRegistry.FROZEN, false);
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity, new SetFrozenPayload(livingEntity.getId(), false));
                }
            }
        }

        return super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration != 0;
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);

        var effect = livingEntity.getEffect(EffectRegistry.CHILL);
        if (effect != null) {
            if (amplifier < effect.getAmplifier()) {
                return;
            }
            if (!livingEntity.level().isClientSide()) {
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity, new SetChilledPayload(livingEntity.getId(), true, effect.getDuration(), amplifier));
                PacketDistributor.sendToPlayersTrackingEntityAndSelf(livingEntity, new SetChillTimePayload(livingEntity.getId(), effect.getDuration()));
            }
            livingEntity.setData(AttachmentTypeRegistry.CHILL_DURATION, effect.getDuration());
        }
    }

    private void freeze(LivingEntity livingEntity) {
        if (livingEntity instanceof Mob mob) {
            mob.yHeadRot = mob.yBodyRot;
            mob.setNoAi(true);
        }
    }

    private void unFreeze(LivingEntity livingEntity) {
        if (livingEntity instanceof Mob mob) {
            mob.setNoAi(false);
        }
    }
}
