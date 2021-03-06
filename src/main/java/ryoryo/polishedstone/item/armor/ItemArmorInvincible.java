package ryoryo.polishedstone.item.armor;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.item.ItemBaseArmor;
import ryoryo.polishedlib.util.enums.EnumArmorType;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.ArmorMaterials;

public class ItemArmorInvincible extends ItemBaseArmor
{
	public ItemArmorInvincible(EnumArmorType type)
	{
		super(ArmorMaterials.INVINCIBLE_ARMOR, type, "invincible_armor", PSV2Core.TAB_MOD);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}