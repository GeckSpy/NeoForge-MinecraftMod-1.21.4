package net.geckspy.geckspymm.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class ModFoodProperties {
    public static final FoodProperties FRIED_EGG = new FoodProperties.Builder().nutrition(4).saturationModifier(0.35f).build();

    public static final FoodProperties PEPPER = new FoodProperties.Builder().nutrition(2).saturationModifier(0.25f).build();

    public static final FoodProperties RED_PEPPER = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).alwaysEdible().build();
    public static final Consumable RED_PEPPER_EFFECT = Consumables.defaultFood().onConsume(
            new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200) )).build();
}
