package ryoryo.polishedstone.block;

import net.minecraft.block.BlockGravel;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class BlockNewGravel extends BlockGravel implements IModId
{
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType> create("type", EnumType.class);

	public BlockNewGravel()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("new_gravel");
		this.setHardness(0.6F);
		this.setSoundType(SoundType.GROUND);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.OLD));
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type)
	{
		return state.getValue(TYPE) == EnumType.PAVING ? false : super.canCreatureSpawn(state, world, pos, type);
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
		OLD(0, "old"),
		PAVING(1, "paving"),;

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