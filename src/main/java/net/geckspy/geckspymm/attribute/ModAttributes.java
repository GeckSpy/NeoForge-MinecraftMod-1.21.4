package net.geckspy.geckspymm.attribute;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MyMod.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> ENTITY_INTERACTION_RANGE =
            ATTRIBUTES.register("entity_interaction_range",
                    () -> new RangedAttribute(
                            Component.translatable("attribute.name.geckspymm.entity_interaction_range").getString(),
                            0.0, 0.0, 100.0));

    public static final DeferredHolder<Attribute, Attribute> BLOCK_INTERACTION_RANGE =
            ATTRIBUTES.register("block_interaction_range",
                    () -> new RangedAttribute(
                            Component.translatable("attribute.name.geckspymm.block_interaction_range").getString(),
                            0.0, 0.0, 100.0));

    public static final DeferredHolder<Attribute, Attribute> ARMOR_EFFECT =
            ATTRIBUTES.register("armor_effect",
                    () -> new RangedAttribute(
                            Component.translatable("attribute.name.geckspymm.armor_effect").getString(),
                            0.0, 0.0, 100.0));

    public static void register(IEventBus eventBus){
        ATTRIBUTES.register(eventBus);
    }
}
