package net.myfavoritepet.classes;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FriendshipAttackImprovementStatus extends StatusEffect {
    public FriendshipAttackImprovementStatus() {
        super(StatusEffectCategory.BENEFICIAL, 0xf54254);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

}
