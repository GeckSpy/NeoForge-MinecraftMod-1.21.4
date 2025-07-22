package net.geckspy.geckspymm.entity.neider;

import net.geckspy.geckspymm.effect.ModEffects;
import net.geckspy.geckspymm.util.ModFunctions;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class NeiderEntity extends Monster {
    private static final EntityDataAccessor<Integer> ANIMATION_STATE =
            SynchedEntityData.defineId(NeiderEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREVIOUS_ANIMATION_STATE =
            SynchedEntityData.defineId(NeiderEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int animationTicks = 0;

    public static int ATTACK_COOLDOWN = 20*10;
    public int attackCooldown = ATTACK_COOLDOWN;

    public static final int normalState = 0;
    public static final int attackState = 4;

    private BlockPos targetBlockPos;

    private static final EntityDataAccessor<Integer> state =
            SynchedEntityData.defineId(NeiderEntity.class, EntityDataSerializers.INT);


    public NeiderEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }



    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION_STATE, idleAnimationId);
        builder.define(PREVIOUS_ANIMATION_STATE, idleAnimationId);
        builder.define(state, normalState);
    }

    @Override
    protected void registerGoals() {
        // Grab
        // Attack
        // Jump

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.1));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return FlyingMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.MOVEMENT_SPEED, 0.28)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.ATTACK_SPEED, 1);
    }


    public void setAnimationState(int animationStateIndex){
        entityData.set(ANIMATION_STATE, animationStateIndex);
    }
    public void setState(int newState){
        entityData.set(state, newState);
    }

    public static final int idleAnimationId = 0;
    public static final int attackAnimationId = 1;
    private final List<Triple<Integer, AnimationState, AnimationDefinition>> ANIMATION_INFO_LIST = List.of(
            Triple.of(idleAnimationId, idleAnimationState, NeiderAnimations.IDLE),
            Triple.of(attackAnimationId, attackAnimationState, NeiderAnimations.GRAB)
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
            setAnimationState(idleAnimationId);
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

        // Handle states
        if(!this.level().isClientSide) {
            switch (this.getState()) {
                case normalState:
                    if(attackCooldown>0){attackCooldown--;}
                    break;


                case attackState:
                    if(this.attackCooldown<=0){
                        this.attackCooldown = ATTACK_COOLDOWN + this.getRandom().nextInt(20*5);
                        this.setAnimationState(idleAnimationId);
                        this.setState(normalState);
                    }else{
                        this.attackCooldown--;
                    }
            }
        }

    }


    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }


    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        super.actuallyHurt(level, damageSource, amount);
    }


    public int getState(){
        return this.getEntityData().get(state);
    }

    public static boolean checkNeitherSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).isSolid() && level.getBrightness(LightLayer.BLOCK, pos) <= 7;
    }

}
