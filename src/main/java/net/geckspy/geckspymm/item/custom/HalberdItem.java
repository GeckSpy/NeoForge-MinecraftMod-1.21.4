package net.geckspy.geckspymm.item.custom;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;


public class HalberdItem extends SwordItem {
    public static final double ENTITY_INTERACTION_RANGE_BONUS = 1.75f;
    public static final float ATTACK_SPEED = -3.5f;
    public static final int ATTACK_DAMAGE = 7;

    public HalberdItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Properties properties){
        super(material.applySwordProperties(properties, attackDamage, attackSpeed));

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

}
