package net.geckspy.geckspymm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class MergerBlockScreen extends AbstractContainerScreen<MergerBlockMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/gui/container/merger_block.png");
    private static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/gui/container/merger_block_arrow.png");

    public MergerBlockScreen(MergerBlockMenu menu, Inventory inventory, Component title){
        super(menu, inventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                RenderType::guiTextured,
                GUI_TEXTURE,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256
        );
        int x = (width -imageWidth)/2;
        int y = (height- imageHeight)/2;
        renderProgressArrow(guiGraphics, x, y);
    }

    public static final int ARROW_TEXTURE_WIDTH = 68;
    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y){
        if(menu.isCrafting()){
            guiGraphics.blit(RenderType::guiTextured, ARROW_TEXTURE,
                    x+49, y+24, 0, 0, menu.getScaledArrowProgress(),
                    38, ARROW_TEXTURE_WIDTH, 38);
        }
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
