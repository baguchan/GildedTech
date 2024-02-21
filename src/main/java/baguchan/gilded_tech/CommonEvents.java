package baguchan.gilded_tech;

import baguchan.gilded_tech.registry.ModItems;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod.EventBusSubscriber(modid = GildedTech.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void cancelAllayInterect(PlayerInteractEvent.EntityInteract event) {
        if (event.getItemStack().is(ModItems.HOST_COMPACT_SPAWNER.get())) {
            event.setCancellationResult(InteractionResult.PASS);
            event.setCanceled(true);
        }
    }
}
