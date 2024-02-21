package baguchan.gilded_tech.client;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.client.overlay.AllayOverlay;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.TickEvent;

@Mod.EventBusSubscriber(modid = GildedTech.MODID, value = Dist.CLIENT)
public class ClientForgeEvent {

    public static void clientTick(TickEvent.ClientTickEvent event) {
    }

    @SubscribeEvent
    public static void onMouseScrolled(InputEvent.MouseScrollingEvent event) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isShiftKeyDown() && AllayOverlay.onMouseScrolled(event.getScrollDeltaY())) {
            event.setCanceled(true);
        }
    }
}
