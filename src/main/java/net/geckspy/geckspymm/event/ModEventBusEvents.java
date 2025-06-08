package net.geckspy.geckspymm.event;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.entity.elephant.ElephantEntity;
import net.geckspy.geckspymm.entity.elephant.ElephantModel;
import net.geckspy.geckspymm.entity.giraffe.GiraffeEntity;
import net.geckspy.geckspymm.entity.giraffe.GiraffeModel;
import net.geckspy.geckspymm.entity.lion.LionEntity;
import net.geckspy.geckspymm.entity.lion.LionModel;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritEntity;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritModel;
import net.geckspy.geckspymm.entity.penguin.PenguinEntity;
import net.geckspy.geckspymm.entity.penguin.PenguinModel;
import net.geckspy.geckspymm.entity.rhinoceros.RhinocerosEntity;
import net.geckspy.geckspymm.entity.rhinoceros.RhinocerosModel;
import net.geckspy.geckspymm.entity.snow_panther.SnowPantherModel;
import net.geckspy.geckspymm.entity.tiger.TigerEntity;
import net.geckspy.geckspymm.entity.tiger.TigerModel;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = MyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(OriumSpiritModel.LAYER_LOCATION, OriumSpiritModel::createBodyLayer);
        event.registerLayerDefinition(ElephantModel.LAYER_LOCATION, ElephantModel::createBodyLayer);
        event.registerLayerDefinition(RhinocerosModel.LAYER_LOCATION, RhinocerosModel::createBodyLayer);
        event.registerLayerDefinition(GiraffeModel.LAYER_LOCATION, GiraffeModel::createBodyLayer);
        event.registerLayerDefinition(TigerModel.LAYER_LOCATION, TigerModel::createBodyLayer);
        event.registerLayerDefinition(LionModel.LAYER_LOCATION, LionModel::createBodyLayer);
        event.registerLayerDefinition(SnowPantherModel.LAYER_LOCATION, SnowPantherModel::createBodyLayer);
        event.registerLayerDefinition(PenguinModel.LAYER_LOCATION, PenguinModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAtrributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.ORIUM_SPIRIT.get(), OriumSpiritEntity.createAttributes().build());
        event.put(ModEntities.ELEPHANT.get(), ElephantEntity.createAttributes().build());
        event.put(ModEntities.RHINOCEROS.get(), RhinocerosEntity.createAttributes().build());
        event.put(ModEntities.GIRAFFE.get(), GiraffeEntity.createAttributes().build());
        event.put(ModEntities.TIGER.get(), TigerEntity.createAttributes().build());
        event.put(ModEntities.LION.get(), LionEntity.createAttributes().build());
        event.put(ModEntities.SNOW_PANTHER.get(), LionEntity.createAttributes().build());
        event.put(ModEntities.PENGUIN.get(), PenguinEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event){
        event.register(ModEntities.RHINOCEROS.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.ELEPHANT.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.GIRAFFE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.TIGER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.LION.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.SNOW_PANTHER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(ModEntities.PENGUIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
