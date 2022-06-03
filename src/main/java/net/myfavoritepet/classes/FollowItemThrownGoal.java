package net.myfavoritepet.classes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;

import java.lang.reflect.Field;
import java.util.List;

public class FollowItemThrownGoal extends Goal {
    private Item item;

    private ItemEntity itemEntity;
    private TameableEntity tameable;

    public FollowItemThrownGoal(TameableEntity tameable, Item itemToFollow){
        this.item = itemToFollow;
        this.tameable = tameable;
    }

    @Override
    public boolean canStart() {
        if (tameable.isTamed()  && !this.tameable.isSitting()){
            Box newBox = new Box(-1000,-1000,-1000,1000,1000,1000);
            List<ItemEntity> itemEntitiesList = tameable.world.getEntitiesByClass(ItemEntity.class, newBox, (asd) -> true);

            for (var itemEntity: itemEntitiesList) {
                if (itemEntity.getStack().getItem() == this.item){
                    this.itemEntity = itemEntity;
                    try{
                        Field field = this.tameable.getClass().getField("IsFollowThrownItem");
                        field.setAccessible(true);
                        field.setBoolean(this.tameable, true);
                    }catch (Exception e){
                    }
                    return true;
                }
            }
        }

        try{
            Field field = this.tameable.getClass().getField("IsFollowThrownItem");
            field.setAccessible(true);
            field.setBoolean(this.tameable, false);
        }catch (Exception e){
        }
        return false;
    }

    @Override
    public void tick() {
        this.tameable.getLookControl().lookAt(this.itemEntity, this.tameable.getMaxHeadRotation() + 20, this.tameable.getMaxLookPitchChange());
        if (this.tameable.squaredDistanceTo(this.itemEntity) < 1) {
            this.tameable.getNavigation().stop();
        } else {
            this.tameable.getNavigation().startMovingTo(this.itemEntity, 1.4);
        }

        if(this.tameable.squaredDistanceTo(this.itemEntity) < 3){
            this.tameable.equipStack(EquipmentSlot.MAINHAND, new ItemStack(item));
            Box newBox = new Box(-1000,-1000,-1000,1000,1000,1000);
            var itemEntityList = tameable.world.getEntitiesByClass(ItemEntity.class, newBox, (asd) -> true);
            var itemEntityFromWorld = itemEntityList.get(itemEntityList.indexOf(this.itemEntity));
            itemEntityFromWorld.remove(Entity.RemovalReason.DISCARDED);
        }
    }
}
