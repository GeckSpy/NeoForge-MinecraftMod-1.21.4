package net.geckspy.geckspymm.entity.ent;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class EntRenderer extends LivingEntityRenderer<EntEntity, EntRenderState, EntModel> {
    private static final ResourceLocation ENT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/ent/ent.png");


    public EntRenderer(EntityRendererProvider.Context context) {
        super(context, new EntModel(context.bakeLayer(EntModel.LAYER_LOCATION)), 1.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntRenderState renderState) {
        return ENT_TEXTURE;
    }

    @Override
    public EntRenderState createRenderState() {
        return new EntRenderState();
    }

    @Override
    public void extractRenderState(EntEntity entity, EntRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.hideAnimationState.copyFrom(entity.hideAnimationState);
        state.unhideAnimationState.copyFrom(entity.unhideAnimationState);
    }

    @Override
    protected boolean shouldShowName(EntEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(EntRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }

}
