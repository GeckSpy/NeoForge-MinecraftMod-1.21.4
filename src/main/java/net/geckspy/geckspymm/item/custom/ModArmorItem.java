package net.geckspy.geckspymm.item.custom;

import it.unimi.dsi.fastutil.Pair;
import net.geckspy.geckspymm.attribute.ModAttributes;
import net.geckspy.geckspymm.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

public class ModArmorItem {

    public static List<Pair<Holder<Attribute>, Double>> PURE_QUARTZ_ATTRIBUTES = List.of(
            Pair.of(Attributes.BLOCK_BREAK_SPEED, 1.7),
            Pair.of(Attributes.SUBMERGED_MINING_SPEED, 0.4)
    );
    public static List<Pair<Holder<Attribute>, Double>> NETHERITE_ATTRIBUTES = List.of(
            Pair.of(Attributes.MAX_HEALTH, 4.0)
    );


    public static void onEquipmentChange(LivingEquipmentChangeEvent event){
        if(event.getEntity() instanceof Player player){

            for(Pair<Holder<Attribute>, Double> pair: PURE_QUARTZ_ATTRIBUTES){
                player.getAttribute(pair.first()).removeModifier(ModAttributes.ARMOR_EFFECT.getId());
            }
            for(Pair<Holder<Attribute>, Double> pair: NETHERITE_ATTRIBUTES){
                player.getAttribute(pair.first()).removeModifier(ModAttributes.ARMOR_EFFECT.getId());
            }

            if(isWearingFullPureQuartzArmor(player)){
                applyPureQuartzAttributes(player);

            }else if(isWearingFullNetheriteArmor(player)){
                applyPureQuartzAttributes(player);
                applyNetheriteAttributes(player);
            }
        }
    }

    private static void applyPureQuartzAttributes(Player player){
        for(Pair<Holder<Attribute>, Double> pair: PURE_QUARTZ_ATTRIBUTES){
            player.getAttribute(pair.first()).addTransientModifier(new AttributeModifier(
                    ModAttributes.ARMOR_EFFECT.getId(), pair.second(), AttributeModifier.Operation.ADD_VALUE
            ));
        }
    }

    private static void applyNetheriteAttributes(Player player){
        for(Pair<Holder<Attribute>, Double> pair: NETHERITE_ATTRIBUTES){
            player.getAttribute(pair.first()).addTransientModifier(new AttributeModifier(
                    ModAttributes.ARMOR_EFFECT.getId(), pair.second(), AttributeModifier.Operation.ADD_VALUE
            ));
        }
    }




    private static boolean isWearingFullPureQuartzArmor(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PURE_QUARTZ_HELMET) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PURE_QUARTZ_CHESTPLATE) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PURE_QUARTZ_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PURE_QUARTZ_BOOTS);
    }

    private static boolean isWearingFullNetheriteArmor(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(Items.NETHERITE_HELMET) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(Items.NETHERITE_CHESTPLATE) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(Items.NETHERITE_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(Items.NETHERITE_BOOTS);
    }
}
