package ryoryo.polishedstone.item;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;

public class ItemRandomBox extends ItemModBase {
	public ItemRandomBox() {
		super("random_box");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if(!Utils.isCreative(player))
			stack.shrink(1);

		if(!world.isRemote) {
			Random random = new Random();
			// int j = random.nextInt(3);
			//
			// for(int i = 0; i < j; i ++)
			// {
			Item item = Item.REGISTRY.getRandomObject(random);
			int mxdmg = item.getMaxDamage();
			ItemStack give = new ItemStack(item, 1, mxdmg > 0 ? random.nextInt(Math.abs(mxdmg)) : 0);
			Utils.giveItemToPlayer(player, give);
			// }
		}

		// player.addExperience(amount);
		// world.playSound(player, player.getPosition(),
		// SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, 10.0F);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
		tooltip.add(TextFormatting.YELLOW + "-+-+WARNING+-+-");
		tooltip.add("If you install many mods,");
		tooltip.add("this item can be balance-breaking and dangerous.");
	}
}
