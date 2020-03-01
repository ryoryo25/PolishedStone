package ryoryo.polishedstone.item;

import net.minecraft.item.ItemSeedFood;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.EnumCropType;
import ryoryo.polishedstone.util.References;

public class ItemModSeedFood extends ItemSeedFood implements IModId
{
	public ItemModSeedFood(EnumCropType type, int healAmount, float saturation)
	{
		super(healAmount, saturation, type.getCropBlock(), type.getSoil());
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName(type.getName());
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}
}