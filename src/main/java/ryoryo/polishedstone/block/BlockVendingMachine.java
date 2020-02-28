package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class BlockVendingMachine extends Block
{
	protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
	protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 1.0D, 1.0D);
	protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

	public static final PropertyDirection FACING = Utils.HORIZONTAL_FACING;
	public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.<EnumHalf> create("half", EnumHalf.class);

	public BlockVendingMachine(String name)
	{
		super(Material.IRON);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("vending_machine" + "_" + name);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setLightLevel(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, EnumHalf.LOWER));
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		state = state.getActualState(source, pos);
		EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);

		switch(enumfacing)
		{
		case EAST:
		default:
			return EAST_AABB;
		case SOUTH:
			return SOUTH_AABB;
		case WEST:
			return WEST_AABB;
		case NORTH:
			return NORTH_AABB;
		}
	}

//	@Override
//	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
//	{
//		EnumFacing enumfacing = EnumFacing.fromAngle((double) placer.rotationYaw);
//		BlockPos blockpos2 = pos.up();
//		IBlockState iblockstate = this.getDefaultState().withProperty(BlockVendingMachine.FACING, enumfacing);
//		world.setBlockState(pos, iblockstate.withProperty(HALF, EnumHalf.LOWER), 2);
//		world.setBlockState(blockpos2, iblockstate.withProperty(HALF, EnumHalf.UPPER), 2);
//		return this.getDefaultState();
//	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		if(state.getValue(HALF) == EnumHalf.UPPER)
		{
			BlockPos posd = pos.down();
			IBlockState stated = world.getBlockState(posd);

			if(stated.getBlock() != this)
			{
				world.setBlockToAir(pos);
			}
			else if(block != this)
			{
				stated.neighborChanged(world, posd, block, fromPos);
			}
		}
		else
		{
			boolean flag = false;
			BlockPos posu = pos.up();
			IBlockState stateu = world.getBlockState(posu);

			if(stateu.getBlock() != this)
			{
//				flag = true;
				world.setBlockToAir(pos);
			}

			if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP))
			{
				flag = true;
				world.setBlockToAir(pos);

				if(stateu.getBlock() == this)
				{
					world.setBlockToAir(posu);
				}
			}

			if(flag)
			{
				if(!world.isRemote)
				{
					this.dropBlockAsItem(world, pos, state, 0);
				}
			}
		}
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		return pos.getY() >= worldIn.getHeight() - 1 ? false : worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
	}

	public static int combineMetadata(IBlockAccess worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		int i = iblockstate.getBlock().getMetaFromState(iblockstate);
		boolean flag = isUpper(i);
		IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
		int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
		int k = flag ? j : i;
		IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
		int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
		int i1 = flag ? i : l;
		boolean flag1 = (i1 & 1) != 0;
		boolean flag2 = (i1 & 2) != 0;
		return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.getValue(HALF) != EnumHalf.LOWER ? state : state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	//ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	//		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta/* & 3*/)/*.rotateYCCW()*/).withProperty(TYPE, TexType.byMetadata(meta)).withProperty(HALF, EnumHalf.LOWER);
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, EnumHalf.UPPER).withProperty(FACING, EnumFacing.getHorizontal((meta & 3) + 8)) : this.getDefaultState().withProperty(HALF, EnumHalf.LOWER).withProperty(FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW());
	}

	//IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		if(state.getValue(HALF) == EnumHalf.UPPER)
		{
			i = i | 8 + ((EnumFacing) state.getValue(FACING)).rotateY().getHorizontalIndex();
		}
		else
		{
			i |= ((EnumFacing) state.getValue(FACING)).rotateY().getHorizontalIndex();
		}

		return i;
	}

	//初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ FACING, HALF });
	}

	protected static int removeHalfBit(int meta)
	{
		return meta & 7;
	}

	protected static boolean isUpper(int meta)
	{
		return (meta & 8) != 0;
	}

	public static enum TexType implements IStringSerializable
	{
		NORMAL(0, "normal"),
		COCA_COLA(1, "coca_cola"),
		SUNTORY(2, "suntory"),
		DYDO(3, "dydo"),
		ITO_EN(4, "ito_en"),
		SUICA(5, "suica"),
		MAX_COFFEE(6, "max_coffee"),
		LOCKER(7, "locker"),;

		private static final TexType[] META_LOOKUP = new TexType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private TexType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public static int getLength()
		{
			return TexType.values().length;
		}

		public static TexType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(TexType textype : values())
			{
				META_LOOKUP[textype.getMeta()] = textype;
				NAMES[textype.getMeta()] = textype.getName();
			}
		}
	}

	public static enum EnumHalf implements IStringSerializable
	{
		UPPER,
		LOWER;

		public String toString()
		{
			return this.getName();
		}

		public String getName()
		{
			return this == UPPER ? "upper" : "lower";
		}
	}
}
