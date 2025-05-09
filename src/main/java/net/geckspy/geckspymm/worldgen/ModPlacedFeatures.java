package net.geckspy.geckspymm.worldgen;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModPlacedFeatures {
    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, MyMod.MOD_ID);

    public static final DeferredHolder<PlacedFeature, PlacedFeature> END_CRISTAL_ORE_PLACED =
            PLACED_FEATURES.register("end_cristal_ore_placed",  () -> new PlacedFeature(
                    Holder.direct(
                            new ConfiguredFeature<>(
                                Feature.ORE,
                                new OreConfiguration(List.of(OreConfiguration.target(
                                        new BlockMatchTest(Blocks.END_STONE),
                                        ModBlocks.IMPURE_END_CRISTAL_BLOCK.get().defaultBlockState()
                                        )),
                                    9, // Vein size
                                    0.5f // Discard chance on air exposure
                            )
                    )),
                    List.of(
                            CountPlacement.of(20), // Number of veins per chunk
                            InSquarePlacement.spread(),
                            HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(250)),
                            BiomeFilter.biome()
                    )
            ));


    public static void register(IEventBus eventBus){
        PLACED_FEATURES.register(eventBus);
    }
}