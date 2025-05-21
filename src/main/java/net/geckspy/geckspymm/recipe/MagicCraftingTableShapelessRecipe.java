package net.geckspy.geckspymm.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record MagicCraftingTableShapelessRecipe(List<Ingredient> ingredients, ItemStack output)
        implements Recipe<MagicCraftingTableShapelessRecipeinput> {
    // Ingredients: read from JSON
    // Input: block's inventory

    @Override
    public boolean matches(MagicCraftingTableShapelessRecipeinput input, Level level) {
        if(level.isClientSide()){return false;}
        if(input.itemStacks().size() != ingredients.size()){return false;}

        int inputSize = input.itemStacks().size();
        ArrayList<Boolean> usedSlots = new ArrayList<>(inputSize);
        for(int i=0;i<inputSize;i++){usedSlots.add(false);}

        for(Ingredient ingr: ingredients){
            boolean b = false;
            for(int i=0; i<inputSize; i++){
                if((!usedSlots.get(i)) && ingr.test(input.getItem(i))){
                    usedSlots.set(i, true);
                    b = true;
                    break;
                }
            }
            if(!b){return false;}
        }
        return true;
    }

    @Override
    public ItemStack assemble(MagicCraftingTableShapelessRecipeinput input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MagicCraftingTableShapelessRecipeinput>> getSerializer() {
        return ModRecipes.MAGIC_SHAPELESS_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagicCraftingTableShapelessRecipeinput>> getType() {
        return ModRecipes.MAGIC_SHAPELESS_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(ingredients);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }


    public static class Serializer implements RecipeSerializer<MagicCraftingTableShapelessRecipe>{
        private static final MapCodec<MagicCraftingTableShapelessRecipe> CODEC = RecordCodecBuilder.mapCodec(
                magicShapelessRecipe -> magicShapelessRecipe.group(
                        Codec.lazyInitialized(() -> Ingredient.CODEC.listOf(1, 9)).fieldOf("ingredients").forGetter(recipe -> recipe.ingredients),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                )
                .apply(magicShapelessRecipe, MagicCraftingTableShapelessRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MagicCraftingTableShapelessRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), recipe -> recipe.ingredients,
                ItemStack.STREAM_CODEC, recipe -> recipe.output,
                MagicCraftingTableShapelessRecipe::new
        );

        @Override
        public MapCodec<MagicCraftingTableShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MagicCraftingTableShapelessRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
