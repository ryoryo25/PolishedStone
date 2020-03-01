package ryoryo.polishedstone.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.util.EnumCropType;
import ryoryo.polishedstone.util.References;

public class BlockModCrops extends BlockCrops implements IModId
{
	private Item seed;
	private Item crop;
	private AxisAlignedBB[] aabbs;

	public BlockModCrops(EnumCropType type)
	{
		this.setUnlocalizedName(type.getName());
		this.seed = type.getSeed();
		this.crop = type.getCrop();
		this.aabbs = type.getAABBs();
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	protected Item getSeed()
	{
		return this.seed;
	}

	@Override
	protected Item getCrop()
	{
		return this.crop;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return this.aabbs[((Integer) state.getValue(this.getAgeProperty())).intValue()];
	}
}