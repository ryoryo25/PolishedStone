package ryoryo.polishedstone.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;

public class BlockYellowLineTile extends BlockModBase
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockYellowLineTile()
	{
		super(Material.ROCK, "yellow_line_tile");
	}

}
