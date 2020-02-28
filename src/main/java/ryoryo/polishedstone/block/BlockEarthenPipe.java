package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class BlockEarthenPipe extends Block
{
	protected static final AxisAlignedBB BASE_X_AABB1 = Utils.creatAABB(2, 3, 1, 14, 13, 15);
	protected static final AxisAlignedBB BASE_X_AABB2 = Utils.creatAABB(2, 2, 2, 14, 14, 14);
	protected static final AxisAlignedBB BASE_X_AABB3 = Utils.creatAABB(2, 1, 3, 14, 15, 13);

	protected static final AxisAlignedBB CONNECTION_WEST_X_AABB1 = Utils.creatAABB(0, 3, 1, 2, 13, 15);
	protected static final AxisAlignedBB CONNECTION_WEST_X_AABB2 = Utils.creatAABB(0, 2, 2, 2, 14, 14);
	protected static final AxisAlignedBB CONNECTION_WEST_X_AABB3 = Utils.creatAABB(0, 1, 3, 2, 15, 13);

	protected static final AxisAlignedBB CONNECTION_EAST_X_AABB1 = Utils.creatAABB(14, 3, 1, 16, 13, 15);
	protected static final AxisAlignedBB CONNECTION_EAST_X_AABB2 = Utils.creatAABB(14, 2, 2, 16, 14, 14);
	protected static final AxisAlignedBB CONNECTION_EAST_X_AABB3 = Utils.creatAABB(14, 1, 3, 16, 15, 13);

	protected static final AxisAlignedBB RING_WEST_X_AABB1 = Utils.creatAABB(0, 3, 0, 2, 13, 16);
	protected static final AxisAlignedBB RING_WEST_X_AABB2 = Utils.creatAABB(0, 2, 1, 2, 14, 15);
	protected static final AxisAlignedBB RING_WEST_X_AABB3 = Utils.creatAABB(0, 1, 2, 2, 15, 14);
	protected static final AxisAlignedBB RING_WEST_X_AABB4 = Utils.creatAABB(0, 0, 3, 2, 16, 13);

	protected static final AxisAlignedBB RING_EAST_X_AABB1 = Utils.creatAABB(14, 3, 0, 16, 13, 16);
	protected static final AxisAlignedBB RING_EAST_X_AABB2 = Utils.creatAABB(14, 2, 1, 16, 14, 15);
	protected static final AxisAlignedBB RING_EAST_X_AABB3 = Utils.creatAABB(14, 1, 2, 16, 15, 14);
	protected static final AxisAlignedBB RING_EAST_X_AABB4 = Utils.creatAABB(14, 0, 3, 16, 16, 13);

//-----------------------------------------------------------------------------------------------------------------------------------------

	protected static final AxisAlignedBB BASE_Y_AABB1 = Utils.creatAABB(3, 2, 1, 13, 14, 15);
	protected static final AxisAlignedBB BASE_Y_AABB2 = Utils.creatAABB(2, 2, 2, 14, 14, 14);
	protected static final AxisAlignedBB BASE_Y_AABB3 = Utils.creatAABB(1, 2, 3, 15, 14, 13);

	protected static final AxisAlignedBB CONNECTION_DOWN_Y_AABB1 = Utils.creatAABB(3, 0, 1, 13, 2, 15);
	protected static final AxisAlignedBB CONNECTION_DOWN_Y_AABB2 = Utils.creatAABB(2, 0, 2, 14, 2, 14);
	protected static final AxisAlignedBB CONNECTION_DOWN_Y_AABB3 = Utils.creatAABB(1, 0, 3, 15, 2, 13);

	protected static final AxisAlignedBB CONNECTION_UP_Y_AABB1 = Utils.creatAABB(3, 14, 1, 13, 16, 15);
	protected static final AxisAlignedBB CONNECTION_UP_Y_AABB2 = Utils.creatAABB(2, 14, 2, 14, 16, 14);
	protected static final AxisAlignedBB CONNECTION_UP_Y_AABB3 = Utils.creatAABB(1, 14, 3, 15, 16, 13);

	protected static final AxisAlignedBB RING_DOWN_Y_AABB1 = Utils.creatAABB(3, 0, 0, 13, 2, 16);
	protected static final AxisAlignedBB RING_DOWN_Y_AABB2 = Utils.creatAABB(2, 0, 1, 14, 2, 15);
	protected static final AxisAlignedBB RING_DOWN_Y_AABB3 = Utils.creatAABB(1, 0, 2, 15, 2, 14);
	protected static final AxisAlignedBB RING_DOWN_Y_AABB4 = Utils.creatAABB(0, 0, 3, 16, 2, 13);

	protected static final AxisAlignedBB RING_UP_Y_AABB1 = Utils.creatAABB(3, 14, 0, 13, 16, 16);
	protected static final AxisAlignedBB RING_UP_Y_AABB2 = Utils.creatAABB(2, 14, 1, 14, 16, 15);
	protected static final AxisAlignedBB RING_UP_Y_AABB3 = Utils.creatAABB(1, 14, 2, 15, 16, 14);
	protected static final AxisAlignedBB RING_UP_Y_AABB4 = Utils.creatAABB(0, 14, 3, 16, 16, 13);

//-----------------------------------------------------------------------------------------------------------------------------------------

	protected static final AxisAlignedBB BASE_Z_AABB1 = Utils.creatAABB(1, 3, 2, 15, 13, 14);
	protected static final AxisAlignedBB BASE_Z_AABB2 = Utils.creatAABB(2, 2, 2, 14, 14, 14);
	protected static final AxisAlignedBB BASE_Z_AABB3 = Utils.creatAABB(3, 1, 2, 13, 15, 14);

	protected static final AxisAlignedBB CONNECTION_NORTH_Z_AABB1 = Utils.creatAABB(1, 3, 0, 15, 13, 2);
	protected static final AxisAlignedBB CONNECTION_NORTH_Z_AABB2 = Utils.creatAABB(2, 2, 0, 14, 14, 2);
	protected static final AxisAlignedBB CONNECTION_NORTH_Z_AABB3 = Utils.creatAABB(3, 1, 0, 13, 15, 2);

	protected static final AxisAlignedBB CONNECTION_SOUTH_Z_AABB1 = Utils.creatAABB(1, 3, 14, 15, 13, 16);
	protected static final AxisAlignedBB CONNECTION_SOUTH_Z_AABB2 = Utils.creatAABB(2, 2, 14, 14, 14, 16);
	protected static final AxisAlignedBB CONNECTION_SOUTH_Z_AABB3 = Utils.creatAABB(3, 1, 14, 13, 15, 16);

	protected static final AxisAlignedBB RING_NORTH_Z_AABB1 = Utils.creatAABB(0, 3, 0, 16, 13, 2);
	protected static final AxisAlignedBB RING_NORTH_Z_AABB2 = Utils.creatAABB(1, 2, 0, 15, 14, 2);
	protected static final AxisAlignedBB RING_NORTH_Z_AABB3 = Utils.creatAABB(2, 1, 0, 14, 15, 2);
	protected static final AxisAlignedBB RING_NORTH_Z_AABB4 = Utils.creatAABB(3, 0, 0, 13, 16, 2);

	protected static final AxisAlignedBB RING_SOUTH_Z_AABB1 = Utils.creatAABB(0, 3, 14, 16, 13, 16);
	protected static final AxisAlignedBB RING_SOUTH_Z_AABB2 = Utils.creatAABB(1, 2, 14, 15, 14, 16);
	protected static final AxisAlignedBB RING_SOUTH_Z_AABB3 = Utils.creatAABB(2, 1, 14, 14, 15, 16);
	protected static final AxisAlignedBB RING_SOUTH_Z_AABB4 = Utils.creatAABB(3, 0, 14, 13, 16, 16);

	public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.<EnumShape> create("shape", EnumShape.class);
	public static final PropertyBool NORTH = Utils.NORTH;
	public static final PropertyBool SOUTH = Utils.SOUTH;
	public static final PropertyBool WEST = Utils.WEST;
	public static final PropertyBool EAST = Utils.EAST;
	public static final PropertyBool UP = Utils.UP;
	public static final PropertyBool DOWN = Utils.DOWN;

	public BlockEarthenPipe()
	{
		super(Material.ROCK);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("earthen_pipe");
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.AXIS_X).withProperty(NORTH, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		IBlockState state = this.getDefaultState();

		switch(facing.getAxis())
		{
		case X:
		default:
			return state.withProperty(SHAPE, EnumShape.AXIS_X);
		case Y:
			return state.withProperty(SHAPE, EnumShape.AXIS_Y);
		case Z:
			return state.withProperty(SHAPE, EnumShape.AXIS_Z);
		}
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
		boolean north = bstate.getValue(NORTH).booleanValue();
		boolean south = bstate.getValue(SOUTH).booleanValue();
		boolean west = bstate.getValue(WEST).booleanValue();
		boolean east = bstate.getValue(EAST).booleanValue();
		boolean up = bstate.getValue(UP).booleanValue();
		boolean down = bstate.getValue(DOWN).booleanValue();

		switch(bstate.getValue(SHAPE))
		{
		case AXIS_X:
		default:
			if(west)
			{
				list.add(CONNECTION_WEST_X_AABB1);
				list.add(CONNECTION_WEST_X_AABB2);
				list.add(CONNECTION_WEST_X_AABB3);
			}
			else
			{
				list.add(RING_WEST_X_AABB1);
				list.add(RING_WEST_X_AABB2);
				list.add(RING_WEST_X_AABB3);
				list.add(RING_WEST_X_AABB4);
			}

			if(east)
			{
				list.add(CONNECTION_EAST_X_AABB1);
				list.add(CONNECTION_EAST_X_AABB2);
				list.add(CONNECTION_EAST_X_AABB3);
			}
			else
			{
				list.add(RING_EAST_X_AABB1);
				list.add(RING_EAST_X_AABB2);
				list.add(RING_EAST_X_AABB3);
				list.add(RING_EAST_X_AABB4);
			}
			list.add(BASE_X_AABB1);
			list.add(BASE_X_AABB2);
			list.add(BASE_X_AABB3);
			break;
		case AXIS_Y:
			if(down)
			{
				list.add(CONNECTION_DOWN_Y_AABB1);
				list.add(CONNECTION_DOWN_Y_AABB2);
				list.add(CONNECTION_DOWN_Y_AABB3);
			}
			else
			{
				list.add(RING_DOWN_Y_AABB1);
				list.add(RING_DOWN_Y_AABB2);
				list.add(RING_DOWN_Y_AABB3);
				list.add(RING_DOWN_Y_AABB4);
			}

			if(up)
			{
				list.add(CONNECTION_UP_Y_AABB1);
				list.add(CONNECTION_UP_Y_AABB2);
				list.add(CONNECTION_UP_Y_AABB3);
			}
			else
			{
				list.add(RING_UP_Y_AABB1);
				list.add(RING_UP_Y_AABB2);
				list.add(RING_UP_Y_AABB3);
				list.add(RING_UP_Y_AABB4);
			}
			list.add(BASE_Y_AABB1);
			list.add(BASE_Y_AABB2);
			list.add(BASE_Y_AABB3);
			break;
		case AXIS_Z:
			if(north)
			{
				list.add(CONNECTION_NORTH_Z_AABB1);
				list.add(CONNECTION_NORTH_Z_AABB2);
				list.add(CONNECTION_NORTH_Z_AABB3);
			}
			else
			{
				list.add(RING_NORTH_Z_AABB1);
				list.add(RING_NORTH_Z_AABB2);
				list.add(RING_NORTH_Z_AABB3);
				list.add(RING_NORTH_Z_AABB4);
			}

			if(south)
			{
				list.add(CONNECTION_SOUTH_Z_AABB1);
				list.add(CONNECTION_SOUTH_Z_AABB2);
				list.add(CONNECTION_SOUTH_Z_AABB3);
			}
			else
			{
				list.add(RING_SOUTH_Z_AABB1);
				list.add(RING_SOUTH_Z_AABB2);
				list.add(RING_SOUTH_Z_AABB3);
				list.add(RING_SOUTH_Z_AABB4);
			}
			list.add(BASE_Z_AABB1);
			list.add(BASE_Z_AABB2);
			list.add(BASE_Z_AABB3);
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
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SHAPE, EnumShape.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(SHAPE).getMeta();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, Boolean.valueOf(this.getAxis(state, world, pos.north(), EnumShape.AXIS_Z)))
				.withProperty(SOUTH, Boolean.valueOf(this.getAxis(state, world, pos.south(), EnumShape.AXIS_Z)))
				.withProperty(WEST, Boolean.valueOf(this.getAxis(state, world, pos.west(), EnumShape.AXIS_X)))
				.withProperty(EAST, Boolean.valueOf(this.getAxis(state, world, pos.east(), EnumShape.AXIS_X)))
				.withProperty(UP, Boolean.valueOf(this.getAxis(state, world, pos.up(), EnumShape.AXIS_Y)))
				.withProperty(DOWN, Boolean.valueOf(this.getAxis(state, world, pos.down(), EnumShape.AXIS_Y)));
	}

	private boolean getAxis(IBlockState state, IBlockAccess world, BlockPos pos, EnumShape shape)
	{
		EnumShape shape1 = state.getValue(SHAPE);

		if(shape1 == shape)
		{
			IBlockState staten = world.getBlockState(pos);
			IBlockState target = this.getDefaultState();
			return staten == target.withProperty(SHAPE, shape);
		}
		else
			return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ SHAPE, NORTH, SOUTH, WEST, EAST, UP, DOWN, });
	}

	public static enum EnumShape implements IStringSerializable
	{
		AXIS_X(0, "axis_x"),
		AXIS_Y(1, "axis_y"),
		AXIS_Z(2, "axis_z"),;

		private static final EnumShape[] META_LOOKUP = new EnumShape[values().length];
		private final int meta;
		private final String name;

		private EnumShape(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta()
		{
			return this.meta;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		public static int getLength()
		{
			return EnumShape.values().length;
		}

		public static EnumShape byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(EnumShape shape : values())
			{
				META_LOOKUP[shape.getMeta()] = shape;
			}
		}
	}
}