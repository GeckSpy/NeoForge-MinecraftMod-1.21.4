package net.geckspy.geckspymm.item.custom;

import net.geckspy.geckspymm.item.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;

import java.util.List;

public class ModArmorItem_old extends ArmorItem {
    public ModArmorItem_old(ArmorMaterial material, ArmorType armorType, Properties properties){
        super(material, armorType, properties);
    }

    public static List<MobEffectInstance> PURE_QUARTZ_EFFECTS = List.of(
            new MobEffectInstance(MobEffects.NIGHT_VISION,300,0,false, false,true),
            new MobEffectInstance(MobEffects.DIG_SPEED,35,0,false, false,true)
    );


    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(entity instanceof Player player && !level.isClientSide()){

            if(isWearingFullPureQuartzArmor(player)){
                for(MobEffectInstance effect: PURE_QUARTZ_EFFECTS){
                    player.addEffect(effect);
                }
            }
        }
    }

    private static boolean isWearingFullPureQuartzArmor(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PURE_QUARTZ_HELMET) &&
                player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PURE_QUARTZ_CHESTPLATE) &&
                player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PURE_QUARTZ_LEGGINGS) &&
                player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PURE_QUARTZ_BOOTS);
    }

}
