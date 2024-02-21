package baguchan.gilded_tech.registry;

import baguchan.gilded_tech.GildedTech;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public class ModMemorys {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_REGISTRY = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, GildedTech.MODID);

    public static final Supplier<MemoryModuleType<GlobalPos>> WORK_POS = MEMORY_REGISTRY.register("work_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
}