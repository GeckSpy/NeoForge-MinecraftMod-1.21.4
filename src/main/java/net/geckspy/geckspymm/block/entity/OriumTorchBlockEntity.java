package net.geckspy.geckspymm.block.entity;

import net.geckspy.geckspymm.block.custom.MergerBlock;
import net.geckspy.geckspymm.block.custom.OriumTorchBlock;
import net.geckspy.geckspymm.entity.spear.ThrownSpear;
import net.geckspy.geckspymm.util.ModFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class OriumTorchBlockEntity extends BlockEntity {
    public static final float TARGET_RADIUS = 2f;
    public static final int SLEEPING_TICK = 20*10;
    protected int attackCooldown = SLEEPING_TICK;

    public OriumTorchBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ORIUM_TORCH_BLOCK_BE.get(), pos, blockState);
    }

    public int getAttackCooldown(){
        return this.attackCooldown;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("orium_torch_block.attack_cooldown", attackCooldown);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        attackCooldown = tag.getInt("orium_torch_block.attack_cooldown");
    }

    public void damage(Level level, Monster target){
        //ThrownSpear thrownSpear = (ThrownSpear) Projectile.spawnProjectileFromRotation(ThrownSpear::new, serverlevel, itemStack, player, 0.0f, 1.8f, 0.5f);
        System.out.println("dammaged");
    }

    public void tick(Level level, BlockPos pos, BlockState state){
        if(attackCooldown>0){
            attackCooldown--;
        }else{
            Monster target = new ModFunctions().getClosestMonster(level, pos, TARGET_RADIUS, true);
            if(target != null){
                damage(level, target);
                attackCooldown = SLEEPING_TICK;
            }
        }
        level.setBlockAndUpdate(pos, state.setValue(OriumTorchBlock.LIT, attackCooldown<=0));
    }
}
