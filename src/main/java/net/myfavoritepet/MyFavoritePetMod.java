package net.myfavoritepet;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.myfavoritepet.classes.*;

public class MyFavoritePetMod implements ModInitializer {
	public static final BallItem BALL_ITEM = new BallItem(new FabricItemSettings().group(ItemGroup.MISC));
	public static final CroquetteItem CROQUETTE_ITEM = new CroquetteItem(new FabricItemSettings().group(ItemGroup.MISC));

	public static final StatusEffect MOVE_IMPROVEMENT_STATUS = new FriendshipMoveImprovementStatus().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", 3, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

	public static final StatusEffect DAMAGE_IMPROVEMENT_STATUS = new FriendshipAttackImprovementStatus().addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "7107DE5E-7CE8-4030-940E-514C1F160890", 3, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

	public static final StatusEffect ATTACK_SPEED_IMPROVEMENT_STATUS = new FriendshipAttackSpeedImprovementStatus().addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", 3, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("myfavoritepet", "ball_item"), BALL_ITEM);
		Registry.register(Registry.ITEM, new Identifier("myfavoritepet", "croquette_item"), CROQUETTE_ITEM);
	}
}
