package ryoryo.polishedstone.itemblock;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedstone.Register;

public class ItemBlockDummyBarrier extends ItemBlock implements IItemColor
{
	public ItemBlockDummyBarrier()
	{
		super(Register.BLOCK_DUMMY_BARRIER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(ItemStack stack, int tintIndex)
	{
		if(tintIndex == 0)
			return 0xFF0000;//赤
		
		return 0xFFFFFFF;
	}
}