package net.geckspy.geckspymm.block.entity;

import net.geckspy.geckspymm.block.custom.MergerBlock;
import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.screen.MergerBlockMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class MergerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(4){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            // How many item can be placed here
            return super.getStackLimit(slot, stack);
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
            super.onContentsChanged(slot);
        }
    };
    public final int fuelSlot = 0;
    public final int topIngredientSlot = 1;
    public final int bottomIngredientSlot = 2;
    public final int resultSlot = 3;

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 320;


    public MergerBlockEntity(BlockPos pos, BlockState blockState){
        super(ModBlockEntities.MERGER_BLOCK_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> MergerBlockEntity.this.progress;
                    case 1 -> MergerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: MergerBlockEntity.this.progress = value;
                    case 1: MergerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                // How many variables we want to store
                // Here:2 because progress and maxProgress
                return 2;
            }
        };
    }

    public void clearContents(){
        for(int i=0; i<inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i=0; i<inventory.getSlots(); i++){
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
        tag.putInt("merger_block.progress", progress);
        tag.putInt("merger_block.max_progress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("merger_block.progress");
        maxProgress = tag.getInt("merger_block.max_progress");
    }


    public void tick(Level level, BlockPos blockPos, BlockState blockState){
        if(hasValidRecipe()){
            level.setBlockAndUpdate(blockPos, blockState.setValue(MergerBlock.LIT, true));
            this.progress++;
            setChanged(level, blockPos, blockState);
            if(this.progress >= this.maxProgress){
                this.progress = 0;
                craftItem();
            }
        }else {
            level.setBlockAndUpdate(blockPos, blockState.setValue(MergerBlock.LIT, false));
            progress=0;
        }
    }

    private boolean hasValidRecipe(){
        ItemStack output = new ItemStack(Items.GOLD_INGOT, 2);

        ItemStack fuelItem =  inventory.getStackInSlot(fuelSlot);
        ItemStack topItem = inventory.getStackInSlot(topIngredientSlot);
        ItemStack botItem = inventory.getStackInSlot(bottomIngredientSlot);

        return fuelItem.is(ModItems.ORIUM_ORB.get()) && topItem.is(Items.IRON_INGOT) && botItem.is(Items.COPPER_INGOT) &&
                canInsertItemIntoOutputSlot(output);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output){
        return inventory.getStackInSlot(resultSlot).isEmpty() ||
                (inventory.getStackInSlot(resultSlot).getItem()==output.getItem() &&
                inventory.getStackInSlot(resultSlot).getCount()+output.getCount() <=
                        inventory.getStackInSlot(resultSlot).getMaxStackSize());
    }

    private void craftItem(){
        ItemStack output = new ItemStack(Items.GOLD_INGOT, 2);
        inventory.extractItem(fuelSlot, 1, false);
        inventory.extractItem(topIngredientSlot, 1, false);
        inventory.extractItem(bottomIngredientSlot, 1, false);
        inventory.setStackInSlot(resultSlot, new ItemStack(output.getItem(),
                inventory.getStackInSlot(resultSlot).getCount() + output.getCount()
                ));
    }




    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return super.getUpdateTag(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.geckspymm.merger_block");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MergerBlockMenu(containerId, playerInventory, this, this.data);
    }

}
