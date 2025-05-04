package net.geckspy.geckspymm.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.geckspy.geckspymm.particle.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record ParticleSummonerEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<ParticleSummonerEnchantmentEffect> CODEC =
            MapCodec.unit(ParticleSummonerEnchantmentEffect::new);


    @Override
    public void apply(ServerLevel level, int enchantmentLevel, EnchantedItemInUse item, Entity entity, Vec3 origin) {
        System.out.println("Custom effect applied with level: " + enchantmentLevel);

        for(int i=0; i<enchantmentLevel; i++){
            System.out.println("i:" +i);
            EntityType.LIGHTNING_BOLT.spawn(level, entity.getOnPos(), EntitySpawnReason.TRIGGERED);
            level.addParticle(ModParticles.LIGHTNING_PARTICLE.get(), entity.getX(), entity.getY()+1, entity.getZ(), 0,0,0);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
