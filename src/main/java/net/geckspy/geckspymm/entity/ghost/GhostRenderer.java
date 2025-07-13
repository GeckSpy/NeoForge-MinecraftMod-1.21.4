package net.geckspy.geckspymm.entity.ghost;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class GhostRenderer extends LivingEntityRenderer<GhostEntity, GhostRenderState, GhostModel> {
    private static final ResourceLocation GHOST_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/ghost/ghost.png");
    private static final ResourceLocation GHOST_INVISIBLE_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/ghost/ghost_invisible.png");


    public GhostRenderer(EntityRendererProvider.Context context) {
        super(context, new GhostModel(context.bakeLayer(GhostModel.LAYER_LOCATION)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(GhostRenderState renderState) {
        return GHOST_TEXTURE;
    }
    public ResourceLocation getInvisibleTextureLocation() {
        return GHOST_INVISIBLE_TEXTURE;
    }

    @Override
    public GhostRenderState createRenderState() {
        return new GhostRenderState();
    }

    @Override
    public void extractRenderState(GhostEntity entity, GhostRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.angryAnimationState.copyFrom(entity.angryAnimationState);
    }

    @Override
    protected boolean shouldShowName(GhostEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(GhostRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }

    @Override
    protected @Nullable RenderType getRenderType(GhostRenderState renderState, boolean isVisible, boolean renderTranslucent, boolean appearsGlowing) {
        if(!isVisible){
            return RenderType.entityTranslucent(getInvisibleTextureLocation());
        }
        return super.getRenderType(renderState, isVisible, renderTranslucent, appearsGlowing);
    }
}
