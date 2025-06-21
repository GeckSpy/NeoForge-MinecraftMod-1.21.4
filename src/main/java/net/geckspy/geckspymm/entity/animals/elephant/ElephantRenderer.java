package net.geckspy.geckspymm.entity.animals.elephant;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class ElephantRenderer extends LivingEntityRenderer<ElephantEntity, ElephantRenderState, ElephantModel> {
    private static final ResourceLocation ELEPHANT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/elephant/elephant.png");


    public ElephantRenderer(EntityRendererProvider.Context context) {
        super(context, new ElephantModel(context.bakeLayer(ElephantModel.LAYER_LOCATION)), 1.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(ElephantRenderState renderState) {
        return ELEPHANT_TEXTURE;
    }

    @Override
    public ElephantRenderState createRenderState() {
        return new ElephantRenderState();
    }

    @Override
    public void extractRenderState(ElephantEntity entity, ElephantRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
        state.eatAnimationState.copyFrom(entity.eatAnimationState);
    }

    @Override
    protected boolean shouldShowName(ElephantEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(ElephantRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
