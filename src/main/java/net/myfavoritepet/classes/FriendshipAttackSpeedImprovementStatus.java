package net.myfavoritepet.classes;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FriendshipAttackSpeedImprovementStatus extends StatusEffect {
    public FriendshipAttackSpeedImprovementStatus() {
        super(StatusEffectCategory.BENEFICIAL, 0xf58d42);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
