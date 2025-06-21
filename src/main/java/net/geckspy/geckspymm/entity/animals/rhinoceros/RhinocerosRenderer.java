package net.geckspy.geckspymm.entity.animals.rhinoceros;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class RhinocerosRenderer extends LivingEntityRenderer<RhinocerosEntity, RhinocerosRenderState, RhinocerosModel> {
    private static final ResourceLocation RHINOCEROS_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/rhinoceros/rhinoceros.png");


    public RhinocerosRenderer(EntityRendererProvider.Context context) {
        super(context, new RhinocerosModel(context.bakeLayer(RhinocerosModel.LAYER_LOCATION)), 2.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(RhinocerosRenderState renderState) {
        return RHINOCEROS_TEXTURE;
    }

    @Override
    public RhinocerosRenderState createRenderState() {
        return new RhinocerosRenderState();
    }

    @Override
    public void extractRenderState(RhinocerosEntity entity, RhinocerosRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.attackAnimationState.copyFrom(entity.attackAnimationState);
    }

    @Override
    protected boolean shouldShowName(RhinocerosEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(RhinocerosRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
