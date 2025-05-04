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
            18,
            Tags.Items.INGOTS_COPPER
    );
}
