package net.geckspy.geckspymm.entity.penguin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class PenguinRenderer extends LivingEntityRenderer<PenguinEntity, PenguinRenderState, PenguinModel> {
    private static final ResourceLocation PENGUIN_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/penguin/penguin.png");


    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel(context.bakeLayer(PenguinModel.LAYER_LOCATION)), 0.55f);
    }

    @Override
    public ResourceLocation getTextureLocation(PenguinRenderState renderState) {
        return PENGUIN_TEXTURE;
    }

    @Override
    public PenguinRenderState createRenderState() {
        return new PenguinRenderState();
    }

    @Override
    public void extractRenderState(PenguinEntity entity, PenguinRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.isSwimming = entity.isSwimming() || entity.isInWater();
    }

    @Override
    protected boolean shouldShowName(PenguinEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(PenguinRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
