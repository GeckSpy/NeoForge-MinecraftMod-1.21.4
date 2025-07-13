package net.geckspy.geckspymm.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;
import java.util.Optional;

public class FlyToVillageGoal extends Goal {
    private final FlyingMob mob;
    private final double speed;
    private final int searchRadius;
    private BlockPos targetPos;

    public FlyToVillageGoal(FlyingMob mob, double speedModifier, int searchRadius) {
        this.mob = mob;
        this.speed = speedModifier;
        this.searchRadius = searchRadius;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null) return false; // Don't go to village if attacking
        if (mob.getRandom().nextInt(40) != 0) return false; // Only occasionally try

        BlockPos mobPos = mob.blockPosition();
        ServerLevel level = (ServerLevel) mob.level();

        Optional<BlockPos> villageOpt = Optional.ofNullable(level.findNearestMapStructure(
                StructureTags.VILLAGE,
                mobPos,
                searchRadius,
                false
        ));

        if (villageOpt.isPresent()) {
            this.targetPos = villageOpt.get();
            return true;
        }
        return false;
    }

    @Override
    public void start() {
        if (targetPos != null) {
            mob.getMoveControl().setWantedPosition(
                    targetPos.getX() + 0.5,
                    targetPos.getY() + 5 + mob.getRandom().nextInt(6), // fly slightly above
                    targetPos.getZ() + 0.5,
                    speed
            );
        }
    }

    @Override
    public boolean canContinueToUse() {
        return targetPos != null && !mob.getNavigation().isDone();
    }
}