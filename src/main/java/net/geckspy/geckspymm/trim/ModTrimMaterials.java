package net.geckspy.geckspymm.trim;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.item.ModItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.trim.TrimMaterial;

import java.util.Map;


public class ModTrimMaterials {
    public static final ResourceKey<TrimMaterial> PURE_QUARTZ = ResourceKey.create(
            Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "pure_quartz"));



    public static void bootstrap(BootstrapContext<TrimMaterial> context){
        register(context, PURE_QUARTZ, ModItems.PURE_QUARTZ.get(), Style.EMPTY.withColor(TextColor.parseColor("#ddd4c6").getOrThrow()));
    }

    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimkey, Item item,
                                Style style){
        TrimMaterial trimMaterial = TrimMaterial.create(trimkey.location().getPath(), item,
                Component.translatable(Util.makeDescriptionId("trim_material", trimkey.location())).withStyle(style),
                Map.of());
        context.register(trimkey, trimMaterial);
    }
}
