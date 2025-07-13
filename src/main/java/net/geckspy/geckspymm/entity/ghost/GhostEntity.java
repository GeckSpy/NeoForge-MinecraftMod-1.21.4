package net.geckspy.geckspymm.entity.ghost;

import net.geckspy.geckspymm.entity.goals.FlyToVillageGoal;
import net.geckspy.geckspymm.entity.goals.RandomFlyGoal;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;


public class GhostEntity extends FlyingMob implements Enemy {
    private static final EntityDataAccessor<Integer> ANIMATION_STATE =
            SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREVIOUS_ANIMATION_STATE =
            SynchedEntityData.defineId(GhostEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState angryAnimationState = new AnimationState();
    private int animationTicks = 0;

    public static int MIN_COOLDOWN = 80;
    public int cooldown;

    private static final boolean canBreakDoor = false;
    public static final int MAX_HIGHT = 10;


    public static final int normalState = 0;
    public static final int hidingState = 1;
    private int state = normalState;
    private static final int HIDING_TIME = 20*1;
    private int hidingTime = 0;

    public GhostEntity(EntityType<? extends FlyingMob> p_20806_, Level p_20807_) {
        super(p_20806_, p_20807_);
        this.moveControl = new FlyingMoveControl(this, 10, true);
        this.cooldown = MIN_COOLDOWN;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, level);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setRequiredPathLength(48.0F);
        return flyingpathnavigation;
    }

    @Override
    public void travel(Vec3 pos) {
        if (this.isControlledByLocalInstance()) {
            if (this.isInWater()) {
                this.moveRelative(0.02F, pos);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.8F));
            } else if (this.isInLava()) {
                this.moveRelative(0.02F, pos);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.5F));
            } else {
                this.moveRelative(this.getSpeed(), pos);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().scale((double)0.91F));
            }
        }

    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANIMATION_STATE, idleAnimationId);
        builder.define(PREVIOUS_ANIMATION_STATE, idleAnimationId);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new GhostFleeGoal(this));
        this.goalSelector.addGoal(2, new GhostGrabAndDropGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));

        this.goalSelector.addGoal(7, new FlyToVillageGoal(this, 0.2, 200));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new RandomFlyGoal(this, MAX_HIGHT, 64, 80));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return FlyingMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.FLYING_SPEED, 0.3)
                .add(Attributes.FOLLOW_RANGE, 80)
                .add(Attributes.ATTACK_DAMAGE, 5.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.7)
                .add(Attributes.ATTACK_SPEED, 1);
    }


    public static final int idleAnimationId = 0;
    public static final int angryAnimationId = 1;
    private final List<Triple<Integer, AnimationState, AnimationDefinition>> ANIMATION_INFO_LIST = List.of(
            Triple.of(idleAnimationId, idleAnimationState, GhostAnimations.IDLE),
            Triple.of(angryAnimationId, angryAnimationState, GhostAnimations.ANGRY)
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
        if(this.cooldown>0){this.cooldown--;}
        if(this.state == hidingState){
            this.hidingTime--;
            if(this.hidingTime <=0){this.state = normalState;}
        }

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
    public void aiStep() {
        if (this.isAlive() && this.isSunBurnTick()) {this.igniteForSeconds(8.0F);}
        super.aiStep();
    }

    @Override
    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }


    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        super.actuallyHurt(level, damageSource, amount);
        this.cooldown = MIN_COOLDOWN;
        this.hide(this.cooldown/2);
    }

    public void hide(int duration){
        state = hidingState;
        hidingTime = duration + this.getRandom().nextInt(40);
        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, hidingTime));
    }

    public int getState(){
        return this.state;
    }

    public static boolean checkGhostSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).isSolid() && level.getRawBrightness(pos, 0) < 8;
    }

    public class GhostGrabAndDropGoal extends Goal {
        private final GhostEntity ghost;
        private Player targetPlayer;
        private int state = 0; // 0: approach, 1: ascend, 2: drop
        private int timer = 0;
        private BlockPos groundPos;
        private double dropHigh;

        private static final int approachState = 0;
        private static final int ascendState = 1;
        private static final int dropState = 2;

        public GhostGrabAndDropGoal(GhostEntity ghost) {
            this.ghost = ghost;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            targetPlayer = ghost.level().getNearestPlayer(ghost, 8);
            return this.ghost.cooldown<=0 && this.ghost.state==this.ghost.normalState &&
                    targetPlayer != null && ghost.getTarget() == targetPlayer;
        }

        @Override
        public boolean canContinueToUse() {
            return targetPlayer != null && targetPlayer.isAlive()
                    && this.ghost.state==this.ghost.normalState && !targetPlayer.isCreative();
        }

        @Override
        public void start() {
            this.ghost.cooldown = this.ghost.MIN_COOLDOWN + this.ghost.getRandom().nextInt(40);
            state = approachState;
            timer = 0;
            groundPos = targetPlayer.blockPosition();
        }

        @Override
        public void tick() {
            switch (state) {
                case approachState: // approach
                    ghost.getMoveControl().setWantedPosition(
                            targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ(), 1.0
                    );
                    if (ghost.distanceToSqr(targetPlayer) < 2.0) {
                        state = ascendState;
                        targetPlayer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20*4, 1));
                        dropHigh = 8 + (ghost.getRandom().nextDouble()-0.3)*8;
                    }
                    break;

                case ascendState: // ascend
                    ghost.getMoveControl().setWantedPosition(
                            ghost.getX(), groundPos.getY() + dropHigh, ghost.getZ(), 0.6
                    );
                    timer++;
                    if (ghost.getY() >= groundPos.getY() + dropHigh || timer > 80) {
                        state = dropState;
                    }
                    if (!level().isClientSide && targetPlayer != null) {
                        Vec3 ghostPos = this.ghost.position().add(0, -1, 0);
                        Vec3 playerPos = targetPlayer.position();
                        Vec3 direction = ghostPos.subtract(playerPos).normalize();

                        double speed = 0.6;
                        Vec3 velocity = direction.scale(speed);
                        targetPlayer.setDeltaMovement(velocity);
                        targetPlayer.hurtMarked = true;

                        targetPlayer.hasImpulse = true;
                        targetPlayer.move(MoverType.SELF, targetPlayer.getDeltaMovement());
                    }
                    break;

                case dropState:
                    break;
            }
        }

    }


    public class GhostFleeGoal extends Goal {
        private final GhostEntity ghost;
        private Vec3 fleeDirection;
        private int duration = 0;

        public GhostFleeGoal(GhostEntity ghost) {
            this.ghost = ghost;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return ghost.getState()== hidingState;
        }

        @Override
        public void start() {
            duration = HIDING_TIME + ghost.getRandom().nextInt(60);
            ghost.hide(duration);
            ghost.hidingTime = duration;

            double dx = ghost.getRandom().nextDouble() - 0.5;
            double dy = (ghost.getRandom().nextDouble() - 0.3) * 0.5;
            double dz = ghost.getRandom().nextDouble() - 0.5;
            fleeDirection = new Vec3(dx, dy, dz).normalize().scale(2.0);
        }

        @Override
        public void tick() {
            if (duration-- <= 0) return;
            Vec3 targetPos = ghost.position().add(fleeDirection);
            ghost.getMoveControl().setWantedPosition(
                    targetPos.x, targetPos.y, targetPos.z, 1.1
            );
        }

        @Override
        public boolean canContinueToUse() {
            return ghost.state==hidingState && duration > 0;
        }
    }


}
