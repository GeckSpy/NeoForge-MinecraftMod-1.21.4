package net.geckspy.geckspymm.entity;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MyMod.MOD_ID);

    public static final ResourceKey<EntityType<?>> TNT_V2_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "tnt_v2"));
    public static final Supplier<EntityType<PrimedTntV2>> TNT_V2 = ENTITY_TYPES.register("tnt_v2",
            ()->EntityType.Builder.<PrimedTntV2>of(PrimedTntV2::new, MobCategory.MISC)
                    .sized(1.1F, 1.1F)
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .build(TNT_V2_KEY));


    public static final ResourceKey<EntityType<?>> TNT_V3_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "tnt_v3"));
    public static final Supplier<EntityType<PrimedTntV3>> TNT_V3 = ENTITY_TYPES.register("tnt_v3",
            ()->EntityType.Builder.<PrimedTntV3>of(PrimedTntV3::new, MobCategory.MISC)
                    .sized(1.3F, 1.3F)
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .build(TNT_V3_KEY));


    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
