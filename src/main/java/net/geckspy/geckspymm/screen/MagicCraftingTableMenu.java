package net.geckspy.geckspymm.screen;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.recipe.ModRecipes;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class MagicCraftingTableMenu extends AbstractCraftingMenu {
    private static final int CRAFTING_GRID_WIDTH = 3;
    private static final int CRAFTING_GRID_HEIGHT = 3;
    public static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_COUNT = 9;
    private static final int CRAFT_SLOT_END = 10;
    private static final int INV_SLOT_START = 10;
    private static final int INV_SLOT_END = 37;
    private static final int USE_ROW_SLOT_START = 37;
    private static final int USE_ROW_SLOT_END = 46;
    private final ContainerLevelAccess access;
    private final Player player;
    private boolean placingRecipe;

    public MagicCraftingTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public MagicCraftingTableMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(MenuType.CRAFTING, containerId, 3, 3);
        this.access = access;
        this.player = playerInventory.player;
        this.addResultSlot(this.player, 124, 35);
        this.addCraftingGridSlots(30, 17);
        this.addStandardInventorySlots(playerInventory, 8, 84);
    }

    protected static void slotChangedCraftingGrid(
            AbstractContainerMenu menu,
            ServerLevel level,
            Player player,
            CraftingContainer craftSlots,
            ResultContainer resultSlots,
            @Nullable RecipeHolder<CraftingRecipe> recipe
    ) {
        // Then check magic recipes
        /*
        ServerPlayer serverplayer = (ServerPlayer)player;
        CraftingInput craftinginput = craftSlots.asCraftInput();
        ItemStack itemstack = ItemStack.EMPTY;

        RecipeType<MagicShapelessRecipe> recipeType = ModRecipes.MAGIC_SHAPELESS_TYPE.get();

        System.out.println("slotChanged");
        System.out.println(Arrays.stream(craftSlots.getItems().toArray(new ItemStack[0])).toList());

        Optional<RecipeHolder<MagicShapelessRecipe>> optional = level.getServer()
                .getRecipeManager()
                .getRecipeFor(recipeType, craftinginput, level);


        System.out.println("CraftingInput is empty: "+ craftinginput.isEmpty());
        RecipeManager recipeManager = level.getServer().getRecipeManager();
        System.out.println("recipeManager: "+recipeManager);

        Stream<RecipeHolder<MagicShapelessRecipe>> str = RecipeMap.EMPTY.byType(recipeType).stream();
        Stream<RecipeHolder<MagicShapelessRecipe>> flt = RecipeMap.EMPTY.byType(recipeType).stream().filter(p_380352_ -> p_380352_.value().matches(craftinginput, level));
        System.out.println(str.toList());
        System.out.println(flt.toList());



        System.out.println(optional);
        if (optional.isPresent()) {
            RecipeHolder<MagicShapelessRecipe> recipeholder = optional.get();
            MagicShapelessRecipe magicRecipe = recipeholder.value();
            if (resultSlots.setRecipeUsed(serverplayer, recipeholder)) {
                ItemStack itemstack1 = magicRecipe.assemble(craftinginput, level.registryAccess());
                if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                    itemstack = itemstack1;
                }
            }
        }

        resultSlots.setItem(0, itemstack);
        menu.setRemoteSlot(0, itemstack);
        serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));

         */

    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(Container inventory) {
        if (!this.placingRecipe) {
            this.access.execute((p_379187_, p_379188_) -> {
                if (p_379187_ instanceof ServerLevel serverlevel) {
                    slotChangedCraftingGrid(this, serverlevel, this.player, this.craftSlots, this.resultSlots, null);
                }
            });
        }
    }

    @Override
    public void beginPlacingRecipe() {
        this.placingRecipe = true;
    }

    @Override
    public void finishPlacingRecipe(ServerLevel p_380098_, RecipeHolder<CraftingRecipe> p_345915_) {
        this.placingRecipe = false;
        slotChangedCraftingGrid(this, p_380098_, this.player, this.craftSlots, this.resultSlots, p_345915_);
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((p_39371_, p_39372_) -> this.clearContainer(player, this.craftSlots));
    }

    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, ModBlocks.MAGIC_CRAFTING_TABLE.get());
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.access.execute((p_39378_, p_39379_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, player));
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (index == 0) {
                player.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    @Override
    public Slot getResultSlot() {
        return this.slots.get(0);
    }

    @Override
    public List<Slot> getInputGridSlots() {
        return this.slots.subList(1, 10);
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return null;
    }

    @Override
    protected Player owner() {
        return this.player;
    }
}
