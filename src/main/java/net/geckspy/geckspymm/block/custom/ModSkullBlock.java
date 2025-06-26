package net.geckspy.geckspymm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ModSkullBlock extends Block {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;

    public ModSkullBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.UP));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (direction) {
            case UP -> Block.box(4, 0, 4, 12, 8, 12);
            case DOWN -> Block.box(4, 8, 4, 12, 16, 12);
            case NORTH -> Block.box(4, 4, 8, 12, 12, 16);
            case SOUTH -> Block.box(4, 4, 0, 12, 12, 8);
            case WEST -> Block.box(8, 4, 4, 16, 12, 12);
            case EAST -> Block.box(0, 4, 4, 8, 12, 12);
        };
    }
}
