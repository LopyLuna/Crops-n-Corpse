package uwu.llkc.cnc.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import uwu.llkc.cnc.common.util.LayerDefinitionMixinHelper;

import javax.annotation.Nullable;
import java.util.Optional;

@Mixin(LayerDefinition.class)
public class LayerDefinitionMixin implements LayerDefinitionMixinHelper {
    @Unique
    @Nullable
    private ModelPart crops_n_Corpse$root;

    @ModifyReturnValue(method = "bakeRoot", at = @At("RETURN"))
    private ModelPart cnc$bakeRoot(ModelPart modelPart) {
        this.crops_n_Corpse$root = modelPart;
        return modelPart;
    }

    @Override
    public Optional<ModelPart> cnc$getRoot() {
        return Optional.ofNullable(crops_n_Corpse$root);
    }
}
