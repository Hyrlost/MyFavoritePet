package net.myfavoritepet.classes;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FriendshipMoveImprovementStatus extends StatusEffect {
    public FriendshipMoveImprovementStatus() {
        super(StatusEffectCategory.BENEFICIAL, 0xbff542);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
