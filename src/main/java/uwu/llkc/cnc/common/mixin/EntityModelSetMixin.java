package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import uwu.llkc.cnc.common.util.ModelSetMixinHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(EntityModelSet.class)
public class EntityModelSetMixin implements ModelSetMixinHelper {
    @Unique
    private final Map<ModelLayerLocation, ModelPart> crops_n_Corpse$roots = new HashMap<>();

    @ModifyReturnValue(method = "bakeLayer", at = @At("RETURN"))
    private ModelPart cnc$bakeRoot(ModelPart modelPart, @Local(argsOnly = true) ModelLayerLocation modelLayerLocation) {
        crops_n_Corpse$roots.put(modelLayerLocation, modelPart);
        return modelPart;
    }

    @Override
    public Optional<ModelPart> cnc$getRoot(ModelLayerLocation modelLayerLocation) {
        return Optional.ofNullable(crops_n_Corpse$roots.get(modelLayerLocation));
    }
}
