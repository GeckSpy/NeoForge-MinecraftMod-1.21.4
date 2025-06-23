package net.geckspy.geckspymm.item;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MyMod.MOD_ID);

    public static final Supplier<CreativeModeTab> MAGIC_TAB = CREATIVE_MODE_TAB.register(
            "magic_tab", () -> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModItems.MAGIC_CRISTAL.get()))
                    .title(Component.translatable("creativetab.geckspymm.magic"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.MAGIC_CRAFTING_TABLE);
                        output.accept(ModItems.MAGIC_ORB);
                        output.accept(ModItems.INVOCATION_ORB);
                        output.accept(ModItems.MAGIC_CRISTAL);
                        output.accept(ModItems.MAGIC_STICK);
                        output.accept(ModItems.LIGHTNING_STAFF);
                    })
                    .build()
    );

    public static final Supplier<CreativeModeTab> RADIANT_TAB = CREATIVE_MODE_TAB.register(
            "radian_tab", () -> CreativeModeTab.builder()
                    .icon(()->new ItemStack(ModItems.END_CRISTAL.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "magic_tab"))
                    .title(Component.translatable("creativetab.geckspymm.radian"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.MAGIC_CRISTAL);
                    })
                    .build()
    );


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
