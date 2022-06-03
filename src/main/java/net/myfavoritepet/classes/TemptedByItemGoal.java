package net.myfavoritepet.classes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;

public class TemptedByItemGoal extends TemptGoal {
    private static final TargetPredicate TEMPTING_ENTITY_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(10.0).ignoreVisibility();

    private int cooldown;
    private final TargetPredicate predicate;
    public TameableEntity tameable;
    public Item item;

    public TemptedByItemGoal(TameableEntity tameable, double speed, Ingredient food, Item item) {
        super(tameable, speed, food, false);
        this.tameable = tameable;
        this.item = item;
        this.predicate = TEMPTING_ENTITY_PREDICATE.copy().setPredicate(this::isTemptedBy);
    }
    @Override
    public boolean canStart() {
        if (!this.tameable.isTamed() || this.tameable.isSitting()) {
            return false;
        }
        LivingEntity livingEntity = this.tameable.getOwner();
        if (livingEntity == null) {
            return false;
        }
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }

        this.closestPlayer = this.mob.world.getClosestPlayer(this.predicate, this.mob);
        return this.closestPlayer != null;
        }

    private boolean isTemptedBy(LivingEntity entity) {
        if (isOwner(entity)){
            return entity.getMainHandStack().getItem() == item;
        }
        return false;
    }

    private boolean isOwner(LivingEntity entity){
        return ((Entity)tameable.getOwner()) == entity;
    }

    @Override
    public void stop() {
        this.closestPlayer = null;
        this.mob.getNavigation().stop();
        this.cooldown = TemptGoal.toGoalTicks(0);
    }

}
