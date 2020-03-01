package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
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

public class BlockLateralGroove extends BlockModBase
{
	protected static final AxisAlignedBB BASE_AABB = Utils.creatAABB(0, 0, 0, 16, 1, 16);

	protected static final AxisAlignedBB GROOVE_AABB_NORTH1 = Utils.creatAABB(0, 0, 0, 6, 1, 16);
	protected static final AxisAlignedBB GROOVE_AABB_NORTH2 = Utils.creatAABB(6, 0, 0.5, 10, 1, 15.5);
	protected static final AxisAlignedBB GROOVE_AABB_NORTH3 = Utils.creatAABB(10, 0, 0, 16, 1, 16);
	protected static final AxisAlignedBB GROOVE_AABB_WEST1 = Utils.creatAABB(0, 0, 0, 16, 1, 6);
	protected static final AxisAlignedBB GROOVE_AABB_WEST2 = Utils.creatAABB(0.5, 0, 6, 15.5, 1, 10);
	protected static final AxisAlignedBB GROOVE_AABB_WEST3 = Utils.creatAABB(0, 0, 10, 16, 1, 16);

	protected static final AxisAlignedBB GROOVE_MESH_AABB_NORTH1 = Utils.creatAABB(0, 0.7, 0, 2, 1, 16);
	protected static final AxisAlignedBB GROOVE_MESH_AABB_NORTH2 = Utils.creatAABB(2, 0, 0, 14, 1, 16);
	protected static final AxisAlignedBB GROOVE_MESH_AABB_NORTH3 = Utils.creatAABB(14, 0.7, 0, 16, 1, 16);
	protected static final AxisAlignedBB GROOVE_MESH_AABB_WEST1 = Utils.creatAABB(0, 0.7, 0, 16, 1, 2);
	protected static final AxisAlignedBB GROOVE_MESH_AABB_WEST2 = Utils.creatAABB(0, 0, 2, 16, 1, 14);
	protected static final AxisAlignedBB GROOVE_MESH_AABB_WEST3 = Utils.creatAABB(0, 0.7, 14, 16, 1, 16);

	protected static final AxisAlignedBB GROOVE_CENTRAL_NORTH1 = Utils.creatAABB(0, 0, 0, 16, 1, 3);
	protected static final AxisAlignedBB GROOVE_CENTRAL_NORTH2 = Utils.creatAABB(0, 0, 3, 7, 1, 4);
	protected static final AxisAlignedBB GROOVE_CENTRAL_NORTH3 = Utils.creatAABB(9, 0, 3, 16, 1, 4);
	protected static final AxisAlignedBB GROOVE_CENTRAL_NORTH4 = Utils.creatAABB(0, 0, 4, 16, 1, 16);

	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType> create("type", EnumType.class);

	public BlockLateralGroove()
	{
		super(Material.ROCK, "lateral_groove");
		this.setHardness(0.8F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.NORMAL_NS));
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

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		EnumType type = state.getValue(TYPE);

		switch(type)
		{
		case NORMAL_NS:
		case NORMAL_WE:
		case POLISHED_NS:
		case POLISHED_WE:
		case CENTRAL_NORMAL:
		case CENTRAL_POLISHED:
		default:
			return SoundType.STONE;
		case MESH_NS:
		case MESH_WE:
			return SoundType.METAL;
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BASE_AABB;
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
		EnumType type = bstate.getValue(TYPE);

		switch(type)
		{
		case NORMAL_NS:
		case POLISHED_NS:
		default:
			list.add(GROOVE_AABB_NORTH1);
			list.add(GROOVE_AABB_NORTH2);
			list.add(GROOVE_AABB_NORTH3);
			break;
		case NORMAL_WE:
		case POLISHED_WE:
			list.add(GROOVE_AABB_WEST1);
			list.add(GROOVE_AABB_WEST2);
			list.add(GROOVE_AABB_WEST3);
			break;
		case MESH_NS:
			list.add(GROOVE_MESH_AABB_NORTH1);
			list.add(GROOVE_MESH_AABB_NORTH2);
			list.add(GROOVE_MESH_AABB_NORTH3);
			break;
		case MESH_WE:
			list.add(GROOVE_MESH_AABB_WEST1);
			list.add(GROOVE_MESH_AABB_WEST2);
			list.add(GROOVE_MESH_AABB_WEST3);
			break;
		case CENTRAL_NORMAL:
		case CENTRAL_POLISHED:
			list.add(GROOVE_CENTRAL_NORTH1);
			list.add(GROOVE_CENTRAL_NORTH2);
			list.add(GROOVE_CENTRAL_NORTH3);
			list.add(GROOVE_CENTRAL_NORTH4);
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
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(TYPE).getItemMeta());
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TYPE, });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		Utils.registerSubBlocks(this, 5, tab, list);
	}

	public static enum EnumType implements IStringSerializable
	{
		NORMAL_NS(0, 0, "normal_ns", "normal"),
		NORMAL_WE(1, 0, "normal_we", "normal"),
		POLISHED_NS(2, 1, "polished_ns", "polished"),
		POLISHED_WE(3, 1, "polished_we", "polished"),
		MESH_NS(4, 2, "mesh_ns", "mesh"),
		MESH_WE(5, 2, "mesh_we", "mesh"),
		CENTRAL_NORMAL(6, 3, "central_normal", "central_normal"),
		CENTRAL_POLISHED(7, 4, "central_polished", "central_polished"),;

		private static final EnumType[] METADATA_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final int itemMeta;
		private final String name;
		private final String unlocalizeName;

		private EnumType(int meta, int itemMeta, String name, String unlocalizeName)
		{
			this.meta = meta;
			this.itemMeta = itemMeta;
			this.name = name;
			this.unlocalizeName = unlocalizeName;
		}

		@Override
		public String getName()
		{
			return name;
		}

		public String toString()
		{
			return this.unlocalizeName;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public int getItemMeta()
		{
			return this.itemMeta;
		}

		public static int getLength()
		{
			return EnumType.values().length;
		}

		public static EnumType byMeta(int meta)
		{
			if(meta < 0 || meta >= METADATA_LOOKUP.length)
			{
				meta = 0;
			}

			return METADATA_LOOKUP[meta];
		}

		static
		{
			for(EnumType enumtype : values())
			{
				METADATA_LOOKUP[enumtype.getMeta()] = enumtype;
			}
		}
	}
}
