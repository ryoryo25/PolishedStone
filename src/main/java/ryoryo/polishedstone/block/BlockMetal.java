package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;

public class BlockMetal extends BlockModBase
{
	public static final PropertyEnum<MaterialType> VARIANT = PropertyEnum.<MaterialType> create("variant", MaterialType.class);

	public BlockMetal()
	{
		super(Material.IRON, "metal", SoundType.METAL);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, MaterialType.ENDER_STEEL));
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((MaterialType)state.getValue(VARIANT)).getMeta());
	}

	//ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, MaterialType.byMeta(meta));
	}

	//IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(VARIANT).getMeta();
	}

	//初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ VARIANT });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		RegistryUtils.registerSubBlocks(this, MaterialType.getLength(), tab, list);
	}

	public static enum MaterialType implements IStringSerializable
	{
		ENDER_STEEL(0, "ender_steel"),
		BLUESTONE(1, "bluestone"),
		BLUESTONE_DUST(2, "bluestone_dust"),
		CRYSTAL(3, "crystal"),
		BLAZE(4, "blaze"),
		WITHER_SKELETON(5, "wither_skeleton"),
		REDSTONE(6, "redstone"),
		CHARCOAL(7, "charcoal"),
		DUMMY_SOLAR(8, "dummy_solar"),
		OLD_IRON(9, "old_iron"),
		OLD_GOLD(10, "old_gold"),
		OLD_DIAMOND(11, "old_diamond"),;

		private static final MaterialType[] META_LOOKUP = new MaterialType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private MaterialType(int meta, String name)
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
			return MaterialType.values().length;
		}

		public static MaterialType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(MaterialType materialtype : values())
			{
				META_LOOKUP[materialtype.getMeta()] = materialtype;
				NAMES[materialtype.getMeta()] = materialtype.getName();
			}
		}
	}
}
