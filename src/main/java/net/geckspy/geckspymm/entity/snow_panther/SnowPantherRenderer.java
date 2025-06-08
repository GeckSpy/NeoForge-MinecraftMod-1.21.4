package net.geckspy.geckspymm.entity.snow_panther;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class SnowPantherRenderer extends LivingEntityRenderer<SnowPantherEntity, SnowPantherRenderState, SnowPantherModel> {
    private static final ResourceLocation SNOW_PANTHER_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/panther/snow_panther.png");


    public SnowPantherRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowPantherModel(context.bakeLayer(SnowPantherModel.LAYER_LOCATION)), 1.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(SnowPantherRenderState renderState) {
        return SNOW_PANTHER_TEXTURE;
    }

    @Override
    public SnowPantherRenderState createRenderState() {
        return new SnowPantherRenderState();
    }

    @Override
    public void extractRenderState(SnowPantherEntity entity, SnowPantherRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }

    @Override
    protected boolean shouldShowName(SnowPantherEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(SnowPantherRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
