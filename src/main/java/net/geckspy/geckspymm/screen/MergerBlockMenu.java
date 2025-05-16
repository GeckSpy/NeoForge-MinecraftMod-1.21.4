package net.geckspy.geckspymm.screen;

import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.block.entity.MergerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;


public class MergerBlockMenu extends AbstractContainerMenu {
    public final MergerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public MergerBlockMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData){
        this(containerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public MergerBlockMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData data){
        super(ModMenuTypes.MERGER_BLOCK_MENU.get(), containerId);
        this.blockEntity = ((MergerBlockEntity)blockEntity);
        this.level = blockEntity.getLevel();
        this.data = data;

        // Add player inventory
        for(int i=0; i<3; i++){for(int j=0; j<9; j++){
                this.addSlot(new Slot(inventory, j+i*9+9, 8+j*18, 84+i*18));
        }}
        // Add player hotbar
        for(int i=0;i<9;i++){
            this.addSlot(new Slot(inventory, i, 8+i*18, 142));
        }
        // Add merger slots
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, this.blockEntity.fuelSlot, 30, 17+18));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, this.blockEntity.topIngredientSlot, 30+36 , 17));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, this.blockEntity.bottomIngredientSlot, 30+36, 17+36));
        addDataSlots(data);
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, this.blockEntity.resultSlot, 124, 35));
    }

    public boolean isCrafting(){
        return data.get(0)>0;
    }

    public int getScaledArrowProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        return maxProgress!=0&&progress!=0 ? progress*MergerBlockScreen.ARROW_TEXTURE_WIDTH/maxProgress : 0;
    }




    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = 3*9;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT+PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 4;
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if(!sourceSlot.hasItem()){return ItemStack.EMPTY;}
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if(index<VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT, false)){
                return ItemStack.EMPTY;
            }
        }else if(index<TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT, false)){
                return ItemStack.EMPTY;
            }
        }else {
            System.out.println("Invalid slotIndex for MergerBlock: "+index);
            return ItemStack.EMPTY;
        }
        if(sourceStack.getCount()==0){
            sourceSlot.set(ItemStack.EMPTY);
        }else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.MERGER_BLOCK.get());
    }
}
