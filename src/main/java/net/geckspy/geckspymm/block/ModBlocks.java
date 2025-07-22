package net.geckspy.geckspymm.block;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.block.custom.*;
import net.geckspy.geckspymm.block.custom.crop.LeekCropBlock;
import net.geckspy.geckspymm.block.custom.crop.PepperCropBlock;
import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.worldgen.tree.ModTreeGrowers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MyMod.MOD_ID);
    // double shift to see files, includes Non-project items, Search for BLOCKS and open Blocks from minecraft\world\level\block

    public static final DeferredBlock<Block> PEPPER_CROP = BLOCKS.registerBlock("pepper_crop",
            properties -> new PepperCropBlock(properties
                    .randomTicks().sound(SoundType.CROP).noOcclusion().pushReaction(PushReaction.DESTROY)
                    .instabreak().noCollission()));

    public static final DeferredBlock<Block> LEEK_CROP = BLOCKS.registerBlock("leek_crop",
            properties -> new LeekCropBlock(properties
                    .randomTicks().sound(SoundType.CROP).noOcclusion().pushReaction(PushReaction.DESTROY)
                    .instabreak().noCollission()));


    public static final DeferredBlock<Block> TNT_V2 = registerBlock(
            "tnt_v2", ()->new TntV2Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:tnt_v2")))
                    .instabreak().sound(SoundType.GRASS)
                    .mapColor(MapColor.FIRE).ignitedByLava()
            )
    );
    public static final DeferredBlock<Block> TNT_V3 = registerBlock(
            "tnt_v3", ()->new TntV3Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:tnt_v3")))
                    .instabreak().sound(SoundType.GRASS)
                    .mapColor(MapColor.FIRE).ignitedByLava()
            )
    );



    public static final DeferredBlock<Block> ORIUM_TORCH = registerBlock(
            "orium_torch", ()->new OriumTorchBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:orium_torch")))
                    .noCollission().instabreak().pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.WOOD)
                    .lightLevel(BlockBehaviour.BlockStateBase::getLightEmission)
            )
    );

    public static final DeferredBlock<Block> MERGER_BLOCK = registerBlock(
            "merger_block", ()->new MergerBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:merger_block")))
                    .strength(3.5F, 3.0F).noOcclusion()
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE)

            )
    );

    public static final DeferredBlock<Block> BATTERY = registerBlock("battery",
            () -> new ModSkullBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:battery")))
                    .strength(1.0f).noOcclusion().sound(SoundType.METAL)
                    .lightLevel(state->3).mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<Block> CONTROL_UNIT = registerBlock("control_unit",
            () -> new ModSkullBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:control_unit")))
                    .strength(1.0f).noOcclusion().sound(SoundType.METAL).mapColor(MapColor.COLOR_RED)));



    // Magical
        // Trend
            // Tree
    public static final DeferredBlock<TrendHeart> TREND_HEART = registerBlockWithFun("trend_heart",
            (properties) -> new TrendHeart(ModTreeGrowers.TREND,
                    properties.mapColor(MapColor.PLANT).noCollission().randomTicks()
                    .strength(2f).sound(SoundType.COBWEB).pushReaction(PushReaction.DESTROY),
                    () -> Blocks.END_STONE)
    );
    public static final DeferredBlock<RotatedPillarBlock> TREND_LOG = registerBlock(
            "trend_log", ()->new RotatedPillarBlock(RotatedPillarBlock.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:trend_log")))
                    .strength(3F, 8.0F).noOcclusion()
                    .requiresCorrectToolForDrops().sound(SoundType.WOOD))
    );
    public static final DeferredBlock<Block> TREND_WOOD = registerBlock(
            "trend_wood", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:trend_wood")))
                    .strength(3F, 8.0F).noOcclusion()
                    .requiresCorrectToolForDrops().sound(SoundType.WOOD))
    );
    public static final DeferredBlock<Block> TREND_WOOD_HEART = registerBlock(
            "trend_wood_heart", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:trend_wood_heart")))
                    .strength(3.2F, 6.0F).noOcclusion()
                    .requiresCorrectToolForDrops().sound(SoundType.WOOD))
    );
    public static final DeferredBlock<TrendFlower> TREND_FLOWER = registerBlock(
            "trend_flower", ()->new TrendFlower(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:trend_flower")))
                    .noOcclusion().pushReaction(PushReaction.DESTROY).noCollission()
                    .strength(2.8f, 2.0f)
                    .requiresCorrectToolForDrops().sound(SoundType.FLOWERING_AZALEA))
    );
    public static final DeferredBlock<Block> POTTED_TREND_FLOWER = registerBlock(
            "potted_trend_flower", ()->new FlowerPotBlock(TREND_FLOWER.get(), BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:potted_trend_flower")))
                    )
    );
            // Trend woods block
    public static final DeferredBlock<Block> TREND_PLANKS = registerBlock(
                    "trend_planks", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:trend_planks")))
                    .mapColor(MapColor.WOOD).strength(2.4F, 5.0F).sound(SoundType.WOOD))
    );
    public static final DeferredBlock<StairBlock> TREND_STAIRS = registerBlockWithFun("trend_stairs",
            (properties)->new StairBlock(ModBlocks.TREND_PLANKS.get().defaultBlockState(),
                    properties.strength(2.4F, 5.0F).requiresCorrectToolForDrops())
    );
    public static final DeferredBlock<SlabBlock> TREND_SLAB = registerBlockWithFun("trend_slab",
            (properties) -> new SlabBlock(properties.strength(2.4F, 5.0F).requiresCorrectToolForDrops())
    );



    public static final DeferredBlock<Block> IMPURE_END_CRISTAL_BLOCK = registerBlock(
            "impure_end_cristal_block", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:impure_end_cristal_block")))
                    .strength(3.2F, 9.0F).noOcclusion()
                    .requiresCorrectToolForDrops().sound(SoundType.STONE))
    );
    public static final DeferredBlock<Block> END_CRISTAL_BLOCK = registerBlock(
            "end_cristal_block", ()->new Block(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:end_cristal_block")))
                    .strength(2.0F, 8.5F)
                    .requiresCorrectToolForDrops().sound(SoundType.METAL))
    );


    public static final DeferredBlock<Block> MAGIC_CRAFTING_TABLE = registerBlock(
            "magic_crafting_table", ()->new MagicCraftingTable(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:magic_crafting_table")))
                    .strength(2.0F, 7.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );
    public static final DeferredBlock<ModFacingBlock> ENT_HEAD = registerBlock(
            "ent_head", ()->new ModFacingBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:ent_head")))
                    .strength(2.0F).sound(SoundType.WOOD).ignitedByLava(),
                    ModItems.ENT_HEAD_ITEM
                    )
    );
    public static final DeferredBlock<ModFacingBlock> ENT_HEAD_LIT = registerBlock(
            "ent_head_lit", ()->new ModFacingBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.parse("geckspymm:ent_head_lit")))
                    .strength(2.0F).sound(SoundType.WOOD).ignitedByLava()
                    .lightLevel(state->12).mapColor(MapColor.COLOR_ORANGE),
                    ModItems.ENT_HEAD_LIT_ITEM
            )
    );





    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerBlockWithFun(String name, Function<BlockBehaviour.Properties, T> block){
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, block);
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
