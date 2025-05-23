package net.geckspy.geckspymm.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;


public class UndyingEffect extends MobEffect {
    public UndyingEffect(MobEffectCategory category, int color){
        super(category, color);
    }
    public static double KNOCKBACK_RADIUS = 10.0;
    public static float KNOCKBACK_STRENGTH = 1.0f;

    public static void onEntityDeath(LivingEntity dyingEntity){
        dyingEntity.setHealth(1.0F);
        dyingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 80, 0));
        dyingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 80, 0));
        dyingEntity.level().broadcastEntityEvent(dyingEntity, (byte)35);

        Level level = dyingEntity.level();
        Vec3 center = dyingEntity.getPosition(0);
        List<LivingEntity> entities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(center, center).inflate(KNOCKBACK_RADIUS),
                e->e!=dyingEntity
        );
        for(LivingEntity livingEntity:entities){
            Vec3 direction = livingEntity.getPosition(0).subtract(center).normalize();
            livingEntity.setDeltaMovement(
                    direction.x * KNOCKBACK_STRENGTH,
                    Math.min(0.5, direction.y * KNOCKBACK_STRENGTH+0.2),
                    direction.z * KNOCKBACK_STRENGTH
            );
        }

    }
}
