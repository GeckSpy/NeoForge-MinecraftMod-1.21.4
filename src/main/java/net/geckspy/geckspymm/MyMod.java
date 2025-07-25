package net.geckspy.geckspymm;

import net.geckspy.geckspymm.attribute.ModAttributes;
import net.geckspy.geckspymm.block.ModBlocks;
import net.geckspy.geckspymm.block.entity.ModBlockEntities;
import net.geckspy.geckspymm.effect.ModEffects;
import net.geckspy.geckspymm.effect.UndyingEffect;
import net.geckspy.geckspymm.enchantment.ModEnchantmentEffects;
import net.geckspy.geckspymm.entity.ent.EntRenderer;
import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.entity.animals.elephant.ElephantRenderer;
import net.geckspy.geckspymm.entity.animals.giraffe.GiraffeRenderer;
import net.geckspy.geckspymm.entity.animals.lion.LionRenderer;
import net.geckspy.geckspymm.entity.ghost.GhostRenderer;
import net.geckspy.geckspymm.entity.neider.NeiderEntity;
import net.geckspy.geckspymm.entity.neider.NeiderRenderer;
import net.geckspy.geckspymm.entity.orium_spirit.OriumSpiritRenderer;
import net.geckspy.geckspymm.entity.animals.penguin.PenguinRenderer;
import net.geckspy.geckspymm.entity.renderer.PrimedTntV2Renderer;
import net.geckspy.geckspymm.entity.renderer.PrimedTntV3Renderer;
import net.geckspy.geckspymm.entity.animals.rhinoceros.RhinocerosRenderer;
import net.geckspy.geckspymm.entity.animals.snow_panther.SnowPantherRenderer;
import net.geckspy.geckspymm.entity.animals.tiger.TigerRenderer;
import net.geckspy.geckspymm.entity.spear.ThrownSpearRenderer;
import net.geckspy.geckspymm.item.ModCreativeModeTabs;
import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.item.custom.HalberdItem;
import net.geckspy.geckspymm.item.custom.ModArmorItem;
import net.geckspy.geckspymm.particle.LightningParticle;
import net.geckspy.geckspymm.particle.ModParticles;
import net.geckspy.geckspymm.particle.OriumParticle;
import net.geckspy.geckspymm.potion.ModPotions;
import net.geckspy.geckspymm.recipe.ModRecipes;
import net.geckspy.geckspymm.screen.MagicCraftingTableScreen;
import net.geckspy.geckspymm.screen.MergerBlockScreen;
import net.geckspy.geckspymm.screen.ModMenuTypes;
import net.geckspy.geckspymm.worldgen.ModWorldGen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEquipmentChangeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.apache.commons.lang3.tuple.Triple;
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
        ModWorldGen.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModEntities.register(modEventBus);

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
            event.accept(ModItems.PEPPER_SEEDS);

            event.accept(ModItems.RHINOCEROS_HORN);
            event.accept(ModItems.ELEPHANT_TUSK);
            event.accept(ModItems.DAMNED_SOUL);

            event.accept(ModItems.PURE_QUARTZ);
            event.accept(ModItems.IMPURE_END_CRISTAL);
            event.accept(ModItems.END_CRISTAL);
            event.accept(ModItems.PURE_QUARTZ_UPGRADE_SMITHING_TEMPLATE);

            event.accept(ModItems.VANILLA_SMITHING_TEMPLATE);
            event.accept(ModItems.THIEF_SMITHING_TEMPLATE);
            event.accept(ModItems.WARRIOR_SMITHING_TEMPLATE);
            event.accept(ModItems.GLADIATOR_SMITHING_TEMPLATE);
            event.accept(ModItems.DESERT_SMITHING_TEMPLATE);
            event.accept(ModItems.KNIGHT_SMITHING_TEMPLATE);
            event.accept(ModItems.ROBOT_SMITHING_TEMPLATE);
        }else if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(ModItems.FRIED_EGG);
            event.accept(ModItems.LEEK);
            event.accept(ModItems.PEPPER);
            event.accept(ModItems.RED_PEPPER);
        }
        else if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModItems.ENT_HEAD_ITEM);
            event.accept(ModItems.ENT_HEAD_LIT_ITEM);
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

            event.accept(ModItems.WOOD_SPEAR);
            event.accept(ModItems.STONE_SPEAR);
            event.accept(ModItems.COPPER_SPEAR);
            event.accept(ModItems.IRON_SPEAR);
            event.accept(ModItems.GOLDEN_SPEAR);
            event.accept(ModItems.DIAMOND_SPEAR);
            event.accept(ModItems.PURE_QUARTZ_SPEAR);
            event.accept(ModItems.NETHERITE_SPEAR);
        }
        else if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS){
            event.accept(ModItems.WIRES);
            event.accept(ModItems.CIRCUIT_BOARD);
            event.accept(ModItems.ORIUM_ORB);
            event.accept(ModBlocks.ORIUM_TORCH);
            event.accept(ModBlocks.MERGER_BLOCK);
            event.accept(ModBlocks.BATTERY);
            event.accept(ModBlocks.CONTROL_UNIT);
            event.accept(ModBlocks.TNT_V2);
            event.accept(ModBlocks.TNT_V3);
        }
        else if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS){
            event.accept(ModItems.RHINOCEROS_SPAWN_EGG);
            event.accept(ModItems.ELEPHANT_SPAWN_EGG);
            event.accept(ModItems.GIRAFFE_SPAWN_EGG);
            event.accept(ModItems.LION_SPAWN_EGG);
            event.accept(ModItems.TIGER_SPAWN_EGG);
            event.accept(ModItems.SNOW_PANTHER_SPAWN_EGG);
            event.accept(ModItems.PENGUIN_SPAWN_EGG);

            event.accept(ModItems.GHOST_SPAWN_EGG);
            event.accept(ModItems.ENT_SPAWN_EGG);
            event.accept(ModItems.NEIDER_SPAWN_EGG);
            event.accept(ModItems.ORIUM_SPIRIT_SPAWN_EGG);

        }
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        ModRecipes.loadRecipe(event);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.ORIUM_SPIRIT.get(), OriumSpiritRenderer::new);
            EntityRenderers.register(ModEntities.ELEPHANT.get(), ElephantRenderer::new);
            EntityRenderers.register(ModEntities.RHINOCEROS.get(), RhinocerosRenderer::new);
            EntityRenderers.register(ModEntities.GIRAFFE.get(), GiraffeRenderer::new);
            EntityRenderers.register(ModEntities.LION.get(), LionRenderer::new);
            EntityRenderers.register(ModEntities.TIGER.get(), TigerRenderer::new);
            EntityRenderers.register(ModEntities.SNOW_PANTHER.get(), SnowPantherRenderer::new);
            EntityRenderers.register(ModEntities.PENGUIN.get(), PenguinRenderer::new);

            EntityRenderers.register(ModEntities.GHOST.get(), GhostRenderer::new);
            EntityRenderers.register(ModEntities.ENT.get(), EntRenderer::new);
            EntityRenderers.register(ModEntities.NEIDER.get(), NeiderRenderer::new);

            EntityRenderers.register(ModEntities.SPEAR_ENTITY.get(), ThrownSpearRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEPPER_CROP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.LEEK_CROP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BATTERY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.TREND_FLOWER.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.POTTED_TREND_FLOWER.get(), RenderType.cutout());
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(ModParticles.LIGHTNING_PARTICLE.get(), LightningParticle.Provider::new);
            event.registerSpriteSet(ModParticles.ORIUM_PARTICLE.get(), OriumParticle.Provider::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event){
            event.register(ModMenuTypes.MERGER_BLOCK_MENU.get(), MergerBlockScreen::new);
            event.register(ModMenuTypes.MAGIC_CRAFTING_TABLE_MENU.get(), MagicCraftingTableScreen::new);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(ModEntities.TNT_V2.get(), PrimedTntV2Renderer::new);
            event.registerEntityRenderer(ModEntities.TNT_V3.get(), PrimedTntV3Renderer::new);
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
    public void onLivingEntityDeath(LivingDeathEvent event){
        if(event.getEntity().hasEffect(ModEffects.UNDYING)){
            event.setCanceled(true);
            UndyingEffect.onEntityDeath(event.getEntity());
        }
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
        builder.addMix(Potions.AWKWARD, ModItems.ENT_HEAD_ITEM.get(), ModPotions.GIGANTISM_POTION);
        builder.addMix(ModPotions.GIGANTISM_POTION, Items.GLOWSTONE_DUST, ModPotions.GIGANTISM_2_POTION);
        builder.addMix(ModPotions.GIGANTISM_2_POTION, Items.GLOWSTONE_DUST, ModPotions.GIGANTISM_3_POTION);
        builder.addMix(ModPotions.GIGANTISM_3_POTION, Items.GLOWSTONE_DUST, ModPotions.GIGANTISM_4_POTION);

        builder.addMix(Potions.AWKWARD, ModItems.ENT_HEAD_LIT_ITEM.get(), ModPotions.MINIATURISM_POTION);
        builder.addMix(ModPotions.MINIATURISM_POTION, Items.GLOWSTONE_DUST, ModPotions.MINIATURISM_2_POTION);
        builder.addMix(ModPotions.MINIATURISM_2_POTION, Items.GLOWSTONE_DUST, ModPotions.MINIATURISM_3_POTION);
        builder.addMix(ModPotions.MINIATURISM_3_POTION, Items.GLOWSTONE_DUST, ModPotions.MINIATURISM_4_POTION);

        builder.addMix(Potions.AWKWARD, Items.TOTEM_OF_UNDYING, ModPotions.UNDYING_POTION);
    }

    @SubscribeEvent
    public void onMobSpawn(FinalizeSpawnEvent event) {
        // Store spawn type in entity's persistent data
        event.getEntity().getPersistentData().putString("spawn_reason", event.getSpawnType().name());
        //System.out.println(event.getSpawnType().name());
        event.getEntity().getPersistentData().putBoolean("got_spawning_modifier", false);
    }

    public static final List<Triple<Holder<Attribute>, Double, Double>> ATTRIBUTE_MODIFIER_MOB_SPAWNING = List.of(
            Triple.of(Attributes.SCALE, 0.15, 0.05),
            Triple.of(Attributes.ATTACK_DAMAGE, 0.1, 0.0),
            Triple.of(Attributes.MOVEMENT_SPEED, 0.15, 0.0)
    );
    public static boolean canHaveSpawnModifiers(Entity entityEvent){
        if(entityEvent instanceof LivingEntity entity){
            if(entity instanceof Player || entity instanceof ArmorStand || entity instanceof Shulker){return false;}
            if(entity.getPersistentData().getBoolean("got_spawning_modifier")){return false;}
            String spawnType = entity.getPersistentData().getString("spawn_reason");
            if(spawnType.equals("BUCKET") || spawnType.equals("COMMAND") || spawnType.equals("LOAD") || spawnType.equals("DIMENSION_TRAVEL")){return false;}
            return true;
        }else{
            return false;
        }
    };
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinLevelEvent event){
        if(!event.getLevel().isClientSide() && canHaveSpawnModifiers(event.getEntity())) {
            LivingEntity entity = (LivingEntity)event.getEntity();

            for (var pair : ATTRIBUTE_MODIFIER_MOB_SPAWNING) {
                var attribute = entity.getAttribute(pair.getLeft());
                if (attribute!=null && attribute.getModifier(ModAttributes.MOB_SPAWNING_MODIFIER.getId())==null && false) {
                    attribute.addPermanentModifier(new AttributeModifier(
                            ModAttributes.MOB_SPAWNING_MODIFIER.getId(),
                            new Random().nextGaussian() * pair.getMiddle() + pair.getRight(),
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                }
            }
        }
        if(event.getEntity() instanceof ArmorStand armorStand){
            armorStand.setShowArms(true);
        }
    }


}
