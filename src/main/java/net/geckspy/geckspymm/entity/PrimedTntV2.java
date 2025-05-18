package net.geckspy.geckspymm.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class PrimedTntV2 extends PrimedTnt {
    private float EXPLOSION_POWER = 10.0F;
    private int FUSE_TIME = 120;

    public PrimedTntV2(EntityType<? extends PrimedTnt> type, Level level) {
        super(type, level);
    }

    public PrimedTntV2(Level level, double x, double y, double z, @Nullable LivingEntity owner){
        super(level, x, y, z, owner);
        this.setFuse(FUSE_TIME);
    }

    @Override
    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(),
                this.EXPLOSION_POWER, false, Level.ExplosionInteraction.TNT);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.TNT_V2.get();
    }
}
