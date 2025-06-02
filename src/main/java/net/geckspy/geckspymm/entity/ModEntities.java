package net.geckspy.geckspymm.entity;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.entity.elephant.ElephantEntity;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritEntity;
import net.geckspy.geckspymm.entity.custom.PrimedTntV2;
import net.geckspy.geckspymm.entity.custom.PrimedTntV3;
import net.geckspy.geckspymm.entity.rhinoceros.RhinocerosEntity;
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
                    .build(TNT_V2_KEY)
    );


    public static final ResourceKey<EntityType<?>> TNT_V3_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "tnt_v3"));
    public static final Supplier<EntityType<PrimedTntV3>> TNT_V3 = ENTITY_TYPES.register("tnt_v3",
            ()->EntityType.Builder.<PrimedTntV3>of(PrimedTntV3::new, MobCategory.MISC)
                    .sized(1.3F, 1.3F)
                    .clientTrackingRange(10)
                    .updateInterval(10)
                    .build(TNT_V3_KEY)
    );


    public static final ResourceKey<EntityType<?>> ORIUM_SPIRIT_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "orium_spirit"));
    public static final Supplier<EntityType<OriumSpiritEntity>> ORIUM_SPIRIT = ENTITY_TYPES.register("orium_spirit",
            ()->EntityType.Builder.of(OriumSpiritEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.8f).build(ORIUM_SPIRIT_KEY));

    // Animals
    public static final ResourceKey<EntityType<?>> ELEPHANT_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "elephant"));
    public static final Supplier<EntityType<ElephantEntity>> ELEPHANT = ENTITY_TYPES.register("elephant",
            ()->EntityType.Builder.of(ElephantEntity::new, MobCategory.CREATURE)
                    .sized(2.7f, 3.85f).build(ELEPHANT_KEY));

    public static final ResourceKey<EntityType<?>> RHINOCEROS_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "rhinoceros"));
    public static final Supplier<EntityType<RhinocerosEntity>> RHINOCEROS = ENTITY_TYPES.register("rhinoceros",
            ()->EntityType.Builder.of(RhinocerosEntity::new, MobCategory.CREATURE)
                    .sized(2.6f, 2.3f).build(RHINOCEROS_KEY));





    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
