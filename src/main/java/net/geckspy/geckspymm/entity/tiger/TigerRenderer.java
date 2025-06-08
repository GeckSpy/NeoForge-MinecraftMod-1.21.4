package net.geckspy.geckspymm.entity.tiger;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class TigerRenderer extends LivingEntityRenderer<TigerEntity, TigerRenderState, TigerModel> {
    private static final ResourceLocation TIGER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/tiger/tiger.png");


    public TigerRenderer(EntityRendererProvider.Context context) {
        super(context, new TigerModel(context.bakeLayer(TigerModel.LAYER_LOCATION)), 1.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(TigerRenderState renderState) {
        return TIGER_TEXTURE;
    }

    @Override
    public TigerRenderState createRenderState() {
        return new TigerRenderState();
    }

    @Override
    public void extractRenderState(TigerEntity entity, TigerRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }

    @Override
    protected boolean shouldShowName(TigerEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(TigerRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
