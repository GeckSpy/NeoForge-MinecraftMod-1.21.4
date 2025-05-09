package net.geckspy.geckspymm.block;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.block.custom.MagicCraftingTable;
import net.geckspy.geckspymm.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MyMod.MOD_ID);

    // double shift to see files, includes Non-project items, Search for BLOCKS and open Blocks from minecraft\world\level\block
    public static final DeferredBlock<Block> IMPURE_END_CRISTAL_BLOCK = registerBlock(
            "impure_end_cristal_block", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:impure_end_cristal_block")))
                    .strength(3.2F, 9.0F).noOcclusion()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final DeferredBlock<Block> END_CRISTAL_BLOCK = registerBlock(
            "end_cristal_block", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:end_cristal_block")))
                    .strength(2.0F, 8.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final DeferredBlock<Block> MAGIC_CRAFTING_TABLE = registerBlock(
            "magic_crafting_table", ()->new MagicCraftingTable(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:magic_crafting_table")))
                    .strength(2.0F, 7.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)
            )
    );



    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name, ()->new BlockItem(block.get(), new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, name)))));
    }



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
