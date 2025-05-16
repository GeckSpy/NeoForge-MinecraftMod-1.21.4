package net.geckspy.geckspymm.block.custom;

import com.mojang.serialization.MapCodec;
import net.geckspy.geckspymm.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class OriumTorchBlock extends BaseTorchBlock {
    public static final MapCodec<OriumTorchBlock> CODEC = simpleCodec(OriumTorchBlock::new);
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public OriumTorchBlock(BlockBehaviour.Properties properties){
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT,true));
    }

    @Override
    protected MapCodec<? extends BaseTorchBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource source) {
        if (blockState.getValue(LIT) && Math.random()<0.1) {
            double d0 = (double)blockPos.getX() + 0.5;
            double d1 = (double)blockPos.getY() + 0.6;
            double d2 = (double)blockPos.getZ() + 0.5;
            double scale =  Math.random() + 0.5;
            level.addParticle(ModParticles.ORIUM_PARTICLE.get(), d0, d1, d2, 0.0, -1, scale);
            //level.addParticle(ModParticles.ORIUM_PARTICLE.get(), d0, d1, d2, 0.0, 0, 2);
        }
    }


    @Nullable
    protected Orientation randomOrientation(Level level, BlockState state) {
        return ExperimentalRedstoneUtils.initialOrientation(level, null, Direction.UP);
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(!level.isClientSide()){
            boolean currentLitState = state.getValue(LIT);
            level.setBlockAndUpdate(pos, state.setValue(LIT, !currentLitState));
        }
        return InteractionResult.SUCCESS;
        //return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if(state.getValue(LIT)){
            return 15;
        }else{
            return 0;
        }
    }
    
}
