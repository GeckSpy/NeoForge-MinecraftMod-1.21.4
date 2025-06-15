package net.geckspy.geckspymm.entity.penguin;

import net.geckspy.geckspymm.entity.ModEntities;

import net.geckspy.geckspymm.entity.goals.ModGroupGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;


public class PenguinEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    public PenguinEntity(EntityType<? extends Animal> entityType, Level level){
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, 0.0F);
        this.moveControl = new PenguinMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        // Behavior of entity
        // priority 0: highest
        this.goalSelector.addGoal(0, new BreathAirGoal(this));
        this.goalSelector.addGoal(1, new PenguinPanicGoal(this, 1.2));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.3));

        this.goalSelector.addGoal(4, new BreedGoal(this, 1));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1, (stack) -> stack.is(Items.COD), false));

        this.goalSelector.addGoal(7, new PenguinSwimWithPlayerGoal(this, 1));
        this.goalSelector.addGoal(8, new PenguinSwinGoal(this));
        this.goalSelector.addGoal(8, new PenguinRandomStrollGoal(this, 1));


        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 4.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new ModGroupGoal(this, 0.8, 8, 40, 15));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12)
                .add(Attributes.MOVEMENT_SPEED, 0.12)
                .add(Attributes.FOLLOW_RANGE, 15)
                .add(Attributes.TEMPT_RANGE, 15)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.COD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.PENGUIN.get().create(level, EntitySpawnReason.BREEDING);
    }


    private void setupAnimationStates(){
        if(this.idleAnimationTimeout<=0){
            this.idleAnimationTimeout = 120;
            this.idleAnimationState.start(this.tickCount);
        }else{
            this.idleAnimationTimeout--;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }
        this.refreshDimensions();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason p_363316_, @Nullable SpawnGroupData p_146749_) {
        SpawnGroupData groupData = super.finalizeSpawn(level, difficulty, p_363316_, p_146749_);
        if(PenguinEntity.this.getSpawnType() != EntitySpawnReason.NATURAL || this.getRandom().nextFloat()>=0.12f){
            return groupData;
        }
        for (PenguinEntity penguin : PenguinEntity.this.level().getEntitiesOfClass(PenguinEntity.class, PenguinEntity.this.getBoundingBox().inflate((double) 3.0F, (double) 2.0F, (double) 3.0F))) {
            if (penguin.isBaby()) {
                this.setBaby(true);
                break;
            }
        }
        return groupData;
    }

    @Override
    protected EntityDimensions getDefaultDimensions(Pose pose) {
        EntityDimensions dimensions;
        if (this.isInWater()) {
            dimensions = EntityDimensions.scalable(1.0F, 0.5F);
        } else {
            dimensions = EntityDimensions.scalable(0.5F, 1.0F);
        }
        return dimensions.scale(this.getAgeScale());
    }

    static class PenguinMoveControl extends MoveControl{
        private final PenguinEntity penguin;

        PenguinMoveControl(PenguinEntity penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        public void tick(){
            if(this.penguin.isInWater()){
                penguin.setDeltaMovement(penguin.getDeltaMovement().add(0.0, 0.02, 0.0));

                if (this.operation != Operation.MOVE_TO || penguin.getNavigation().isDone()) {
                    penguin.setSpeed(0.0f);
                    return;
                }

                double dx = this.wantedX - penguin.getX();
                double dy = this.wantedY - penguin.getY();
                double dz = this.wantedZ - penguin.getZ();
                double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

                dy /= dist;

                penguin.setYRot(this.rotlerp(penguin.getYRot(), (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F, 10.0F));
                penguin.yBodyRot = penguin.getYRot();
                penguin.setXRot((float)(45 * (180F / Math.PI)));


                this.speedModifier = 1.5;
                float speed = (float)(this.speedModifier * penguin.getAttributeValue(Attributes.WATER_MOVEMENT_EFFICIENCY));
                penguin.setDeltaMovement(penguin.getDeltaMovement().add(
                        Math.signum(dx) * 0.05 * speed,
                        dy * 0.05 * speed,
                        Math.signum(dz) * 0.05 *speed
                ));

            }else{
                super.tick();
            }
        }
    }

    static class PenguinSwinGoal extends Goal{
        private final PathfinderMob mob;
        public PenguinSwinGoal(PathfinderMob mob){
            this.mob = mob;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }
        @Override
        public boolean canUse() {
            return this.mob.isInWater() && mob.getRandom().nextFloat()<0.1;
        }
        @Override
        public boolean canContinueToUse() {
            return mob.getNavigation().isInProgress() && mob.isInWater();
        }

        @Override
        public void start() {
            RandomSource random = mob.getRandom();
            double x = mob.getX() + (random.nextDouble() * 16.0D - 8.0D);
            double y = mob.getY() + (random.nextDouble() * 8.0D - 4.0D);
            double z = mob.getZ() + (random.nextDouble() * 16.0D - 8.0D);
            BlockPos targetPos = new BlockPos((int)x, (int)y, (int)z);

            if (mob.level().getFluidState(targetPos).is(FluidTags.WATER)) {
                mob.getNavigation().moveTo(x, y, z, this.mob.getSpeed());
            }
        }
    }

    static class PenguinSwimWithPlayerGoal extends Goal {
        private final PenguinEntity penguin;
        private final double speedModifier;
        @javax.annotation.Nullable
        private Player player;

        PenguinSwimWithPlayerGoal(PenguinEntity penguin, double speedModifier) {
            this.penguin = penguin;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            this.player = getServerLevel(this.penguin).getNearestPlayer(this.penguin, 10);
            return this.player == null ? false : this.player.isSwimming() && this.penguin.getTarget() != this.player && this.penguin.getRandom().nextFloat()<0.4f;
        }

        public boolean canContinueToUse() {
            return this.player != null && this.player.isSwimming() && this.penguin.distanceToSqr(this.player) < 150;
        }

        public void stop() {
            this.player = null;
            this.penguin.getNavigation().stop();
        }

        public void tick() {
            this.penguin.getLookControl().setLookAt(this.player, (float)(this.penguin.getMaxHeadYRot() + 20), (float)this.penguin.getMaxHeadXRot());
            if (this.penguin.distanceToSqr(this.player) < (double)6.25F) {
                this.penguin.getNavigation().stop();
            } else {
                this.penguin.getNavigation().moveTo(this.player, this.speedModifier);
            }
        }
    }

    static class PenguinPanicGoal extends PanicGoal {
        private final PenguinEntity penguin;
        PenguinPanicGoal(PenguinEntity penguin, double speedModifier) {
            super(penguin, speedModifier);
            this.penguin = penguin;
        }

        public boolean canUse() {
            super.canUse();
            if (!this.shouldPanic()) {
                return false;
            } else {
                if(!this.penguin.isInWater()) {
                    BlockPos blockpos = this.lookForWater(this.mob.level(), this.mob, 7);
                    if (blockpos != null) {
                        this.posX = (double) blockpos.getX();
                        this.posY = (double) blockpos.getY();
                        this.posZ = (double) blockpos.getZ();
                        return true;
                    }
                }
                return this.findRandomPosition();
            }
        }
    }

    static class PenguinRandomStrollGoal extends RandomStrollGoal{
        PenguinRandomStrollGoal(PenguinEntity penguin, int speedModifier){
            super(penguin, speedModifier);
        }

        @Override
        public boolean canUse() {
            return !this.mob.isInWater() && super.canUse();
        }
    }
}
