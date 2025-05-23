package net.geckspy.geckspymm.effect;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import it.unimi.dsi.fastutil.Pair;
import java.util.List;
import java.util.Map;

public class GigantismEffect extends MobEffect {
    public GigantismEffect(MobEffectCategory category, int color){
        super(category, color);
    }

    public static Map<Integer, Double> BASIC_MAP = Map.of(
            0, 0.4,
            1, 1.0,
            2,2.0,
            3,4.0,
            4,8.0,
            5,16.0
    );


    public static final List< Pair<Holder<Attribute>, Map<Integer, Double>>> ATTRIBUTES = List.of(
            Pair.of(Attributes.SCALE, BASIC_MAP),
            Pair.of(Attributes.ENTITY_INTERACTION_RANGE, BASIC_MAP),
            Pair.of(Attributes.BLOCK_INTERACTION_RANGE, BASIC_MAP),
            Pair.of(Attributes.STEP_HEIGHT, BASIC_MAP),

            Pair.of(Attributes.MOVEMENT_SPEED, Map.of(0,0.0,1,0.1,2,0.4,3,0.6,4,0.8,5,1.0)),
            Pair.of(Attributes.JUMP_STRENGTH, Map.of(0,0.0,1,0.1,2,0.4,3,0.6,4,0.8,5,1.0)),
            Pair.of(Attributes.ATTACK_DAMAGE, BASIC_MAP),
            Pair.of(Attributes.KNOCKBACK_RESISTANCE, BASIC_MAP),
            Pair.of(Attributes.BLOCK_BREAK_SPEED, BASIC_MAP),
            Pair.of(Attributes.FLYING_SPEED, Map.of(0,-0.1,1,-0.2,2,-0.4,3,-0.5,4,-0.6,5,-0.8)),
            Pair.of(Attributes.GRAVITY, Map.of(0,0.0,1,0.1,2,0.4,3,0.6,4,0.8,5,1.0))
    );

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        for(var pair: ATTRIBUTES){
            AttributeInstance attribute = entity.getAttribute(pair.first());
            if(attribute!=null) {
                double coeff = pair.second().get(amplifier);
                var entityAttribute = attribute.getModifier(ModAttributes.GIGANTISM_EFFECT.getId());
                if (entityAttribute == null || Math.abs(coeff) > Math.abs(entityAttribute.amount())) {
                    attribute.removeModifier(ModAttributes.GIGANTISM_EFFECT.getId());
                    attribute.addTransientModifier(new AttributeModifier(
                            ModAttributes.GIGANTISM_EFFECT.getId(),
                            coeff,
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ));
                }
            }
        }
    }


    public static void onEffectEnded(LivingEntity entity){
        for (var pair : ATTRIBUTES) {
            AttributeInstance attrib = entity.getAttribute(pair.first());
            if(attrib!=null){
               attrib.removeModifier(ModAttributes.GIGANTISM_EFFECT.getId());
            }
        }
    }

}
