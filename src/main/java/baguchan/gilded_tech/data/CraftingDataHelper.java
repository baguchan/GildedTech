package baguchan.gilded_tech.data;

import baguchan.gilded_tech.GildedTech;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public abstract class CraftingDataHelper extends RecipeProvider {
    public CraftingDataHelper(PackOutput generator) {
        super(generator);
    }

    protected final void miscCooking(Supplier<? extends ItemLike> material, Supplier<? extends ItemLike> result, float xp, RecipeOutput consumer, String recipeName) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(material.get()), RecipeCategory.MISC, result.get(), xp, 200).unlockedBy("has_item", has(material.get())).save(consumer, GildedTech.prefix("smelting_" + recipeName));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(material.get()), RecipeCategory.MISC, result.get(), xp, 100).unlockedBy("has_item", has(material.get())).save(consumer, GildedTech.prefix("smoking_" + recipeName));
    }

    protected final void helmetItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("###")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void chestplateItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void leggingsItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void bootsItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("# #")
                .pattern("# #")
                .define('#', material.get())
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void pickaxeItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern("###")
                .pattern(" X ")
                .pattern(" X ")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void swordItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .pattern("#")
                .pattern("#")
                .pattern("X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void axeItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern("##")
                .pattern("#X")
                .pattern(" X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void shovelItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern("#")
                .pattern("X")
                .pattern("X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final void hoeItem(RecipeOutput consumer, String name, Supplier<? extends ItemLike> result, Supplier<? extends ItemLike> material, TagKey<Item> handle) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, result.get())
                .pattern("##")
                .pattern(" X")
                .pattern(" X")
                .define('#', material.get())
                .define('X', handle)
                .unlockedBy("has_item", has(material.get()))
                .save(consumer, locEquip(name));
    }

    protected final ResourceLocation locEquip(String name) {
        return GildedTech.prefix("equipment/" + name);
    }
}