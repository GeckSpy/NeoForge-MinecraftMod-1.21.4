package net.geckspy.geckspymm.block.custom;

import net.geckspy.geckspymm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrendFlower extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public TrendFlower(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case UP -> Block.box(1, 0, 1, 15, 8, 15);
            case DOWN -> Block.box(1, 8, 1, 15, 16, 15);
            case NORTH -> Block.box(3, 3, 8, 13, 13, 16);
            case SOUTH -> Block.box(3, 3, 0, 13, 13, 8);
            case WEST -> Block.box(8, 3, 3, 16, 13, 13);
            case EAST -> Block.box(0, 3, 3, 8, 13, 13);
        };
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        Direction facing = state.getValue(FACING);
        if (direction == facing.getOpposite() && !canSurvive(state, level, pos)) {
            if (level instanceof Level lvl) {lvl.destroyBlock(pos, false);}
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    public boolean canBePlaced(BlockState state){
        return state.is(Blocks.END_STONE)
                || state.is(ModBlocks.IMPURE_END_CRISTAL_BLOCK)
                || state.is(ModBlocks.TREND_LOG)
                || state.is(ModBlocks.TREND_WOOD)
                || state.is(ModBlocks.TREND_WOOD_HEART);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos attachedPos = pos.relative(facing.getOpposite());
        return canBePlaced(world.getBlockState(attachedPos)) && world.getBlockState(attachedPos).isFaceSturdy(world, attachedPos, facing);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && !entity.isSteppingCarefully()) {
            entity.makeStuckInBlock(state, new Vec3(0.8D, 0.75D, 0.8D));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 20*10, 2));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20*15, 1));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20*12, 1));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20*20, 2));
        }
    }


}
