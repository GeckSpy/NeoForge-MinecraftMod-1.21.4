package net.geckspy.geckspymm.event;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.entity.elephant.ElephantEntity;
import net.geckspy.geckspymm.entity.elephant.ElephantModel;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritEntity;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritModel;
import net.geckspy.geckspymm.entity.rhinoceros.RhinocerosEntity;
import net.geckspy.geckspymm.entity.rhinoceros.RhinocerosModel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = MyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(OriumSpiritModel.LAYER_LOCATION, OriumSpiritModel::createBodyLayer);
        event.registerLayerDefinition(ElephantModel.LAYER_LOCATION, ElephantModel::createBodyLayer);
        event.registerLayerDefinition(RhinocerosModel.LAYER_LOCATION, RhinocerosModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAtrributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.ORIUM_SPIRIT.get(), OriumSpiritEntity.createAttributes().build());
        event.put(ModEntities.ELEPHANT.get(), ElephantEntity.createAttributes().build());
        event.put(ModEntities.RHINOCEROS.get(), RhinocerosEntity.createAttributes().build());
    }
}
