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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;

public class BlockIronPlate extends BlockModBase {
	protected static final AxisAlignedBB PLATE_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
	public static final PropertyEnum<PlateType> TYPE = PropertyEnum.<PlateType> create("type", PlateType.class);

	public BlockIronPlate() {
		super(Material.CLAY, "iron_plate", SoundType.METAL);
		this.setHardness(0.2F);
		this.setResistance(15.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, PlateType.NORMAL));
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return PLATE_AABB;
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, ((PlateType) state.getValue(TYPE)).getMeta());
	}

	// ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, PlateType.byMeta(meta));
	}

	// IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(TYPE).getMeta();
	}

	// 初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		RegistryUtils.registerSubBlocks(this, PlateType.getLength(), tab, list);
	}

	public static enum PlateType implements IStringSerializable
	{
		NORMAL(0, "normal"),
		RUSTY(1, "rusty"),;

		private static final PlateType[] META_LOOKUP = new PlateType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private PlateType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return this.meta;
		}

		public static int getLength() {
			return PlateType.values().length;
		}

		public static PlateType byMeta(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static {
			for(PlateType platetype : values()) {
				META_LOOKUP[platetype.getMeta()] = platetype;
				NAMES[platetype.getMeta()] = platetype.getName();
			}
		}
	}
}