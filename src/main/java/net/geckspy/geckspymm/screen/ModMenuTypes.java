package net.geckspy.geckspymm.screen;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MyMod.MOD_ID);


    public static final DeferredHolder<MenuType<?>, MenuType<MergerBlockMenu>> MERGER_BLOCK_MENU =
            registerMenuType("merger_block_menu", MergerBlockMenu::new);



    public static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(
            String name, IContainerFactory<T> factory){
        return MENUS.register(name, ()-> IMenuTypeExtension.create(factory));
    }


    public static final DeferredHolder<MenuType<?>, MenuType<MagicCraftingTableMenu>> MAGIC_CRAFTING_TABLE_MENU =
            MENUS.register("magic_crafting_table", () ->
                    new MenuType<>(
                            (containerId, playerInventory) ->
                                    new MagicCraftingTableMenu(containerId, playerInventory),
                            FeatureFlags.DEFAULT_FLAGS
                    )
            );


    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
