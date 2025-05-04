package net.geckspy.geckspymm.item;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.neoforged.neoforge.common.Tags;

import java.util.EnumMap;

public class ModArmorMaterials {
    public static final ArmorMaterial COPPER = new ArmorMaterial(
            // The durability multiplier of the armor material. ArmorType have different unit durabilities that the multiplier is applied to:
            // HELMET:11, CHESTPLATE:16, LEGGINGS: 15, BOOTS: 13, BODY: 16
            15,
            // Determines the defense value (or the number of half-armors on the bar).
            Util.make(new EnumMap<>(ArmorType.class),map -> {
                    map.put(ArmorType.BOOTS, 2);
                    map.put(ArmorType.LEGGINGS, 4);
                    map.put(ArmorType.CHESTPLATE, 5);
                    map.put(ArmorType.HELMET, 2);
                    map.put(ArmorType.BODY, 4);
            }),
            // Determines the enchantability of the armor; Gold uses 25.
            10,
            // Determines the sound played when equipping this armor.
            SoundEvents.ARMOR_EQUIP_GENERIC,
            // Returns the toughness value of the armor.
            0,
            // Returns the knockback resistance value of the armor.
            0,
            // The tag that determines what items can repair this armor.
            Tags.Items.INGOTS_COPPER,
            // The resource key of the EquipmentClientInfo JSON discussed below
            // Points to assets/examplemod/equipment/copper.json
            ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "copper"))
    );
}
