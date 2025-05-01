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
    public static final DeferredItem<SwordItem> COPPER_SWORD = ITEMS.registerItem("copper_sword",
            properties -> new SwordItem(ModToolMaterials.COPPER, 4.5f, -2.4f, properties));
    public static final DeferredItem<AxeItem> COPPER_AXE = ITEMS.registerItem("copper_axe",
            properties -> new AxeItem(ModToolMaterials.COPPER, 8, -3.15f, properties));
    public static final DeferredItem<PickaxeItem> COPPER_PICKAXE = ITEMS.registerItem("copper_pickaxe",
            properties -> new PickaxeItem(ModToolMaterials.COPPER, 2.5f, -2.8f, properties));
    public static final DeferredItem<ShovelItem> COPPER_SHOVEL = ITEMS.registerItem("copper_shovel",
            properties -> new ShovelItem(ModToolMaterials.COPPER, 3, -3f, properties));
    public static final DeferredItem<HoeItem> COPPER_HOE = ITEMS.registerItem("copper_hoe",
            properties -> new HoeItem(ModToolMaterials.COPPER, 0f, -2f, properties));



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
