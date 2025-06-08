package net.geckspy.geckspymm.entity.lion;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class LionModel extends EntityModel<LionRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "tiger"), "main");


    private final ModelPart Body;
    private final ModelPart BackLeftLeg;
    private final ModelPart BackRightLeg;
    private final ModelPart FrontLeftLeg;
    private final ModelPart FrontRightLeg;
    private final ModelPart Head;
    private final ModelPart LeftEar;
    private final ModelPart RightEar;
    private final ModelPart Tail;

    public LionModel(ModelPart root) {
        super(root);
        this.Body = root.getChild("Body");
        this.BackLeftLeg = root.getChild("BackLeftLeg");
        this.BackRightLeg = root.getChild("BackRightLeg");
        this.FrontLeftLeg = root.getChild("FrontLeftLeg");
        this.FrontRightLeg = root.getChild("FrontRightLeg");
        this.Head = root.getChild("Head");
        this.LeftEar = this.Head.getChild("LeftEar");
        this.RightEar = this.Head.getChild("RightEar");
        this.Tail = root.getChild("Tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -24.0F, -12.0F, 12.0F, 8.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(-17.0F, -24.0F, -20.0F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
                .texOffs(0, 37).addBox(-15.0F, -26.0F, -23.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 31.0F, 10.0F));

        PartDefinition BackLeftLeg = partdefinition.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(16, 51).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 9.0F));

        PartDefinition BackRightLeg = partdefinition.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(32, 51).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 15.0F, 9.0F));

        PartDefinition FrontLeftLeg = partdefinition.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(50, 0).addBox(-2.0F, -1.0F, -3.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 14.0F, -7.0F));

        PartDefinition FrontRightLeg = partdefinition.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(0, 51).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 14.0F, -8.0F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(40, 21).addBox(-3.5F, -6.0F, -4.0F, 7.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(56, 33).addBox(-2.0F, -3.0F, -7.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 37).addBox(-7.0F, -9.0F, 1.1F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.0F, -14.0F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create().texOffs(50, 15).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -6.0F, -1.0F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create().texOffs(59, 15).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -6.0F, -1.0F));

        PartDefinition Tail = partdefinition.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(48, 51).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 40).addBox(-2.0F, 9.0F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 9.0F, 11.0F, 0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 70, 66)
                .apply(MeshTransformer.scaling(1.3f));
    }


    @Override
    public void setupAnim(LionRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.Head.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.Head.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(LionAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, LionAnimations.IDLE, state.ageInTicks, 1f );
    }
}
