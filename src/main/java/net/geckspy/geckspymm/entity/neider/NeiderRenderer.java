package net.geckspy.geckspymm.entity.neider;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class NeiderRenderer extends LivingEntityRenderer<NeiderEntity, NeiderRenderState, NeiderModel> {
    private static final ResourceLocation NEIDER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/neider/neider.png");


    public NeiderRenderer(EntityRendererProvider.Context context) {
        super(context, new NeiderModel(context.bakeLayer(NeiderModel.LAYER_LOCATION)), 1.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(NeiderRenderState renderState) {
        return NEIDER_TEXTURE;
    }

    @Override
    public NeiderRenderState createRenderState() {
        return new NeiderRenderState();
    }

    @Override
    public void extractRenderState(NeiderEntity entity, NeiderRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
    }

    @Override
    protected boolean shouldShowName(NeiderEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(NeiderRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }

}
