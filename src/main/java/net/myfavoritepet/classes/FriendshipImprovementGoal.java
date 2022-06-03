package net.myfavoritepet.classes;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.myfavoritepet.MyFavoritePetMod;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class FriendshipImprovementGoal extends Goal {
    private final TameableEntity tameable;

    private final StatusEffect statusEffect;
    @Nullable
    private PlayerEntity closestPlayer;

    public FriendshipImprovementGoal(TameableEntity tameable, StatusEffect statusEffect) {
        this.tameable = tameable;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        this.statusEffect = statusEffect;
    }

    @Override
    public boolean canStart() {
        this.closestPlayer = this.tameable.world.getClosestPlayer(this.tameable, 5);
        return this.closestPlayer == this.tameable.getOwner();
    }

    @Override
    public boolean shouldContinue() {
        return this.closestPlayer == this.tameable.getOwner() && this.tameable.squaredDistanceTo(this.closestPlayer) < 256.0;
    }

    @Override
    public void start() {
        this.closestPlayer.addStatusEffect(new StatusEffectInstance(this.statusEffect, 100), this.tameable);
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
        this.tameable.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.closestPlayer.addStatusEffect(new StatusEffectInstance(this.statusEffect, 100), this.tameable);
    }
}