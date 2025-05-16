package net.geckspy.geckspymm.recipe;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.Map;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MyMod.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, MyMod.MOD_ID);


    /*
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagicShapelessRecipe>> MAGIC_SHAPELESS_SERIALIZER =
            SERIALIZERS.register("magic_shapeless", MagicShapelessRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MagicShapelessRecipe>> MAGIC_SHAPELESS_TYPE =
            TYPES.register("magic_shapeless", ()->new RecipeType<MagicShapelessRecipe>() {
                @Override
                public String toString() {
                    return "magic_shapeless";
                }
            });


     */


    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }


    public static void loadRecipe(ServerStartingEvent event){
        // Load from datapacks
        RecipeManager recipeManager = event.getServer().getRecipeManager();
        Collection<RecipeHolder<?>> recipes = recipeManager.getRecipes();

        System.out.println("loadRecipe called");
        System.out.println(recipes.stream().toList());

        RecipeHolder<?> recipe = recipes.stream().toList().getFirst();
        System.out.println(recipe);
        System.out.println(recipe.id());
        System.out.println(recipe.id().location());
        System.out.println(recipe.id().registry());
        System.out.println(recipe.value());
    }
}
