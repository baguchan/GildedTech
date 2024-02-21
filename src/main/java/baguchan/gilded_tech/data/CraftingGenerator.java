package baguchan.gilded_tech.data;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.neoforged.neoforge.common.Tags;

public class CraftingGenerator extends CraftingDataHelper{
    public CraftingGenerator(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        miscCooking(ModItems.NETHERITE_POWDER, ModItems.WROUGHT_NETHERITE_NUGGET, 0.2F, consumer, "wrought_netherite");

        helmetItem(consumer, "wrought_netherite_helmet", ModItems.WROUGHT_NETHERITE_HELMET, ModItems.WROUGHT_NETHERITE_INGOT);
        chestplateItem(consumer, "wrought_netherite_chestplate", ModItems.WROUGHT_NETHERITE_CHESTPLATE, ModItems.WROUGHT_NETHERITE_INGOT);
        leggingsItem(consumer, "wrought_netherite_leggings", ModItems.WROUGHT_NETHERITE_LEGGINGS, ModItems.WROUGHT_NETHERITE_INGOT);
        bootsItem(consumer, "wrought_netherite_boots", ModItems.WROUGHT_NETHERITE_BOOTS, ModItems.WROUGHT_NETHERITE_INGOT);

        swordItem(consumer, "wrought_netherite_sword", ModItems.WROUGHT_NETHERITE_SWORD, ModItems.WROUGHT_NETHERITE_INGOT, Tags.Items.RODS_WOODEN);
        pickaxeItem(consumer, "wrought_netherite_pickaxe", ModItems.WROUGHT_NETHERITE_PICKAXE, ModItems.WROUGHT_NETHERITE_INGOT, Tags.Items.RODS_WOODEN);
        axeItem(consumer, "wrought_netherite_axe", ModItems.WROUGHT_NETHERITE_AXE, ModItems.WROUGHT_NETHERITE_INGOT, Tags.Items.RODS_WOODEN);
        shovelItem(consumer, "wrought_netherite_shovel", ModItems.WROUGHT_NETHERITE_SHOVEL, ModItems.WROUGHT_NETHERITE_INGOT, Tags.Items.RODS_WOODEN);
        hoeItem(consumer, "wrought_netherite_hoe", ModItems.WROUGHT_NETHERITE_HOE, ModItems.WROUGHT_NETHERITE_INGOT, Tags.Items.RODS_WOODEN);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WROUGHT_NETHERITE_INGOT.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.WROUGHT_NETHERITE_NUGGET.get())
                .unlockedBy("has_item", has(ModItems.WROUGHT_NETHERITE_NUGGET.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WROUGHT_NETHERITE_NUGGET.get())
                .requires(ModItems.WROUGHT_NETHERITE_INGOT.get())
                .unlockedBy("has_item", has(ModItems.WROUGHT_NETHERITE_INGOT.get()))
                .save(consumer);
    }
}
