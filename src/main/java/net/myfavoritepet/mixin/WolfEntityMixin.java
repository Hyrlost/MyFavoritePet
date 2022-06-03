package net.myfavoritepet.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.myfavoritepet.MyFavoritePetMod;
import net.myfavoritepet.classes.FollowItemThrownGoal;
import net.myfavoritepet.classes.FriendshipImprovementGoal;
import net.myfavoritepet.classes.ReturnItemGoal;
import net.myfavoritepet.classes.TemptedByItemGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public class WolfEntityMixin extends MobEntity {
    public int friendshipLevel = 0;
    protected WolfEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals()V", at = @At("TAIL"))
    public void newGoals(CallbackInfo ci){
        this.goalSelector.add(1, new TemptedByItemGoal((WolfEntity) (Object) this, 1.4, Ingredient.ofItems(MyFavoritePetMod.BALL_ITEM), MyFavoritePetMod.BALL_ITEM));
        this.goalSelector.add(2, new FollowItemThrownGoal((WolfEntity) (Object) this, MyFavoritePetMod.BALL_ITEM));
        this.goalSelector.add(1, new ReturnItemGoal((WolfEntity) (Object) this, 1.4, Ingredient.ofItems(MyFavoritePetMod.BALL_ITEM), MyFavoritePetMod.BALL_ITEM));

    }

    @Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"))
    public void addCroquette(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (item == MyFavoritePetMod.CROQUETTE_ITEM){
            this.friendshipLevel++;
            switch (this.friendshipLevel){
                case 1: this.goalSelector.add(1, new FriendshipImprovementGoal((WolfEntity) (Object) this, MyFavoritePetMod.MOVE_IMPROVEMENT_STATUS));
                break;

                case 2: this.goalSelector.add(1, new FriendshipImprovementGoal((WolfEntity) (Object) this, MyFavoritePetMod.DAMAGE_IMPROVEMENT_STATUS));
                this.goalSelector.add(1, new FriendshipImprovementGoal((WolfEntity) (Object) this, MyFavoritePetMod.ATTACK_SPEED_IMPROVEMENT_STATUS));
                break;
            }
            return;
        }
    }
}
