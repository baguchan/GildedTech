package baguchan.gilded_tech;

import baguchan.gilded_tech.message.EntityCylcleMessage;
import baguchan.gilded_tech.registry.ModCreatives;
import baguchan.gilded_tech.registry.ModEntities;
import baguchan.gilded_tech.registry.ModItems;
import baguchan.gilded_tech.registry.ModMemorys;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.Locale;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GildedTech.MODID)
public class GildedTech {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "gilded_tech";

    // Directly reference a slf4j logger
    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public GildedTech(IEventBus modEventBus) {
        ModMemorys.MEMORY_REGISTRY.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEntities.ENTITIES_REGISTRY.register(modEventBus);
        ModCreatives.CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::setupPackets);
    }

    public void setupPackets(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.play(EntityCylcleMessage.ID, EntityCylcleMessage::new, payload -> payload.server(EntityCylcleMessage::handle));
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(GildedTech.MODID, name.toLowerCase(Locale.ROOT));
    }
}