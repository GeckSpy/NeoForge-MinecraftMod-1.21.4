package net.geckspy.geckspymm.recipe;

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
import java.util.Optional;

public record MagicCraftingTableShapedRecipe(ShapedRecipePattern pattern, ItemStack output)
        implements Recipe<MagicCraftingTableShapelessRecipeinput> {
    // Ingredients: read from JSON
    // Input: block's inventory

    @Override
    public boolean matches(MagicCraftingTableShapelessRecipeinput input, Level level) {
        if(level.isClientSide()){return false;}
        int x_min=2; int x_max=0; int y_min=2; int y_max=0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                int index = i*3 + j;
                if(!input.getItem(index).isEmpty()){
                    x_min = Math.min(x_min, i);
                    x_max = Math.max(x_max, i);
                    y_min = Math.min(y_min, j);
                    y_max = Math.max(y_max, j);
                }
            }
        }
        int inputHeight = x_max-x_min+1;
        int inputWidth = y_max-y_min+1;
        if(inputHeight!=pattern.height() || inputWidth!=pattern.width()){return false;}

        ArrayList<ItemStack> inputItemStacks = new ArrayList<>(inputHeight*inputWidth);
        for(int i=0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                if(i>=x_min && i<=x_max && j>=y_min && j<=y_max) {
                    int index = i * 3 + j;
                    inputItemStacks.add(input.itemStacks().get(index));
                }
            }
        }
        List<Optional<Ingredient>> ingredients = pattern.ingredients();
        for(int i=0; i<inputHeight*inputWidth; i++){
            if(ingredients.get(i).isPresent()){
                if(!ingredients.get(i).get().test(inputItemStacks.get(i))){
                    return false;
                }
            }else{
                if(!inputItemStacks.get(i).isEmpty()){
                    return false;
                }
            }

        }
        return true;
    }

    @Override
    public ItemStack assemble(MagicCraftingTableShapelessRecipeinput input, HolderLookup.Provider registries) {
        return this.output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MagicCraftingTableShapelessRecipeinput>> getSerializer() {
        return ModRecipes.MAGIC_SHAPED_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MagicCraftingTableShapelessRecipeinput>> getType() {
        return ModRecipes.MAGIC_SHAPED_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(this.pattern.ingredients());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }


    public static class Serializer implements RecipeSerializer<MagicCraftingTableShapedRecipe>{
        private static final MapCodec<MagicCraftingTableShapedRecipe> CODEC = RecordCodecBuilder.mapCodec(
                magicShapedRecipe -> magicShapedRecipe.group(
                                ShapedRecipePattern.MAP_CODEC.forGetter(recipe -> recipe.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                        )
                        .apply(magicShapedRecipe, MagicCraftingTableShapedRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MagicCraftingTableShapedRecipe> STREAM_CODEC = StreamCodec.of(
                MagicCraftingTableShapedRecipe.Serializer::toNetwork,
                MagicCraftingTableShapedRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<MagicCraftingTableShapedRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MagicCraftingTableShapedRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static MagicCraftingTableShapedRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(buffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buffer);
            return new MagicCraftingTableShapedRecipe(shapedrecipepattern, itemstack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, MagicCraftingTableShapedRecipe recipe) {
            ShapedRecipePattern.STREAM_CODEC.encode(buffer, recipe.pattern);
            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
        }
    }
}
