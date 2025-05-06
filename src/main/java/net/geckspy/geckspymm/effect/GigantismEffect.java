package net.geckspy.geckspymm.effect;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;

public class GigantismEffect extends MobEffect {
    public GigantismEffect(MobEffectCategory category, int color){
        super(category, color);
    }

    public static double coefficient(int amplifier){
        if(amplifier==0){return 0.25;}
        else if(amplifier==1){return 0.5;}
        else{
            return Math.pow(2, amplifier-2);
        }
    };

    public static final List< Holder<Attribute>> ATTRIBUTES = List.of(
            Attributes.SCALE,
            Attributes.ENTITY_INTERACTION_RANGE,
            Attributes.BLOCK_INTERACTION_RANGE,
            Attributes.STEP_HEIGHT,

            Attributes.MOVEMENT_SPEED,
            Attributes.ATTACK_DAMAGE,
            Attributes.KNOCKBACK_RESISTANCE,
            Attributes.GRAVITY,
            Attributes.BLOCK_BREAK_SPEED
    );

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        for(var attribute: ATTRIBUTES){
            var entityAttribute = entity.getAttribute(attribute).getModifier(ModAttributes.GIGANTISM_EFFECT.getId());
            if(entityAttribute==null || coefficient(amplifier) > entityAttribute.amount()){
                entity.getAttribute(attribute).removeModifier(ModAttributes.GIGANTISM_EFFECT.getId());
                entity.getAttribute(attribute).addTransientModifier(new AttributeModifier(
                        ModAttributes.GIGANTISM_EFFECT.getId(),
                        coefficient(amplifier),
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                ));
            }
        }
    }


    public static void onEffectEnded(LivingEntity entity){
        for (var attribute : ATTRIBUTES) {
            entity.getAttribute(attribute).removeModifier(ModAttributes.GIGANTISM_EFFECT.getId());
        }
    }

}
