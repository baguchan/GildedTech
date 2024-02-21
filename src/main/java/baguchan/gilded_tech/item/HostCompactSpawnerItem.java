package baguchan.gilded_tech.item;

import baguchan.gilded_tech.entity.AbstractWorkerAllay;
import baguchan.gilded_tech.entity.GatherAllay;
import baguchan.gilded_tech.entity.IHostMob;
import baguchan.gilded_tech.registry.ModEntities;
import baguchan.gilded_tech.registry.ModMemorys;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class HostCompactSpawnerItem extends Item {

    public static final int MAX_SIZE = 6;
    public HostCompactSpawnerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        ItemStack itemstack = player.getItemInHand(p_41434_);

        Vec3 vec3 = player.getEyePosition();
        Vec3 vec31 = player.getViewVector(1.0F);
        double d0 = player.getEntityReach();
        Vec3 vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        float f = 1.0F;
        AABB aabb = player.getBoundingBox().expandTowards(vec31.scale(d0)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(player, vec3, vec32, aabb, (p_234237_) -> {
            return !p_234237_.isSpectator() && p_234237_.getType() == EntityType.ALLAY;
        }, d0 * d0);

        AABB aabbForMob = player.getBoundingBox().inflate(16.0D, 16.0D, 16.0D);

        List<Entity> listWorkerMob = level.getEntities(player, aabbForMob, living -> {
            return living instanceof IHostMob host && host.isPlayerFriendly(player);
        });

        int size = getMobList(itemstack).size();

        if (entityhitresult != null && size < MAX_SIZE) {
            Entity entity = entityhitresult.getEntity();
            if (entity instanceof Allay allay) {
                if (!level.isClientSide) {
                    allay.getInventory().removeAllItems().forEach(player::spawnAtLocation);
                    ItemStack allayInventory = allay.getItemBySlot(EquipmentSlot.MAINHAND);
                    if (!allayInventory.isEmpty() && !EnchantmentHelper.hasVanishingCurse(allayInventory)) {
                        player.spawnAtLocation(allayInventory);
                        allay.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    }
                    allay.discard();

                    GatherAllay gatherMob = ModEntities.GATHER_ALLAY.get().create(level);

                    addMob(gatherMob, itemstack);

                    player.getCooldowns().addCooldown(this, 40);
                }
                player.playSound(SoundEvents.ITEM_PICKUP);

                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        } else {
            HitResult hitResult = player.pick(20.0D, 0.0F, false);

            Vec3 pos = hitResult.getLocation();
            if (hitResult.getType() != HitResult.Type.MISS && !player.isShiftKeyDown()) {
                if (!getMobList(itemstack).isEmpty()) {

                    hitResult = BlockHitResult.miss(pos, Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(pos));


                    if (hitResult instanceof BlockHitResult blockHitResult) {
                        BlockPos blockpos = blockHitResult.getBlockPos();
                        if (removeMobWithSpawn(itemstack, level, player, blockpos)) {
                            player.playSound(SoundEvents.ALLAY_ITEM_GIVEN);
                            player.awardStat(Stats.ITEM_USED.get(this));
                            player.getCooldowns().addCooldown(this, 40);
                        }

                        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                    }
                }
            } else if (!listWorkerMob.isEmpty() && player.isShiftKeyDown()) {
                for (Entity gatherMob : listWorkerMob) {
                    if(gatherMob instanceof AbstractWorkerAllay allay) {
                        allay.giveResource();
                    }
                    if(gatherMob instanceof LivingEntity living) {
                        addMob(living, itemstack);
                    }
                    gatherMob.discard();
                    size++;
                    if(size >= MAX_SIZE){
                        break;
                    }
                }
                player.getCooldowns().addCooldown(this, 100);
                player.playSound(SoundEvents.ALLAY_ITEM_TAKEN);

                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        }

        return super.use(level, player, p_41434_);
    }

    public void appendHoverText(ItemStack p_40880_, @Nullable Level p_40881_, List<Component> p_40882_, TooltipFlag p_40883_) {
        p_40882_.add(Component.literal(getMobList(p_40880_).size() + "").withStyle(ChatFormatting.DARK_AQUA).append(" ").append(Component.translatable("item.gilded_tech.host_compact_spawner.tooltip.mobs")));
        p_40882_.add(Component.translatable("item.gilded_tech.host_compact_spawner.tooltip"));
        p_40882_.add(Component.translatable("item.gilded_tech.host_compact_spawner.tooltip2"));

        super.appendHoverText(p_40880_, p_40881_, p_40882_, p_40883_);
    }


    public static void addMob(LivingEntity allay, ItemStack p_40885_) {
        CompoundTag compoundtag = p_40885_.getOrCreateTag();
        CompoundTag allayHolderTag = compoundtag.contains("MobHolder") ? compoundtag.getCompound("MobHolder") : new CompoundTag();
        ListTag listTag = allayHolderTag.contains("Mobs") ? allayHolderTag.getList("Mobs", 10) : new ListTag();
        CompoundTag allayTag = new CompoundTag();
        allay.addAdditionalSaveData(allayTag);
        if(allay.getEncodeId() != null) {
            allayTag.putString("id", allay.getEncodeId());
        }
        listTag.add(allayTag);
        allayHolderTag.put("Mobs", listTag);
        compoundtag.put("MobHolder", allayHolderTag);
    }

    public static ListTag getMobList(ItemStack stack) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        CompoundTag allayHolderTag = compoundtag.contains("MobHolder") ? compoundtag.getCompound("MobHolder") : new CompoundTag();
        ListTag listTag = allayHolderTag.contains("Mobs") ? allayHolderTag.getList("Mobs", 10) : new ListTag();
        return listTag;
    }

    public static boolean removeMobWithSpawn(ItemStack stack, Level level, Player player, BlockPos blockpos) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        CompoundTag allayHolderTag = compoundtag.contains("MobHolder") ? compoundtag.getCompound("MobHolder") : new CompoundTag();
        ListTag listTag = allayHolderTag.contains("Mobs") ? allayHolderTag.getList("Mobs", 10) : new ListTag();

        if (!listTag.isEmpty() && listTag.size() >= getSelectedMob(stack)) {

            Optional<Entity> entity = EntityType.create((CompoundTag) listTag.get(getSelectedMob(stack)), level);
            if (entity.isPresent() && entity.get() instanceof AbstractWorkerAllay allay) {
                if(!level.isClientSide()) {
                    allay.getBrain().setMemory(MemoryModuleType.LIKED_PLAYER, player.getUUID());
                    allay.setPos(player.position());
                    allay.getBrain().setMemory(ModMemorys.WORK_POS.get(), GlobalPos.of(level.dimension(), blockpos));
                    level.addFreshEntity(allay);
                }
                listTag.remove(getSelectedMob(stack));
                allayHolderTag.put("Mobs", listTag);
                compoundtag.put("MobHolder", allayHolderTag);
                return true;
            }
        }
        return false;
    }

    public static List<Entity> getMobEntitys(ItemStack stack, Level level) {
        CompoundTag compoundtag = stack.getOrCreateTag();
        CompoundTag allayHolderTag = compoundtag.contains("MobHolder") ? compoundtag.getCompound("MobHolder") : new CompoundTag();
        ListTag listTag = allayHolderTag.contains("Mobs") ? allayHolderTag.getList("Mobs", 10) : new ListTag();

        List<Entity> entities = Lists.newArrayList();
        for(int i = 0; i < listTag.size(); i++){
            Optional<Entity> entity = EntityType.create((CompoundTag) listTag.get(i), level);
            if (entity.isPresent()) {
                entities.add(entity.get());
            }
        }
        return entities;
    }

    public static int getSelectedMob(ItemStack p_40885_) {
        CompoundTag compoundtag = p_40885_.getOrCreateTag();
        if (compoundtag.contains("MobSelect")) {
            return compoundtag.getInt("MobSelect");
        }

        return 0;
    }

    public static void setSelectedMob(int select, ItemStack p_40885_) {
        CompoundTag compoundtag = p_40885_.getOrCreateTag();
        compoundtag.putInt("MobSelect", select);
    }


    public static boolean cycle(ItemStack stack) {
        return cycle(1, stack);
    }

    public static boolean cycle(boolean clockWise, ItemStack stack) {
        return cycle(clockWise ? 1 : -1, stack);
    }

    public static boolean cycle(int slotsMoved, ItemStack stack) {
        int originalSlot = getSelectedMob(stack);
        var content = getMobList(stack);
        CompoundTag selected;
        if (slotsMoved == 0) {
            if (content.isEmpty()) return false;
        }
        int maxSlots = content.size();
        if(maxSlots <= 0){
            return false;
        }
        slotsMoved = slotsMoved % maxSlots;
        setSelectedMob((maxSlots + (getSelectedMob(stack) + slotsMoved)) % maxSlots, stack);
        for (int i = 0; i < maxSlots; i++) {
            setSelectedMob((maxSlots + (getSelectedMob(stack) + (slotsMoved >= 0 ? 1 : -1))) % maxSlots, stack);
        }
        return originalSlot != getSelectedMob(stack);
    }

}
