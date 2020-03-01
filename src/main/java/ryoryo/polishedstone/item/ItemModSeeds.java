package ryoryo.polishedstone.item;

import net.minecraft.item.ItemSeeds;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.EnumCropType;
import ryoryo.polishedstone.util.References;

public class ItemModSeeds extends ItemSeeds implements IModId
{
	public ItemModSeeds(EnumCropType type)
	{
		super(type.getCropBlock(), type.getSoil());
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("seed_" + type.getName());
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}
}