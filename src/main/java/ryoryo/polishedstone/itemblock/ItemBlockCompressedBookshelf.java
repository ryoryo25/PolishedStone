package ryoryo.polishedstone.itemblock;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.itemblock.ItemBlockMeta;
import ryoryo.polishedstone.Register;

public class ItemBlockCompressedBookshelf extends ItemBlockMeta
{
	public static final String[] NAMES = new String[]
	{
			"x1",
			"x2",
	};

	public ItemBlockCompressedBookshelf()
	{
		super(Register.BLOCK_COMPRESSED_BOOKSHELF, NAMES);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
//		if(GuiScreen.isShiftKeyDown())
//		{
			tooltip.add("Bookshelves : " + (stack.getItemDamage() + 1) * 8);
//		}
	}
}
