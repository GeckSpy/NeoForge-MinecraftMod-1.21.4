package net.geckspy.geckspymm.item.custom;

import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;


public class LightningStaff extends Item {
    // how many ticks you can hold at max (same default as bow: 72000)
    private static final int MAX_CHARGE = 72000;
    // minimum ticks required to actually fire
    private static final int MIN_CHARGE = 20;
    // From how far the staff can summon lightning
    private static final int MAX_RANGE = 100;
    // Minimum number of magic_orb to use
    private static final int NB_MAGIC_ORB = 1;

    public LightningStaff(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide){
            double x = livingEntity.getX() + 0.25 + Math.random()/2-0.5;
            double y = livingEntity.getY() + livingEntity.getEyeHeight() + 0.25 + Math.random()/2;
            double z = livingEntity.getZ() + 0.25 + Math.random()/2-0.5;
            level.addParticle(ModParticles.LIGHTNING_PARTICLE.get(), x, y, z, 0, 0, 0);
        }
    }

    // Which animation to play while “drawing”
    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.SPEAR;                 // bow-style draw
    }

    // How long they can hold the draw
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return MAX_CHARGE;
    }


    // Called when they release the right-click:
    //  timeLeft = ticks still remaining out of MAX_CHARGE
    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity shooter, int timeLeft) {
        if (world.isClientSide) return false;    // only on server

        int chargeTicks = MAX_CHARGE - timeLeft;
        if (chargeTicks < MIN_CHARGE) {
            // not charged enough
            return false;
        }

        // Verify that the player has at least 3 magic_orb
        if (shooter instanceof Player player && !player.isCreative()) {
            int orbCount = 0;
            for (ItemStack invStack : player.getInventory().items) {
                if (invStack.getItem() == ModItems.MAGIC_ORB.get()) {
                    orbCount += invStack.getCount();
                }
            }
            if(orbCount< NB_MAGIC_ORB){return false;}

            // Remove used magic_orbs
            int toRemove = NB_MAGIC_ORB;
            for (int i = 0; i < player.getInventory().items.size() && toRemove > 0; i++) {
                ItemStack invStack = player.getInventory().items.get(i);
                if (invStack.getItem() == ModItems.MAGIC_ORB.get()) {
                    int removed = Math.min(invStack.getCount(), toRemove);
                    invStack.shrink(removed);
                    toRemove -= removed;
                }
            }
        }

        // perform a ray-trace up to max 100
        Vec3 eyePos   = shooter.getEyePosition(1.0F);
        Vec3 lookDir  = shooter.getLookAngle();
        Vec3 endPoint = eyePos.add(lookDir.scale(MAX_RANGE));

        // Look at if there is an entity
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                world,
                shooter,
                eyePos,
                endPoint,
                shooter.getBoundingBox().expandTowards(lookDir.scale(MAX_RANGE)).inflate(1.0D),
                (e) -> e instanceof LivingEntity && e != shooter
        );

        if (entityHit != null) {
            // We hit a living entity
            LivingEntity target = (LivingEntity) entityHit.getEntity();
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
            bolt.setPos(target.getX(), target.getY(), target.getZ());
            if (shooter instanceof ServerPlayer sp) bolt.setCause(sp);
            world.addFreshEntity(bolt);
        }else {

            BlockHitResult hit = world.clip(new ClipContext(
                    eyePos,
                    endPoint,
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    shooter
            ));

            if (hit.getType() == BlockHitResult.Type.BLOCK) {
                BlockPos pos = hit.getBlockPos();
                LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                bolt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

                if (shooter instanceof ServerPlayer serverPlayer) {
                    bolt.setCause(serverPlayer);
                }
                world.addFreshEntity(bolt);
            }
        }

        // Take of durability
        if(shooter instanceof Player player && !player.isCreative()) {
            int newDamage = stack.getDamageValue() + 1; // getDamageValue() → current damage
            if (newDamage >= stack.getMaxDamage()) {    // getMaxDamage() → durability cap
                stack.shrink(1);
                shooter.swing(shooter.getUsedItemHand());         // play break animation
            } else {
                stack.setDamageValue(newDamage);  // setDamageValue(int) :contentReference[oaicite:2]{index=2}
            }
        }

        return true;
    }
}
