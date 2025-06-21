package net.geckspy.geckspymm.entity.spear;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ThrownSpearRenderer extends EntityRenderer<ThrownSpear, ThrownSpearRenderState> {
    public ThrownSpearRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ThrownSpearRenderState createRenderState() {
        return new ThrownSpearRenderState();
    }

    @Override
    public void render(ThrownSpearRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        ItemStack stack = new ItemStack(ModItems.IRON_SPEAR.get());
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.yRot - 90f)); // Yaw
        poseStack.mulPose(Axis.ZP.rotationDegrees(renderState.xRot));   // Pitch

        Minecraft.getInstance().getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.HEAD,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                //renderState.level(),
                null,
                0
        );

        poseStack.popPose();
        super.render(renderState, poseStack, bufferSource, packedLight);
    }


    @Override
    public void extractRenderState(ThrownSpear thrownSpear, ThrownSpearRenderState renderState, float partialTick) {
        super.extractRenderState(thrownSpear, renderState, partialTick);
        renderState.xRot = thrownSpear.getXRot();
        renderState.yRot = thrownSpear.getYRot();
        renderState.isFoil = thrownSpear.isFoil();
    }
}
