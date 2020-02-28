package ryoryo.polishedstone.itemblock;

import ryoryo.polishedlib.itemblock.ItemBlockMeta;
import ryoryo.polishedstone.Register;

public class ItemBlockThreePillars extends ItemBlockMeta
{
	public static final String[] NAMES = new String[]
	{
			"normal",
			"black",
	};

	public ItemBlockThreePillars()
	{
		super(Register.BLOCK_THREE_PILLARS, NAMES);
	}
}