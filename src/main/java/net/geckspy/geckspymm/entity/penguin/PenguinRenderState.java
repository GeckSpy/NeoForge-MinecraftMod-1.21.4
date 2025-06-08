package net.geckspy.geckspymm.entity.penguin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PenguinRenderState extends LivingEntityRenderState {
    public AnimationState idleAnimationState = new AnimationState();
    public boolean isSwimming = false;
}
