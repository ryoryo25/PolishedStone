package ryoryo.polishedstone.itemblock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.block.BlockVendingMachine;
import ryoryo.polishedstone.block.BlockVendingMachine.EnumHalf;

public class ItemBlockVendingMachine extends ItemBlock
{
	public ItemBlockVendingMachine(Block block)
	{
		super(block);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.getHeldItem(hand);
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if(!block.isReplaceable(world, pos))
		{
			pos = pos.offset(facing);
		}

		if(player.canPlayerEdit(pos, facing, stack) && this.block.canPlaceBlockAt(world, pos))
		{
			EnumFacing enumfacing = EnumFacing.fromAngle((double) player.rotationYaw);
			placeMachine(world, pos, enumfacing, this.block, stack.getItemDamage());
			SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
			world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
			if(!Utils.isCreative(player))
				stack.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}

	}

	private static void placeMachine(World world, BlockPos pos, EnumFacing facing, Block machine, int meta)
	{
		BlockPos blockpos2 = pos.up();
		IBlockState iblockstate = machine.getDefaultState().withProperty(BlockVendingMachine.FACING, facing);
		world.setBlockState(pos, iblockstate.withProperty(BlockVendingMachine.HALF, EnumHalf.LOWER), 2);
		world.setBlockState(blockpos2, iblockstate.withProperty(BlockVendingMachine.HALF, EnumHalf.UPPER), 2);
		world.notifyNeighborsOfStateChange(pos, machine, false);
		world.notifyNeighborsOfStateChange(blockpos2, machine, false);
	}
}
