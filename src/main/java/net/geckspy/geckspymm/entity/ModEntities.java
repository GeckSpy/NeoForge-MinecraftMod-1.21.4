package net.geckspy.geckspymm.entity;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.entity.ent.EntEntity;
import net.geckspy.geckspymm.entity.animals.elephant.ElephantEntity;
import net.geckspy.geckspymm.entity.animals.giraffe.GiraffeEntity;
import net.geckspy.geckspymm.entity.animals.lion.LionEntity;
import net.geckspy.geckspymm.entity.ghost.GhostEntity;
import net.geckspy.geckspymm.entity.neider.NeiderEntity;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritEntity;
import net.geckspy.geckspymm.entity.custom.PrimedTntV2;
import net.geckspy.geckspymm.entity.custom.PrimedTntV3;
import net.geckspy.geckspymm.entity.animals.penguin.PenguinEntity;
import net.geckspy.geckspymm.entity.animals.rhinoceros.RhinocerosEntity;
import net.geckspy.geckspymm.entity.animals.snow_panther.SnowPantherEntity;
import net.geckspy.geckspymm.entity.animals.tiger.TigerEntity;
import net.geckspy.geckspymm.entity.spear.ThrownSpear;
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

    // Weapons
        // TNT
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

    //mobs
    public static final ResourceKey<EntityType<?>> SPEAR_ENTITY_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "spear_entity"));
    public static final Supplier<EntityType<ThrownSpear>> SPEAR_ENTITY = ENTITY_TYPES.register("spear_entity",
            ()->EntityType.Builder.<ThrownSpear>of(ThrownSpear::new, MobCategory.MISC)
                    .sized(0.2f, 0.2f).build(SPEAR_ENTITY_KEY));



    //mobs
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

    public static final ResourceKey<EntityType<?>> GIRAFFE_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "giraffe"));
    public static final Supplier<EntityType<GiraffeEntity>> GIRAFFE = ENTITY_TYPES.register("giraffe",
            ()->EntityType.Builder.of(GiraffeEntity::new, MobCategory.CREATURE)
                    .sized(2.6f, 3.5f).build(GIRAFFE_KEY));

    public static final ResourceKey<EntityType<?>> TIGER_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "tiger"));
    public static final Supplier<EntityType<TigerEntity>> TIGER = ENTITY_TYPES.register("tiger",
            ()->EntityType.Builder.of(TigerEntity::new, MobCategory.CREATURE)
                    .sized(1.8f, 1.5f).build(TIGER_KEY));

    public static final ResourceKey<EntityType<?>> LION_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "lion"));
    public static final Supplier<EntityType<LionEntity>> LION = ENTITY_TYPES.register("lion",
            ()->EntityType.Builder.of(LionEntity::new, MobCategory.CREATURE)
                    .sized(1.8f, 1.5f).build(LION_KEY));

    public static final ResourceKey<EntityType<?>> SNOW_PANTHER_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "snow_panther"));
    public static final Supplier<EntityType<SnowPantherEntity>> SNOW_PANTHER = ENTITY_TYPES.register("snow_panther",
            ()->EntityType.Builder.of(SnowPantherEntity::new, MobCategory.CREATURE)
                    .sized(1.9f, 1.6f).build(SNOW_PANTHER_KEY));

    public static final ResourceKey<EntityType<?>> PENGUIN_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "penguin"));
    public static final Supplier<EntityType<PenguinEntity>> PENGUIN = ENTITY_TYPES.register("penguin",
            ()->EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE)
                    .sized(.5f, 1f).build(PENGUIN_KEY));



    // Mobs
    public static final ResourceKey<EntityType<?>> GHOST_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "ghost"));
    public static final Supplier<EntityType<GhostEntity>> GHOST = ENTITY_TYPES.register("ghost",
            ()->EntityType.Builder.of(GhostEntity::new, MobCategory.MONSTER)
                    .sized(.8f, 1.8f).build(GHOST_KEY));

    public static final ResourceKey<EntityType<?>> ENT_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "ent"));
    public static final Supplier<EntityType<EntEntity>> ENT = ENTITY_TYPES.register("ent",
            ()->EntityType.Builder.of(EntEntity::new, MobCategory.MONSTER)
                    .sized(1.2f, 2.4f).build(ENT_KEY));


    public static final ResourceKey<EntityType<?>> NEIDER_KEY = ResourceKey.create(
            Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MyMod.MOD_ID, "neider"));
    public static final Supplier<EntityType<NeiderEntity>> NEIDER = ENTITY_TYPES.register("neider",
            ()->EntityType.Builder.of(NeiderEntity::new, MobCategory.MONSTER)
                    .sized(1.2f, 2.4f).build(NEIDER_KEY));



    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
