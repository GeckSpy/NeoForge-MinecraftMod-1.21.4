package net.geckspy.geckspymm.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MagicCraftingTable extends CraftingTableBlock {
    public static final MapCodec<MagicCraftingTable> CODEC = simpleCodec(MagicCraftingTable::new);
    private static final Component CONTAINER_TITLE = Component.translatable("container.craft");
    private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 14.0D, 16.0);

    @Override
    public MapCodec<MagicCraftingTable> codec() {
        return CODEC;
    }

    public MagicCraftingTable(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
                (containerId, inventory, player) ->
                        new CraftingMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)) {
                            @Override
                            public boolean stillValid(Player player) {
                                return true; // Critical fix
                            }
                            @Override
                            public void slotsChanged(Container container){
                                // Custom recipe handling
                                //super.slotsChanged(container);
                            }
                            },
                            CONTAINER_TITLE
        );
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState p_56428_, Level p_56429_, BlockPos p_56430_, Player p_56431_, BlockHitResult p_56433_) {
        if (!p_56429_.isClientSide) {
            p_56431_.openMenu(p_56428_.getMenuProvider(p_56429_, p_56430_));
        }
        return InteractionResult.SUCCESS;
    }






    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random); // Keep vanilla particles
        // Add custom particles if desired
    }
}
