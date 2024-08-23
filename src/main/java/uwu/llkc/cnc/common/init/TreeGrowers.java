package uwu.llkc.cnc.common.init;

import net.minecraft.world.level.block.grower.TreeGrower;
import uwu.llkc.cnc.CNCMod;

import java.util.Optional;

public class TreeGrowers {
    public static final TreeGrower WALNUT_TREE = new TreeGrower(CNCMod.rlStr("walnut_tree"),
            Optional.empty(), Optional.of(ConfiguredFeatureInit.WALNUT_TREE), Optional.empty());
}
