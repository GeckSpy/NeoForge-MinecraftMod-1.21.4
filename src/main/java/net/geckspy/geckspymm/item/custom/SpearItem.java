package net.geckspy.geckspymm.item.custom;

import net.geckspy.geckspymm.entity.spear.ThrownSpear;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class SpearItem extends SwordItem implements ProjectileItem {
    public static final float ATTACK_SPEED = -2.8f;
    public static final int ATTACK_DAMAGE = 4;
    private static final int MIN_CHARGE = 10;
    public ToolMaterial toolMaterial;

    public SpearItem(ToolMaterial material, float attackDamage, float attackSpeed, Properties properties) {
        super(material.applySwordProperties(properties, attackDamage, attackSpeed));
        toolMaterial = material;
    }




    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        player.startUsingItem(interactionHand);
        return InteractionResult.CONSUME;
    }

    @Override
    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int timeLeft) {
        if (entity instanceof Player player) {
            int i = this.getUseDuration(itemStack, entity) - timeLeft;
            if (i < MIN_CHARGE) {return false;}

            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW,
                    SoundSource.NEUTRAL, 0.5f, 0.4f/(level.getRandom().nextFloat()*0.4f+0.8f));
            player.awardStat(Stats.ITEM_USED.get(this));
            if (level instanceof ServerLevel) {
                System.out.println("releaseUsing");
                ServerLevel serverlevel = (ServerLevel)level;
                itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                ThrownSpear thrownSpear = (ThrownSpear)Projectile.spawnProjectileFromRotation(ThrownSpear::new, serverlevel, itemStack, player, 0.0f, 2.5f, 1.0f);
                if (player.hasInfiniteMaterials()) {
                    thrownSpear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                } else {
                    System.out.println("Remove");
                    player.getInventory().removeItem(itemStack);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.SPEAR;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack p_345950_, LivingEntity p_345617_, LivingEntity p_345537_) {
        p_345950_.hurtAndBreak(1, p_345537_, EquipmentSlot.MAINHAND);
    }

    @Override
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ThrownSpear thrownSpear = new ThrownSpear(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
        thrownSpear.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownSpear;
    }

}
