package ryoryo.polishedstone.item;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;

public class ItemInvincibleBow extends ItemModBase
{
	//Assault rifle 3d model reference
	//https://aleksadragutin.artstation.com/projects/JbWRm
	//https://www.cgtrader.com/3d-models/military/gun/fn-scar-l-7065ba0d-4070-4a13-bd7b-6c1ef1f41f1d
	//https://jp.3dexport.com/3dmodel-akm-119675.htm
	//https://pubgitems.pro/ja

	public ItemInvincibleBow()
	{
		super("invincible_bow");
		this.setMaxStackSize(1);
	}

	private ItemStack findMagazine(EntityPlayer player)
	{
		if(this.isMagazine(player.getHeldItem(EnumHand.OFF_HAND)))
			return player.getHeldItem(EnumHand.OFF_HAND);
		else if(this.isMagazine(player.getHeldItem(EnumHand.MAIN_HAND)))
			return player.getHeldItem(EnumHand.MAIN_HAND);
		else
		{
			for(int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack stack = player.inventory.getStackInSlot(i);

				if(this.isMagazine(stack))
					return stack;
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isMagazine(ItemStack stack)
	{
		return stack.getItem() instanceof ItemBasicMagazine;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		boolean flag = !this.findMagazine(player).isEmpty();

		if(!Utils.isCreative(player) && !flag)
		{
			return flag ? new ActionResult<ItemStack>(EnumActionResult.PASS, stack) : new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}
		else
		{
			player.setActiveHand(hand);
			this.fireAmmo(world, player);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}
	}

	/**
	 * Called each tick while using an item.
	 * @param stack The Item being used
	 * @param entity The Player using the item
	 * @param count The amount of time in tick the item has been used for continuously
	 */
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count)
	{
	}

	private void fireAmmo(World world, EntityPlayer player)
	{
		ItemStack ammo = this.findMagazine(player);

		if(ammo.isEmpty())
			ammo = new ItemStack(Items.ARROW);

		if(!world.isRemote)
		{
			ItemArrow itemarrow = (ItemArrow) (ammo.getItem() instanceof ItemArrow ? ammo.getItem() : Items.ARROW);
			EntityArrow entityarrow = itemarrow.createArrow(world, ammo, player);
			entityarrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 100.0F, 0.5F);
			if(Utils.isCreative(player))
				entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
			world.spawnEntity(entityarrow);
		}

		this.recoil(player, world.rand);
		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, Register.SOUND_GUN_FIRE, SoundCategory.PLAYERS, 0.5F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);

		if(!Utils.isCreative(player))
		{
			ammo.shrink(1);

			if(ammo.isEmpty())
				player.inventory.deleteStack(ammo);
		}
	}

	private void recoil(EntityPlayer player, Random random)
	{
		double verbase = 1 + random.nextFloat();
		double horbase = random.nextGaussian() * 0.5;
		double vertical = verbase;
		double horizontal = horbase;

		//スニークしてたら小さくする
		if(player.isSneaking())
		{
			vertical *= 0.8;
			horizontal *= 0.8;
		}

		//動いてたら大きくする
		if(Utils.isPlayerMoving(player))
		{
			vertical *= 1.2;
			horizontal *= 1.2;
		}

		player.rotationPitch -= vertical;
		player.rotationYaw += horizontal;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 0;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		return slotChanged;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack)
	{
		return true;
	}

	/**
	 * Queries the percentage of the 'Durability' bar that should be drawn.
	 *
	 * @param stack The current ItemStack
	 * @return 0.0 for 100% (no damage / full bar), 1.0 for 0% (fully damaged / empty bar)
	 */
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
	{
		return 0.000001/*(double)stack.getItemDamage() / (double)stack.getMaxDamage()*/;
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack)
	{
		return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - getDurabilityForDisplay(stack))) / 1.7F, 1.0F, 1.0F);
	}
}