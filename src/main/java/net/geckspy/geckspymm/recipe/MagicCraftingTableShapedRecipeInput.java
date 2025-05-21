package net.geckspy.geckspymm.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public record MagicCraftingTableShapedRecipeInput(ShapedRecipePattern pattern)
    implements RecipeInput {

    public MagicCraftingTableShapedRecipeInput(ShapedRecipePattern pattern){
        this.pattern = pattern;
    }

    @Override
    public ItemStack getItem(int index) {
        return null;
    }

    @Override
    public int size() {
        //return pattern.pattern().stream().filter(s->s!=" ").toList().size();
        return 9;
    }

    public ShapedRecipePattern getPattern(){return pattern;}
}
