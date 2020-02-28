package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ryoryo.polishedstone.PSV2Core;

public class BlockTest extends Block
{

	public BlockTest()
	{
		super(Material.ROCK);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("test");
	}

}
