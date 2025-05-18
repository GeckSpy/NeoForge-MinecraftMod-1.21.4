package net.geckspy.geckspymm.entity;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PrimedTntV3 extends PrimedTnt {
    private final float EXPLOSION_POWER = 20.0F;
    private final int FUSE_TIME = 145;

    public PrimedTntV3(EntityType<? extends PrimedTnt> type, Level level) {
        super(type, level);
    }

    public PrimedTntV3(Level level, double x, double y, double z, @Nullable LivingEntity owner){
        super(level, x, y, z, owner);
        this.setFuse(FUSE_TIME);
    }

    @Override
    protected void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625), this.getZ(),
                this.EXPLOSION_POWER, false, Level.ExplosionInteraction.TNT);
    }

    @Override
    @NotNull
    public EntityType<?> getType() {
        return ModEntities.TNT_V3.get();
    }
}
