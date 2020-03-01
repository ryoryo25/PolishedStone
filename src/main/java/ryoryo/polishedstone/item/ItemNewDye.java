package ryoryo.polishedstone.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class ItemNewDye extends ItemDye implements IItemColor, IModId
{
	public static final int[] colorValues = new int[]
	{ 0x36F193, 0xF19336, 0x36F1F1, 0xAB8115, 0x93F136, 0xF13636, 0xE26225, 0xF13693, 0xF1F136, 0x7F7F7F, 0x36F136, 0xF136F1, 0xF39C9C, 0x72B1F1, 0x007979, 0x8282F1 };

	public ItemNewDye()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("new_dye");
		this.setHasSubtypes(true);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex)
	{
		return colorValues[stack.getItemDamage()];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getMetadata();

		for(int i = 0; i < EnumColor.getLength(); i++)
		{
			if(meta == i)
				return this.getUnlocalizedName() + "_" + EnumColor.byDyeDamage(i).getName();
		}

		return this.getUnlocalizedName() + "_default";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		Utils.registerSubItems(this, EnumColor.getLength(), tab, items);
	}
}