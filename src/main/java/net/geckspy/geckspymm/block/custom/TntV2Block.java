package net.geckspy.geckspymm.block.custom;

import net.geckspy.geckspymm.entity.PrimedTntV2;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TntV2Block extends TntBlock {

    public TntV2Block(BlockBehaviour.Properties properties) {
        super(properties);
    }


    @Override
    public void wasExploded(ServerLevel level, BlockPos blockPos, Explosion explosion) {
        PrimedTnt primedtnt = new PrimedTntV2(
                level, (double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5, explosion.getIndirectSourceEntity()
        );
        int i = primedtnt.getFuse();
        primedtnt.setFuse((short)(level.random.nextInt(i / 6) + i / 10));
        level.addFreshEntity(primedtnt);
    }

    @Override
    public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
        if (!level.isClientSide) {
            PrimedTntV2 primedTnt = new PrimedTntV2(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, igniter);

            level.addFreshEntity(primedTnt);
            level.playSound(null, primedTnt.getX(), primedTnt.getY(), primedTnt.getZ(),
                    SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }

}
