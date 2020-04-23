package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedstone.config.ModConfig;

public class BlockLattice extends BlockModBase
{
	protected static final AxisAlignedBB LATTICE_BASE_AABB = new AxisAlignedBB(0.0D, 0.4D, 0.0D, 1.0D, 0.6D, 1.0D);
	protected static final AxisAlignedBB LATTICE_BASE1_AABB = new AxisAlignedBB(0.0D, 0.4375D, 0.4D, 1.0D, 0.5625D, 0.6D);
	protected static final AxisAlignedBB LATTICE_BASE2_AABB = new AxisAlignedBB(0.4D, 0.4D, 0.0D, 0.6D, 0.6D, 1.0D);

	protected static final AxisAlignedBB LATTICE_TOP_AABB = new AxisAlignedBB(0.0D, 0.4D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB LATTICE_TOP1_AABB = new AxisAlignedBB(0.375D, 0.4D, 0.375D, 0.625D, 1.0D, 0.625D);

	protected static final AxisAlignedBB LATTICE_BOTTOM_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6D, 1.0D);
	protected static final AxisAlignedBB LATTICE_BOTTOM1_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.6D, 0.625D);

	protected static final AxisAlignedBB LATTICE_TB_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

	public static final PropertyBool TOP = Props.TOP;
	public static final PropertyBool BOTTOM = Props.BOTTOM;
	public static List<Block> connectables = Lists.newArrayList();
	private final IBlockState baseState;

	public BlockLattice(Block base, String name)
	{
		super(base.getDefaultState().getMaterial(), "lattice_" + name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TOP, Boolean.valueOf(false)).withProperty(BOTTOM, Boolean.valueOf(false)));
		this.baseState = base.getDefaultState();
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos)
	{
		return this.baseState.getBlock().getBlockHardness(blockState, world, pos);
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		return this.baseState.getBlock().getExplosionResistance(world, pos, exploder, explosion);
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		return this.baseState.getBlock().getSoundType(state, world, pos, entity);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		state = this.getActualState(state, source, pos);
		boolean top = state.getValue(TOP).booleanValue();
		boolean bottom = state.getValue(BOTTOM).booleanValue();

		if(top && bottom)
			return FULL_BLOCK_AABB;
		else if(top)
			return LATTICE_TOP_AABB;
		else if(bottom)
			return LATTICE_BOTTOM_AABB;
		else
			return LATTICE_BASE_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
	{
		state = this.getActualState(state, world, pos);

		for(AxisAlignedBB aabb : getCollisionBoxList(state))
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
		}
	}

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
	{
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();
		boolean flg = bstate.getValue(TOP).booleanValue();
		boolean flg1 = bstate.getValue(BOTTOM).booleanValue();
		list.add(LATTICE_BASE1_AABB);
		list.add(LATTICE_BASE2_AABB);

		if(flg && flg1)
			list.add(LATTICE_TB_AABB);
		else if(flg && !flg1)
			list.add(LATTICE_TOP1_AABB);
		else if(!flg && flg1)
			list.add(LATTICE_BOTTOM1_AABB);

		return list;
	}

	@Override
	@Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		List<RayTraceResult> list = new ArrayList<RayTraceResult>();

		for(AxisAlignedBB aabb : getCollisionBoxList(this.getActualState(blockState, world, pos)))
		{
			list.add(this.rayTrace(pos, start, end, aabb));
		}

		RayTraceResult bestresult = null;
		double d1 = 0.0D;

		for(RayTraceResult result : list)
		{
			if(result != null)
			{
				double d0 = result.hitVec.squareDistanceTo(end);

				if(d0 > d1)
				{
					bestresult = result;
					d1 = d0;
				}
			}
		}

		return bestresult;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(TOP, Boolean.valueOf(getConnectableTop(world, pos))).withProperty(BOTTOM, Boolean.valueOf(getConnectableBottom(world, pos)));
	}

	private static boolean getConnectableTop(IBlockAccess world, BlockPos pos)
	{
		if(isConnectableBlock(world.getBlockState(pos.up())))
			return true;
		return false;
	}

	private static boolean getConnectableBottom(IBlockAccess world, BlockPos pos)
	{
		if(isConnectableBlock(world.getBlockState(pos.down())))
			return true;
		return false;
	}

	private static boolean isConnectableBlock(IBlockState state)
	{
		if(connectables.contains(state.getBlock()) || (ModConfig.connectToSolidBlocks && state.isNormalCube()))
			return true;
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TOP, BOTTOM, });
	}
}
