package uwu.llkc.cnc.common.util;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;

import java.util.Optional;

public interface ModelSetMixinHelper {
    Optional<ModelPart> cnc$getRoot(ModelLayerLocation modelLayerLocation);
}
