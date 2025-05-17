package net.geckspy.geckspymm.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record MergerBlockRecipeInput(ItemStack fuel, ItemStack top, ItemStack bottom) implements RecipeInput {

    public MergerBlockRecipeInput(ItemStack fuel, ItemStack top, ItemStack bottom){
        this.fuel = fuel;
        this.top = top;
        this.bottom = bottom;
    }

    @Override
    public ItemStack getItem(int index) {
        ItemStack item;
        switch (index){
            case 0 -> item = this.fuel;
            case 1 -> item = this.top;
            case 2 -> item = this.bottom;
            default -> throw new IllegalArgumentException("MergerBlock Recipe does not contain slot "+index);
        }
        return item;
    }

    @Override
    public int size() {
        return 3;
    }

    public ItemStack getFuel(){return this.fuel;}
    public ItemStack getTop(){return this.top;}
    public ItemStack getBottom(){return this.bottom;}
}
