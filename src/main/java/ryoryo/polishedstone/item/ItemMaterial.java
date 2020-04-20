package ryoryo.polishedstone.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.block.BlockMetal.MaterialType;

public class ItemMaterial extends ItemModBase
{
	private static String ig = "_ingot";
	public static final String[] NAMES = new String[]
	{
			MaterialType.ENDER_STEEL.getName() + ig,
			MaterialType.BLUESTONE.getName() + ig,
			MaterialType.BLAZE.getName() + ig,
			MaterialType.WITHER_SKELETON.getName() + ig,
			MaterialType.REDSTONE.getName() + ig,
			"netherrack_ingot",
			MaterialType.CRYSTAL.getName(),
			"ender_pearl_dust",
			MaterialType.BLUESTONE_DUST.getName(),
			"blaze_pearl_dust",
			"wither_skeleton_pearl_dust",
			"spinach",
			"empty_can",
			"bundled_feather",
			"rail_rusty",
			"rail_rusty_x4",
			"rail_rusty_x16",
			"key",
			"quartz_black",
			"quartz_purple",
	};

	public ItemMaterial()
	{
		super("material");
		this.setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		if(stack.getItemDamage() == 6)
			return true;

		return false;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return this.getUnlocalizedName() + "_" + NAMES[itemStack.getItemDamage()];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		Utils.registerSubItems(this, NAMES.length, tab, items);
	}
}
