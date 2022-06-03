package net.myfavoritepet.classes;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

public class ReturnItemGoal extends TemptGoal {

    private int cooldown;
    public TameableEntity tameable;
    public Item item;

    public double speed;

    public ReturnItemGoal(TameableEntity tameable, double speed, Ingredient food, Item item) {
        super(tameable, speed, food, false);
        this.tameable = tameable;
        this.speed = speed;
        this.item = item;
    }
    @Override
    public boolean canStart() {
        if (!this.tameable.isTamed() || this.tameable.isSitting()) {
            return false;
        }
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity == null || this.tameable.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        }
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.closestPlayer = (PlayerEntity) livingEntity;
        return this.closestPlayer != null;
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
        this.mob.getNavigation().stop();
        this.cooldown = TemptGoal.toGoalTicks(0);
        }

    @Override
    public void tick() {
        this.mob.getLookControl().lookAt(this.closestPlayer, this.mob.getMaxHeadRotation() + 20, this.mob.getMaxLookPitchChange());
        if (this.mob.squaredDistanceTo(this.closestPlayer) < 3) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().startMovingTo(this.closestPlayer, this.speed);
        }

        if (this.mob.squaredDistanceTo(this.closestPlayer) < 3) {
            this.tameable.world.spawnEntity(new ItemEntity(this.tameable.world, this.tameable.getX(), this.tameable.getY(), this.tameable.getZ(), new ItemStack(this.item)));
            this.tameable.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
    }

}
