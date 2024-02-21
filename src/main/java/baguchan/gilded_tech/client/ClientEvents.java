package baguchan.gilded_tech.client;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.entity.ai.GatherBlocks;
import baguchan.gilded_tech.item.HostCompactSpawnerItem;
import baguchan.gilded_tech.registry.ModEntities;
import baguchan.gilded_tech.registry.ModItems;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = GildedTech.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void registerEntityRenders(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            LocalPlayer player = Minecraft.getInstance().player;
            Camera camera = Minecraft.getInstance().getEntityRenderDispatcher().camera;
            if (player != null && camera != null && player.isHolding(ModItems.HOST_COMPACT_SPAWNER.get())) {
                ItemStack hand = !player.getMainHandItem().isEmpty() ? player.getMainHandItem() : player.getOffhandItem();

                if(HostCompactSpawnerItem.getMobList(hand).size() > HostCompactSpawnerItem.getSelectedMob(hand)) {
                    if(HostCompactSpawnerItem.getMobEntitys(hand, player.clientLevel).get(HostCompactSpawnerItem.getSelectedMob(hand)).getType() == ModEntities.GATHER_ALLAY.get()) {
                        HitResult hitResult = player.pick(20.0D, 0.0F, false);


                        Vec3 pos = hitResult.getLocation();
                        if (hitResult.getType() != HitResult.Type.MISS) {


                            VertexConsumer vertexconsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());
                            LevelRenderer.renderLineBox(event.getPoseStack(), vertexconsumer, (int) pos.x - GatherBlocks.RANGE - (int) camera.getPosition().x, (int) pos.y - GatherBlocks.RANGE - (int) camera.getPosition().y, (int) pos.z - GatherBlocks.RANGE - (int) camera.getPosition().z, (int) (pos.x + GatherBlocks.RANGE) - (int) camera.getPosition().x, (int) (pos.y + GatherBlocks.RANGE) - (int) camera.getPosition().y, (int) (pos.z + GatherBlocks.RANGE) - (int) camera.getPosition().z, 0.3F, 0.3F, 1.0F, 1.0F);


                        }
                    }
                }
            }
        }
    }

}
