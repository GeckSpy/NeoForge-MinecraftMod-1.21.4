package net.geckspy.geckspymm.entity.ent;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class EntModel extends EntityModel<EntRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "ent"), "main");

    private final ModelPart All;
    private final ModelPart body;
    private final ModelPart hat;
    private final ModelPart legs;
    private final ModelPart FrontBackFoot;
    private final ModelPart FrontRightFoot;
    private final ModelPart BackLeftFoot;
    private final ModelPart FrontLeftFoot;

    public EntModel(ModelPart root) {
        super(root);
        this.All = root.getChild("All");
        this.body = this.All.getChild("body");
        this.hat = this.body.getChild("hat");
        this.legs = this.All.getChild("legs");
        this.FrontBackFoot = this.legs.getChild("FrontBackFoot");
        this.FrontRightFoot = this.legs.getChild("FrontRightFoot");
        this.BackLeftFoot = this.legs.getChild("BackLeftFoot");
        this.FrontLeftFoot = this.legs.getChild("FrontLeftFoot");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition All = partdefinition.addOrReplaceChild("All", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = All.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 30).addBox(-8.0F, -32.0F, -8.0F, 16.0F, 30.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition hat = body.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -3.0F, -12.0F, 24.0F, 6.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(0, 76).addBox(-8.0F, -9.0F, -8.0F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -35.0F, 0.0F));

        PartDefinition legs = All.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition FrontBackFoot = legs.addOrReplaceChild("FrontBackFoot", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -8.0F, 7.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r1 = FrontBackFoot.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 30).addBox(-7.0F, -3.0F, -1.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition FrontRightFoot = legs.addOrReplaceChild("FrontRightFoot", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.0F, -8.0F, -7.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r2 = FrontRightFoot.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 30).addBox(-7.0F, -3.0F, -7.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition BackLeftFoot = legs.addOrReplaceChild("BackLeftFoot", CubeListBuilder.create().texOffs(64, 30).addBox(-2.0F, -2.0F, -2.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -8.0F, 7.0F));

        PartDefinition FrontLeftFoot = legs.addOrReplaceChild("FrontLeftFoot", CubeListBuilder.create(), PartPose.offsetAndRotation(7.0F, -8.0F, -7.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = FrontLeftFoot.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 30).addBox(-1.0F, -3.0F, -7.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 96, 98);
    }

    @Override
    public void setupAnim(EntRenderState state) {
        //super.setupAnim(state);
        this.root().getAllParts().forEach(ModelPart::resetPose);
        //qthis.All.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.All.yRot = state.yRot * (float) (Math.PI / 180.0);

        animateWalk(EntAnimations.WALK, state.walkAnimationPos, state.walkAnimationSpeed, 2f, 2.5f);
        this.animate(state.idleAnimationState, EntAnimations.IDLE, state.ageInTicks, 1f );
        this.animate(state.attackAnimationState, EntAnimations.ATTACK, state.ageInTicks, 1f );
        this.animate(state.hideAnimationState, EntAnimations.HIDE, state.ageInTicks, 1f );
        this.animate(state.unhideAnimationState, EntAnimations.UNHIDE, state.ageInTicks, 1f );
    }



}
