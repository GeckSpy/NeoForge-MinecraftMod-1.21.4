package net.geckspy.geckspymm;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.enchantment.ModEnchantmentEffects;
import net.geckspy.geckspymm.item.ModCreativeModeTabs;
import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.item.custom.HalberdItem;
import net.geckspy.geckspymm.particle.LightningParticle;
import net.geckspy.geckspymm.particle.ModParticles;
import net.minecraft.ResourceLocationException;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MyMod.MOD_ID)
public class MyMod {
    public static final String MOD_ID = "geckspymm";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public MyMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);
        ModAttributes.register(modEventBus);
        ModEnchantmentEffects.register(modEventBus);



        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.PURE_QUARTZ);
            event.accept(ModItems.IMPURE_END_CRISTAL);
            event.accept(ModItems.END_CRISTAL);
        }
        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.IMPURE_END_CRISTAL_BLOCK);
            event.accept(ModBlocks.END_CRISTAL_BLOCK);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.COPPER_SHOVEL);
            event.accept(ModItems.COPPER_PICKAXE);
            event.accept(ModItems.COPPER_AXE);
            event.accept(ModItems.COPPER_HOE);
        }
        if(event.getTabKey() == CreativeModeTabs.COMBAT){
            event.accept(ModItems.COPPER_SWORD);
            event.accept(ModItems.COPPER_HELMET);
            event.accept(ModItems.COPPER_CHESTPLATE);
            event.accept(ModItems.COPPER_LEGGINGS);
            event.accept(ModItems.COPPER_BOOTS);
            event.accept(ModItems.COPPER_HORSE_ARMOR);

            event.accept(ModItems.PURE_QUARTZ_HELMET);
            event.accept(ModItems.PURE_QUARTZ_CHESTPLATE);
            event.accept(ModItems.PURE_QUARTZ_LEGGINGS);
            event.accept(ModItems.PURE_QUARTZ_BOOTS);
            event.accept(ModItems.PURE_QUARTZ_HORSE_ARMOR);

            event.accept(ModItems.WOODEN_HALBERD);
            event.accept(ModItems.STONE_HALBERD);
            event.accept(ModItems.COPPER_HALBERD);
            event.accept(ModItems.IRON_HALBERD);
            event.accept(ModItems.GOLDEN_HALBERD);
            event.accept(ModItems.DIAMOND_HALBERD);
            event.accept(ModItems.NETHERITE_HALBERD);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(ModParticles.LIGHTNING_PARTICLE.get(), LightningParticle.Provider::new);
        }
    }

    public boolean HALBERD_IN_HAND = false;
    @SubscribeEvent
    public void onGatherModifiers(ItemAttributeModifierEvent event) {
        // Only modify main‐hand items
        HalberdItem.onGatherModifier(event);
    }
}
