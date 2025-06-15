package net.geckspy.geckspymm.entity.goals;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ModMeleeAttackGoal extends MeleeAttackGoal {
    public int ticksUntilNextAttack = 0;
    public int attackCooldown;
    public ModMeleeAttackGoal(PathfinderMob mob, double speedModifier, int tickAttackCooldown, boolean followingTargetEventIfNotSeen) {
        super(mob, speedModifier, followingTargetEventIfNotSeen);
        this.attackCooldown = tickAttackCooldown;
    }

    private float getAttackDamage(){
        return (float)this.mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.ticksUntilNextAttack>0){
            this.ticksUntilNextAttack--;
        }
    }

    @Override
    protected boolean canPerformAttack(LivingEntity entity) {
        return this.ticksUntilNextAttack<=0 && super.canPerformAttack(entity);
    }

    @Override
    protected void resetAttackCooldown() {
        super.resetAttackCooldown();
        this.ticksUntilNextAttack = (int)((double)this.attackCooldown * 1/getAttackDamage());
    }
}