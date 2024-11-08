package uwu.llkc.cnc.client.events;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CalculatePlayerTurnEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import uwu.llkc.cnc.CNCMod;
import uwu.llkc.cnc.common.init.AttachmentTypeRegistry;

@EventBusSubscriber(modid = CNCMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class NeoForgeEvents {
    @SubscribeEvent
    public static void turnPlayer(final CalculatePlayerTurnEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.getData(AttachmentTypeRegistry.FROZEN)) {
                event.setMouseSensitivity(-1 / 3f);
            }
        }
    }

    @SubscribeEvent
    public static void inputEvent(final InputEvent.InteractionKeyMappingTriggered event) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.getData(AttachmentTypeRegistry.FROZEN)) {
                event.setSwingHand(false);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void movementEvent(final MovementInputUpdateEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.getData(AttachmentTypeRegistry.FROZEN)) {
                event.getInput().up = false;
                event.getInput().down = false;
                event.getInput().left = false;
                event.getInput().right = false;
                event.getInput().jumping = false;
                event.getInput().shiftKeyDown = false;
                event.getInput().forwardImpulse = 0;
                event.getInput().leftImpulse = 0;
            }
        }
    }
}
