package net.geckspy.geckspymm.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record MergerBlockRecipe(Optional<Ingredient> fuel, Optional<Ingredient> top, Optional<Ingredient> bottom, ItemStack output)
        implements Recipe<MergerBlockRecipeInput> {
    // Ingredients: read from JSON
    // Input: merger block's inventory

    @Override
    public boolean matches(MergerBlockRecipeInput input, Level level) {
        if(level.isClientSide()){return false;}

        boolean isRecipe = top.get().test(input.getTop()) && bottom.get().test(input.getBottom());
        boolean isMirroredRecipe = top.get().test(input.getBottom()) && bottom.get().test(input.getTop());
        return fuel.get().test(input.getFuel()) && (isRecipe || isMirroredRecipe);
    }

    @Override
    public ItemStack assemble(MergerBlockRecipeInput input, HolderLookup.Provider registries) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MergerBlockRecipeInput>> getSerializer() {
        return ModRecipes.MERGER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MergerBlockRecipeInput>> getType() {
        return ModRecipes.MERGER_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(List.of(fuel.get(), top.get(), bottom.get()));
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<MergerBlockRecipe>{
        private static final MapCodec<MergerBlockRecipe> CODEC = RecordCodecBuilder.mapCodec(
                mergerBlockRecipe -> mergerBlockRecipe.group(
                                Ingredient.CODEC.optionalFieldOf("fuel").forGetter(recipe -> recipe.fuel),
                                Ingredient.CODEC.optionalFieldOf("top").forGetter(recipe -> recipe.top),
                                Ingredient.CODEC.optionalFieldOf("bottom").forGetter(recipe -> recipe.bottom),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
                        )
                        .apply(mergerBlockRecipe, MergerBlockRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, MergerBlockRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,recipe -> recipe.fuel,
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,recipe -> recipe.top,
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,recipe -> recipe.bottom,
                ItemStack.STREAM_CODEC, recipe -> recipe.output,
                MergerBlockRecipe::new
        );

        @Override
        public MapCodec<MergerBlockRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MergerBlockRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
