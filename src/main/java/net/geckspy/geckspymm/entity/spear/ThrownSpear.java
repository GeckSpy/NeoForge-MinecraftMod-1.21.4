package net.geckspy.geckspymm.entity.spear;

import net.geckspy.geckspymm.entity.ModEntities;
import net.geckspy.geckspymm.item.ModItems;
import net.geckspy.geckspymm.item.ModToolMaterials;
import net.geckspy.geckspymm.item.custom.SpearItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Map;

public class ThrownSpear extends AbstractArrow {
    private static final EntityDataAccessor<Byte> ID_LOYALTY;
    private static final EntityDataAccessor<Boolean> ID_FOIL;
    private static final EntityDataAccessor<String> toolMaterialName =
            SynchedEntityData.defineId(ThrownSpear.class, EntityDataSerializers.STRING);
    public static final ToolMaterial DEFAULT_MATERIAL = ToolMaterial.WOOD;

    public int clientSideReturnSpearTickCount;
    private static final float WATER_INERTIA = 0.6F;
    private boolean dealtDamage;
    private int lifeTicks = 0;


    public static final Map<String, SpearItem> stringToItem = Map.of(
            ToolMaterial.WOOD.toString(), ModItems.WOOD_SPEAR.get(),
            ToolMaterial.STONE.toString(), ModItems.STONE_SPEAR.get(),
            ModToolMaterials.COPPER.toString(), ModItems.COPPER_SPEAR.get(),
            ToolMaterial.IRON.toString(), ModItems.IRON_SPEAR.get(),
            ToolMaterial.GOLD.toString(), ModItems.GOLDEN_SPEAR.get(),
            ToolMaterial.DIAMOND.toString(), ModItems.DIAMOND_SPEAR.get(),
            ModToolMaterials.PURE_QUARTZ.toString(), ModItems.PURE_QUARTZ_SPEAR.get(),
            ToolMaterial.NETHERITE.toString(), ModItems.NETHERITE_SPEAR.get()
    );


    public ThrownSpear(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(ModEntities.SPEAR_ENTITY.get(), shooter, level, pickupItemStack, (ItemStack)null);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());

        ToolMaterial material = DEFAULT_MATERIAL;
        Item item = pickupItemStack.getItem();
        if(item instanceof SpearItem){material = ((SpearItem) item).toolMaterial;}
        System.out.println(material);
        this.entityData.set(toolMaterialName, material.toString());
    }

    public ThrownSpear(EntityType<ThrownSpear> thrownSpearEntityType, Level level) {
        super(thrownSpearEntityType, level);
    }

    public ThrownSpear(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(EntityType.TRIDENT, x, y, z, level, pickupItemStack, pickupItemStack);
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(pickupItemStack));
        this.entityData.set(ID_FOIL, pickupItemStack.hasFoil());

        ToolMaterial material = DEFAULT_MATERIAL;
        Item item = pickupItemStack.getItem();
        if(item instanceof SpearItem){material = ((SpearItem) item).toolMaterial;}
        this.entityData.set(toolMaterialName, material.toString());
    }


    protected void defineSynchedData(SynchedEntityData.Builder p_326249_) {
        super.defineSynchedData(p_326249_);
        p_326249_.define(ID_LOYALTY, (byte)0);
        p_326249_.define(ID_FOIL, false);
        p_326249_.define(toolMaterialName, DEFAULT_MATERIAL.toString());
    }

    public void tick() {
        this.lifeTicks++;
        if (this.inGroundTime > 4) {this.dealtDamage = true;}

        Entity entity = this.getOwner();
        int i = (Byte)this.entityData.get(ID_LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoPhysics()) && entity != null) {
            if (!this.isAcceptibleReturnOwner()) {
                Level var4 = this.level();
                if (var4 instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel)var4;
                    if (this.pickup == Pickup.ALLOWED) {
                        this.spawnAtLocation(serverlevel, this.getPickupItem(), 0.1F);
                    }
                }

                this.discard();
            } else {
                if (!(entity instanceof Player) && this.position().distanceTo(entity.getEyePosition()) < (double)entity.getBbWidth() + (double)1.0F) {
                    this.discard();
                    return;
                }

                this.setNoPhysics(true);
                Vec3 vec3 = entity.getEyePosition().subtract(this.position());
                this.setPosRaw(this.getX(), this.getY() + vec3.y * 0.015 * (double)i, this.getZ());
                double d0 = 0.05 * (double)i;
                this.setDeltaMovement(this.getDeltaMovement().scale(0.95).add(vec3.normalize().scale(d0)));
                if (this.clientSideReturnSpearTickCount == 0) {
                    this.playSound(SoundEvents.TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.clientSideReturnSpearTickCount;
            }
        }
        super.tick();
    }

    private boolean isAcceptibleReturnOwner() {
        Entity entity = this.getOwner();
        return entity != null && entity.isAlive() ? !(entity instanceof ServerPlayer) || !entity.isSpectator() : false;
    }

    public boolean isFoil() {
        return (Boolean)this.entityData.get(ID_FOIL);
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 startVec, Vec3 endVec) {
        return this.dealtDamage ? null : super.findHitEntity(startVec, endVec);
    }


    public float getDamageAmount(){
        return SpearItem.THROWN_ATTACK_DAMAGE
                + getSpearItem().toolMaterial.attackDamageBonus();
    }


    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        float f = getDamageAmount();
        DamageSource damagesource = this.damageSources().thrown(this, this.getOwner());
        Level level = this.level();
        
        if (level instanceof ServerLevel serverlevel) {
            f = EnchantmentHelper.modifyDamage(serverlevel, this.getWeaponItem(), entity, damagesource, f);
        }

        this.dealtDamage = true;
        if (entity.hurtOrSimulate(damagesource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {return;}

            level = this.level();
            if (level instanceof ServerLevel) {
                ServerLevel serverlevel1 = (ServerLevel)level;
                EnchantmentHelper.doPostAttackEffectsWithItemSourceOnBreak(serverlevel1, entity, damagesource, this.getWeaponItem(), (p_375964_) -> this.kill(serverlevel1));
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                this.doKnockback(livingentity, damagesource);
                this.doPostHurtEffects(livingentity);
            }
        }

        this.deflect(ProjectileDeflection.REVERSE, entity, this.getOwner(), false);
        this.setDeltaMovement(this.getDeltaMovement().multiply(0.02, 0.2, 0.02));
        this.playSound(SoundEvents.TRIDENT_HIT, 1.0F, 1.0F);
    }



    @Override
    public ItemStack getWeaponItem() {
        return this.getPickupItemStackOrigin();
    }


    @Override
    protected boolean tryPickup(Player p_150196_) {
        return super.tryPickup(p_150196_) || this.isNoPhysics() && this.ownedBy(p_150196_) && p_150196_.getInventory().add(this.getPickupItem());
    }

    public String getToolMaterialString(){
        return this.getEntityData().get(toolMaterialName);
    }

    public SpearItem getSpearItem(){
        return stringToItem.get(getToolMaterialString());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        //System.out.println("getDefaultPickupItem :" + stringToItem.get(this.getEntityData().get(toolMaterialName)));
        return new ItemStack(getSpearItem());
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.TRIDENT_HIT_GROUND;
    }


    @Override
    public void playerTouch(Player entity) {
        if (this.ownedBy(entity) || this.getOwner() == null) {
            super.playerTouch(entity);
        }
    }



    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.dealtDamage = compound.getBoolean("DealtDamage");
        this.entityData.set(ID_LOYALTY, this.getLoyaltyFromItem(this.getPickupItemStackOrigin()));
        this.entityData.set(toolMaterialName, compound.getString("materialName"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("DealtDamage", this.dealtDamage);
        compound.putString("materialName", this.getEntityData().get(toolMaterialName));
    }

    private byte getLoyaltyFromItem(ItemStack stack) {
        Level var3 = this.level();
        byte var10000;
        if (var3 instanceof ServerLevel serverlevel) {
            var10000 = (byte) Mth.clamp(EnchantmentHelper.getTridentReturnToOwnerAcceleration(serverlevel, stack, this), 0, 127);
        } else {
            var10000 = 0;
        }
        return var10000;
    }


    @Override
    public void tickDespawn() {
        int i = (Byte)this.entityData.get(ID_LOYALTY);
        if (this.pickup != Pickup.ALLOWED || i <= 0) {
            if(this.lifeTicks>2400){
                this.discard();
            }
        }
    }

    @Override
    protected float getWaterInertia() {
        return WATER_INERTIA;
    }


    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }


    static {
        ID_LOYALTY = SynchedEntityData.defineId(ThrownSpear.class, EntityDataSerializers.BYTE);
        ID_FOIL = SynchedEntityData.defineId(ThrownSpear.class, EntityDataSerializers.BOOLEAN);
    }

}
