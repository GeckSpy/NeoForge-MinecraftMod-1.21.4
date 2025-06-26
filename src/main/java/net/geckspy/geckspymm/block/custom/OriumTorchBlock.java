package net.geckspy.geckspymm.block.custom;

import com.mojang.serialization.MapCodec;
import net.geckspy.geckspymm.block.entity.MergerBlockEntity;
import net.geckspy.geckspymm.block.entity.ModBlockEntities;
import net.geckspy.geckspymm.block.entity.OriumTorchBlockEntity;
import net.geckspy.geckspymm.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class OriumTorchBlock extends Block {
    protected static final int AABB_STANDING_OFFSET = 2;
    protected static final VoxelShape AABB = Block.box((double)6.0F, (double)0.0F, (double)6.0F, (double)10.0F, (double)10.0F, (double)10.0F);

    public static final MapCodec<OriumTorchBlock> CODEC = simpleCodec(OriumTorchBlock::new);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");


    public OriumTorchBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT,false));
    }

    @Override
    protected VoxelShape getShape(BlockState p_304673_, BlockGetter p_304919_, BlockPos p_304930_, CollisionContext p_304757_) {
        return AABB;
    }

    @Override
    protected BlockState updateShape(BlockState p_304418_, LevelReader p_374159_, ScheduledTickAccess p_374152_, BlockPos p_304633_, Direction p_304475_, BlockPos p_304603_, BlockState p_304669_, RandomSource p_374111_) {
        return p_304475_ == Direction.DOWN && !this.canSurvive(p_304418_, p_374159_, p_304633_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_304418_, p_374159_, p_374152_, p_304633_, p_304475_, p_304603_, p_304669_, p_374111_);
    }

    @Override
    protected boolean canSurvive(BlockState p_304413_, LevelReader p_304885_, BlockPos p_304808_) {
        return canSupportCenter(p_304885_, p_304808_.below(), Direction.UP);
    }

    @Override
    protected MapCodec<? extends OriumTorchBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }


    @Nullable
    protected Orientation randomOrientation(Level level, BlockState state) {
        return ExperimentalRedstoneUtils.initialOrientation(level, null, Direction.UP);
    }


    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if(state.getValue(LIT)){
            return 12;
        }else{
            return 0;
        }
    }


    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource source) {
        if (blockState.getValue(LIT) && Math.random()<0.1) {
            double d0 = (double)blockPos.getX() + 0.5;
            double d1 = (double)blockPos.getY() + 0.6;
            double d2 = (double)blockPos.getZ() + 0.5;
            double scale =  Math.random() + 0.5;
            level.addParticle(ModParticles.ORIUM_PARTICLE.get(), d0, d1, d2, 0.0, -1, scale);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        level.setBlockAndUpdate(pos, state.setValue(LIT, !state.getValue(LIT)));
        return InteractionResult.SUCCESS;
    }
}
