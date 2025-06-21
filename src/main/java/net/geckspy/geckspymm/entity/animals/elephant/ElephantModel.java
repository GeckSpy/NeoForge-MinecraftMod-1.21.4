package net.geckspy.geckspymm.entity.animals.elephant;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class ElephantModel extends EntityModel<ElephantRenderState>  {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "elephant"), "main");

    private final ModelPart Body;
    private final ModelPart BackLeftLeg;
    private final ModelPart BackRightLeg;
    private final ModelPart FrontLeftLeg;
    private final ModelPart FrontRightLeg;
    private final ModelPart Head;
    private final ModelPart LeftEar;
    private final ModelPart RightEar;
    private final ModelPart Trunk;
    private final ModelPart TrunkBottom;
    private final ModelPart Tail;

    public ElephantModel(ModelPart root) {
        super(root);
        this.Body = root.getChild("Body");
        this.BackLeftLeg = root.getChild("BackLeftLeg");
        this.BackRightLeg = root.getChild("BackRightLeg");
        this.FrontLeftLeg = root.getChild("FrontLeftLeg");
        this.FrontRightLeg = root.getChild("FrontRightLeg");
        this.Head = root.getChild("Head");
        this.LeftEar = this.Head.getChild("LeftEar");
        this.RightEar = this.Head.getChild("RightEar");
        this.Trunk = this.Head.getChild("Trunk");
        this.TrunkBottom = this.Trunk.getChild("TrunkBottom");
        this.Tail = root.getChild("Tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-23.0F, -36.0F, -25.0F, 24.0F, 20.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 24.0F, 9.0F));

        PartDefinition BackLeftLeg = partdefinition.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 8.0F, 12.0F));

        PartDefinition BackRightLeg = partdefinition.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(32, 52).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 8.0F, 12.0F));

        PartDefinition FrontLeftLeg = partdefinition.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(0, 52).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 8.0F, -12.0F));

        PartDefinition FrontRightLeg = partdefinition.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(32, 52).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 8.0F, -12.0F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(80, 8).addBox(-7.0F, -8.0F, -9.0F, 14.0F, 15.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -16.0F));

        PartDefinition righttusk_r1 = Head.addOrReplaceChild("righttusk_r1", CubeListBuilder.create().texOffs(91, 42).mirror().addBox(1.0F, -2.0F, -9.0F, 0.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 5.0F, -8.0F, -0.2618F, 0.1745F, 0.0F));

        PartDefinition lefttusk_r1 = Head.addOrReplaceChild("lefttusk_r1", CubeListBuilder.create().texOffs(91, 42).addBox(-1.0F, -2.0F, -9.0F, 0.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 5.0F, -8.0F, -0.2618F, -0.1745F, 0.0F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offset(7.0F, 6.0F, -5.0F));

        PartDefinition leftear_r1 = LeftEar.addOrReplaceChild("leftear_r1", CubeListBuilder.create().texOffs(82, 52).addBox(0.0F, -15.0F, -1.0F, 9.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 1.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offset(-7.0F, 6.0F, -5.0F));

        PartDefinition rightear_r1 = RightEar.addOrReplaceChild("rightear_r1", CubeListBuilder.create().texOffs(64, 52).addBox(-9.0F, -15.0F, -1.0F, 9.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 1.0F, 0.0F, 0.6981F, 0.0F));

        PartDefinition Trunk = Head.addOrReplaceChild("Trunk", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -7.0F));

        PartDefinition TrunkBottom = Trunk.addOrReplaceChild("TrunkBottom", CubeListBuilder.create(), PartPose.offset(0.0F, 11.0F, -1.0F));

        PartDefinition trunkbottom_r1 = TrunkBottom.addOrReplaceChild("trunkbottom_r1", CubeListBuilder.create().texOffs(16, 0).addBox(-3.0F, -2.0F, -1.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, -1.0F, 0.6981F, 0.0F, 0.0F));

        PartDefinition Tail = partdefinition.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(118, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(100, 66).addBox(-2.0F, 10.0F, 1.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 16.0F));

        return LayerDefinition.create(meshdefinition, 126, 76).apply(MeshTransformer.scaling(1.7f));
    }

    @Override
    public void setupAnim(ElephantRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.Head.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.Head.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(ElephantAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, ElephantAnimations.IDLE, state.ageInTicks, 1f );
        this.animate(state.attackAnimationState, ElephantAnimations.ATTACK, state.ageInTicks, 1f );
        this.animate(state.eatAnimationState, ElephantAnimations.EAT, state.ageInTicks, 1f);
    }



}
