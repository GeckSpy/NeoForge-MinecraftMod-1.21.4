package net.geckspy.geckspymm.util;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{

        public static final TagKey<Block> NEEDS_COPPER_TOOL = TagKey.create(
                BuiltInRegistries.BLOCK.key(),
                ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "needs_copper_tool"));
        public static final TagKey<Block> INCORRECT_FOR_COPPER_TOOL = TagKey.create(
                BuiltInRegistries.BLOCK.key(),
                ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID,"incorrect_for_copper_tool"));

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, name));
        }

    }

    public static class Items{

        //public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items")

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, name));
        }
    }

}
