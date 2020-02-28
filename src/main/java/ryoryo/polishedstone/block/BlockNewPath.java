package ryoryo.polishedstone.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.LibTool;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public class BlockNewPath extends BlockGrassPath
{
	protected static final AxisAlignedBB PATH_AABB = Utils.creatAABB(0, 0, 0, 16, 15, 16);
	protected static final AxisAlignedBB PATH_SOUL_SAND_AABB = Utils.creatAABB(0, 0, 0, 16, 13, 16);
	public static final PropertyEnum<PathType> TYPE = PropertyEnum.<PathType> create("type", PathType.class);

	public BlockNewPath()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("new_path");
		this.setHardness(0.65F);
		this.setHarvestLevel(LibTool.TOOL_CLASS_SHOVEL, LibTool.LEVEL_WOOD);
		this.setSoundType(SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, PathType.DIRT));
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		PathType type = state.getValue(TYPE);

		switch(type)
		{
		case SAND:
		case RED_SAND:
		case SOUL_SAND:
			return SoundType.SAND;
		default:
			return SoundType.GROUND;
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		PathType type = state.getValue(TYPE);

		switch(type)
		{
		case SOUL_SAND:
			return PATH_SOUL_SAND_AABB;
		default:
			return PATH_AABB;
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if(state.getValue(TYPE) == PathType.SOUL_SAND)
		{
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((PathType)state.getValue(TYPE)).getMeta());
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		PathType type = state.getValue(TYPE);
		switch(type)
		{
		case COARSE_DIRT:
		case RED_SAND:
		case PAVING_GRAVEL:
			return 1;
		default:
			return 0;
		}
	}

	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		PathType type = state.getValue(TYPE);

		switch(type)
		{
		case DIRT:
		case COARSE_DIRT:
		case PODZOL:
		default:
			return Item.getItemFromBlock(Blocks.DIRT);
		case GRAVEL:
			return Item.getItemFromBlock(Blocks.GRAVEL);
		case SAND:
		case RED_SAND:
			return Item.getItemFromBlock(Blocks.SAND);
		case CLAY:
			return Item.getItemFromBlock(Blocks.CLAY);
		case SOUL_SAND:
			return Item.getItemFromBlock(Blocks.SOUL_SAND);
		case OLD_GRAVEL:
		case PAVING_GRAVEL:
			return Item.getItemFromBlock(Register.BLOCK_NEW_GRAVEL);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		super.neighborChanged(state, world, pos, block, fromPos);

		if(world.getBlockState(pos.up()).getMaterial().isSolid())
		{
			PathType type = state.getValue(TYPE);

			switch(type)
			{
			case DIRT:
			case PODZOL:
			default:
				world.setBlockState(pos, Blocks.DIRT.getDefaultState());
				break;
			case COARSE_DIRT:
				world.setBlockState(pos, Blocks.DIRT/*.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT)*/.getStateFromMeta(1));
				break;
			case GRAVEL:
				world.setBlockState(pos, Blocks.GRAVEL.getDefaultState());
				break;
			case SAND:
				world.setBlockState(pos, Blocks.SAND.getDefaultState());
				break;
			case RED_SAND:
				world.setBlockState(pos, Blocks.SAND.getStateFromMeta(1));
				break;
			case CLAY:
				world.setBlockState(pos, Blocks.CLAY.getDefaultState());
				break;
			case SOUL_SAND:
				world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
				break;
			case OLD_GRAVEL:
				world.setBlockState(pos, Register.BLOCK_NEW_GRAVEL.getDefaultState());
				break;
			case PAVING_GRAVEL:
				world.setBlockState(pos, Register.BLOCK_NEW_GRAVEL.getStateFromMeta(1));
				break;
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, PathType.byMeta(meta));
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
		{ TYPE });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		Utils.registerSubBlocks(this, PathType.getLength(), tab, list);
	}

	public static enum PathType implements IStringSerializable
	{
		DIRT(0, "dirt"),
		COARSE_DIRT(1, "coarse_dirt"),
		PODZOL(2, "podzol"),
		GRAVEL(3, "gravel"),
		SAND(4, "sand"),
		RED_SAND(5, "red_sand"),
		CLAY(6, "clay"),
		SOUL_SAND(7, "soul_sand"),
		OLD_GRAVEL(8, "old_gravel"),
		PAVING_GRAVEL(9, "paving_gravel"),;

		private static final PathType[] META_LOOKUP = new PathType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private PathType(int meta, String name)
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
			return PathType.values().length;
		}

		public static PathType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(PathType pathtype : values())
			{
				META_LOOKUP[pathtype.getMeta()] = pathtype;
				NAMES[pathtype.getMeta()] = pathtype.getName();
			}
		}
	}
}
