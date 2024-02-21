package baguchan.gilded_tech.data;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.registry.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static baguchan.gilded_tech.GildedTech.prefix;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, GildedTech.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTex(ModItems.WROUGHT_NETHERITE_INGOT);
        singleTex(ModItems.WROUGHT_NETHERITE_NUGGET);
        singleTex(ModItems.NETHERITE_POWDER);


        singleTexTool(ModItems.WROUGHT_NETHERITE_SWORD);
        singleTexTool(ModItems.WROUGHT_NETHERITE_PICKAXE);
        singleTexTool(ModItems.WROUGHT_NETHERITE_AXE);
        singleTexTool(ModItems.WROUGHT_NETHERITE_SHOVEL);
        singleTexTool(ModItems.WROUGHT_NETHERITE_HOE);
        trimmedArmor(ModItems.WROUGHT_NETHERITE_HELMET);
        trimmedArmor(ModItems.WROUGHT_NETHERITE_CHESTPLATE);
        trimmedArmor(ModItems.WROUGHT_NETHERITE_LEGGINGS);
        trimmedArmor(ModItems.WROUGHT_NETHERITE_BOOTS);

    }

    public void sign(Supplier<? extends SignBlock> sign) {
        withExistingParent(blockName(sign), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + blockName(sign)));
    }

    private void woodenFence(Supplier<? extends Block> fence, Supplier<? extends Block> block) {
        getBuilder(BuiltInRegistries.BLOCK.getKey(fence.get()).getPath())
                .parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", "block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath());
    }

    public ItemModelBuilder torchItem(Supplier<Block> item) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(item.get()).getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + BuiltInRegistries.BLOCK.getKey(item.get()).getPath()));
    }


    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        return buildItem(name, "item/generated", 0, layers);
    }


    private ItemModelBuilder buildItem(String name, String parent, int emissivity, ResourceLocation... layers) {
        ItemModelBuilder builder = withExistingParent(name, parent);
        for (int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        if (emissivity > 0)
            builder = builder.customLoader(ItemLayerModelBuilder::begin).emissive(emissivity, emissivity, 0).renderType("minecraft:translucent", 0).end();
        return builder;
    }

    private ItemModelBuilder tool(String name, ResourceLocation... layers) {
        return buildItem(name, "item/handheld", 0, layers);
    }

    private ItemModelBuilder singleTexTool(Supplier<? extends Item> item) {
        return tool(itemPath(item).getPath(), prefix("item/" + itemPath(item).getPath()));
    }

    private ItemModelBuilder singleTexRodTool(Supplier<? extends Item> item) {
        return toolRod(itemPath(item).getPath(), prefix("item/" + itemPath(item).getPath()));
    }

    private ItemModelBuilder toolRod(String name, ResourceLocation... layers) {
        return buildItem(name, "item/handheld_rod", 0, layers);
    }

    private ItemModelBuilder singleTex(Supplier<? extends ItemLike> item) {
        return generated(itemPath(item).getPath(), prefix("item/" + itemPath(item).getPath()));
    }

    public ItemModelBuilder bowItem(Supplier<? extends Item> item) {
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item.get());
        withExistingParent(id.getPath() + "_pulling_0", mcLoc("item/bow")).texture("layer0", modLoc("item/" + id.getPath() + "_pulling_0"));
        withExistingParent(id.getPath() + "_pulling_1", mcLoc("item/bow")).texture("layer0", modLoc("item/" + id.getPath() + "_pulling_1"));
        withExistingParent(id.getPath() + "_pulling_2", mcLoc("item/bow")).texture("layer0", modLoc("item/" + id.getPath() + "_pulling_2"));
        return withExistingParent(id.getPath(), mcLoc("item/bow"))
                .texture("layer0", modLoc("item/" + id.getPath()))
                .override().predicate(new ResourceLocation("pulling"), 1).model(getExistingFile(modLoc("item/" + id.getPath() + "_pulling_0"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.65F).model(getExistingFile(modLoc("item/" + id.getPath() + "_pulling_1"))).end()
                .override().predicate(new ResourceLocation("pulling"), 1).predicate(new ResourceLocation("pull"), 0.9F).model(getExistingFile(modLoc("item/" + id.getPath() + "_pulling_2"))).end();
    }

    private void woodenButton(Supplier<? extends Block> button, String variant) {
        getBuilder(BuiltInRegistries.BLOCK.getKey(button.get()).getPath())
                .parent(getExistingFile(mcLoc("block/button_inventory")))
                .texture("texture", "block/wood/planks_" + variant + "_0");
    }

    private void woodenFence(Block fence, String variant) {
        getBuilder(BuiltInRegistries.BLOCK.getKey(fence).getPath())
                .parent(getExistingFile(mcLoc("block/fence_inventory")))
                .texture("texture", "block/wood/planks_" + variant + "_0");
    }

    public ItemModelBuilder wall(Supplier<? extends WallBlock> wall, Supplier<? extends Block> fullBlock) {
        return wallInventory(BuiltInRegistries.BLOCK.getKey(wall.get()).getPath(), texture(blockName(fullBlock)));
    }

    private ItemModelBuilder toBlock(Supplier<? extends Block> b) {
        return toBlockModel(b, BuiltInRegistries.BLOCK.getKey(b.get()).getPath());
    }

    private ItemModelBuilder toBlockModel(Supplier<? extends Block> b, String model) {
        return toBlockModel(b, ("block/" + model));
    }

    private ItemModelBuilder toBlockModel(Supplier<? extends Block> b, ResourceLocation model) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(b.get()).getPath(), model);
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block) {
        return itemBlockFlat(block, blockName(block));
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String name) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + name));
    }

    public ItemModelBuilder egg(Supplier<Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(), mcLoc("item/template_spawn_egg"));
    }

    public String blockName(Supplier<? extends Block> block) {
        return BuiltInRegistries.BLOCK.getKey(block.get()).getPath();
    }

    @Override
    public String getName() {
        return "TofuCraftReload item and itemblock models";
    }

    private ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    public ResourceLocation itemPath(Supplier<? extends ItemLike> item) {
        return BuiltInRegistries.ITEM.getKey(item.get().asItem());
    }

    //Thanks Twilight Forest Team! https://github.com/TeamTwilight/twilightforest/blob/1.20.x/src/main/java/twilightforest/data/ItemModelGenerator.java#L827C23-L837
    private void trimmedArmor(Supplier<ArmorItem> armor) {
        ItemModelBuilder base = this.singleTex(armor);
        for (ItemModelGenerators.TrimModelData trim : ItemModelGenerators.GENERATED_TRIM_MODELS) {
            String material = trim.name();
            String name = itemPath(armor).getPath() + "_" + material + "_trim";
            ModelFile trimModel = this.withExistingParent(name, this.mcLoc("item/generated"))
                    .texture("layer0", prefix("item/" + itemPath(armor).getPath()))
                    .texture("layer1", this.mcLoc("trims/items/" + armor.get().getType().getName() + "_trim_" + material));
            base.override().predicate(new ResourceLocation("trim_type"), trim.itemModelIndex()).model(trimModel).end();
        }
    }
}