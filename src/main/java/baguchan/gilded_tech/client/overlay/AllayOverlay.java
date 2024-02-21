package baguchan.gilded_tech.client.overlay;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.item.HostCompactSpawnerItem;
import baguchan.gilded_tech.message.EntityCylcleMessage;
import baguchan.gilded_tech.registry.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.List;

public class AllayOverlay implements IGuiOverlay {
    private static final ResourceLocation TEXTURE = GildedTech.prefix("hud/allay_select");
    private static final ResourceLocation TEXTURE_SELECT = GildedTech.prefix("hud/allay_selected");


    public static boolean onMouseScrolled(double scrollDelta) {

        Player player = Minecraft.getInstance().player;
        ItemStack spawner = !player.getMainHandItem().isEmpty() ? player.getMainHandItem() : player.getOffhandItem();
        if (spawner.getItem() == ModItems.HOST_COMPACT_SPAWNER.get()) {

            PacketDistributor.SERVER.noArg().send(new EntityCylcleMessage(
                    scrollDelta > 0 ? -1 : 1));
            return true;
        }
        return false;
    }

    private void renderSlot(GuiGraphics graphics, int pX, int pY, Entity entity) {
        if(entity instanceof LivingEntity livingEntity) {
            InventoryScreen.renderEntityInInventory(graphics, pX, pY, 30, new Vector3f(), new Quaternionf().rotationXYZ(0F, (float) Math.PI, (float) Math.PI), null, livingEntity);
        }
    }

    public void renderEntityContent(GuiGraphics graphics, float partialTicks, int screenWidth, int screenHeight) {

        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            ItemStack spawner = !player.getMainHandItem().isEmpty() ? player.getMainHandItem() : player.getOffhandItem();
            if (spawner.getItem() == ModItems.HOST_COMPACT_SPAWNER.get()) {
                ///gui.setupOverlayRenderState(true, false);
                PoseStack poseStack = graphics.pose();
                poseStack.pushPose();

                int selected = HostCompactSpawnerItem.getSelectedMob(spawner);
                List<Entity> list = HostCompactSpawnerItem.getMobEntitys(spawner, Minecraft.getInstance().level);
                int slots = list.size();
                int centerX = screenWidth / 2;

                poseStack.pushPose();
                poseStack.translate(0, 0, -90);

                int uWidth = slots * 20 + 2;
                int px = uWidth / 2;
                int py = screenHeight / 2 - 40;


                //TODO Config
                px += 0;
                py += 0;
                poseStack.popPose();

                int i1 = 1;
                for (int i = 0; i < slots; ++i) {
                    int kx = centerX - px + 3 + i * 20;
                    if (!list.isEmpty() && list.size() > i) {
                        this.renderSlot(graphics, kx + 12, py + 20, list.get(i));
                        if(selected == i){
                            graphics.blitSprite(TEXTURE_SELECT, kx, py, 22, 22);
                        }else {
                            graphics.blitSprite(TEXTURE, kx, py, 22, 22);
                        }
                    }else {
                        this.renderSlot(graphics, kx + 12, py  + 20, null);
                        if(selected == i){
                            graphics.blitSprite(TEXTURE_SELECT, kx, py, 22, 22);
                        }else {
                            graphics.blitSprite(TEXTURE, kx, py, 22, 22);
                        }
                    }
                }


                if (!list.isEmpty() && list.size() > selected) {
                    Entity selectedArrow = list.get(selected);
                    drawHighlight(graphics, screenWidth, py, selectedArrow);
                }


                poseStack.popPose();
            }
        }
    }


    protected void drawHighlight(GuiGraphics graphics, int screenWidth, int py, Entity selectedEntity) {
        int l;

        MutableComponent mutablecomponent = Component.empty().append(selectedEntity.getName());
        Component highlightTip = mutablecomponent;
        int fontWidth = Minecraft.getInstance().font.width(highlightTip);
        int nx = (screenWidth - fontWidth) / 2;
        int ny = py - 19;

        l = 255;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        graphics.fill(nx - 2, ny - 2, nx + fontWidth + 2, ny + 9 + 2, Minecraft.getInstance().options.getBackgroundColor(0));
        graphics.drawString(Minecraft.getInstance().font, highlightTip, nx, ny, 0xFFFFFF + (l << 24));
        RenderSystem.disableBlend();
    }

    @Override
    public void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        if(Minecraft.getInstance().player != null && Minecraft.getInstance().player.isShiftKeyDown()) {
            renderEntityContent(guiGraphics, partialTick,
                    Minecraft.getInstance().getWindow().getGuiScaledWidth(),
                    Minecraft.getInstance().getWindow().getGuiScaledHeight());
        }
    }
}