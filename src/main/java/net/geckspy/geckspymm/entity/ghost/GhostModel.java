package net.geckspy.geckspymm.entity.ghost;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class GhostModel extends EntityModel<GhostRenderState>  {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "ghost"), "main");

    private final ModelPart all;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public GhostModel(ModelPart root) {
        super(root);
        this.all = root.getChild("all");
        this.rightArm = this.all.getChild("rightArm");
        this.leftArm = this.all.getChild("leftArm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("all", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition rightArm = all.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(0, 26).addBox(-2.0F, -2.0F, -2.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -1.0F, 0.0F));

        PartDefinition leftArm = all.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(12, 26).addBox(0.0F, -2.0F, -2.0F, 2.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 28, 42).apply(MeshTransformer.scaling(1.5f));
    }

    @Override
    public void setupAnim(GhostRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.all.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.all.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(GhostAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, GhostAnimations.IDLE, state.ageInTicks, 1f );
        this.animate(state.angryAnimationState, GhostAnimations.ANGRY, state.ageInTicks, 1f);
    }



}
