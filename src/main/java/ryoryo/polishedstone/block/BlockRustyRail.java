package ryoryo.polishedstone.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.util.References;

public class BlockRustyRail extends BlockRail implements IModId
{
	private int rusty = 0;

	public BlockRustyRail(int rusty)
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("rusty_rail" + "_" + rusty);
		this.setHardness(0.7F);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
		this.rusty = rusty;
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Nullable
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
	{
		return new ItemStack(this);
	}

	@Nullable
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Register.ITEM_MATERIAL;
	}

	@Override
	public int quantityDropped(Random random)
	{
		int i = random.nextInt(2);
		return this == Register.BLOCK_RUSTY_RAIL1 ? 4 + i : (this == Register.BLOCK_RUSTY_RAIL2 ? 2 + i : (this == Register.BLOCK_RUSTY_RAIL3 ? i : 0));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 14;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return true;
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
	{
		return super.getRailMaxSpeed(world, cart, pos) - rusty / 10.0F;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockRailBase.EnumRailDirection) state.getValue(SHAPE)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ SHAPE });
	}
}
