package ryoryo.polishedstone.block;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.References;

public class BlockBlackQuartz extends BlockQuartz implements IModId
{
	public BlockBlackQuartz()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("black_quartz");
		this.setHardness(0.8F);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.DEFAULT));
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumType) state.getValue(VARIANT)).getMetadata();
	}

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
		if(tab == this.getCreativeTabToDisplayOn())
		{
			list.add(new ItemStack(this, 1, BlockQuartz.EnumType.DEFAULT.getMetadata()));
			list.add(new ItemStack(this, 1, BlockQuartz.EnumType.CHISELED.getMetadata()));
			list.add(new ItemStack(this, 1, BlockQuartz.EnumType.LINES_Y.getMetadata()));
		}
	}
}
