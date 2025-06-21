package net.geckspy.geckspymm.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class ModHurtByTargetGoal extends HurtByTargetGoal {
    public ModHurtByTargetGoal(PathfinderMob mob) {
        super(mob);
    }

    @Override
    public void start() {
        super.start();
        if (this.mob.isBaby()) {
            this.alertOthers();
            this.stop();
        }
    }
    @Override
    protected void alertOther(Mob mob, LivingEntity target) {
        if (mob.getClass() == this.mob.getClass() && !mob.isBaby()) {
            super.alertOther(mob, target);
        }
    }
}
