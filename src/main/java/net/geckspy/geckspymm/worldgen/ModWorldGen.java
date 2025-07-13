package net.geckspy.geckspymm.worldgen;

import net.geckspy.geckspymm.worldgen.tree.ModTrunkPlacer;
import net.neoforged.bus.api.IEventBus;

public class ModWorldGen {
    public static void register(IEventBus bus){
        ModTrunkPlacer.register(bus);
    }
}
