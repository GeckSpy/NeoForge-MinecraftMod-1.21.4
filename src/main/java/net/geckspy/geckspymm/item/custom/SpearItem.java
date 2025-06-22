package net.geckspy.geckspymm.item.custom;

import net.geckspy.geckspymm.entity.spear.ThrownSpear;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbility;

public class SpearItem extends SwordItem implements ProjectileItem {
    public static final float ATTACK_SPEED = -2.8f;
    public static final float ATTACK_DAMAGE = 1;
    public static final float THROWN_ATTACK_DAMAGE = 4;
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
                ServerLevel serverlevel = (ServerLevel)level;
                itemStack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);

                ThrownSpear thrownSpear = (ThrownSpear)Projectile.spawnProjectileFromRotation(ThrownSpear::new, serverlevel, itemStack, player, 0.0f, 1.8f, 0.5f);
                if (player.hasInfiniteMaterials()) {
                    thrownSpear.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                } else {
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
    public Projectile asProjectile(Level level, Position position, ItemStack itemStack, Direction direction) {
        ThrownSpear thrownSpear = new ThrownSpear(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
        thrownSpear.pickup = AbstractArrow.Pickup.ALLOWED;
        return thrownSpear;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return false;
    }

}
