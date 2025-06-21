package net.geckspy.geckspymm.entity.animals.rhinoceros;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class RhinocerosRenderState extends LivingEntityRenderState {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
}
