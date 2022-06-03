package net.myfavoritepet.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.lang.reflect.Field;

@Mixin(FollowOwnerGoal.class)
public abstract class FollowOwnerGoalMixin extends Goal
{
    @Shadow @Final private EntityNavigation navigation;

    @Shadow @Final private TameableEntity tameable;

    @Shadow private LivingEntity owner;

    @Shadow private int updateCountdownTicks;

    @Shadow protected abstract void tryTeleport();

    @Shadow @Final private double speed;

    @Overwrite
    public void tick() {
        this.tameable.getLookControl().lookAt(this.owner, 10.0f, this.tameable.getMaxLookPitchChange());
        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        if (this.tameable.isLeashed() || this.tameable.hasVehicle()) {
            return;
        }
        boolean isFollowThrownItem = false;
        try {
            Field field = this.tameable.getClass().getField("IsFollowThrownItem");
            field.setAccessible(true);
            Object value = field.get(this.tameable);
            isFollowThrownItem = (Boolean) value;
        }catch (Exception e){
        }
        if (this.tameable.squaredDistanceTo(this.owner) >= 144.0 && !isFollowThrownItem) {
            this.tryTeleport();
        } else {
            this.navigation.startMovingTo(this.owner, this.speed);
        }
    }
}
