package ryoryo.polishedstone.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.item.ItemBaseFood;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.LibUnlocalizedString;

public class ItemDamageFood extends ItemBaseFood
{
	public ItemDamageFood(String name, int size)
	{
		super(1, 1.5F, false, name, PSV2Core.TAB_MOD, 8);
		this.setMaxStackSize(1);
		this.setMaxDamage(size);
	}

	@Override
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving)
	{
		if(stack.getItemDamage() < 16)
		{
			if(entityLiving instanceof EntityPlayer && !Utils.isCreative((EntityPlayer) entityLiving))
				stack.setItemDamage(stack.getItemDamage() + 1);

			if(entityLiving instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) entityLiving;
				entityplayer.getFoodStats().addStats(this, stack);
				world.playSound((EntityPlayer) null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
				this.onFoodEaten(stack, world, entityplayer);
				entityplayer.addStat(StatList.getObjectUseStats(this));
			}
		}

		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(stack.getItemDamage() >= 16)
		{
			Utils.sendChat(player, LibUnlocalizedString.CHAT_DAMAGE_FOOD);
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}

		return super.onItemRightClick(world, player, hand);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add(Utils.translatableString(LibUnlocalizedString.TOOLTIP_DAMAGE_FOOD, stack.getMaxDamage() - stack.getItemDamage()));
	}
}