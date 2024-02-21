package baguchan.gilded_tech.client;


import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.client.overlay.AllayOverlay;
import baguchan.gilded_tech.client.render.GatherAllayRenderer;
import baguchan.gilded_tech.registry.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GildedTech.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrar {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GATHER_ALLAY.get(), GatherAllayRenderer::new);
    }
    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll(new ResourceLocation(GildedTech.MODID, "entity_select"), new AllayOverlay());
    }

}
