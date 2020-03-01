package ryoryo.polishedstone.item.armor;

import net.minecraft.creativetab.CreativeTabs;
import ryoryo.polishedlib.item.ItemBaseArmor;
import ryoryo.polishedlib.util.enums.EnumArmorType;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class ItemModBaseArmor extends ItemBaseArmor
{
	public ItemModBaseArmor(ArmorMaterial material, EnumArmorType type, String unlocalizeName, CreativeTabs tab)
	{
		super(material, type, unlocalizeName, tab);
	}

	public ItemModBaseArmor(ArmorMaterial material, EnumArmorType type, String unlocalizeName)
	{
		this(material, type, unlocalizeName, PSV2Core.TAB_MOD);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}
}