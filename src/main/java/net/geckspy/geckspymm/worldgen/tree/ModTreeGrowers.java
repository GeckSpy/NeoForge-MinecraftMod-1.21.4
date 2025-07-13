package net.geckspy.geckspymm.worldgen.tree;


import net.geckspy.geckspymm.MyMod;
import net.geckspy.geckspymm.worldgen.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower TREND = new TreeGrower(MyMod.MOD_ID + ":trend",
            Optional.empty(), Optional.of(ModConfiguredFeatures.TREND_KEY), Optional.empty());

}