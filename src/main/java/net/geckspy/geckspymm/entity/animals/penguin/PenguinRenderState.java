package net.geckspy.geckspymm.entity.animals.penguin;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PenguinRenderState extends LivingEntityRenderState {
    public AnimationState idleAnimationState = new AnimationState();
    public boolean isLayingDown = false;
}
