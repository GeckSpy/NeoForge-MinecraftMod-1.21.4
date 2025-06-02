package net.geckspy.geckspymm.entity.rhinoceros;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class RhinocerosModel extends EntityModel<RhinocerosRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "rhinoceros"), "main");

    private final ModelPart Body;
    private final ModelPart BackLeftLeg;
    private final ModelPart BackRightLeg;
    private final ModelPart FrontLeftLeg;
    private final ModelPart FrontRightLeg;
    private final ModelPart Head;
    private final ModelPart LeftEar;
    private final ModelPart RightEar;
    private final ModelPart Tail;

    public RhinocerosModel(ModelPart root) {
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

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-20.0F, -29.0F, -24.0F, 18.0F, 13.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 31.0F, 9.0F));

        PartDefinition BackLeftLeg = partdefinition.addOrReplaceChild("BackLeftLeg", CubeListBuilder.create().texOffs(48, 43).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 15.0F, 12.0F));

        PartDefinition BackRightLeg = partdefinition.addOrReplaceChild("BackRightLeg", CubeListBuilder.create().texOffs(72, 43).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 15.0F, 12.0F));

        PartDefinition FrontLeftLeg = partdefinition.addOrReplaceChild("FrontLeftLeg", CubeListBuilder.create().texOffs(0, 43).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 15.0F, -12.0F));

        PartDefinition FrontRightLeg = partdefinition.addOrReplaceChild("FrontRightLeg", CubeListBuilder.create().texOffs(24, 43).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 15.0F, -12.0F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(66, 8).addBox(-5.0F, -7.0F, -13.0F, 10.0F, 8.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, -11).addBox(0.0F, -14.0F, -16.0F, 0.0F, 9.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -14.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offset(4.0F, -6.0F, -1.0F));

        PartDefinition leftear_r1 = LeftEar.addOrReplaceChild("leftear_r1", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -7.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -3.0F, 0.3121F, -1.1554F, -0.1052F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offset(-4.0F, -6.0F, -1.0F));

        PartDefinition rightear_r1 = RightEar.addOrReplaceChild("rightear_r1", CubeListBuilder.create().texOffs(10, 9).addBox(-5.0F, -7.0F, 0.0F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, -3.0F, 0.3121F, 1.1554F, 0.1052F));

        PartDefinition Tail = partdefinition.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(22, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 14).addBox(-2.0F, 10.0F, 0.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 16.0F));

        return LayerDefinition.create(meshdefinition, 114, 58)
                .apply(MeshTransformer.scaling(1.7f));
    }


    @Override
    public void setupAnim(RhinocerosRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.Head.xRot = state.xRot * (float) (Math.PI / 180.0) + (float)(Math.PI * 30 / 180);
        this.Head.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(RhinocerosAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, RhinocerosAnimations.IDLE, state.ageInTicks, 1f );
        this.animate(state.attackAnimationState, RhinocerosAnimations.ATTACK, state.ageInTicks, 1f );
    }
}
