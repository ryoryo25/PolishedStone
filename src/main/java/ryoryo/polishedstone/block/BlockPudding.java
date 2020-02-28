package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ryoryo.polishedstone.PSV2Core;

public class BlockPudding extends Block
{
	//TODO
	public BlockPudding()
	{
		super(Material.GROUND);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("pudding_block");
	}
}
