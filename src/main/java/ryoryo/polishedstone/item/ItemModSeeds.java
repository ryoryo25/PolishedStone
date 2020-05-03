package ryoryo.polishedstone.item;

import net.minecraft.item.ItemSeeds;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.EnumCropType;

public class ItemModSeeds extends ItemSeeds {
	public ItemModSeeds(EnumCropType type) {
		super(type.getCropBlock(), type.getSoil());
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("seed_" + type.getName());
	}
}