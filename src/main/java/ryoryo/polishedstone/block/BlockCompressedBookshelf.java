package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;

public class BlockCompressedBookshelf extends BlockModBase {
	public static final PropertyInteger MULTIPLE = PropertyInteger.create("multiple", 1, 2);

	public BlockCompressedBookshelf() {
		super(Material.WOOD, "compressed_bookshelf", SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MULTIPLE, 1));
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) {
		int multi = state.getValue(MULTIPLE);
		return 1.5F * 8.0F * multi;
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos) {
		int multi = world.getBlockState(pos).getValue(MULTIPLE);
		return 8.0F * multi;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(MULTIPLE).intValue() - 1;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(MULTIPLE, meta + 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(MULTIPLE) - 1;
	}

	// 初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { MULTIPLE });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		RegistryUtils.registerSubBlocks(this, 2, tab, list);
	}
}
