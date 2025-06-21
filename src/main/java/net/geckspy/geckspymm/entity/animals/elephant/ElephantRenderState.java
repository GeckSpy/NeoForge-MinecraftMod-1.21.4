package net.geckspy.geckspymm.entity.animals.elephant;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class ElephantRenderState extends LivingEntityRenderState {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState eatAnimationState = new AnimationState();
}
