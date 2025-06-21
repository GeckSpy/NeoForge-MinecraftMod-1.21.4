package net.geckspy.geckspymm.entity.animals.lion;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class LionRenderState extends LivingEntityRenderState {
    public AnimationState idleAnimationState = new AnimationState();
    public LionEntityVariant variant;
}
