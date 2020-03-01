package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ryoryo.polishedlib.block.BlockBase;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class BlockModBase extends BlockBase
{
	public BlockModBase(Material material, String name)
	{
		super(material, name, PSV2Core.TAB_MOD);
	}

	public BlockModBase(Material material, String name, SoundType soundType)
	{
		super(material, name, PSV2Core.TAB_MOD, soundType);
	}

	public BlockModBase(Material material, String name, CreativeTabs tab)
	{
		super(material, name, tab);
	}

	public BlockModBase(Material material, String name, CreativeTabs tab, SoundType soundType)
	{
		super(material, name, tab, soundType);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}
}