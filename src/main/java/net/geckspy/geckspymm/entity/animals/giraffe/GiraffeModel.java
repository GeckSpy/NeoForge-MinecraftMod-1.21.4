package net.geckspy.geckspymm.entity.animals.giraffe;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class GiraffeModel extends EntityModel<GiraffeRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "giraffe"), "main");

    private final ModelPart Body;
    private final ModelPart BackLeftLeg;
    private final ModelPart BackRightLeg;
    private final ModelPart FrontLeftLeg;
    private final ModelPart FrontRightLeg;
    private final ModelPart Tail;
    private final ModelPart NeckAndHead;
    private final ModelPart Head;
    private final ModelPart LeftEar;
    private final ModelPart RightEar;

    public GiraffeModel(ModelPart root) {
        super(root);
        this.Body = root.getChild("Body");
        this.BackLeftLeg = root.getChild("BackLeftLeg");
        this.BackRightLeg = root.getChild("BackRightLeg");
        this.FrontLeftLeg = root.getChild("FrontLeftLeg");
        this.FrontRightLeg = root.getChild("FrontRightLeg");
        this.Tail = root.getChild("Tail");
        this.NeckAndHead = root.getChild("NeckAndHead");
        this.Head = this.NeckAndHead.getChild("Head");
        this.LeftEar = this.Head.getChild("LeftEar");
        this.RightEar = this.Head.getChild("RightEar");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 25).addBox(-18.0F, -38.0F, -22.0F, 14.0F, 13.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-18.0F, -35.0F, -13.0F, 14.0F, 10.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 29.0F, 10.0F));

        PartDefinition BackLeftLeg = partdefinition.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(26, 47).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 4.0F, 11.0F));

        PartDefinition BackRightLeg = partdefinition.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(58, 0).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 4.0F, 11.0F));

        PartDefinition FrontLeftLeg = partdefinition.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(38, 53).addBox(-2.0F, 0.0F, -1.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 4.0F, -11.0F));

        PartDefinition FrontRightLeg = partdefinition.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(50, 53).addBox(-1.0F, 0.0F, -1.0F, 3.0F, 20.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 4.0F, -11.0F));

        PartDefinition Tail = partdefinition.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(18, 60).addBox(-1.0F, 0.1136F, 0.0727F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, 11.1136F, 1.0727F, 4.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 12.0F));

        PartDefinition NeckAndHead = partdefinition.addOrReplaceChild("NeckAndHead", CubeListBuilder.create().texOffs(46, 25).addBox(-2.0F, -22.0F, -2.0F, 4.0F, 24.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -12.0F));

        PartDefinition Head = NeckAndHead.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 47).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 60).addBox(-2.0F, -3.0F, -11.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -23.0F, 1.0F));

        PartDefinition righthorn_r1 = Head.addOrReplaceChild("righthorn_r1", CubeListBuilder.create().texOffs(62, 37).addBox(0.2F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(62, 33).addBox(2.8F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -5.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offset(4.0F, -6.0F, -1.0F));

        PartDefinition leftear_r1 = LeftEar.addOrReplaceChild("leftear_r1", CubeListBuilder.create().texOffs(62, 23).addBox(1.0F, -3.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0808F, -0.4293F, -0.1922F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offset(-4.0F, -6.0F, -1.0F));

        PartDefinition rightear_r1 = RightEar.addOrReplaceChild("rightear_r1", CubeListBuilder.create().texOffs(62, 28).addBox(-7.0F, -3.0F, 0.0F, 6.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 0.0F, 0.0808F, 0.4293F, 0.1922F));

        return LayerDefinition.create(meshdefinition, 70, 76)
                .apply(MeshTransformer.scaling(1.7f));
    }


    @Override
    public void setupAnim(GiraffeRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.Head.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.Head.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(GiraffeAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, GiraffeAnimations.IDLE, state.ageInTicks, 1f );
    }
}
