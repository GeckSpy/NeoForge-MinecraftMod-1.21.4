package net.geckspy.geckspymm.entity.goals;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.List;


public class ModGroupGoal extends Goal {
    private final Mob mob;
    private final double speedModifier;
    private final double maxDistance;
    private final double minDistance;
    private final int maxGroupSize;
    private Mob targetMob;

    public ModGroupGoal(Mob mob, double speedModifier, double minDistance, double maxDistance, int maxGroupSize) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.maxGroupSize = maxGroupSize;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }


    public int groupSize(Mob wantedMob){
        List<Mob> nearbyMobs = (List<Mob>) wantedMob.level().getEntitiesOfClass(mob.getClass(), wantedMob.getBoundingBox().inflate(minDistance));
        return nearbyMobs.size();
    }


    @Override
    public boolean canUse() {
        if (mob.isPassenger() || mob.getRandom().nextDouble()<0.1) return false;
        int mobGroupSize = groupSize(this.mob);
        if(mobGroupSize>=maxGroupSize){return false;}

        List<Mob> nearbyPenguins = (List<Mob>) mob.level().getEntitiesOfClass(mob.getClass(), mob.getBoundingBox().inflate(maxDistance)).stream()
                .filter(e->e!=this.mob && mob.distanceToSqr(e)>minDistance*minDistance && groupSize(e)>=mobGroupSize).toList();

        this.targetMob = null;
        double closestDistSq = maxDistance * maxDistance;
        for (Mob other : nearbyPenguins) {
            double distSq = mob.distanceToSqr(other);
            if(distSq<closestDistSq) {
                closestDistSq = distSq;
                this.targetMob = other;
            }
        }
        return this.targetMob != null;
    }

    @Override
    public void start() {
        if (targetMob != null) {
            mob.getNavigation().moveTo(targetMob, speedModifier);
        }
    }
    @Override
    public boolean canContinueToUse() {
        return targetMob != null && mob.distanceToSqr(targetMob) > minDistance * minDistance;
    }
}

