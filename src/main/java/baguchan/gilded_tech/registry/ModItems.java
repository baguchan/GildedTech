package baguchan.gilded_tech.registry;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.item.HostCompactSpawnerItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, GildedTech.MODID);


    public static final Supplier<Item> NETHERITE_POWDER = ITEMS.register("netherite_powder", () -> new Item((new Item.Properties().fireResistant())));

    public static final Supplier<Item> WROUGHT_NETHERITE_INGOT = ITEMS.register("wrought_netherite_ingot", () -> new Item((new Item.Properties().fireResistant())));
    public static final Supplier<Item> WROUGHT_NETHERITE_NUGGET = ITEMS.register("wrought_netherite_nugget", () -> new Item((new Item.Properties().fireResistant())));

    public static final Supplier<ArmorItem> WROUGHT_NETHERITE_HELMET = ITEMS.register("wrought_netherite_helmet", () -> new ArmorItem(ModArmorMaterial.WROUGHT_NETHERITE, ArmorItem.Type.HELMET, (new Item.Properties().fireResistant())));
    public static final Supplier<ArmorItem> WROUGHT_NETHERITE_CHESTPLATE = ITEMS.register("wrought_netherite_chestplate", () -> new ArmorItem(ModArmorMaterial.WROUGHT_NETHERITE, ArmorItem.Type.CHESTPLATE, (new Item.Properties().fireResistant())));
    public static final Supplier<ArmorItem> WROUGHT_NETHERITE_LEGGINGS = ITEMS.register("wrought_netherite_leggings", () -> new ArmorItem(ModArmorMaterial.WROUGHT_NETHERITE, ArmorItem.Type.LEGGINGS, (new Item.Properties().fireResistant())));
    public static final Supplier<ArmorItem> WROUGHT_NETHERITE_BOOTS = ITEMS.register("wrought_netherite_boots", () -> new ArmorItem(ModArmorMaterial.WROUGHT_NETHERITE, ArmorItem.Type.BOOTS, (new Item.Properties().fireResistant())));

    public static final Supplier<Item> WROUGHT_NETHERITE_SWORD = ITEMS.register("wrought_netherite_sword", () -> new SwordItem(ModItemTier.WROUGHT_NETHERITE, 3, -2.4F, (new Item.Properties().fireResistant())));
    public static final Supplier<Item> WROUGHT_NETHERITE_AXE = ITEMS.register("wrought_netherite_axe", () -> new AxeItem(ModItemTier.WROUGHT_NETHERITE, 6.0F, -3.0F, (new Item.Properties().fireResistant())));
    public static final Supplier<Item> WROUGHT_NETHERITE_PICKAXE = ITEMS.register("wrought_netherite_pickaxe", () -> new PickaxeItem(ModItemTier.WROUGHT_NETHERITE, 1, -2.8F, (new Item.Properties().fireResistant())));
    public static final Supplier<Item> WROUGHT_NETHERITE_SHOVEL = ITEMS.register("wrought_netherite_shovel", () -> new ShovelItem(ModItemTier.WROUGHT_NETHERITE, 1.5F, -3.0F, (new Item.Properties().fireResistant())));
    public static final Supplier<Item> WROUGHT_NETHERITE_HOE = ITEMS.register("wrought_netherite_hoe", () -> new HoeItem(ModItemTier.WROUGHT_NETHERITE, -3, 0.0F, (new Item.Properties().fireResistant())));

    public static final Supplier<Item> HOST_COMPACT_SPAWNER = ITEMS.register("host_compact_spawner", () -> new HostCompactSpawnerItem((new Item.Properties().stacksTo(1))));


}
