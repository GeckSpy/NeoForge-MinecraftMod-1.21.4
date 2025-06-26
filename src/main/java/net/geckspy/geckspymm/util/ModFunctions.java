package net.geckspy.geckspymm.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ModFunctions {

    public List<Monster> getNearbyMonster(Level level, BlockPos pos, float radius, boolean shouldBeVisible){
        List<Monster> nearbyMonsters = level.getEntitiesOfClass(
                Monster.class,
                new AABB(pos).inflate(radius),
                entity -> entity.isAlive() && entity.isPickable()
        );
        if(shouldBeVisible){
            Vec3 origin = Vec3.atCenterOf(pos);
            for(Monster monster: nearbyMonsters){
                Vec3 targetPos = monster.getEyePosition();
                BlockHitResult hit = level.clip(new ClipContext(
                        origin,
                        targetPos,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        monster
                ));
                boolean visible = hit.getType()== HitResult.Type.MISS||hit.getBlockPos().equals(monster.blockPosition());
                if(!visible){
                    nearbyMonsters.remove(monster);
                }
            }
        }
        return nearbyMonsters;
    }

    public Monster getClosestMonster(Level level, BlockPos pos, float radius, boolean shouldBeVisible){
        List<Monster> nearbyMonsters = getNearbyMonster(level, pos, radius, shouldBeVisible);
        Monster nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        Vec3 origin = Vec3.atCenterOf(pos);
        for (Monster monster : nearbyMonsters) {
                double distance = origin.distanceToSqr(monster.getEyePosition());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = monster;
                }
        }
        return nearest;
    }
}
