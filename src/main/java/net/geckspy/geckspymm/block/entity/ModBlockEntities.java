package net.geckspy.geckspymm.block.entity;

import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MyMod.MOD_ID);


    public static final Supplier<BlockEntityType<MergerBlockEntity>> MERGER_BLOCK_BE =
            BLOCK_ENTITIES.register("merger_block_be", ()-> new BlockEntityType<>(
                        MergerBlockEntity::new,
                        ModBlocks.MERGER_BLOCK.get()
                    ));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
