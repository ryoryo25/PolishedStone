package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ryoryo.polishedstone.PSV2Core;

public class BlockSpecial extends Block
{
	public BlockSpecial()
	{
		super(Material.GROUND);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("special");
	}

}