package ryoryo.polishedstone.itemblock;

import net.minecraft.block.BlockQuartz.EnumType;
import ryoryo.polishedlib.itemblock.ItemBlockMeta;
import ryoryo.polishedstone.Register;

public class ItemBlockBlackQuartz extends ItemBlockMeta
{
	public static final String[] NAMES = new String[]
	{
			"normal",
			EnumType.CHISELED.toString(),
			EnumType.LINES_Y.toString(),
	};

	public ItemBlockBlackQuartz()
	{
		super(Register.BLOCK_BLACK_QUARTZ, NAMES);
	}
}