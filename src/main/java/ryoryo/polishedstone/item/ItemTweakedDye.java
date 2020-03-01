package ryoryo.polishedstone.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class ItemTweakedDye extends ItemDye implements IModId
{
	public static final String[] NAMES = new String[]
	{ EnumColor.BLACK.getName(), EnumColor.BROWN.getName(), EnumColor.BLUE.getName(), EnumColor.WHITE.getName(), "cadmium", "cinnabar" };

	public ItemTweakedDye()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("tweaked_dye");
		this.setHasSubtypes(true);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		if(target instanceof EntitySheep)
		{
			EntitySheep entitysheep = (EntitySheep) target;
			EnumDyeColor enumdyecolor = null;
			switch(stack.getItemDamage())
			{
			case 0:
				enumdyecolor = EnumDyeColor.BLACK;//黒
				break;
			case 1:
				enumdyecolor = EnumDyeColor.BROWN;//茶色
				break;
			case 2:
				enumdyecolor = EnumDyeColor.BLUE;//青
				break;
			case 3:
				enumdyecolor = EnumDyeColor.WHITE;//白
				break;
			case 4:
				enumdyecolor = EnumDyeColor.YELLOW;//黄色
				break;
			case 5:
				enumdyecolor = EnumDyeColor.RED;//赤
				break;
			}
			if(!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor)
			{
				entitysheep.setFleeceColor(enumdyecolor);
				stack.shrink(1);
			}
			return true;
		}
		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int meta = stack.getItemDamage();

		for(int i = 0; i < NAMES.length; i++)
		{
			if(meta == i)
			{
				return this.getUnlocalizedName() + "_" + NAMES[i];
			}
		}
		return this.getUnlocalizedName() + "_default";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		Utils.registerSubItems(this, NAMES.length, tab, items);
	}
}
