package net.geckspy.geckspymm.item;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.item.custom.LightningStaff;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MyMod.MOD_ID);

// Better Minecraft
    public static final DeferredItem<Item> COPPER_SWORD = ITEMS.registerItem(
            "copper_sword",
            properties -> new SwordItem(ModToolMaterials.COPPER_MATERIAL, 5.5f, -2.4f, properties));
    public static final DeferredItem<Item> COPPER_AXE = ITEMS.registerItem(
            "copper_axe",
            properties -> new AxeItem(ModToolMaterials.COPPER_MATERIAL, 9, -3.2f, properties));
    public static final DeferredItem<Item> COPPER_PICKAXE = ITEMS.registerItem(
            "copper_pickaxe",
            properties -> new PickaxeItem(ModToolMaterials.COPPER_MATERIAL, 3, -2.8f, properties));
    public static final DeferredItem<Item> COPPER_SHOVEL = ITEMS.registerItem(
            "copper_shovel",
            properties -> new ShovelItem(ModToolMaterials.COPPER_MATERIAL, 2, -3f, properties));
    public static final DeferredItem<Item> COPPER_HOE = ITEMS.registerItem(
            "copper_hoe",
            properties -> new HoeItem(ModToolMaterials.COPPER_MATERIAL, 0.5f, -2f, properties));



// Magical Minecraft
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
            "lightning_staff", LightningStaff::new, new Item.Properties().durability(60));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
