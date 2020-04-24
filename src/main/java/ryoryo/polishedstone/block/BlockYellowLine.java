package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedlib.util.Utils;

public class BlockYellowLine extends BlockModBase
{
	protected static final AxisAlignedBB BASE_AABB = Utils.creatAABB(0, 8, 0, 16, 16, 16);
	protected static final AxisAlignedBB NORTH_AABB = Utils.creatAABB(0, 0, 8, 16, 8, 16);
	protected static final AxisAlignedBB SOUTH_AABB = Utils.creatAABB(0, 0, 0, 16, 8, 8);
	protected static final AxisAlignedBB WEST_AABB = Utils.creatAABB(8, 0, 0, 16, 8, 16);
	protected static final AxisAlignedBB EAST_AABB = Utils.creatAABB(0, 0, 0, 8, 8, 16);
	public static final PropertyDirection FACING = Props.HORIZONTAL_FACING;

	public BlockYellowLine()
	{
		super(Material.ROCK, "yellow_line", SoundType.STONE);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
	{
		getCollisionBoxList(this.getActualState(state, world, pos))
		.forEach(aabb -> addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb));
	}

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
	{
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();
		EnumFacing facing = bstate.getValue(FACING);
		list.add(BASE_AABB);

		switch(facing)
		{
		case NORTH:
		default:
			list.add(NORTH_AABB);
			break;
		case SOUTH:
			list.add(SOUTH_AABB);
			break;
		case WEST:
			list.add(WEST_AABB);
			break;
		case EAST:
			list.add(EAST_AABB);
			break;
		}

		return list;
	}

	@Override
	@Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		return getCollisionBoxList(this.getActualState(blockState, world, pos)).stream()
				.map(aabb -> this.rayTrace(pos, start, end, aabb))
				.filter(Objects::nonNull)
				.max((result1, result2) -> (int) (result1.hitVec.squareDistanceTo(end) - result2.hitVec.squareDistanceTo(end)))
				.orElse(null);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ FACING });
	}
}
