package net.geckspy.geckspymm.entity.animals.rhinoceros;

import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.entity.animals.tiger.TigerEntity;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.level.ServerLevelAccessor;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public class RhinocerosEntity extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> ANIMATION_STATE =
            SynchedEntityData.defineId(RhinocerosEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREVIOUS_ANIMATION_STATE =
            SynchedEntityData.defineId(RhinocerosEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int animationTicks = 0;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(80, 140);
    private int remainingPersistentAngerTime;
    public static float attackRangeSquared = 12.0f;

    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public RhinocerosEntity(EntityType<? extends Animal> entityType, Level level){
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION_STATE, idleAnimationId);
        builder.define(PREVIOUS_ANIMATION_STATE, idleAnimationId);
    }

    @Override
    protected void registerGoals() {
        // Behavior of entity
        this.goalSelector.addGoal(0, new FloatGoal(this)); // So that mob don't sink down
        this.targetSelector.addGoal(1, new RhinocerosHurtByTargetGoal());
        this.targetSelector.addGoal(2, new RhinocerosMeleeAttackGoal());
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));

        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 120)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 30)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.5)
                .add(Attributes.STEP_HEIGHT, 1.2)
                .add(Attributes.ATTACK_SPEED, 1);
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }


    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.RHINOCEROS.get().create(level, EntitySpawnReason.BREEDING);
    }


    public static final int idleAnimationId = 0;
    public static final int attackAnimationId = 1;
    private final List<Triple<Integer, AnimationState, AnimationDefinition>> ANIMATION_INFO_LIST = List.of(
            Triple.of(idleAnimationId, idleAnimationState, RhinocerosAnimations.IDLE),
            Triple.of(attackAnimationId, attackAnimationState, RhinocerosAnimations.ATTACK)
    );
    private void setupAnimationStates() {
        SynchedEntityData entityData = this.getEntityData();
        int state = entityData.get(ANIMATION_STATE);
        for(var info: ANIMATION_INFO_LIST){
            if(state==info.getLeft() && !info.getMiddle().isStarted()){
                ANIMATION_INFO_LIST.get(entityData.get(PREVIOUS_ANIMATION_STATE)).getMiddle().stop();
                entityData.set(PREVIOUS_ANIMATION_STATE, state);
                info.getMiddle().start(this.tickCount);
                this.animationTicks = (int)(info.getRight().lengthInSeconds() * 20);
            }
        }
        this.animationTicks--;
        if(this.animationTicks<=0){
            ANIMATION_INFO_LIST.get(state).getMiddle().stop();
            entityData.set(ANIMATION_STATE, idleAnimationId);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (this.animationTicks > 0) {
                this.animationTicks--;
                if (this.animationTicks <= 0) {
                    this.getEntityData().set(ANIMATION_STATE, idleAnimationId);
                }
            }
        } else {
            this.setupAnimationStates();
        }

    }

    @Override
    public boolean doHurtTarget(ServerLevel level, Entity entity) {
        boolean result = super.doHurtTarget(level, entity);
        if (result && !this.level().isClientSide()) {
            this.getEntityData().set(ANIMATION_STATE, attackAnimationId); // set to 1
            this.animationTicks = (int)(RhinocerosAnimations.ATTACK.lengthInSeconds() * 20); // duration
        }
        return result;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        this.remainingPersistentAngerTime = remainingPersistentAngerTime;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @javax.annotation.Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@javax.annotation.Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override
    public boolean isWithinMeleeAttackRange(LivingEntity target) {
        return this.distanceToSqr(target.getX(), target.getY(), target.getZ()) <= attackRangeSquared;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, EntitySpawnReason p_363316_, @Nullable SpawnGroupData p_146749_) {
        SpawnGroupData groupData = super.finalizeSpawn(p_146746_, p_146747_, p_363316_, p_146749_);
        if(RhinocerosEntity.this.getSpawnType() != EntitySpawnReason.NATURAL){
            return groupData;
        }
        boolean isAdultAround = true;
        for (TigerEntity tiger : RhinocerosEntity.this.level().getEntitiesOfClass(TigerEntity.class, RhinocerosEntity.this.getBoundingBox().inflate((double) 4.0F, (double) 2.0F, (double) 4.0F))) {
            if (tiger.isBaby()) {
                isAdultAround = true;
                break;
            }
        }
        if(isAdultAround && this.getRandom().nextFloat()<0.4f){
            this.setBaby(true);
        }
        return groupData;
    }


    class RhinocerosHurtByTargetGoal extends HurtByTargetGoal {
        public RhinocerosHurtByTargetGoal() {
            super(RhinocerosEntity.this);
        }

        @Override
        public void start() {
            super.start();
            if (RhinocerosEntity.this.isBaby()) {
                this.alertOthers();
                this.stop();
            }
        }
        @Override
        protected void alertOther(Mob mob, LivingEntity target) {
            if (mob instanceof RhinocerosEntity && !mob.isBaby()) {
                super.alertOther(mob, target);
            }
        }
    }

    class RhinocerosMeleeAttackGoal extends MeleeAttackGoal {
        public int ticksUntilNextAttack = 0;
        public int attackCooldown = 15;
        public RhinocerosMeleeAttackGoal() {
            super(RhinocerosEntity.this, 2, true);
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
}
