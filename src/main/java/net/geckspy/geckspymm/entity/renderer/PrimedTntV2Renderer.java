package net.geckspy.geckspymm.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.entity.custom.PrimedTntV2;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.TntRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;

public class PrimedTntV2Renderer extends EntityRenderer<PrimedTntV2, TntRenderState> {
    private final BlockRenderDispatcher blockRenderer;

    public PrimedTntV2Renderer(EntityRendererProvider.Context context){
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public TntRenderState createRenderState() {
        return new TntRenderState();
    }

    @Override
    public void render(TntRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(-0.5D, 0.0D, -0.5D);
        renderState.fuseRemainingInTicks --;

        int fuse = (int)renderState.fuseRemainingInTicks;
        if (fuse / 32 % 2 == 0) { // Every other tick when fuse < 40
            float flashAlpha = Mth.clamp((float)fuse + renderState.partialTick, 0.0F, 1.0F);
            if (fuse < 10) {
                flashAlpha = fuse % 8 == 0 ? 0.0F : 1.0F; // Faster flash near explosion
            }
            blockRenderer.renderSingleBlock(ModBlocks.TNT_V2.get().defaultBlockState(), poseStack,
                    bufferSource, packedLight, OverlayTexture.pack(OverlayTexture.u(flashAlpha), OverlayTexture.v(false)));
        } else {
            // Normal render
            blockRenderer.renderSingleBlock(ModBlocks.TNT_V2.get().defaultBlockState(), poseStack,
                    bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
        }

        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }
}
