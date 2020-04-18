package ryoryo.polishedstone.block;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.LibTool;
import ryoryo.polishedlib.util.Utils;

public class BlockDecoration extends BlockModBase
{
	public static final PropertyEnum<BlockType> TYPE = PropertyEnum.<BlockType> create("type", BlockType.class);

	public BlockDecoration()
	{
		super(Material.ROCK, "decoration_block");
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockType.COLORED_BRICK));
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
	{
		BlockType type = state.getValue(TYPE);

		switch(type)
		{
		default:
			return 2.0F;
		case ANDESITE_BRICK:
		case ANDESITE_BRICK_CARVED:
		case DIORITE_BRICK:
		case DIORITE_BRICK_CARVED:
		case GRANITE_BRICK:
		case GRANITE_BRICK_CARVED:
			return 1.5F;
		}
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		IBlockState state = world.getBlockState(pos);
		BlockType type = state.getValue(TYPE);

		switch(type)
		{
		default:
			return 10.0F;
		}

	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		BlockType type = state.getValue(TYPE);

		switch(type)
		{
		default:
			return LibTool.TOOL_CLASS_PICKAXE;
		}
	}

	@Override
	public int getHarvestLevel(IBlockState state)
	{
		BlockType type = state.getValue(TYPE);

		switch(type)
		{
		default:
			return LibTool.LEVEL_WOOD;
		}
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
	{
		BlockType type = state.getValue(TYPE);

		switch(type)
		{
		default:
			return SoundType.STONE;
		}
	}

	@Override
	public int getLightValue(IBlockState state)
	{
		BlockType type = state.getValue(TYPE);
		float base = 15.0F;

		switch(type)
		{
		default:
			return (int) (base * 0.0F);
		}
	}

	@Override
	public Material getMaterial(IBlockState state)
	{
		BlockType type = state.getValue(TYPE);

		switch(type)
		{
		default:
			return Material.ROCK;
		}
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type)
	{
		BlockType block = state.getValue(TYPE);

		switch(block)
		{
		default:
			return super.canCreatureSpawn(state, world, pos, type);
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this, 1, ((BlockType) state.getValue(TYPE)).getMeta());
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((BlockType) state.getValue(TYPE)).getMeta();
	}

	//ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, BlockType.byMeta(meta));
	}

	//IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(TYPE).getMeta();
	}

	//初期BlockStateの生成。
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
		Utils.registerSubBlocks(this, BlockType.getLength(), tab, list);
	}

	public static enum BlockType implements IStringSerializable
	{
		COLORED_BRICK(0, "colored_brick"),
		GRANITE_BRICK(1, "granite_brick"),
		GRANITE_BRICK_CARVED(2, "granite_brick_carved"),
		DIORITE_BRICK(3, "diorite_brick"),
		DIORITE_BRICK_CARVED(4, "diorite_brick_carved"),
		ANDESITE_BRICK(5, "andesite_brick"),
		ANDESITE_BRICK_CARVED(6, "andesite_brick_carved"),
		BASALT(7, "basalt"),
		BASALT_SMOOTH(8, "basalt_smooth"),
		BASALT_BRICK(9, "basalt_brick"),
		BASALT_BRICK_CARVED(10, "basalt_brick_carved"),;

		private static final BlockType[] META_LOOKUP = new BlockType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private BlockType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
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

		public static int getLength()
		{
			return BlockType.values().length;
		}

		public static BlockType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(BlockType blocktype : values())
			{
				META_LOOKUP[blocktype.getMeta()] = blocktype;
				NAMES[blocktype.getMeta()] = blocktype.getName();
			}
		}
	}
}
