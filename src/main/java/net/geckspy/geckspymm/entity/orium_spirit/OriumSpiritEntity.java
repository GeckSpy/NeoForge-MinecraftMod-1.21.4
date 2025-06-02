package net.geckspy.geckspymm.entity.orium_spirit;

import net.geckspy.geckspymm.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


public class OriumSpiritEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    public OriumSpiritEntity(EntityType<? extends Animal> entityType, Level level){
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        // Behavior of entity
        // priority 0: highest
        this.goalSelector.addGoal(0, new FloatGoal(this)); // So that mob don't sink down
        // stay close to lightning convertor
        // Summon lightning when ligthing convertor around
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.25, stack->stack.is(ModItems.ORIUM_ORB), false));
        // Produce orium orb item (rarely)
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 10)
                .add(Attributes.TEMPT_RANGE, 20);
    }


    @Override
    public boolean isFood(ItemStack stack) {
        // For breeding, ex:
        //return stack.is(Items.APPLE);
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        //return ModEntities.ORIUM_SPIRIT.get().create(level, EntitySpawnReason.BREEDING);
        return null;
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

}
