package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;
import ryoryo.polishedstone.PSV2Core;

public class BlockNewFlower extends BlockBush
{
	public static final PropertyEnum<NewFlowerType> TYPE = PropertyEnum.<NewFlowerType>create("type", NewFlowerType.class);

	public BlockNewFlower()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("new_flower");
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, NewFlowerType.LAVENDER_SAGE));
	}

	public int damageDropped(IBlockState state)
	{
		return ((NewFlowerType)state.getValue(TYPE)).getMeta();
	}

	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, NewFlowerType.byMeta(meta));
	}

	public int getMetaFromState(IBlockState state)
	{
		return ((NewFlowerType) state.getValue(TYPE)).getMeta();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TYPE });
	}

	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return Block.EnumOffsetType.XZ;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		RegistryUtils.registerSubBlocks(this, NewFlowerType.getLength(), tab, list);
	}

	public static enum NewFlowerType implements IStringSerializable
	{
		LAVENDER_SAGE(0, "lavender_sage"),
		TULIP_BLACK(1, "tulip_black"),;

		private static final NewFlowerType[] META_LOOKUP = new NewFlowerType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private NewFlowerType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public String getName()
		{
			return this.name;
		}

		public static int getLength()
		{
			return NewFlowerType.values().length;
		}

		public static NewFlowerType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(NewFlowerType newflowertype : values())
			{
				META_LOOKUP[newflowertype.getMeta()] = newflowertype;
				NAMES[newflowertype.getMeta()] = newflowertype.getName();
			}
		}
	}
}