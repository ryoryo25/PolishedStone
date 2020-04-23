package ryoryo.polishedstone.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.block.BlockBase;
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.config.ModConfig;

public class BlockRunningWater extends BlockBase
{
	public static final PropertyBool TOP = Props.TOP;

	public BlockRunningWater()
	{
		super(Material.VINE, "running_water");
		this.setTickRandomly(true);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if((!world.isRemote) && (world.getBlockState(pos.down()).getBlock() == Blocks.CAULDRON))
		{
			neighborChanged(state, world, pos, Blocks.CAULDRON, pos);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return Utils.ZERO_AABB;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		IBlockState topId = world.getBlockState(pos.up());
		IBlockState downId = world.getBlockState(pos.down());

		if(topId != Register.BLOCK_RUNNING_WATER && (topId != Register.BLOCK_FAUCET || !topId.getValue(BlockFaucet.RUNNING).booleanValue()))
		{
			world.setBlockToAir(pos);
			if(downId == Blocks.WATER && ModConfig.waterOriginDelete)
			{
				world.setBlockToAir(pos.down());
			}
			return;
		}
		if(downId == Blocks.CAULDRON)
		{
			world.setBlockState(pos.down(), Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, 3));
			//			world.func_147464_a(i, j, k, mod_Faucet.blockWater, 50);
			world.notifyNeighborsOfStateChange(pos, Register.BLOCK_FAUCET/*, 50*/, false);
			return;
		}
		if(downId == Blocks.AIR)
		{
			world.setBlockState(pos.down(), Register.BLOCK_RUNNING_WATER.getDefaultState());
			neighborChanged(state, world, pos.down(), Register.BLOCK_RUNNING_WATER, fromPos);
			return;
		}
		if(downId != Register.BLOCK_RUNNING_WATER && downId != Blocks.WATER && downId != Blocks.FLOWING_WATER)
		{
			world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());

			return;
		}
		if(downId == Blocks.WATER || downId == Blocks.FLOWING_WATER)
		{
			world.setBlockState(pos.down(), Blocks.FLOWING_WATER.getDefaultState());
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(TOP, Boolean.valueOf(isTop(world, pos)));
	}

	private static boolean isTop(IBlockAccess world, BlockPos pos)
	{
		BlockPos posu = pos.up();

		return world.getBlockState(posu).getBlock() == Register.BLOCK_FAUCET ? true : false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TOP, });
	}
}