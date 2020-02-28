package ryoryo.polishedstone.util;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import ryoryo.polishedlib.util.LibTool;
import ryoryo.polishedlib.util.NumericalConstant;

public class ArmorMaterials
{
	public static ArmorMaterial DECORATIVE_HAZMAT;
	public static ArmorMaterial SAFETY_CLOTHES;
	public static ArmorMaterial LIFE_JACKET;
	public static ArmorMaterial INVINCIBLE_ARMOR;

	public static ToolMaterial INVINCIBLE_SWORD;

	public static void registerArmorMaterial()
	{
		//armor materials
		DECORATIVE_HAZMAT = EnumHelper.addArmorMaterial("DECORATIVE_HAZMAT", References.PREFIX + "hazmat", 0, new int[] { 0, 0, 0, 0 }, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
		SAFETY_CLOTHES = EnumHelper.addArmorMaterial("SAFETY_CLOTHES", References.PREFIX + "safety_clothes", 15, new int[] { 1, 4, 5, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
		LIFE_JACKET = EnumHelper.addArmorMaterial("LIFE_JACKET", References.PREFIX + "life_jacket", 15, new int[] { 1, 4, 5, 2 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
		INVINCIBLE_ARMOR = EnumHelper.addArmorMaterial("INVINCIBLE_ARMOR", References.PREFIX + "invincible_armor", 0, new int[] {1000, 1000, 1000, 1000}, NumericalConstant.INT_MAX, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);

		//tool materials
		INVINCIBLE_SWORD = EnumHelper.addToolMaterial("INVINCBLE", LibTool.LEVEL_DIAMOND, NumericalConstant.INT_MAX, NumericalConstant.FLOAT_MAX, NumericalConstant.FLOAT_POSITIVE_INFINITY, NumericalConstant.INT_MAX);
	}
}