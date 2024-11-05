package uwu.llkc.cnc.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uwu.llkc.cnc.CNCMod;

import java.util.Map;
import java.util.function.Supplier;

public class AttachmentTypeRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CNCMod.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> CHILL_TIME = ATTACHMENT_TYPES.register("chill",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Boolean>> FROZEN = ATTACHMENT_TYPES.register("frozen",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

    public static final Supplier<AttachmentType<Map<ModelPart, PartPose>>> MODEL_PARTS = ATTACHMENT_TYPES.register("model_parts",
            () -> AttachmentType.<Map<ModelPart, PartPose>>builder(Map::of).build());
}
