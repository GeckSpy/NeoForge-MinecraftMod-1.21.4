package net.geckspy.geckspymm.item;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.item.custom.HalberdItem;
import net.geckspy.geckspymm.item.custom.LightningStaff;
import net.geckspy.geckspymm.item.custom.SpearItem;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MyMod.MOD_ID);

// Better Minecraft
    // Food
    public static final DeferredItem<Item> FRIED_EGG = ITEMS.registerItem("fried_egg",
        properties -> new Item(properties.food(ModFoodProperties.FRIED_EGG)));

    public static final DeferredItem<Item> PEPPER_SEEDS = ITEMS.registerItem("pepper_seeds",
            properties->new BlockItem(ModBlocks.PEPPER_CROP.get(), properties));
    public static final DeferredItem<Item> PEPPER = ITEMS.registerItem("pepper",
            properties -> new Item(properties.food(ModFoodProperties.PEPPER)));
    public static final DeferredItem<Item> RED_PEPPER = ITEMS.registerItem("red_pepper",
            properties -> new Item(properties.food(ModFoodProperties.RED_PEPPER, ModFoodProperties.RED_PEPPER_EFFECT)));

    public static final DeferredItem<Item> LEEK = ITEMS.registerItem("leek",
            properties -> new BlockItem(ModBlocks.LEEK_CROP.get(), properties.food(ModFoodProperties.LEEK)));


    // Copper material
    public static final DeferredItem<SwordItem> COPPER_SWORD = ITEMS.registerItem("copper_sword",
            properties -> new SwordItem(ModToolMaterials.COPPER, 3f, -2.4f, properties));
    public static final DeferredItem<AxeItem> COPPER_AXE = ITEMS.registerItem("copper_axe",
            properties -> new AxeItem(ModToolMaterials.COPPER, 6.5f, -3.15f, properties));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = ITEMS.registerItem("copper_pickaxe",
            properties -> new PickaxeItem(ModToolMaterials.COPPER, 1f, -2.8f, properties));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = ITEMS.registerItem("copper_shovel",
            properties -> new ShovelItem(ModToolMaterials.COPPER, 1.5f, -3f, properties));
    public static final DeferredItem<HoeItem> COPPER_HOE = ITEMS.registerItem("copper_hoe",
            properties -> new HoeItem(ModToolMaterials.COPPER, -1.5f, -1.5f, properties));

    public static final DeferredItem<ArmorItem> COPPER_HELMET = ITEMS.registerItem("copper_helmet",
            properties -> new ArmorItem(ModArmorMaterials.COPPER, ArmorType.HELMET, properties));
    public static final DeferredItem<ArmorItem> COPPER_CHESTPLATE = ITEMS.registerItem("copper_chestplate",
            properties -> new ArmorItem(ModArmorMaterials.COPPER, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<ArmorItem> COPPER_LEGGINGS = ITEMS.registerItem("copper_leggings",
            properties -> new ArmorItem(ModArmorMaterials.COPPER, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<ArmorItem> COPPER_BOOTS = ITEMS.registerItem("copper_boots",
            properties -> new ArmorItem(ModArmorMaterials.COPPER, ArmorType.BOOTS, properties));
    public static final DeferredItem<AnimalArmorItem> COPPER_HORSE_ARMOR = ITEMS.registerItem("copper_horse_armor",
            props -> new AnimalArmorItem(ModArmorMaterials.COPPER, AnimalArmorItem.BodyType.EQUESTRIAN, props));


    // Pure quartz material
    public static final DeferredItem<Item> PURE_QUARTZ = ITEMS.registerItem(
            "pure_quartz", Item::new, new Item.Properties());
    public static final DeferredItem<Item> PURE_QUARTZ_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem(
            "pure_quartz_upgrade_smithing_template", Item::new, new Item.Properties());
    public static final DeferredItem<ArmorItem> PURE_QUARTZ_HELMET = ITEMS.registerItem("pure_quartz_helmet",
            properties -> new ArmorItem(ModArmorMaterials.PURE_QUARTZ, ArmorType.HELMET, properties));
    public static final DeferredItem<ArmorItem> PURE_QUARTZ_CHESTPLATE = ITEMS.registerItem("pure_quartz_chestplate",
            properties -> new ArmorItem(ModArmorMaterials.PURE_QUARTZ, ArmorType.CHESTPLATE, properties));
    public static final DeferredItem<ArmorItem> PURE_QUARTZ_LEGGINGS = ITEMS.registerItem("pure_quartz_leggings",
            properties -> new ArmorItem(ModArmorMaterials.PURE_QUARTZ, ArmorType.LEGGINGS, properties));
    public static final DeferredItem<ArmorItem> PURE_QUARTZ_BOOTS = ITEMS.registerItem("pure_quartz_boots",
            properties -> new ArmorItem(ModArmorMaterials.PURE_QUARTZ, ArmorType.BOOTS, properties));
    public static final DeferredItem<AnimalArmorItem> PURE_QUARTZ_HORSE_ARMOR = ITEMS.registerItem("pure_quartz_horse_armor",
            props -> new AnimalArmorItem(ModArmorMaterials.PURE_QUARTZ, AnimalArmorItem.BodyType.EQUESTRIAN, props));

    public static final DeferredItem<SwordItem> PURE_QUARTZ_SWORD = ITEMS.registerItem("pure_quartz_sword",
            properties -> new SwordItem(ModToolMaterials.PURE_QUARTZ, 5f, -2.4f, properties));
    public static final DeferredItem<AxeItem> PURE_QUARTZ_AXE = ITEMS.registerItem("pure_quartz_axe",
            properties -> new AxeItem(ModToolMaterials.PURE_QUARTZ, 6f, -3.15f, properties));
    public static final DeferredItem<PickaxeItem> PURE_QUARTZ_PICKAXE = ITEMS.registerItem("pure_quartz_pickaxe",
            properties -> new PickaxeItem(ModToolMaterials.PURE_QUARTZ, 2f, -2.8f, properties));
    public static final DeferredItem<ShovelItem> PURE_QUARTZ_SHOVEL = ITEMS.registerItem("pure_quartz_shovel",
            properties -> new ShovelItem(ModToolMaterials.PURE_QUARTZ, 2.5f, -3f, properties));
    public static final DeferredItem<HoeItem> PURE_QUARTZ_HOE = ITEMS.registerItem("pure_quartz_hoe",
            properties -> new HoeItem(ModToolMaterials.PURE_QUARTZ, -2.5f, -1.5f, properties));



    // Weapons
        // halberd
    public static final DeferredItem<HalberdItem> WOODEN_HALBERD = ITEMS.registerItem("wooden_halberd",
            properties -> new HalberdItem(ToolMaterial.WOOD, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> STONE_HALBERD = ITEMS.registerItem("stone_halberd",
            properties -> new HalberdItem(ToolMaterial.STONE, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> COPPER_HALBERD = ITEMS.registerItem("copper_halberd",
            properties -> new HalberdItem(ModToolMaterials.COPPER, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> IRON_HALBERD = ITEMS.registerItem("iron_halberd",
            properties -> new HalberdItem(ToolMaterial.IRON, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> GOLDEN_HALBERD = ITEMS.registerItem("golden_halberd",
            properties -> new HalberdItem(ToolMaterial.GOLD, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> DIAMOND_HALBERD = ITEMS.registerItem("diamond_halberd",
            properties -> new HalberdItem(ToolMaterial.DIAMOND, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> NETHERITE_HALBERD = ITEMS.registerItem("netherite_halberd",
            properties -> new HalberdItem(ToolMaterial.NETHERITE, HalberdItem.ATTACK_DAMAGE, HalberdItem.ATTACK_SPEED, properties));
    public static final DeferredItem<HalberdItem> PURE_QUARTZ_HALBERD = ITEMS.registerItem("pure_quartz_halberd",
            properties -> new HalberdItem(ModToolMaterials.PURE_QUARTZ, HalberdItem.ATTACK_DAMAGE+1, HalberdItem.ATTACK_SPEED, properties));

        // Spear
    public static final DeferredItem<SpearItem> WOOD_SPEAR = ITEMS.registerItem("wooden_spear",
            properties -> new SpearItem(ToolMaterial.WOOD, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> STONE_SPEAR = ITEMS.registerItem("stone_spear",
            properties -> new SpearItem(ToolMaterial.STONE, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> COPPER_SPEAR = ITEMS.registerItem("copper_spear",
            properties -> new SpearItem(ModToolMaterials.COPPER, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> IRON_SPEAR = ITEMS.registerItem("iron_spear",
            properties -> new SpearItem(ToolMaterial.IRON, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> GOLDEN_SPEAR = ITEMS.registerItem("golden_spear",
            properties -> new SpearItem(ToolMaterial.GOLD, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> DIAMOND_SPEAR = ITEMS.registerItem("diamond_spear",
            properties -> new SpearItem(ToolMaterial.DIAMOND, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> PURE_QUARTZ_SPEAR = ITEMS.registerItem("pure_quartz_spear",
            properties -> new SpearItem(ModToolMaterials.PURE_QUARTZ, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));
    public static final DeferredItem<SpearItem> NETHERITE_SPEAR = ITEMS.registerItem("netherite_spear",
            properties -> new SpearItem(ToolMaterial.NETHERITE, SpearItem.ATTACK_DAMAGE, SpearItem.ATTACK_SPEED, properties));


    // Armor Trim
    public static final DeferredItem<Item> THIEF_SMITHING_TEMPLATE = ITEMS.registerItem("thief_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);
    public static final DeferredItem<Item> GLADIATOR_SMITHING_TEMPLATE = ITEMS.registerItem("gladiator_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);
    public static final DeferredItem<Item> KNIGHT_SMITHING_TEMPLATE = ITEMS.registerItem("knight_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);
    public static final DeferredItem<Item> VANILLA_SMITHING_TEMPLATE = ITEMS.registerItem("vanilla_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);
    public static final DeferredItem<Item> WARRIOR_SMITHING_TEMPLATE = ITEMS.registerItem("warrior_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);
    public static final DeferredItem<Item> ROBOT_SMITHING_TEMPLATE = ITEMS.registerItem("robot_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);
    public static final DeferredItem<Item> DESERT_SMITHING_TEMPLATE = ITEMS.registerItem("desert_armor_trim_smithing_template",
            SmithingTemplateItem::createArmorTrimTemplate);



    // Orium
    public static final DeferredItem<Item> WIRES = ITEMS.registerItem("wires", Item::new, new Item.Properties());
    public static final DeferredItem<Item> CIRCUIT_BOARD = ITEMS.registerItem("circuit_board", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ORIUM_ORB = ITEMS.registerItem("orium_orb", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ORIUM_SPIRIT_SPAWN_EGG = ITEMS.registerItem("orium_spirit_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.ORIUM_SPIRIT.get(), properties));



    // Animals
    public static final DeferredItem<Item> ELEPHANT_SPAWN_EGG = ITEMS.registerItem("elephant_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.ELEPHANT.get(), properties));
    public static final DeferredItem<Item> ELEPHANT_TUSK = ITEMS.registerItem("elephant_tusk", Item::new, new Item.Properties().stacksTo(16));

    public static final DeferredItem<Item> RHINOCEROS_SPAWN_EGG = ITEMS.registerItem("rhinoceros_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.RHINOCEROS.get(), properties));
    public static final DeferredItem<Item> RHINOCEROS_HORN = ITEMS.registerItem("rhinoceros_horn", Item::new, new Item.Properties().stacksTo(16));


    public static final DeferredItem<Item> GIRAFFE_SPAWN_EGG = ITEMS.registerItem("giraffe_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.GIRAFFE.get(), properties));
    public static final DeferredItem<Item> TIGER_SPAWN_EGG = ITEMS.registerItem("tiger_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.TIGER.get(), properties));
    public static final DeferredItem<Item> LION_SPAWN_EGG = ITEMS.registerItem("lion_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.LION.get(), properties));
    public static final DeferredItem<Item> SNOW_PANTHER_SPAWN_EGG = ITEMS.registerItem("snow_panther_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.SNOW_PANTHER.get(), properties));
    public static final DeferredItem<Item> PENGUIN_SPAWN_EGG = ITEMS.registerItem("penguin_spawn_egg",
            properties -> new SpawnEggItem(ModEntities.PENGUIN.get(), properties));


    // Magical Minecraft
    public static final DeferredItem<Item> TREND_HEART = ITEMS.registerItem(
            "trend_heart", Item::new, new Item.Properties());
    public static final DeferredItem<Item> IMPURE_END_CRISTAL = ITEMS.registerItem(
            "impure_end_cristal", Item::new, new Item.Properties());
    public static final DeferredItem<Item> END_CRISTAL = ITEMS.registerItem(
            "end_cristal", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MAGIC_CRISTAL = ITEMS.registerItem(
            "magic_cristal", Item::new, new Item.Properties());
    public static final DeferredItem<Item> MAGIC_ORB = ITEMS.registerItem(
            "magic_orb", Item::new, new Item.Properties());
    public static final DeferredItem<Item> INVOCATION_ORB = ITEMS.registerItem(
            "invocation_orb", Item::new, new Item.Properties());


    public static final DeferredItem<Item> MAGIC_STICK = ITEMS.registerItem(
            "magic_stick", Item::new, new Item.Properties());
    public static final DeferredItem<Item> LIGHTNING_STAFF = ITEMS.registerItem(
            "lightning_staff", LightningStaff::new, new Item.Properties().durability(60).stacksTo(1));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
