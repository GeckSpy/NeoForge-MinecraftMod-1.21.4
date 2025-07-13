package net.geckspy.geckspymm.worldgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.geckspy.geckspymm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.function.BiConsumer;

public class TrendBaseTrunkPlacer extends StraightTrunkPlacer {
    public static final MapCodec<TrendBaseTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance ->
            trunkPlacerParts(instance).apply(instance, TrendBaseTrunkPlacer::new)
    );

    public TrendBaseTrunkPlacer(int baiseHeight, int heightRandA, int heightRandB) {
        super(baiseHeight, heightRandA, heightRandB);
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> replacer, RandomSource random,
            int heightIndex, BlockPos pos, TreeConfiguration config) {
        //return super.placeTrunk(level, replacer, random, heightIndex, pos, config);

        replacer.accept(pos, ModBlocks.TREND_WOOD_HEART.get().defaultBlockState());

        Direction randomDir = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if(heightIndex==1){
            replacer.accept(pos.above(1), ModBlocks.TREND_WOOD.get().defaultBlockState());
            replacer.accept(pos.relative(randomDir, 1), ModBlocks.TREND_WOOD.get().defaultBlockState());
            replacer.accept(pos.relative(randomDir.getClockWise(), 1), ModBlocks.TREND_WOOD.get().defaultBlockState());
        }else{
            replacer.accept(pos.relative(randomDir, 1), ModBlocks.TREND_WOOD.get().defaultBlockState());
            replacer.accept(pos.relative(randomDir.getClockWise(), 1), ModBlocks.TREND_WOOD.get().defaultBlockState());
            replacer.accept(pos.relative(randomDir.getCounterClockWise(), 1), ModBlocks.TREND_WOOD.get().defaultBlockState());
            replacer.accept(pos.relative(randomDir.getOpposite(), 1), ModBlocks.TREND_LOG.get().defaultBlockState());
            replacer.accept(pos.relative(randomDir.getOpposite(), 1).above(1), ModBlocks.TREND_WOOD.get().defaultBlockState());

            int height = 4;
            if(heightIndex==3){height=6;}
            for (int i = 1; i <= heightIndex; i++) {
                BlockPos logPos = pos.above(i);
                replacer.accept(logPos, config.trunkProvider.getState(random, logPos));
            }
            BlockPos topPos = pos.above(heightIndex+1);
            if(heightIndex==2){
                replacer.accept(topPos, ModBlocks.TREND_WOOD.get().defaultBlockState());
                replacer.accept(topPos.relative(randomDir, 1), ModBlocks.TREND_WOOD.get().defaultBlockState());
                replacer.accept(topPos.relative(randomDir, 1).relative(Direction.DOWN, 1),
                        ModBlocks.TREND_FLOWER.get().defaultBlockState().setValue(
                                BlockStateProperties.FACING, Direction.DOWN
                        ));
            }else {
                replacer.accept(topPos, ModBlocks.TREND_WOOD.get().defaultBlockState());
                replacer.accept(topPos.relative(randomDir, 1), ModBlocks.TREND_LOG.get().defaultBlockState()
                        .setValue(RotatedPillarBlock.AXIS, randomDir.getAxis()));
                replacer.accept(topPos.relative(randomDir, 1).above(1), ModBlocks.TREND_WOOD.get().defaultBlockState());
                replacer.accept(topPos.relative(randomDir, 2), ModBlocks.TREND_WOOD.get().defaultBlockState());
                replacer.accept(topPos.relative(randomDir, 2).relative(Direction.DOWN, 1),
                        ModBlocks.TREND_FLOWER.get().defaultBlockState().setValue(
                                BlockStateProperties.FACING, Direction.DOWN
                        ));
            }
        }


        // No foliage attachment (since have no leaves)
        return List.of();
    }
}
