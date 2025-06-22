package net.geckspy.geckspymm.entity.spear;

import net.geckspy.geckspymm.item.custom.SpearItem;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.item.ToolMaterial;

public class ThrownSpearRenderState extends EntityRenderState {
    public SpearItem spearItem;
    public float xRot;
    public float yRot;
    public boolean isFoil;
}
