package ryoryo.polishedstone.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;

public class BlockTemporary extends BlockModBase
{
	public BlockTemporary()
	{
		super(Material.ROCK, "temporary_block", SoundType.METAL);
		this.setHardness(0.0F);
		this.setResistance(10000.0F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack held = player.getHeldItem(hand);

		if(!world.isRemote && !held.isEmpty())
		{
			Item heldItem = held.getItem();

			if(heldItem != null && heldItem instanceof ItemBlock)
			{
				ItemBlock heldBlock = (ItemBlock) heldItem;
				Block block = heldBlock.getBlock();

				if(block != null && block != this)
				{
					int meta = heldBlock.getMetadata(held);
					IBlockState toPlace = block.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, player, hand);

					world.destroyBlock(pos, false);

					if(block.canPlaceBlockAt(world, pos))
					{
						world.setBlockState(pos, toPlace);

						if(!Utils.isCreative(player))
						{
							Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_TEMPORARY));
							held.shrink(1);
						}

						return true;
					}
					else
					{
						world.setBlockState(pos, Register.BLOCK_TEMPORARY.getDefaultState());
						Utils.sendChat(player, "This block can't be placed here!");
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.AIR;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(!Utils.isCreative(player))
			Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_TEMPORARY));
	}
}
