package net.geckspy.geckspymm.entity.orium_spirit;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class OriumSpiritModel extends EntityModel<OriumSpiritRenderState>  {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "orium_spirit"), "main");

    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart backpack;
    private final ModelPart base;
    private final ModelPart leftarm;
    private final ModelPart rightarm;

    public OriumSpiritModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.backpack = root.getChild("backpack");
        this.base = root.getChild("base");
        this.leftarm = root.getChild("leftarm");
        this.rightarm = root.getChild("rightarm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 20.0F, 0.5F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 21.5F, 0.5F));

        PartDefinition backpack = partdefinition.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(12, 10).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 20.0F, 2.0F));

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(4, 16).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 23.5F, 0.5F));

        PartDefinition leftarm = partdefinition.addOrReplaceChild("leftarm", CubeListBuilder.create(), PartPose.offset(-2.0F, 20.0F, 1.0F));

        PartDefinition leftarm_r1 = leftarm.addOrReplaceChild("leftarm_r1", CubeListBuilder.create().texOffs(12, 13).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition rightarm = partdefinition.addOrReplaceChild("rightarm", CubeListBuilder.create(), PartPose.offset(1.0F, 20.0F, 1.0F));

        PartDefinition rightarm_r1 = rightarm.addOrReplaceChild("rightarm_r1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 3.1416F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 20, 20);
    }

    @Override
    public void setupAnim(OriumSpiritRenderState state) {
        super.setupAnim(state);
        //this.root().getAllParts().forEach(ModelPart::resetPose);
        this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
        this.head.yRot = state.yRot * (float) (Math.PI / 180.0);

        this.animateWalk(OriumSpiritAnimations.FLYING, state.walkAnimationPos, state.walkAnimationSpeed, 1f, 1f);
        this.animate(state.animationState, OriumSpiritAnimations.IDLE, state.ageInTicks, 1f );
    }



}
