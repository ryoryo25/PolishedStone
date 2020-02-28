package ryoryo.polishedstone.item;

import ryoryo.polishedlib.item.ItemBase;
import ryoryo.polishedstone.PSV2Core;

public class ItemModBase extends ItemBase
{
	public ItemModBase(String name)
	{
		super(name, PSV2Core.TAB_MOD);
		this.setUnlocalizedName(name);
	}
}