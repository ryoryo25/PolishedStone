package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;

public class BlockPavingStone extends BlockModBase
{
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType> create("type", EnumType.class);

	public BlockPavingStone()
	{
		super(Material.ROCK, "paving_stone", SoundType.STONE);
		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.NORMAL));
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type)
	{
		return false;
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return state.getValue(TYPE) == EnumType.POWERED ? true : false;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.DOWN ? 1 : 0;
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TYPE, });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		Utils.registerSubBlocks(this, EnumType.getLength(), tab, list);
	}

	public static enum EnumType implements IStringSerializable
	{
		NORMAL(0, "normal"),
		VERTICAL(1, "vertical"),
		CROSSED(2, "crossed"),
		POWERED(3, "powered");

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private EnumType(int meta, String name)
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
