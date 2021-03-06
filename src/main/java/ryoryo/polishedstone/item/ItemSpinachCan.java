package ryoryo.polishedstone.item;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.ArithmeticUtils;
import ryoryo.polishedlib.util.LibPotionId;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public class ItemSpinachCan extends ItemModBaseFood {
	public ItemSpinachCan() {
		super(10, 10.0F, false, "spinach_can", PSV2Core.TAB_MOD, 8);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("spinach_can");
	}

	@Override
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, world, entityLiving);
		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			Utils.giveItemToPlayer(player, new ItemStack(Register.ITEM_MATERIAL, 1, 12));
		}
		return stack;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if(player.getHealth() <= player.getMaxHealth() * ArithmeticUtils.percentToDecimal(20F)) {
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}
		else
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}

	@Override
	protected void onFoodEaten(ItemStack itmeStack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.STRENGTH), (int) ArithmeticUtils.minuteToTick(1F), 4));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.REGENERATION), (int) ArithmeticUtils.minuteToTick(1F), 4));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.HEALTH_BOOST), (int) ArithmeticUtils.minuteToTick(1F), 4));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.ABSORPTION), (int) ArithmeticUtils.minuteToTick(1F), 4));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.RESISTANCE), (int) ArithmeticUtils.minuteToTick(1F), 4));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.SATURATION), (int) ArithmeticUtils.minuteToTick(1F), 4));
		}
	}
}
