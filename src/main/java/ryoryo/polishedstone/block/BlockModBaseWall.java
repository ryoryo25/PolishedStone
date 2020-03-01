package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import ryoryo.polishedlib.block.BlockBaseWall;
import ryoryo.polishedstone.util.References;

public class BlockModBaseWall extends BlockBaseWall
{
	public BlockModBaseWall(Block baseBlock)
	{
		super(baseBlock);
	}

	public BlockModBaseWall(Block baseBlock, String name)
	{
		super(baseBlock, name);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}
}