package net.geckspy.geckspymm.entity.ent;

import net.geckspy.geckspymm.effect.ModEffects;
import net.geckspy.geckspymm.util.ModFunctions;
import net.minecraft.client.animation.AnimationDefinition;
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


public class EntEntity extends Monster {
    private static final EntityDataAccessor<Integer> ANIMATION_STATE =
            SynchedEntityData.defineId(EntEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PREVIOUS_ANIMATION_STATE =
            SynchedEntityData.defineId(EntEntity.class, EntityDataSerializers.INT);
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState hideAnimationState = new AnimationState();
    public final AnimationState unhideAnimationState = new AnimationState();
    private int animationTicks = 0;

    public static int ATTACK_COOLDOWN = 20*10;
    public static int HEAL_RADIUS = 12;
    public static int GROW_RADIUS = 15;
    public static int HEAL_AMOUNT = 2;

    private static final int SEARCH_RADIUS = 20;
    public static int HIDE_COOLDOWN = 20*10;
    private static final int HIDING_TIME = 20*3;
    public int hideCooldown = 0;
    public int attackCooldown = ATTACK_COOLDOWN;

    public static final int normalState = 0;
    public static final int hidingState = 1;
    public static final int hideState = 2;
    public static final int unhidingState = 3;
    public static final int attackState = 4;

    private BlockPos targetBlockPos;

    private static final EntityDataAccessor<Integer> state =
            SynchedEntityData.defineId(EntEntity.class, EntityDataSerializers.INT);


    public EntEntity(EntityType<? extends Monster> entityType, Level level) {
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
        this.goalSelector.addGoal(4, new EntSpellMonsterGoal(this));
        this.goalSelector.addGoal(6, new EntRandomHideGoal(this));
        this.goalSelector.addGoal(7, new EntGetCloserToMonsterGoal(this, 1.5));

        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new EntRandomStrollGoal(this, 0.1));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return FlyingMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 60)
                .add(Attributes.MOVEMENT_SPEED, 0.12)
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
    public static final int hideAnimationId = 2;
    public static final int unhideAnimationId = 3;
    private final List<Triple<Integer, AnimationState, AnimationDefinition>> ANIMATION_INFO_LIST = List.of(
            Triple.of(idleAnimationId, idleAnimationState, EntAnimations.IDLE),
            Triple.of(attackAnimationId, attackAnimationState, EntAnimations.ATTACK),
            Triple.of(hideAnimationId, hideAnimationState, EntAnimations.HIDE),
            Triple.of(unhideAnimationId, unhideAnimationState, EntAnimations.UNHIDE)
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
                    if(hideCooldown>0){hideCooldown--;}
                    if(attackCooldown>0){attackCooldown--;}
                    break;

                case hidingState:
                    if (this.hideCooldown <= 0) {
                        this.hideCooldown = HIDING_TIME;
                        setState(hideState);
                        this.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, HIDING_TIME));
                        this.teleportTo(targetBlockPos.getX(), targetBlockPos.getY(), targetBlockPos.getZ());
                    } else {
                        this.hideCooldown--;
                    }
                    break;

                case hideState:
                    this.refreshDimensions();
                    if (this.hideCooldown <= 0) {
                        this.hideCooldown = (int)(EntAnimations.UNHIDE.lengthInSeconds() * 20);
                        this.setAnimationState(unhideAnimationId);
                        setState(unhidingState);
                    } else {
                        this.hideCooldown--;
                    }
                    break;

                case unhidingState:
                    this.refreshDimensions();
                    if (this.hideCooldown <= 0) {
                        setState(normalState);
                        this.setAnimationState(idleAnimationId);
                        this.hideCooldown = HIDE_COOLDOWN;
                    } else {
                        this.hideCooldown--;
                    }
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
    protected @NotNull EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions dimensions;
        if (getState()==hideState) {
            dimensions = EntityDimensions.scalable(0.1F, 0.1F);
        } else {
            dimensions = EntityDimensions.scalable(1.2f, 2.4f);
        }
        return dimensions.scale(this.getAgeScale());
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        super.actuallyHurt(level, damageSource, amount);
        if(canHide()){hide();}
    }

    public boolean canHide(){
        return this.hideCooldown<=0 && this.getState()==normalState && this.getHealth()>0;
    }

    public void hide(){
        List<Monster> surroundingMonsters = new ModFunctions().getNearbyMonster(level(), this.blockPosition(), SEARCH_RADIUS, false);
        BlockPos blockPos = this.blockPosition();
        if(!surroundingMonsters.isEmpty()){
            int index = this.getRandom().nextInt(surroundingMonsters.size());
            System.out.println(index);
            index = 0;
            blockPos = surroundingMonsters.get(index).blockPosition();
        }
        this.targetBlockPos = new ModFunctions().getRandomGroundBlockNear(blockPos, level(), SEARCH_RADIUS);
        this.hideCooldown = (int)(EntAnimations.HIDE.lengthInSeconds()*20);
        this.setAnimationState(hideAnimationId);
        setState(hidingState);
    }

    public int getState(){
        return this.getEntityData().get(state);
    }

    public static boolean checkEntSpawnRules(EntityType<? extends Mob> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).isSolid() && level.getBrightness(LightLayer.BLOCK, pos) <= 7;
    }

    public int difficultyGigantismEffectAmplifier(){
        if(level().getDifficulty().equals(Difficulty.HARD)){return 1;}
        else{return 0;}
    }



    public class EntRandomStrollGoal extends RandomStrollGoal{
        private static EntEntity ent;
        public EntRandomStrollGoal(EntEntity mob, double speedModifier) {
            super(mob, speedModifier);
            this.ent = mob;
        }

        @Override
        public boolean canUse() {
            return super.canUse() && this.ent.getState()==normalState;
        }
    }

    public class EntGetCloserToMonsterGoal extends Goal{
        private static final int SEARCH_RADIUS = 20;
        private static EntEntity ent;
        private Monster targetMonster;
        private final double speed;

        public EntGetCloserToMonsterGoal(EntEntity mob, double speed) {
            this.ent = mob;
            this.speed = speed;
        }

        @Override
        public boolean canUse() {
            if(this.ent.getRandom().nextDouble()<0.5){return false;}
            targetMonster = new ModFunctions().getClosestMonster(level(), this.ent.blockPosition(), SEARCH_RADIUS, false);
            return targetMonster!=null;
        }

        @Override
        public boolean canContinueToUse() {
            return targetMonster != null && targetMonster.isAlive() && this.ent.distanceToSqr(targetMonster) > 6.0;
        }

        @Override
        public void tick() {
            if (targetMonster != null) {
                this.ent.getNavigation().moveTo(targetMonster, speed);
            }
        }

        @Override
        public void stop() {
            targetMonster = null;
            this.ent.getNavigation().stop();
        }
    }

    public class EntRandomHideGoal extends Goal{
        private static EntEntity ent;

        public EntRandomHideGoal(EntEntity ent){
            this.ent = ent;
        }

        @Override
        public boolean canUse() {
            if(this.ent.getRandom().nextDouble()<0.1 && this.ent.canHide()){
                Player player = level().getNearestPlayer(this.ent, 15);
                return player!=null || this.ent.getRandom().nextDouble()<0.1;
            }else{
                return false;
            }
        }
    }

    public class EntSpellMonsterGoal extends Goal{
        private EntEntity ent;
        private int internalCooldown;

        public EntSpellMonsterGoal(EntEntity ent){
            this.ent = ent;
        }

        @Override
        public boolean canUse() {
            return this.ent.attackCooldown<=0;
        }

        @Override
        public boolean canContinueToUse() {
            return this.ent.getState()==attackState;
        }


        @Override
        public void start() {
            this.ent.setAnimationState(attackAnimationId);
            this.ent.setState(attackState);
            this.ent.attackCooldown = (int)(EntAnimations.ATTACK.lengthInSeconds()*20);
            this.internalCooldown = 7;
        }

        @Override
        public void tick() {
            if(this.internalCooldown >0){this.internalCooldown--;}
            else if(this.internalCooldown ==0){
                this.internalCooldown = -1;
                if(this.ent.getRandom().nextDouble()<0.75){
                    healMonsters();
                }else{
                    growMonsters();
                }
            }
        }

        public void healMonsters(){
            List<Monster> monsters = new ModFunctions().getNearbyMonster(level(), this.ent.blockPosition(), HEAL_RADIUS, false);
            for(Monster monster: monsters){
                if(!(monster instanceof EntEntity)) {
                    monster.heal(HEAL_AMOUNT);
                    ((ServerLevel) level()).sendParticles(ParticleTypes.HEART,
                            monster.getX(), monster.getEyeY()+1, monster.getZ(),
                            1,
                            0,0,0,
                            0.0);
                }
            }
        }

        public void growMonsters(){
            List<Monster> monsters = new ModFunctions().getNearbyMonster(level(), this.ent.blockPosition(), GROW_RADIUS, false);
            for(Monster monster: monsters){
                if(!(monster instanceof EntEntity)) {
                    monster.addEffect(new MobEffectInstance(ModEffects.GIGANTISM, 200, difficultyGigantismEffectAmplifier()));
                }
            }
            this.ent.addEffect(new MobEffectInstance(ModEffects.MINIATURISM, 160, 1));
        }

    }


}
