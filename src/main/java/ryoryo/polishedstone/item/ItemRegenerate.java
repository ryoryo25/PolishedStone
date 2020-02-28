package ryoryo.polishedstone.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.config.ModConfig;

public class ItemRegenerate extends ItemModBase
{
	public ItemRegenerate()
	{
		super("heart_of_regeneration");
	}

	@Override
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		if(entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLiving;

			if(!Utils.isCreative(player))
				stack.shrink(1);

			if(!world.isRemote)
				player.setHealth(player.getHealth() + (int) (4.0D * ModConfig.regenTimes));

			world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
		}

		return stack;
	}

	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 16;
	}

	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(!Utils.isCreative(player) && player.getHealth() < player.getMaxHealth())
		{
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		else
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
}