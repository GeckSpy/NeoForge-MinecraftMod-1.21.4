package net.geckspy.geckspymm.entity.giraffe;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class GiraffeRenderer extends LivingEntityRenderer<GiraffeEntity, GiraffeRenderState, GiraffeModel> {
    private static final ResourceLocation GIRAFFE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/giraffe/giraffe.png");


    public GiraffeRenderer(EntityRendererProvider.Context context) {
        super(context, new GiraffeModel(context.bakeLayer(GiraffeModel.LAYER_LOCATION)), 1.8f);
    }

    @Override
    public ResourceLocation getTextureLocation(GiraffeRenderState renderState) {
        return GIRAFFE_TEXTURE;
    }

    @Override
    public GiraffeRenderState createRenderState() {
        return new GiraffeRenderState();
    }

    @Override
    public void extractRenderState(GiraffeEntity entity, GiraffeRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }

    @Override
    protected boolean shouldShowName(GiraffeEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(GiraffeRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
