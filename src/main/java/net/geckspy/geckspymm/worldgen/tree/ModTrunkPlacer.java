package net.geckspy.geckspymm.worldgen.tree;

import net.geckspy.geckspymm.MyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModTrunkPlacer {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, MyMod.MOD_ID);

    public static final Supplier<TrunkPlacerType<TrendBaseTrunkPlacer>> TREND_TRUNK_PLACER =
            TRUNK_PLACERS.register("trend_trunk_placer", () -> new TrunkPlacerType<>(TrendBaseTrunkPlacer.CODEC));

    public static void register(IEventBus bus) {
        TRUNK_PLACERS.register(bus);
    }
}
