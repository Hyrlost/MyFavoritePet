package net.myfavoritepet.classes;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BallItem extends Item {
    public BallItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient) {
            Random random = new Random();
            double d = user.getEyeY() - (double)0.3f;
            ItemEntity itemEntity = new ItemEntity(world, user.getX(), d, user.getZ(), new ItemStack(this));
            itemEntity.setPickupDelay(40);
            float g = MathHelper.sin(user.getPitch() * ((float)Math.PI / 180));
            float h = MathHelper.cos(user.getPitch() * ((float)Math.PI / 180));
            float i = MathHelper.sin(user.getYaw() * ((float)Math.PI / 180));
            float j = MathHelper.cos(user.getYaw() * ((float)Math.PI / 180));
            float k = random.nextFloat() * ((float)Math.PI * 2);
            float l = 0.02f * random.nextFloat();
            itemEntity.setVelocity((double)(-i * h * 1f) + Math.cos(k) * (double)l, -g * 1f + 0.1f + (random.nextFloat() - random.nextFloat()) * 0.1f, (double)(j * h * 1f) + Math.sin(k) * (double)l);
            world.spawnEntity(itemEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
