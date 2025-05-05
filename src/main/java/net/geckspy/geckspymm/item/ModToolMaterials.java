package net.geckspy.geckspymm.item;

import net.geckspy.geckspymm.util.ModTags;
import net.minecraft.world.item.ToolMaterial;
import net.neoforged.neoforge.common.Tags;

public class ModToolMaterials {
    public static final ToolMaterial COPPER = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_COPPER_TOOL,
            200,
            5f,
            1.5f,
            12,
            Tags.Items.INGOTS_COPPER
    );

    public static final ToolMaterial PURE_QUARTZ = new ToolMaterial(
            ModTags.Blocks.INCORRECT_FOR_PURE_QUARTZ_TOOL,
            1751,
            8.5f,
            2.5f,
            20,
            ModTags.Items.PURE_QUARTZ_MATERIALS
    );

}
