package net.geckspy.geckspymm.entity.neider;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class NeiderModel extends EntityModel<NeiderRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "neider"), "main");

    private final ModelPart All;
    private final ModelPart Head;
    private final ModelPart Body;
    private final ModelPart LeftLeg0;
    private final ModelPart Bottom0;
    private final ModelPart LeftLeg1;
    private final ModelPart Bottom1;
    private final ModelPart LeftLeg2;
    private final ModelPart Bottom2;
    private final ModelPart RightLeg0;
    private final ModelPart Bottom3;
    private final ModelPart RightLeg1;
    private final ModelPart Bottom4;
    private final ModelPart RightLeg2;
    private final ModelPart Bottom5;
    private final ModelPart Tail;
    private final ModelPart T1;
    private final ModelPart T2;
    private final ModelPart T3;

    public NeiderModel(ModelPart root) {
        super(root);
        this.All = root.getChild("All");
        this.Head = this.All.getChild("Head");
        this.Body = this.All.getChild("Body");
        this.LeftLeg0 = this.All.getChild("LeftLeg0");
        this.Bottom0 = this.LeftLeg0.getChild("Bottom0");
        this.LeftLeg1 = this.All.getChild("LeftLeg1");
        this.Bottom1 = this.LeftLeg1.getChild("Bottom1");
        this.LeftLeg2 = this.All.getChild("LeftLeg2");
        this.Bottom2 = this.LeftLeg2.getChild("Bottom2");
        this.RightLeg0 = this.All.getChild("RightLeg0");
        this.Bottom3 = this.RightLeg0.getChild("Bottom3");
        this.RightLeg1 = this.All.getChild("RightLeg1");
        this.Bottom4 = this.RightLeg1.getChild("Bottom4");
        this.RightLeg2 = this.All.getChild("RightLeg2");
        this.Bottom5 = this.RightLeg2.getChild("Bottom5");
        this.Tail = this.All.getChild("Tail");
        this.T1 = this.Tail.getChild("T1");
        this.T2 = this.T1.getChild("T2");
        this.T3 = this.T2.getChild("T3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition All = partdefinition.addOrReplaceChild("All", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, -3.0F));

        PartDefinition Head = All.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-2.2F))
                .texOffs(32, 17).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-2.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));

        PartDefinition Body = All.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -6.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 3.0F));

        PartDefinition LeftLeg0 = All.addOrReplaceChild("LeftLeg0", CubeListBuilder.create().texOffs(32, 8).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -2.0F, -0.5713F, 0.5724F, -0.8706F));

        PartDefinition Bottom0 = LeftLeg0.addOrReplaceChild("Bottom0", CubeListBuilder.create(), PartPose.offset(-4.0F, 6.0F, 5.0F));

        PartDefinition bottom_r1 = Bottom0.addOrReplaceChild("bottom_r1", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.0F, -6.0F, -5.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition LeftLeg1 = All.addOrReplaceChild("LeftLeg1", CubeListBuilder.create().texOffs(32, 8).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 3.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition Bottom1 = LeftLeg1.addOrReplaceChild("Bottom1", CubeListBuilder.create(), PartPose.offset(-4.0F, 6.0F, 5.0F));

        PartDefinition bottom_r2 = Bottom1.addOrReplaceChild("bottom_r2", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.0F, -6.0F, -5.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition LeftLeg2 = All.addOrReplaceChild("LeftLeg2", CubeListBuilder.create().texOffs(32, 8).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 8.0F, 0.5713F, -0.5724F, -0.8706F));

        PartDefinition Bottom2 = LeftLeg2.addOrReplaceChild("Bottom2", CubeListBuilder.create(), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition bottom_r3 = Bottom2.addOrReplaceChild("bottom_r3", CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition RightLeg0 = All.addOrReplaceChild("RightLeg0", CubeListBuilder.create().texOffs(48, 8).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -2.0F, -0.5713F, -0.5724F, 0.8706F));

        PartDefinition Bottom3 = RightLeg0.addOrReplaceChild("Bottom3", CubeListBuilder.create(), PartPose.offset(4.0F, 5.0F, 5.0F));

        PartDefinition bottom_r4 = Bottom3.addOrReplaceChild("bottom_r4", CubeListBuilder.create().texOffs(36, 4).addBox(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-10.0F, -5.0F, -5.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition RightLeg1 = All.addOrReplaceChild("RightLeg1", CubeListBuilder.create().texOffs(48, 8).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 3.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition Bottom4 = RightLeg1.addOrReplaceChild("Bottom4", CubeListBuilder.create(), PartPose.offset(4.0F, 5.0F, 5.0F));

        PartDefinition bottom_r5 = Bottom4.addOrReplaceChild("bottom_r5", CubeListBuilder.create().texOffs(36, 4).addBox(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-10.0F, -5.0F, -5.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition RightLeg2 = All.addOrReplaceChild("RightLeg2", CubeListBuilder.create().texOffs(48, 8).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 8.0F, 0.5713F, 0.5724F, 0.8706F));

        PartDefinition Bottom5 = RightLeg2.addOrReplaceChild("Bottom5", CubeListBuilder.create(), PartPose.offset(-6.0F, 0.0F, 0.0F));

        PartDefinition bottom_r6 = Bottom5.addOrReplaceChild("bottom_r6", CubeListBuilder.create().texOffs(36, 4).addBox(-9.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition Tail = All.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(44, 35).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition T1 = Tail.addOrReplaceChild("T1", CubeListBuilder.create().texOffs(24, 35).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.0F, 1.0472F, 0.0F, 0.0F));

        PartDefinition T2 = T1.addOrReplaceChild("T2", CubeListBuilder.create().texOffs(0, 33).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 1.0472F, 0.0F, 0.0F));

        PartDefinition T3 = T2.addOrReplaceChild("T3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 10.0F));

        PartDefinition cube_r1 = T3.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 25).addBox(0.0F, -6.0F, -2.0F, 0.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 45);
    }

    @Override
    public void setupAnim(NeiderRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.Head.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.Head.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(NeiderAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, NeiderAnimations.IDLE, state.ageInTicks, 1f );
        this.animate(state.attackAnimationState, NeiderAnimations.GRAB, state.ageInTicks, 1f );
    }



}
