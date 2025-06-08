package net.geckspy.geckspymm.entity.lion;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.entity.tiger.TigerModel;
import net.geckspy.geckspymm.entity.tiger.TigerRenderState;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class LionRenderer extends LivingEntityRenderer<LionEntity, LionRenderState, LionModel> {
    private static final Map<LionEntityVariant, ResourceLocation> LOCATION_MAP =
            Util.make(Maps.newEnumMap(LionEntityVariant.class), map->{
                map.put(LionEntityVariant.FEMALE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/lion/lionne.png"));
                map.put(LionEntityVariant.MALE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/lion/lion.png"));
            });



    public LionRenderer(EntityRendererProvider.Context context) {
        super(context, new LionModel(context.bakeLayer(TigerModel.LAYER_LOCATION)), 1.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(LionRenderState renderState) {
        return LOCATION_MAP.get(renderState.variant);
    }

    @Override
    public LionRenderState createRenderState() {
        return new LionRenderState();
    }

    @Override
    public void extractRenderState(LionEntity entity, LionRenderState state, float p_361157_) {
        super.extractRenderState(entity, state, p_361157_);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.variant = entity.getVariant();
    }

    @Override
    protected boolean shouldShowName(LionEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    public void render(LionRenderState state, PoseStack poseStack, MultiBufferSource p_115312_, int p_115313_) {
        if(state.isBaby){
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(state, poseStack, p_115312_, p_115313_);
    }
}
