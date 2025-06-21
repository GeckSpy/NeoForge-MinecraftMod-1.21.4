package net.geckspy.geckspymm.entity.animals.snow_panther;

import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.entity.goals.ModHurtByTargetGoal;
import net.geckspy.geckspymm.entity.goals.ModMeleeAttackGoal;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
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


public class SnowPantherEntity extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> ANIMATION_STATE =
            SynchedEntityData.defineId(SnowPantherEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREVIOUS_ANIMATION_STATE =
            SynchedEntityData.defineId(SnowPantherEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    private int animationTicks = 0;

    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(40, 70);
    private int remainingPersistentAngerTime;

    @javax.annotation.Nullable
    private UUID persistentAngerTarget;

    public SnowPantherEntity(EntityType<? extends Animal> entityType, Level level){
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
        this.targetSelector.addGoal(2, new ModMeleeAttackGoal(this, 1.3, 0, true));
        this.targetSelector.addGoal(3, new ModHurtByTargetGoal(this));

        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, false));

        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5, (mob) -> mob.isBaby() ? DamageTypeTags.PANIC_CAUSES : DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1,0.5f));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 20.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 90)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.FOLLOW_RANGE, 30)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.STEP_HEIGHT, 1.5);
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
        return ModEntities.SNOW_PANTHER.get().create(level, EntitySpawnReason.BREEDING);
    }


    public static final int idleAnimationId = 0;
    private final List<Triple<Integer, AnimationState, AnimationDefinition>> ANIMATION_INFO_LIST = List.of(
            Triple.of(idleAnimationId, idleAnimationState, SnowPantherAnimations.IDLE)
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
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason p_363316_, @Nullable SpawnGroupData p_146749_) {
        SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, p_363316_, p_146749_);
        if(SnowPantherEntity.this.getSpawnType() != EntitySpawnReason.NATURAL || this.getRandom().nextFloat()>=0.3f){
            return groupData;
        }
        for (SnowPantherEntity panther : SnowPantherEntity.this.level().getEntitiesOfClass(SnowPantherEntity.class, SnowPantherEntity.this.getBoundingBox().inflate((double) 3.0F, (double) 2.0F, (double) 3.0F))) {
            if (panther.isBaby()) {
                this.setBaby(true);
                break;
            }
        }
        return groupData;
    }
}
