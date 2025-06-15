package net.geckspy.geckspymm.entity.penguin;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class PenguinModel extends EntityModel<PenguinRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "penguin"), "main");

    private final ModelPart All;
    private final ModelPart Head;
    private final ModelPart LeftLeg;
    private final ModelPart RightLeg;
    private final ModelPart Tail;
    private final ModelPart LeftWing;
    private final ModelPart RightWing;

    public PenguinModel(ModelPart root) {
        super(root);
        this.All = root.getChild("All");
        this.Head = this.All.getChild("Head");
        this.LeftLeg = this.All.getChild("LeftLeg");
        this.RightLeg = this.All.getChild("RightLeg");
        this.Tail = this.All.getChild("Tail");
        this.LeftWing = this.All.getChild("LeftWing");
        this.RightWing = this.All.getChild("RightWing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition All = partdefinition.addOrReplaceChild("All", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -10.0F, -3.0F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, -1.0F));

        PartDefinition Head = All.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 17).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(22, 18).addBox(-1.0F, -0.5F, -5.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -2.0F));

        PartDefinition LeftLeg = All.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(10, 25).addBox(-0.5F, 0.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 21).addBox(-1.0F, 1.0F, -4.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.0F, 0.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition RightLeg = All.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, 0.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 17).addBox(-2.0F, 1.0F, -4.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition Tail = All.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(22, 21).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 3.0F));

        PartDefinition LeftWing = All.addOrReplaceChild("LeftWing", CubeListBuilder.create(), PartPose.offset(3.0F, -9.0F, 1.0F));

        PartDefinition leftwing_r1 = LeftWing.addOrReplaceChild("leftwing_r1", CubeListBuilder.create().texOffs(24, 5).addBox(0.0F, 0.0F, -3.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition RightWing = All.addOrReplaceChild("RightWing", CubeListBuilder.create().texOffs(24, -4).addBox(0.0F, 0.0F, -3.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -9.0F, 1.0F, 0.0F, 0.0F, 0.0873F));

        return LayerDefinition.create(meshdefinition, 32, 29)
                .apply(MeshTransformer.scaling(1.2f));
    }


    @Override
    public void setupAnim(PenguinRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if(state.isSwimming){
            this.Head.xRot = -90 * (float) (Math.PI / 180.0);

            this.All.xRot = (state.xRot +90) * (float) (Math.PI / 180.0) ;
            this.All.yRot = state.yRot * (float) (Math.PI / 180.0);
            animateWalk(PenguinAnimations.SWIM, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
            this.animate(state.idleAnimationState, PenguinAnimations.SWIM, state.ageInTicks, 1f );
        }else{
            this.Head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.Head.yRot = state.yRot * (float) (Math.PI / 180.0);
            animateWalk(PenguinAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
            this.animate(state.idleAnimationState, PenguinAnimations.IDLE, state.ageInTicks, 1f );
        }
    }
}
