package net.geckspy.geckspymm.effect;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, MyMod.MOD_ID);

    public static final Holder<MobEffect> GIGANTISM = MOB_EFFECTS.register("gigantism",
            ()->new GigantismEffect(MobEffectCategory.BENEFICIAL, 0xe21212));

    public static final Holder<MobEffect> MINIATURISM = MOB_EFFECTS.register("miniaturism",
            ()->new MiniaturismEffect(MobEffectCategory.NEUTRAL, 0xcc9978));

    public static final Holder<MobEffect> UNDYING = MOB_EFFECTS.register("undying_effect",
            ()->new UndyingEffect(MobEffectCategory.BENEFICIAL, 0x41e67f));




    public static void onEffectRemoved(MobEffectEvent.Remove event){
        if(event.getEffectInstance().is(ModEffects.GIGANTISM)) {
            GigantismEffect.onEffectEnded(event.getEntity());
        }else if(event.getEffectInstance().is(ModEffects.MINIATURISM)){
            MiniaturismEffect.onEffectEnded(event.getEntity());
        }
    }

    public static void onEffectExpired(MobEffectEvent.Expired event){
        if(event.getEffectInstance().is(ModEffects.GIGANTISM)){
            GigantismEffect.onEffectEnded(event.getEntity());
        }else if(event.getEffectInstance().is(ModEffects.MINIATURISM)){
            MiniaturismEffect.onEffectEnded(event.getEntity());
        }
    }

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
