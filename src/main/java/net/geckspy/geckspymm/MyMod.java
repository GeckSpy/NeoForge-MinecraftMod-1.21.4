package net.geckspy.geckspymm;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.effect.ModEffects;
import net.geckspy.geckspymm.enchantment.ModEnchantmentEffects;
import net.geckspy.geckspymm.item.ModCreativeModeTabs;
import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.item.custom.HalberdItem;
import net.geckspy.geckspymm.item.custom.ModArmorItem;
import net.geckspy.geckspymm.particle.LightningParticle;
import net.geckspy.geckspymm.particle.ModParticles;
import net.geckspy.geckspymm.potion.ModPotions;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;

import it.unimi.dsi.fastutil.Pair;
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

import java.util.List;
import java.util.Random;


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
        ModEffects.register(modEventBus);
        ModPotions.register(modEventBus);



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
            event.accept(ModItems.PURE_QUARTZ_UPGRADE_SMITHING_TEMPLATE);
            event.accept(ModItems.IMPURE_END_CRISTAL);
            event.accept(ModItems.END_CRISTAL);
        }
        else if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.IMPURE_END_CRISTAL_BLOCK);
            event.accept(ModBlocks.END_CRISTAL_BLOCK);
        }
        else if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(ModItems.COPPER_SHOVEL);
            event.accept(ModItems.COPPER_PICKAXE);
            event.accept(ModItems.COPPER_AXE);
            event.accept(ModItems.COPPER_HOE);

            event.accept(ModItems.PURE_QUARTZ_SHOVEL);
            event.accept(ModItems.PURE_QUARTZ_PICKAXE);
            event.accept(ModItems.PURE_QUARTZ_AXE);
            event.accept(ModItems.PURE_QUARTZ_HOE);
        }
        else if(event.getTabKey() == CreativeModeTabs.COMBAT){
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
            event.accept(ModItems.PURE_QUARTZ_SWORD);


            event.accept(ModItems.WOODEN_HALBERD);
            event.accept(ModItems.STONE_HALBERD);
            event.accept(ModItems.COPPER_HALBERD);
            event.accept(ModItems.IRON_HALBERD);
            event.accept(ModItems.GOLDEN_HALBERD);
            event.accept(ModItems.DIAMOND_HALBERD);
            event.accept(ModItems.PURE_QUARTZ_HALBERD);
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

    @SubscribeEvent
    public void onGatherModifiers(ItemAttributeModifierEvent event) {
        // Only modify main‐hand items
        HalberdItem.onGatherModifier(event);

    }

    @SubscribeEvent
    public void onEquipmentChange(LivingEquipmentChangeEvent event){
        ModArmorItem.onEquipmentChange(event);
    }

    @SubscribeEvent
    public void onEntityTickEvent(EntityTickEvent.Post event){

    }

    @SubscribeEvent
    public void onEffectRemoved(MobEffectEvent.Remove event){
        ModEffects.onEffectRemoved(event);
    }

    @SubscribeEvent
    public void onEffectExpired(MobEffectEvent.Expired event){
        ModEffects.onEffectExpired(event);
    }

    @SubscribeEvent
    public void registerBrewingRecipes(RegisterBrewingRecipesEvent event){
        PotionBrewing.Builder builder = event.getBuilder();
        builder.addMix(Potions.AWKWARD, Items.RED_MUSHROOM, ModPotions.GIGANTISM_POTION);
        builder.addMix(ModPotions.GIGANTISM_POTION, Items.GLOWSTONE_DUST, ModPotions.GIGANTISM_2_POTION);
        builder.addMix(ModPotions.GIGANTISM_2_POTION, Items.GLOWSTONE_DUST, ModPotions.GIGANTISM_3_POTION);
        builder.addMix(ModPotions.GIGANTISM_3_POTION, Items.GLOWSTONE_DUST, ModPotions.GIGANTISM_4_POTION);
    }

    @SubscribeEvent
    public void onMobSpawn(FinalizeSpawnEvent event) {
        // Store spawn type in entity's persistent data
        event.getEntity().getPersistentData().putString("spawn_reason", event.getSpawnType().name());
    }

    public static final List<Triple<Holder<Attribute>, Double, Double>> ATTRIBUTE_MODIFIER_MOB_SPAWNING = List.of(
            Triple.of(Attributes.SCALE, 0.2, 0.05),
            Triple.of(Attributes.ATTACK_DAMAGE, 0.1, 0.0),
            Triple.of(Attributes.MOVEMENT_SPEED, 0.3, 0.0)
    );
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event){
        if(event.getEntity() instanceof LivingEntity entity && !(entity instanceof Player) && !event.getLevel().isClientSide()) {
            String spawnType = entity.getPersistentData().getString("spawn_reason");
            if(!(spawnType.equals("BUCKET") || spawnType.equals("COMMAND")
                    || spawnType.equals("LOAD") || spawnType.equals("DIMENSION_TRAVEL"))) {

                for (var pair : ATTRIBUTE_MODIFIER_MOB_SPAWNING) {
                    var attribute = entity.getAttribute(pair.getLeft());
                    if (attribute != null && attribute.getModifier(ModAttributes.MOB_SPAWNING_MODIFIER.getId())==null) {
                        attribute.addTransientModifier(new AttributeModifier(
                                ModAttributes.MOB_SPAWNING_MODIFIER.getId(),
                                new Random().nextGaussian()*pair.getMiddle() + pair.getRight(),
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    }
                }
            }
        }

    }
}
