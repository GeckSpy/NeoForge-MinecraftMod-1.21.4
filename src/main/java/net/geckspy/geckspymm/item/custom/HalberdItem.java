package net.geckspy.geckspymm.item.custom;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.List;


public class HalberdItem extends SwordItem {
    public static final double ENTITY_INTERACTION_RANGE_BONUS = 1.8f;
    public static final float ATTACK_SPEED = -3.5f;
    public static final float ATTACK_DAMAGE = 7;
    public static final float SWEEP_DAMAGE = 0.5f;
    public static final float SWEEP_DISTANCE = 3;
    public static ToolMaterial toolMaterial;

    public HalberdItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Properties properties){
        super(material.applySwordProperties(properties, attackDamage, attackSpeed));
        this.toolMaterial = material;
    }

    public static void onGatherModifier(ItemAttributeModifierEvent event){
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof HalberdItem) {
            event.addModifier(Attributes.ENTITY_INTERACTION_RANGE,
                    new AttributeModifier(
                            ModAttributes.ENTITY_INTERACTION_RANGE.getId(),
                            ENTITY_INTERACTION_RANGE_BONUS,
                            AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND );

        }
    }

    public float getSweepDamage(){
        return SWEEP_DAMAGE + this.toolMaterial.attackDamageBonus();
    }



    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker instanceof Player player &&  player.getAttackStrengthScale(0) <1.0){
                return super.hurtEnemy(stack, target, attacker);
        }

        List<LivingEntity> nearby = attacker.level().getEntitiesOfClass(LivingEntity.class,
                target.getBoundingBox().inflate(SWEEP_DISTANCE),
                e -> e!=attacker && e!=target);

        for (LivingEntity entity : nearby) {
            entity.hurt(attacker.damageSources().mobAttack(attacker), getSweepDamage());
        }

        return super.hurtEnemy(stack, target, attacker);
    }

}
