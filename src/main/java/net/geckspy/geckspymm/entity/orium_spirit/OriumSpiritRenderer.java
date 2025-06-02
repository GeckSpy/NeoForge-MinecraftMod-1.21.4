package net.geckspy.geckspymm.entity.orium_spirit;

import com.mojang.blaze3d.vertex.PoseStack;
import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;

public class OriumSpiritRenderer extends LivingEntityRenderer<OriumSpiritEntity, OriumSpiritRenderState, OriumSpiritModel> {
    private static final ResourceLocation ORIUM_SPIRIT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "textures/entity/orium_spirit/orium_spirit.png");


    public OriumSpiritRenderer(EntityRendererProvider.Context context) {
        super(context, new OriumSpiritModel(context.bakeLayer(OriumSpiritModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(OriumSpiritRenderState renderState) {
        return ORIUM_SPIRIT_TEXTURE;
    }

    @Override
    public OriumSpiritRenderState createRenderState() {
        return new OriumSpiritRenderState();
    }

    @Override
    protected boolean shouldShowName(OriumSpiritEntity entity, double p_365448_) {
        if(entity.hasCustomName()){
            return super.shouldShowName(entity, p_365448_);
        }else{
            return false;
        }
    }

    @Override
    protected void scale(OriumSpiritRenderState renderState, PoseStack poseStack) {
        super.scale(renderState, poseStack);
        poseStack.scale(1.2f, 1.2f, 1.2f);
    }
}
