package net.geckspy.geckspymm.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import java.util.Random;

public class OriumParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public OriumParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet,
                         double xSpeed, double rotation, double scale){
        super(level, x, y, z);
        this.spriteSet = spriteSet;

        this.lifetime = 18;
        this.roll = (float) (Math.random() * Math.PI * 2);
        if(rotation>=0){this.roll = (float)rotation;}

        this.oRoll = this.roll;

        this.scale(1.5F);
        if(scale!=0){this.scale((float)scale);}

        this.setSpriteFromAge(spriteSet);
    }



    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double nothing, double rotation, double scale) {

            return new OriumParticle(level, x, y, z, this.spriteSet, nothing, rotation, scale);
        }
    };

}
