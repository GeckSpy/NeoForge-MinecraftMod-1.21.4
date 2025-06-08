package net.geckspy.geckspymm.entity.penguin;

import net.geckspy.geckspymm.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;


public class PenguinEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    public PenguinEntity(EntityType<? extends Animal> entityType, Level level){
        super(entityType, level);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.moveControl = new PenguinMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        // Behavior of entity
        // priority 0: highest
        this.goalSelector.addGoal(0, new FloatGoal(this)); // So that mob don't sink down
        // stay close to lightning convertor
        // Summon lightning when ligthing convertor around
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        // Produce orium orb item (rarely)
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1f));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.1, (stack) -> stack.is(Items.COD), false));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.1)
                .add(Attributes.FOLLOW_RANGE, 15)
                .add(Attributes.TEMPT_RANGE, 15)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 10);
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
    }



    static class PenguinMoveControl extends MoveControl {
        private final PenguinEntity penguin;

        PenguinMoveControl(PenguinEntity penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        private void updateSpeed() {
            if (this.penguin.isInWater()) {
                this.speedModifier = 2;
                this.penguin.setDeltaMovement(this.penguin.getDeltaMovement().add((double)0.0F, 0.005, (double)0.0F));
            } else if (this.penguin.onGround()) {
                //this.penguin.setSpeed(Math.max(this.penguin.getSpeed() / 2.0F, 0.06F));
            }

        }

        public void tick() {
            this.updateSpeed();
            super.tick();
        }
    }

}
