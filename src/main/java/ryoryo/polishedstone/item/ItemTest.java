package ryoryo.polishedstone.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import ryoryo.polishedstone.network.PacketHandler;
import ryoryo.polishedstone.network.PacketTest;

public class ItemTest extends ItemModBase
{
	public ItemTest()
	{
		super("test");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		if(world.isRemote)
			PacketHandler.INSTANCE.sendToServer(new PacketTest("Hello world"));

		return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
	}
}