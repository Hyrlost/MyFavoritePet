package net.myfavoritepet.mixin;

import net.minecraft.entity.passive.TameableEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TameableEntity.class)
public class TameableEntityMixin {
    public boolean IsFollowThrownItem;

    public void setIsFollowThrownItem(boolean isFollowThrownItem){
        this.IsFollowThrownItem = isFollowThrownItem;
    }
}
