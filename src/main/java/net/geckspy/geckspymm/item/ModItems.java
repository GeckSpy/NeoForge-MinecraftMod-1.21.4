package net.geckspy.geckspymm.item;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MyMod.MOD_ID);

    public static final DeferredItem<Item> END_CRISTAL = ITEMS.registerItem(
            "end_cristal", Item::new, new Item.Properties());


    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
