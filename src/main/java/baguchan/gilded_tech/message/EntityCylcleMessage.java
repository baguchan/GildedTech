package baguchan.gilded_tech.message;

import baguchan.gilded_tech.GildedTech;
import baguchan.gilded_tech.item.HostCompactSpawnerItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class EntityCylcleMessage implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(GildedTech.MODID, "entity_cylcle");


    public int index;

    public EntityCylcleMessage(int index) {
        this.index = index;
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public EntityCylcleMessage(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
    }


    public static void handle(EntityCylcleMessage message, PlayPayloadContext context) {
        Player player = context.player().get();
        ItemStack stack  = !player.getMainHandItem().isEmpty() ? player.getMainHandItem() : player.getOffhandItem();
        HostCompactSpawnerItem.cycle(message.index, stack);
    }
}