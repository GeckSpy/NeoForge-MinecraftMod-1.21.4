package net.geckspy.geckspymm.entity.ent;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class EntRenderState extends LivingEntityRenderState {
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState hideAnimationState = new AnimationState();
    public AnimationState unhideAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
}
