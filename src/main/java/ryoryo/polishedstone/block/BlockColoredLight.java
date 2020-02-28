package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.EnumColor;
import ryoryo.polishedlib.util.LibTool;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class BlockColoredLight extends Block
{
	public static final PropertyEnum<EnumColor> COLOR = Utils.COLOR;

	public BlockColoredLight()
	{
		super(Material.GLASS);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("colored_light");
		this.setResistance(10000F);
		this.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		this.setSoundType(SoundType.GLASS);
		this.setLightLevel(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumColor.WHITE));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(COLOR).getWoolNumber();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(COLOR, EnumColor.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(COLOR).getWoolNumber();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ COLOR });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		Utils.registerSubBlocks(this, EnumColor.getLength(), tab, list);
	}
}
