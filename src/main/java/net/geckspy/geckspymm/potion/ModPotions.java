package net.geckspy.geckspymm.potion;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, MyMod.MOD_ID);


    public static final Holder<Potion> GIGANTISM_POTION = POTIONS.register("gigantism_potion", registerName ->
            new Potion(registerName.getPath(), new MobEffectInstance(ModEffects.GIGANTISM, 3600, 0)));

    public static final Holder<Potion> GIGANTISM_2_POTION = POTIONS.register("gigantism_2_potion", registerName ->
            new Potion(registerName.getPath(), new MobEffectInstance(ModEffects.GIGANTISM, 1800, 1)));

    public static final Holder<Potion> GIGANTISM_3_POTION = POTIONS.register("gigantism_3_potion", registerName ->
            new Potion(registerName.getPath(), new MobEffectInstance(ModEffects.GIGANTISM, 1200, 2)));

    public static final Holder<Potion> GIGANTISM_4_POTION = POTIONS.register("gigantism_4_potion", registerName ->
            new Potion(registerName.getPath(), new MobEffectInstance(ModEffects.GIGANTISM, 600, 3)));


    public static final void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }

}
