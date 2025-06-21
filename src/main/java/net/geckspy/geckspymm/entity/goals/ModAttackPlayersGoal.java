package net.geckspy.geckspymm.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;

public class ModAttackPlayersGoal extends NearestAttackableTargetGoal<Player> {
    private static double boundingBoxInflation;
    public ModAttackPlayersGoal(PathfinderMob mob, int interval, double boundingBox, boolean mustSee, boolean mustReach) {
        super(mob, Player.class, interval, mustSee, mustReach, (TargetingConditions.Selector) null);
        this.boundingBoxInflation = boundingBox;
    }

    public boolean canUse() {
        if (this.mob.isBaby()) {
            return false;
        } else {
            if (super.canUse()) {
                for (var other : this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(this.boundingBoxInflation, this.boundingBoxInflation, this.boundingBoxInflation))){
                    if (other.isBaby()) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
