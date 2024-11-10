package uwu.llkc.cnc.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.util.ModelPartData;

import java.util.Map;
import java.util.function.Supplier;

public class AttachmentTypeRegistry {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, CNCMod.MOD_ID);

    public static final Supplier<AttachmentType<Integer>> CHILL_DURATION = ATTACHMENT_TYPES.register("chill_duration",
            () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build());

    public static final Supplier<AttachmentType<Integer>> CHILL_STRENGTH = ATTACHMENT_TYPES.register("chill_strength",
            () -> AttachmentType.builder(() -> 0).build());

    public static final Supplier<AttachmentType<Integer>> CHILL_TIME = ATTACHMENT_TYPES.register("chill_time",
            () -> AttachmentType.builder(() -> 0).build());

    public static final Supplier<AttachmentType<Boolean>> FROZEN = ATTACHMENT_TYPES.register("frozen",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

    public static final Supplier<AttachmentType<Boolean>> CHILLED = ATTACHMENT_TYPES.register("chilled",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

    public static final Supplier<AttachmentType<Boolean>> CHILL_UPDATED = ATTACHMENT_TYPES.register("chill_updated",
            () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build());

    public static final Supplier<AttachmentType<Map<ModelPart, ModelPartData>>> MODEL_PARTS = ATTACHMENT_TYPES.register("model_parts",
            () -> AttachmentType.<Map<ModelPart, ModelPartData>>builder(Map::of).build());
}
