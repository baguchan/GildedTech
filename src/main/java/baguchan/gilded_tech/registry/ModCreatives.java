package baguchan.gilded_tech.registry;

import baguchan.gilded_tech.GildedTech;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModCreatives {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GildedTech.MODID);

    public static final Supplier<CreativeModeTab> GILDED_TECH = CREATIVE_MODE_TABS.register("gilded_tech", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup." + GildedTech.MODID))
            .icon(() -> ModItems.HOST_COMPACT_SPAWNER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(Stream.of(
                        ModItems.HOST_COMPACT_SPAWNER,
                        ModItems.NETHERITE_POWDER,
                        ModItems.WROUGHT_NETHERITE_NUGGET,
                        ModItems.WROUGHT_NETHERITE_INGOT,
                        ModItems.WROUGHT_NETHERITE_SWORD,
                        ModItems.WROUGHT_NETHERITE_PICKAXE,
                        ModItems.WROUGHT_NETHERITE_AXE,
                        ModItems.WROUGHT_NETHERITE_SHOVEL,
                        ModItems.WROUGHT_NETHERITE_HOE,
                        ModItems.WROUGHT_NETHERITE_HELMET,
                        ModItems.WROUGHT_NETHERITE_CHESTPLATE,
                        ModItems.WROUGHT_NETHERITE_LEGGINGS,
                        ModItems.WROUGHT_NETHERITE_BOOTS
                ).map(sup -> {
                    return sup.get().getDefaultInstance();
                }).toList());
            }).build());
}
