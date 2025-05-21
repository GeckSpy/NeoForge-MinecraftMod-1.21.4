package net.geckspy.geckspymm.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record MagicCraftingTableShapelessRecipeinput(List<ItemStack> itemStacks)
        implements RecipeInput {

    public MagicCraftingTableShapelessRecipeinput(List<ItemStack> itemStacks){
        this.itemStacks = itemStacks;
    }

    @Override
    public ItemStack getItem(int index) {
        ItemStack item;
        if(index>=0 && index<10){
            //item = new ItemStack(this.itemStacks.get(index).getItem());
            item = this.itemStacks.get(index).copy();
        }else{
            throw new IllegalArgumentException("MergerBlock Recipe does not contain slot "+index);
        }
        return item;
    }

    @Override
    public int size() {
        return 9;
    }

    public List<ItemStack> getItemStacks(){return this.itemStacks;}
}
