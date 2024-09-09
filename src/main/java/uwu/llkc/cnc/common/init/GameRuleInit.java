package uwu.llkc.cnc.common.init;

import net.minecraft.world.level.GameRules;

public class GameRuleInit {
    public static final GameRules.Key<GameRules.BooleanValue> RULE_SEED_PACKET_COOLDOWN = GameRules.register(
            "seedPacketCooldown", GameRules.Category.MISC, GameRules.BooleanValue.create(true)
    );

    public static void init() {}
}
