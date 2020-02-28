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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class BlockThreePillars extends Block
{
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE = Utils.creatAABB(7, 0, 7, 9, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_N = Utils.creatAABB(7, 0, 0, 9, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_S = Utils.creatAABB(7, 0, 7, 9, 16, 16);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_W = Utils.creatAABB(7, 0, 7, 16, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_E = Utils.creatAABB(0, 0, 7, 9, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_NW = Utils.creatAABB(0, 0, 0, 9, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_NE = Utils.creatAABB(7, 0, 0, 16, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_SW = Utils.creatAABB(0, 0, 7, 9, 16, 16);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_SE = Utils.creatAABB(7, 0, 7, 16, 16, 16);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_NWE = Utils.creatAABB(0, 0, 0, 16, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_NSW = Utils.creatAABB(0, 0, 0, 9, 16, 16);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_SWE = Utils.creatAABB(0, 0, 7, 16, 16, 16);
	protected static final AxisAlignedBB BASE_AABB_UP_SINGLE_NSE = Utils.creatAABB(7, 0, 0, 16, 16, 16);
	protected static final AxisAlignedBB COLL_AABB_UP_SINGLE_N = Utils.creatAABB(7, 0, 0, 9, 16, 7);
	protected static final AxisAlignedBB COLL_AABB_UP_SINGLE_S = Utils.creatAABB(7, 0, 9, 9, 16, 16);
	protected static final AxisAlignedBB COLL_AABB_UP_SINGLE_W = Utils.creatAABB(0, 0, 7, 7, 16, 9);
	protected static final AxisAlignedBB COLL_AABB_UP_SINGLE_E = Utils.creatAABB(9, 0, 7, 16, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_NS = Utils.creatAABB(7, 0, 0, 9, 16, 16);
	protected static final AxisAlignedBB BASE_AABB_WE = Utils.creatAABB(0, 0, 7, 16, 16, 9);
	protected static final AxisAlignedBB BASE_AABB_SIDE_H = Utils.creatAABB(0, 7, 0, 16, 9, 16);

	public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.<EnumShape> create("shape", EnumShape.class);
	public static final PropertyBool NORTH = Utils.NORTH;
	public static final PropertyBool SOUTH = Utils.SOUTH;
	public static final PropertyBool WEST = Utils.WEST;
	public static final PropertyBool EAST = Utils.EAST;

	public BlockThreePillars()
	{
		super(Material.ROCK);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("three_pillars");
		this.setHardness(1.0F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.NORMAL_UP_SINGLE).withProperty(NORTH, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)));
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
		EnumFacing facingp = placer.getHorizontalFacing();
		boolean flg = placer.isSneaking();

		if(meta == 0)
		{
			switch(facing.getAxis())
			{
			case Z:
			default :
				return state.withProperty(SHAPE, flg ? EnumShape.NORMAL_SIDE_NS_V : EnumShape.NORMAL_SIDE_NS_H);
			case X:
				return state.withProperty(SHAPE, flg ? EnumShape.NORMAL_SIDE_WE_V : EnumShape.NORMAL_SIDE_WE_H);
			case Y:
				if(flg)
					return state.withProperty(SHAPE, EnumShape.NORMAL_UP_SINGLE);
				else
				{
					switch(facingp.getAxis())
					{
					case Z:
					default:
						return state.withProperty(SHAPE, EnumShape.NORMAL_UP_WE);
					case X:
						return state.withProperty(SHAPE, EnumShape.NORMAL_UP_NS);
					}
				}
			}
		}
		else
		{
			switch(facing.getAxis())
			{
			case Z:
			default :
				return state.withProperty(SHAPE, flg ? EnumShape.BLACK_SIDE_NS_V : EnumShape.BLACK_SIDE_NS_H);
			case X:
				return state.withProperty(SHAPE, flg ? EnumShape.BLACK_SIDE_WE_V : EnumShape.BLACK_SIDE_WE_H);
			case Y:
				if(flg)
					return state.withProperty(SHAPE, EnumShape.BLACK_UP_SINGLE);
				else
				{
					switch(facingp.getAxis())
					{
					case Z:
					default:
						return state.withProperty(SHAPE, EnumShape.BLACK_UP_WE);
					case X:
						return state.withProperty(SHAPE, EnumShape.BLACK_UP_NS);
					}
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		state = this.getActualState(state, source, pos);
		EnumShape shape = state.getValue(SHAPE);
		boolean north = state.getValue(NORTH).booleanValue();
		boolean south = state.getValue(SOUTH).booleanValue();
		boolean west = state.getValue(WEST).booleanValue();
		boolean east = state.getValue(EAST).booleanValue();

		switch(shape)
		{
		case NORMAL_UP_SINGLE:
		case BLACK_UP_SINGLE:
		default:
			if(north && south && west && east)
				return FULL_BLOCK_AABB;
			else if(!north && south && west && east)
				return BASE_AABB_UP_SINGLE_SWE;
			else if(north && !south && west && east)
				return BASE_AABB_UP_SINGLE_NWE;
			else if(north && south && !west && east)
				return BASE_AABB_UP_SINGLE_NSE;
			else if(north && south && west && !east)
				return BASE_AABB_UP_SINGLE_NSW;
			else if(!north && !south && west && east)
				return BASE_AABB_WE;
			else if(north && !south && !west && east)
				return BASE_AABB_UP_SINGLE_NE;
			else if(north && south && !west && !east)
				return BASE_AABB_NS;
			else if(!north && south && west && !east)
				return BASE_AABB_UP_SINGLE_SW;
			else if(north && !south && west && !east)
				return BASE_AABB_UP_SINGLE_NW;
			else if(!north && south && !west && east)
				return BASE_AABB_UP_SINGLE_SE;
			else if(north && !south && !west && !east)
				return BASE_AABB_UP_SINGLE_N;
			else if(!north && south && !west && !east)
				return BASE_AABB_UP_SINGLE_S;
			else if(!north && !south && west && !east)
				return BASE_AABB_UP_SINGLE_E;
			else if(!north && !south && !west && east)
				return BASE_AABB_UP_SINGLE_W;
			else
				return BASE_AABB_UP_SINGLE;
		case NORMAL_UP_NS:
		case NORMAL_SIDE_NS_V:
		case BLACK_UP_NS:
		case BLACK_SIDE_NS_V:
			return BASE_AABB_NS;
		case NORMAL_UP_WE:
		case NORMAL_SIDE_WE_V:
		case BLACK_UP_WE:
		case BLACK_SIDE_WE_V:
			return BASE_AABB_WE;
		case NORMAL_SIDE_NS_H:
		case NORMAL_SIDE_WE_H:
		case BLACK_SIDE_NS_H:
		case BLACK_SIDE_WE_H:
			return BASE_AABB_SIDE_H;
		}
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
		EnumShape shape = bstate.getValue(SHAPE);
		boolean north = bstate.getValue(NORTH).booleanValue();
		boolean south = bstate.getValue(SOUTH).booleanValue();
		boolean west = bstate.getValue(WEST).booleanValue();
		boolean east = bstate.getValue(EAST).booleanValue();

		switch(shape)
		{
		case NORMAL_UP_SINGLE:
		case BLACK_UP_SINGLE:
		default:
			list.add(BASE_AABB_UP_SINGLE);
			if(north)
				list.add(COLL_AABB_UP_SINGLE_N);
			if(south)
				list.add(COLL_AABB_UP_SINGLE_S);
			if(west)
				list.add(COLL_AABB_UP_SINGLE_W);
			if(east)
				list.add(COLL_AABB_UP_SINGLE_E);
			break;
		case NORMAL_UP_NS:
		case NORMAL_SIDE_NS_V:
		case BLACK_UP_NS:
		case BLACK_SIDE_NS_V:
			list.add(BASE_AABB_NS);
			break;
		case NORMAL_UP_WE:
		case NORMAL_SIDE_WE_V:
		case BLACK_UP_WE:
		case BLACK_SIDE_WE_V:
			list.add(BASE_AABB_WE);
			break;
		case NORMAL_SIDE_NS_H:
		case NORMAL_SIDE_WE_H:
		case BLACK_SIDE_NS_H:
		case BLACK_SIDE_WE_H:
			list.add(BASE_AABB_SIDE_H);
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
		boolean flg = state.getValue(SHAPE).getMeta() < 7;
		return new ItemStack(Item.getItemFromBlock(this), 1, flg ? 0 : 1);
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
		return state.withProperty(NORTH, canConnectNS(state, world, pos.north()))
				.withProperty(SOUTH, canConnectNS(state, world, pos.south()))
				.withProperty(WEST, canConnectWE(state, world, pos.west()))
				.withProperty(EAST, canConnectWE(state, world, pos.east()));
	}

	private boolean canConnectNS(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		EnumShape shape = state.getValue(SHAPE);
		if(shape == EnumShape.NORMAL_UP_SINGLE || shape == EnumShape.BLACK_UP_SINGLE)
		{
			IBlockState staten = world.getBlockState(pos);
			IBlockState target = this.getDefaultState();
			return staten == target.withProperty(SHAPE, EnumShape.NORMAL_UP_NS)
					|| staten == target.withProperty(SHAPE, EnumShape.BLACK_UP_NS);
		}

		return false;
	}

	private boolean canConnectWE(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		EnumShape shape = state.getValue(SHAPE);
		if(shape == EnumShape.NORMAL_UP_SINGLE || shape == EnumShape.BLACK_UP_SINGLE)
		{
			IBlockState staten = world.getBlockState(pos);
			IBlockState target = this.getDefaultState();
			return staten == target.withProperty(SHAPE, EnumShape.NORMAL_UP_WE)
					|| staten == target.withProperty(SHAPE, EnumShape.BLACK_UP_WE);
		}

		return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ SHAPE, NORTH, SOUTH, WEST, EAST, });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		Utils.registerSubBlocks(this, 2, tab, list);
	}

	public static enum EnumShape implements IStringSerializable
	{
		/**
		 * H = Horizontal
		 * V = Vertical
		 * NS = North-South
		 * WE = West-East
		 * UP = Upward
		 * SIDE = Sideways
		 */
		NORMAL_UP_SINGLE(0, 0, "normal_up_single", false),
		NORMAL_UP_NS(1, 0, "normal_up_ns", true),
		NORMAL_UP_WE(2, 0, "normal_up_we", true),
		NORMAL_SIDE_NS_V(3, 0, "normal_side_ns_v", false),
		NORMAL_SIDE_WE_V(4, 0, "normal_side_we_v", false),
		NORMAL_SIDE_NS_H(5, 0, "normal_side_ns_h", false),
		NORMAL_SIDE_WE_H(6, 0, "normal_side_we_h", false),

		BLACK_UP_SINGLE(7, 1, "black_up_single", false),
		BLACK_UP_NS(8, 1, "black_up_ns", true),
		BLACK_UP_WE(9, 1, "black_up_we", true),
		BLACK_SIDE_NS_V(10, 1, "black_side_ns_v", false),
		BLACK_SIDE_WE_V(11, 1, "black_side_we_v", false),
		BLACK_SIDE_NS_H(12, 1, "black_side_ns_h", false),
		BLACK_SIDE_WE_H(13, 1, "black_side_we_h", false),;

		private static final EnumShape[] META_LOOKUP = new EnumShape[values().length];
		private final int meta;
		private final int metaDummy;
		private final String name;
		private final boolean connectable;

		private EnumShape(int meta, int metaDummy, String name, boolean connectable)
		{
			this.meta = meta;
			this.metaDummy = metaDummy;
			this.name = name;
			this.connectable = connectable;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public int getMetaDummy()
		{
			return this.metaDummy;
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

		public boolean connectable()
		{
			return connectable;
		}

		public static boolean isNormal(EnumShape shape)
		{
			return shape == EnumShape.NORMAL_UP_SINGLE
					|| shape == EnumShape.NORMAL_UP_NS
					|| shape == EnumShape.NORMAL_UP_WE
					|| shape == EnumShape.NORMAL_SIDE_NS_V
					|| shape == EnumShape.NORMAL_SIDE_WE_V
					|| shape == EnumShape.NORMAL_SIDE_NS_H
					|| shape == EnumShape.NORMAL_SIDE_WE_H;
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