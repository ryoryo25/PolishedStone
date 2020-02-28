package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
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
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedstone.PSV2Core;

public class BlockFenceSlab extends Block
{
	protected static final AxisAlignedBB AABB_BOTTOM_SLAB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_BOTTOM_FENCE = new AxisAlignedBB(0.375D, 0.5D, 0.375D, 0.625D, 1.0D, 0.625D);
	protected static final AxisAlignedBB AABB_TOP_SLAB = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_TOP_FENCE = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D);

	public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.<EnumBlockHalf> create("half", EnumBlockHalf.class);
	private final IBlockState baseState;

	public BlockFenceSlab(Block base, String name)
	{
		super(base.getDefaultState().getMaterial());
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("fence_slab_" + name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
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

	//TODO 繋がるようにして、モデルも変えればいい
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
	{
		switch(face)
		{
		case UP:
		case DOWN:
		default:
			return BlockFaceShape.SOLID;
		case NORTH:
		case SOUTH:
		case WEST:
		case EAST:
			return BlockFaceShape.CENTER;
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		IBlockState state = super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(HALF, EnumBlockHalf.BOTTOM);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? state : state.withProperty(HALF, EnumBlockHalf.TOP);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FULL_BLOCK_AABB;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
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
		EnumBlockHalf half = bstate.getValue(HALF);
		switch(half)
		{
		case TOP:
		default:
			list.add(AABB_TOP_SLAB);
			list.add(AABB_TOP_FENCE);
			break;
		case BOTTOM:
			list.add(AABB_BOTTOM_SLAB);
			list.add(AABB_BOTTOM_FENCE);
			break;
		}

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
		return this.getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(HALF) == EnumBlockHalf.BOTTOM ? 0 : 1;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ HALF, });
	}
}
