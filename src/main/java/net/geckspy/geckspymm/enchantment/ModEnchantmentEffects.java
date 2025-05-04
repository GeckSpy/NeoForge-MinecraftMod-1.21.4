package net.geckspy.geckspymm.enchantment;

import com.mojang.serialization.MapCodec;
import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.enchantment.custom.ParticleSummonerEnchantmentEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {
    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, MyMod.MOD_ID);

    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> PARTICLE_SUMMONER =
            ENTITY_ENCHANTMENT_EFFECTS.register("particle_summoner", ()-> ParticleSummonerEnchantmentEffect.CODEC);


    public static void register(IEventBus eventBus){
        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
    }
}
