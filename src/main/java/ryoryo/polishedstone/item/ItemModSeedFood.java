package ryoryo.polishedstone.item;

import net.minecraft.item.ItemSeedFood;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.EnumCropType;

public class ItemModSeedFood extends ItemSeedFood {
	public ItemModSeedFood(EnumCropType type, int healAmount, float saturation) {
		super(healAmount, saturation, type.getCropBlock(), type.getSoil());
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName(type.getName());
	}
}