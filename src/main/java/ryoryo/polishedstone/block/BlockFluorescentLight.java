package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedlib.util.Utils;

public class BlockFluorescentLight extends BlockModBase {
	public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.<EnumShape> create("shape", EnumShape.class);
	public static final PropertyBool NORTH = Props.NORTH;
	public static final PropertyBool SOUTH = Props.SOUTH;
	public static final PropertyBool WEST = Props.WEST;
	public static final PropertyBool EAST = Props.EAST;
	public static final PropertyBool UP = Props.UP;
	public static final PropertyBool DOWN = Props.DOWN;

	public BlockFluorescentLight(String name, float lightLevel) {
		super(Material.GLASS, "fluorescent_light_" + name, SoundType.GLASS);
		this.setHardness(0.05F);
		this.setLightLevel(lightLevel);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, EnumShape.NORTH_H).withProperty(NORTH, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = this.getDefaultState();
		EnumFacing facingp = placer.getHorizontalFacing();
		boolean flg = placer.isSneaking();

		switch(facing) {
			case NORTH:
			default:
				return state.withProperty(SHAPE, flg ? EnumShape.NORTH_V : EnumShape.NORTH_H);
			case SOUTH:
				return state.withProperty(SHAPE, flg ? EnumShape.SOUTH_V : EnumShape.SOUTH_H);
			case WEST:
				return state.withProperty(SHAPE, flg ? EnumShape.WEST_V : EnumShape.WEST_H);
			case EAST:
				return state.withProperty(SHAPE, flg ? EnumShape.EAST_V : EnumShape.EAST_H);
			case UP:
				switch(facingp) {
					case NORTH:
					case SOUTH:
					default:
						return state.withProperty(SHAPE, EnumShape.UP_WE);
					case WEST:
					case EAST:
						return state.withProperty(SHAPE, EnumShape.UP_NS);
				}
			case DOWN:
				switch(facingp) {
					case NORTH:
					case SOUTH:
					default:
						return state.withProperty(SHAPE, EnumShape.DOWN_WE);
					case WEST:
					case EAST:
						return state.withProperty(SHAPE, EnumShape.DOWN_NS);
				}
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		boolean north = state.getValue(NORTH).booleanValue();
		boolean south = state.getValue(SOUTH).booleanValue();
		boolean west = state.getValue(WEST).booleanValue();
		boolean east = state.getValue(EAST).booleanValue();
		boolean up = state.getValue(UP).booleanValue();
		boolean down = state.getValue(DOWN).booleanValue();

		switch(state.getValue(SHAPE)) {
			case NORTH_H:
			default:
				if(west && east)
					return Utils.creatAABB(0, 7, 14, 16, 9, 16);
				else if(west && !east)
					return Utils.creatAABB(0, 7, 14, 15, 9, 16);
				else if(!west && east)
					return Utils.creatAABB(1, 7, 14, 16, 9, 16);
				else
					return Utils.creatAABB(1, 7, 14, 15, 9, 16);
			case NORTH_V:
				if(up && down)
					return Utils.creatAABB(7, 0, 14, 9, 16, 16);
				else if(up && !down)
					return Utils.creatAABB(7, 1, 14, 9, 16, 16);
				else if(!up && down)
					return Utils.creatAABB(7, 0, 14, 9, 15, 16);
				else
					return Utils.creatAABB(7, 1, 14, 9, 15, 16);
			case SOUTH_H:
				if(west && east)
					return Utils.creatAABB(0, 7, 0, 16, 9, 2);
				else if(west && !east)
					return Utils.creatAABB(0, 7, 0, 15, 9, 2);
				else if(!west && east)
					return Utils.creatAABB(1, 7, 0, 16, 9, 2);
				else
					return Utils.creatAABB(1, 7, 0, 15, 9, 2);
			case SOUTH_V:
				if(up && down)
					return Utils.creatAABB(7, 0, 0, 9, 16, 2);
				else if(up && !down)
					return Utils.creatAABB(7, 1, 0, 9, 16, 2);
				else if(!up && down)
					return Utils.creatAABB(7, 0, 0, 9, 15, 2);
				else
					return Utils.creatAABB(7, 1, 0, 9, 15, 2);
			case WEST_H:
				if(north && south)
					return Utils.creatAABB(14, 7, 0, 16, 9, 16);
				else if(north && !south)
					return Utils.creatAABB(14, 7, 0, 16, 9, 15);
				else if(!north && south)
					return Utils.creatAABB(14, 7, 1, 16, 9, 16);
				else
					return Utils.creatAABB(14, 7, 1, 16, 9, 15);
			case WEST_V:
				if(up && down)
					return Utils.creatAABB(14, 0, 7, 16, 16, 9);
				else if(up && !down)
					return Utils.creatAABB(14, 1, 7, 16, 16, 9);
				else if(!up && down)
					return Utils.creatAABB(14, 0, 7, 16, 15, 9);
				else
					return Utils.creatAABB(14, 1, 7, 16, 15, 9);
			case EAST_H:
				if(north && south)
					return Utils.creatAABB(0, 7, 0, 2, 9, 16);
				else if(north && !south)
					return Utils.creatAABB(0, 7, 0, 2, 9, 15);
				else if(!north && south)
					return Utils.creatAABB(0, 7, 1, 2, 9, 16);
				else
					return Utils.creatAABB(0, 7, 1, 2, 9, 15);
			case EAST_V:
				if(up && down)
					return Utils.creatAABB(0, 0, 7, 2, 16, 9);
				else if(up && !down)
					return Utils.creatAABB(0, 1, 7, 2, 16, 9);
				else if(!up && down)
					return Utils.creatAABB(0, 0, 7, 2, 15, 9);
				else
					return Utils.creatAABB(0, 1, 7, 2, 15, 9);
			case UP_NS:
				if(north && south)
					return Utils.creatAABB(7, 0, 0, 9, 2, 16);
				else if(north && !south)
					return Utils.creatAABB(7, 0, 0, 9, 2, 15);
				else if(!north && south)
					return Utils.creatAABB(7, 0, 1, 9, 2, 16);
				else
					return Utils.creatAABB(7, 0, 1, 9, 2, 15);
			case UP_WE:
				if(west && east)
					return Utils.creatAABB(0, 0, 7, 16, 2, 9);
				else if(west && !east)
					return Utils.creatAABB(0, 0, 7, 15, 2, 9);
				else if(!west && east)
					return Utils.creatAABB(1, 0, 7, 16, 2, 9);
				else
					return Utils.creatAABB(1, 0, 7, 15, 2, 9);
			case DOWN_NS:
				if(north && south)
					return Utils.creatAABB(7, 14, 0, 9, 16, 16);
				else if(north && !south)
					return Utils.creatAABB(7, 14, 0, 9, 16, 15);
				else if(!north && south)
					return Utils.creatAABB(7, 14, 1, 9, 16, 16);
				else
					return Utils.creatAABB(7, 14, 1, 9, 16, 15);
			case DOWN_WE:
				if(west && east)
					return Utils.creatAABB(0, 14, 7, 16, 16, 9);
				else if(west && !east)
					return Utils.creatAABB(0, 14, 7, 15, 16, 9);
				else if(!west && east)
					return Utils.creatAABB(1, 14, 7, 16, 16, 9);
				else
					return Utils.creatAABB(1, 14, 7, 15, 16, 9);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(SHAPE, EnumShape.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).getMeta();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(NORTH, Boolean.valueOf(this.getNS(state, world, pos.north())))
				.withProperty(SOUTH, Boolean.valueOf(this.getNS(state, world, pos.south())))
				.withProperty(WEST, Boolean.valueOf(this.getWE(state, world, pos.west())))
				.withProperty(EAST, Boolean.valueOf(this.getWE(state, world, pos.east())))
				.withProperty(UP, Boolean.valueOf(this.getUD(state, world, pos.up())))
				.withProperty(DOWN, Boolean.valueOf(this.getUD(state, world, pos.down())));
	}

	private boolean getNS(IBlockState state, IBlockAccess world, BlockPos pos) {
		EnumShape shape = state.getValue(SHAPE);

		if(shape == EnumShape.WEST_H || shape == EnumShape.EAST_H || shape == EnumShape.UP_NS || shape == EnumShape.DOWN_NS) {
			IBlockState staten = world.getBlockState(pos);
			IBlockState target = this.getDefaultState();
			return staten == target.withProperty(SHAPE, EnumShape.WEST_H)
					|| staten == target.withProperty(SHAPE, EnumShape.EAST_H)
					|| staten == target.withProperty(SHAPE, EnumShape.UP_NS)
					|| staten == target.withProperty(SHAPE, EnumShape.DOWN_NS);
		}
		else
			return false;
	}

	private boolean getWE(IBlockState state, IBlockAccess world, BlockPos pos) {
		EnumShape shape = state.getValue(SHAPE);

		if(shape == EnumShape.NORTH_H || shape == EnumShape.SOUTH_H || shape == EnumShape.UP_WE || shape == EnumShape.DOWN_WE) {
			IBlockState staten = world.getBlockState(pos);
			IBlockState target = this.getDefaultState();

			return staten == target.withProperty(SHAPE, EnumShape.NORTH_H)
					|| staten == target.withProperty(SHAPE, EnumShape.SOUTH_H)
					|| staten == target.withProperty(SHAPE, EnumShape.UP_WE)
					|| staten == target.withProperty(SHAPE, EnumShape.DOWN_WE);
		}
		else
			return false;
	}

	private boolean getUD(IBlockState state, IBlockAccess world, BlockPos pos) {
		EnumShape shape = state.getValue(SHAPE);

		if(shape == EnumShape.NORTH_V || shape == EnumShape.SOUTH_V || shape == EnumShape.WEST_V || shape == EnumShape.EAST_V) {
			IBlockState staten = world.getBlockState(pos);
			IBlockState target = this.getDefaultState();

			return staten == target.withProperty(SHAPE, EnumShape.NORTH_V)
					|| staten == target.withProperty(SHAPE, EnumShape.SOUTH_V)
					|| staten == target.withProperty(SHAPE, EnumShape.WEST_V)
					|| staten == target.withProperty(SHAPE, EnumShape.EAST_V);
		}
		else
			return false;
	}

	private boolean isConnected(IBlockState state) {
		return state.getValue(NORTH).booleanValue()
				|| state.getValue(SOUTH).booleanValue()
				|| state.getValue(WEST).booleanValue()
				|| state.getValue(EAST).booleanValue()
				|| state.getValue(UP).booleanValue()
				|| state.getValue(DOWN).booleanValue();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { SHAPE, NORTH, SOUTH, WEST, EAST, UP, DOWN, });
	}

	public static enum EnumShape implements IStringSerializable
	{
		/**
		 * H = Horizontal V = Vertical NS = North-South WE = West-East
		 */
		NORTH_H(0, "north_h"),
		NORTH_V(1, "north_v"),
		SOUTH_H(2, "south_h"),
		SOUTH_V(3, "south_v"),
		WEST_H(4, "west_h"),
		WEST_V(5, "west_v"),
		EAST_H(6, "east_h"),
		EAST_V(7, "east_v"),
		UP_NS(8, "up_ns"),
		UP_WE(9, "up_we"),
		DOWN_NS(10, "down_ns"),
		DOWN_WE(11, "down_we"),;

		private static final EnumShape[] META_LOOKUP = new EnumShape[values().length];
		private final int meta;
		private final String name;

		private EnumShape(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		public int getMeta() {
			return this.meta;
		}

		@Override
		public String getName() {
			return this.name;
		}

		public static int getLength() {
			return EnumShape.values().length;
		}

		public static EnumShape byMeta(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static {
			for(EnumShape shape : values()) {
				META_LOOKUP[shape.getMeta()] = shape;
			}
		}
	}
}