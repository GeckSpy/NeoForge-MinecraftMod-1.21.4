package net.geckspy.geckspymm.entity.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.EnumSet;

public class RandomFlyGoal extends Goal {
    private final FlyingMob mob;
    private int cooldown;
    private static double maxHeight;
    private static double maxDist;
    private static int cooldownTime;

    public RandomFlyGoal(FlyingMob mob, double maxHeight, double maxDist, int cooldownTime) {
        this.mob = mob;
        this.maxHeight = maxHeight;
        this.maxDist = maxDist;
        this.cooldownTime = cooldownTime;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (mob.getTarget() != null ) return false;
        return cooldown-- <= 0;
    }

    @Override
    public void start() {
        RandomSource random = mob.getRandom();
        double baseX = mob.getX();
        double baseZ = mob.getZ();
        BlockPos groundPos = new BlockPos((int)baseX, (int)mob.getY(), (int)baseZ);
        int groundY = mob.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, groundPos.getX(), groundPos.getZ());

        double maxFlyY = groundY + this.maxHeight;
        double dx = baseX + (random.nextDouble() - 0.5) * this.maxDist;
        double dz = baseZ + (random.nextDouble() - 0.5) * this.maxDist;
        double dy = Math.min(mob.getY() + (random.nextDouble() - 0.5) * 8.0, maxFlyY);

        mob.getMoveControl().setWantedPosition(dx, dy, dz, 1.0);
        cooldown = this.cooldownTime + random.nextInt(60);
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
