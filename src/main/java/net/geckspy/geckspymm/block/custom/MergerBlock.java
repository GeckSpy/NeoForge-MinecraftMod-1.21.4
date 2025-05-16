package net.geckspy.geckspymm.block.custom;

import com.mojang.serialization.MapCodec;
import net.geckspy.geckspymm.block.entity.MergerBlockEntity;
import net.geckspy.geckspymm.block.entity.ModBlockEntities;
import net.geckspy.geckspymm.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MergerBlock extends BaseEntityBlock{
    public static final MapCodec<MergerBlock> CODEC = simpleCodec(MergerBlock::new);
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MergerBlock(Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT,false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MergerBlockEntity(pos, state);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof MergerBlockEntity mergerEntity) {
                mergerEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
        }}
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof MergerBlockEntity mergerBlockEntity){
            if(!level.isClientSide()){
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(
                        mergerBlockEntity, mergerBlockEntity.getDisplayName()), pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }


    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()){return null;}

        return createTickerHelper(blockEntityType, ModBlockEntities.MERGER_BLOCK_BE.get(),
                (level2, blockPos, blockState, blockEntity) ->
                blockEntity.tick(level2, blockPos, blockState));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        if (blockState.getValue(LIT) && Math.random()<0.2) {
            double d0 = (double)blockPos.getX() + 0.5;
            double d1 = (double)blockPos.getY() + 1.0;
            double d2 = (double)blockPos.getZ() + 0.5;
            level.addParticle(ModParticles.ORIUM_PARTICLE.get(), d0, d1, d2,
                    0.0, Math.PI*(2-0.25), 1.3);
        }
    }
}
