package ryoryo.polishedstone.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;

public class ItemSolidMilk extends ItemModBase
{
	public ItemSolidMilk()
	{
		super("solid_milk");
	}

	@Override
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		if(entityLiving instanceof EntityPlayer && !Utils.isCreative((EntityPlayer) entityLiving))
		{
			stack.shrink(1);
		}

		if(!world.isRemote)
		{
			entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		}

		if(entityLiving instanceof EntityPlayer)
		{
			((EntityPlayer) entityLiving).addStat(StatList.getObjectUseStats(this));
		}

		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 8;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.EAT;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(!player.getActivePotionEffects().isEmpty() && !Utils.isCreative(player))
		{
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		else
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}
}