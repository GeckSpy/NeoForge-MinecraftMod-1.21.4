package net.geckspy.geckspymm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class LightningParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public LightningParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet,
                            double xSpeed, double ySpeed, double zSpeed){
        super(level, x, y, z);
        this.spriteSet = spriteSet;

        this.gravity = -0.1F;
        this.lifetime = 10;

        this.xd = xSpeed + (Math.random() * 2.0 - 1.0) * 0.08F;
        this.yd = ySpeed + (Math.random() * 2.0 - 1.0) * 0.08F;
        this.zd = zSpeed + (Math.random() * 2.0 - 1.0) * 0.08F;


        this.setSpriteFromAge(spriteSet);
    }

    /*
    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }
    */

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new LightningParticle(level, x, y, z, this.spriteSet, xSpeed, ySpeed, zSpeed);
        }
    };

}
