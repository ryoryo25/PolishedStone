package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ryoryo.polishedlib.block.BlockBaseDoor;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class BlockModBaseDoor extends BlockBaseDoor
{
	public BlockModBaseDoor(String name, CreativeTabs tab)
	{
		super(name, tab);
	}

	public BlockModBaseDoor(String name)
	{
		super(name, PSV2Core.TAB_MOD);
	}

	public BlockModBaseDoor(Material material, String name, SoundType sound)
	{
		super(material, name, PSV2Core.TAB_MOD, sound);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}
}
