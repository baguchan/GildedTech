package baguchan.gilded_tech.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements Tier {
    WROUGHT_NETHERITE(2, 300, 6.0F, 2.0F, 16, () -> Ingredient.of(ModItems.WROUGHT_NETHERITE_INGOT.get()));

    private final int level;

    private final int uses;

    private final float speed;

    private final float damage;

    private final int enchantmentValue;

    private final LazyLoadedValue<Ingredient> repairIngredient;

    ModItemTier(int p_i48458_3_, int p_i48458_4_, float p_i48458_5_, float p_i48458_6_, int p_i48458_7_, Supplier<Ingredient> p_i48458_8_) {
        this.level = p_i48458_3_;
        this.uses = p_i48458_4_;
        this.speed = p_i48458_5_;
        this.damage = p_i48458_6_;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = new LazyLoadedValue(p_i48458_8_);
    }

    @Override
    public int getLevel() {
        return level;
    }

    public int getUses() {
        return this.uses;
    }

    public float getSpeed() {
        return this.speed;
    }

    public float getAttackDamageBonus() {
        return this.damage;
    }

    public int level() {
        return this.level;
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}