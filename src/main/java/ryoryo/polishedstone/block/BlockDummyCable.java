package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cofh.redstoneflux.api.IEnergyConnection;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.NumericalConstant;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class BlockDummyCable extends Block
{
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType> create("type", EnumType.class);
	public static final PropertyBool NORTH = Utils.NORTH;
	public static final PropertyBool SOUTH = Utils.SOUTH;
	public static final PropertyBool WEST = Utils.WEST;
	public static final PropertyBool EAST = Utils.EAST;
	public static final PropertyBool UP = Utils.UP;
	public static final PropertyBool DOWN = Utils.DOWN;

	public BlockDummyCable()
	{
		super(Material.IRON);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("dummy_cable");
		this.setHardness(1.0F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.NORMAL).withProperty(NORTH, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)));
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
	@SideOnly (Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {

		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		double min = state.getValue(TYPE).getAABBMin();
		double max = 1 - min;

		return new AxisAlignedBB(min, min, min, max, max, max);
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
		double min = bstate.getValue(TYPE).getAABBMin();
		double max = 1 - min;

		list.add(new AxisAlignedBB(min, min, min, max, max, max));

		if(north)
			list.add(new AxisAlignedBB(min, min, 0, max, max, max));
		if(south)
			list.add(new AxisAlignedBB(min, min, min, max, max, 1));
		if(west)
			list.add(new AxisAlignedBB(0, min, min, max, max, max));
		if(east)
			list.add(new AxisAlignedBB(min, min, min, 1, max, max));
		if(down)
			list.add(new AxisAlignedBB(min, 0, min, max, max, max));
		if(up)
			list.add(new AxisAlignedBB(min, min, min, max, 1, max));

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
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(TYPE).getMeta());
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, EnumType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, Boolean.valueOf(this.canConnect(state, world, pos.north(), EnumFacing.NORTH)))
				.withProperty(SOUTH, Boolean.valueOf(this.canConnect(state, world, pos.south(), EnumFacing.SOUTH)))
				.withProperty(WEST, Boolean.valueOf(this.canConnect(state, world, pos.west(), EnumFacing.WEST)))
				.withProperty(EAST, Boolean.valueOf(this.canConnect(state, world, pos.east(), EnumFacing.EAST)))
				.withProperty(UP, Boolean.valueOf(this.canConnect(state, world, pos.up(), EnumFacing.UP)))
				.withProperty(DOWN, Boolean.valueOf(this.canConnect(state, world, pos.down(), EnumFacing.DOWN)));
	}

	private boolean canConnect(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		EnumType type = state.getValue(TYPE);
		IBlockState target = world.getBlockState(pos);
		Block targetBlock = target.getBlock();
		TileEntity targetTile = world.getTileEntity(pos);

		return (targetBlock == this && type == target.getValue(TYPE))
				|| targetBlock.canConnectRedstone(state, world, pos, side)//TODO
				|| targetBlock instanceof BlockRedstoneWire
				|| targetTile instanceof IInventory
				|| targetTile instanceof IFluidHandler
				|| targetTile instanceof IEnergyConnection
				|| targetTile instanceof IEnergyStorage;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TYPE, NORTH, SOUTH, WEST, EAST, UP, DOWN, });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		Utils.registerSubBlocks(this, EnumType.getLength(), tab, list);
	}

	public static enum EnumType implements IStringSerializable
	{
		NORMAL(0, "normal", NumericalConstant.D6_0),
		THICK(1, "thick", NumericalConstant.D5_0),
		THICKER(2, "thicker", NumericalConstant.D4_0),
		RED(3, "red", NumericalConstant.D5_0),;

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;
		private final double aabbMin;

		private EnumType(int meta, String name, double aabbMin)
		{
			this.meta = meta;
			this.name = name;
			this.aabbMin = aabbMin;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public double getAABBMin()
		{
			return this.aabbMin;
		}

		public static int getLength()
		{
			return EnumType.values().length;
		}

		public static EnumType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(EnumType enumtype : values())
			{
				META_LOOKUP[enumtype.getMeta()] = enumtype;
				NAMES[enumtype.getMeta()] = enumtype.getName();
			}
		}
	}
}