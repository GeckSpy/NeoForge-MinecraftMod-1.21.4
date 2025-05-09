package net.geckspy.geckspymm.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class MagicCraftingTableMenu extends AbstractContainerMenu {
    private final Player player;
    private final TransientCraftingContainer craftSlots;
    private final ResultContainer resultSlots = new ResultContainer();


    public MagicCraftingTableMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData){
        this(containerId, inventory);
    }


    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_ROW_COUNT*PLAYER_INVENTORY_COLUMN_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    // Only thing to change:
    private static final int TE_INVENTORY_SLOT_COUNT = 9;

    public MagicCraftingTableMenu(int containerId, Inventory playerInventory) {
        super(ModMenuTypes.MAGIC_CRAFTING_TABLE_MENU.get(), containerId);
        this.player = playerInventory.player;
        this.craftSlots = new TransientCraftingContainer(this, 3, 3);

        // Add player inventory
        for(int i=0;i<3; i++){for(int j=0;j<9;j++){
            this.addSlot(new Slot(playerInventory, j+i*9+9, 8+j*18, 84+i*18));
        }}
        // Add player Hotbar
        for(int i=0;i<9;i++){
            this.addSlot(new Slot(playerInventory, i, 8+i*18, 142));
        }

        // Add result slot
        this.addSlot(new ResultSlot(playerInventory.player, craftSlots, resultSlots,
                TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT,
                124, 35));

        // Crafting grid (3x3)
        for(int row = 0; row < 3; ++row) {for(int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(craftSlots, col + row * 3, 30 + col * 18, 17 + row * 18));
        }}

    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // For shift+click
        Slot sourceSlot = slots.get(index);
        if(sourceSlot==null || !sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if(index<VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT,
                    false)){
                return ItemStack.EMPTY;
            }
        }else if(index<TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT){
            if(!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX+VANILLA_SLOT_COUNT,
                    false)){
                return  ItemStack.EMPTY;
            }
        }else{
            System.out.println("Invalid slotIndex: "+index);
            return ItemStack.EMPTY;
        }
        if(sourceStack.getCount()==0){sourceSlot.set(ItemStack.EMPTY);}
        else{sourceSlot.setChanged();}
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }


    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        // Implement custom recipe checking here
        
        this.resultSlots.setItem(TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT, ItemStack.EMPTY);
    }

    private void updateResultSlot() {
        /*
        Optional<CustomRecipe> optional = this.player.level()
                .getRecipeManager()
                .getRecipeFor(ModRecipeTypes.MAGIC_CRAFTING.get(), this.craftSlots, this.player.level());

        if (optional.isPresent()) {
            this.resultSlots.setItem(0, optional.get().assemble(this.craftSlots, this.player.level().registryAccess()));
        } else {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        }
        */

        this.resultSlots.setItem(0, ItemStack.EMPTY);
    }

}
