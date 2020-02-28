package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public class BlockTemporary extends Block
{
	public BlockTemporary()
	{
		super(Material.ROCK);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("temporary_block");
		this.setHardness(0.0F);
		this.setResistance(10000.0F);
		this.setSoundType(SoundType.METAL);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack heldItem = player.getHeldItem(hand);
		if(heldItem != ItemStack.EMPTY)
		{
			Item held = heldItem.getItem();

			if(held instanceof ItemBlock)
			{
				ItemBlock heldBlock = (ItemBlock) held;
				if(heldBlock.getBlock() != this)
				{
					int meta = heldBlock.getMetadata(heldItem.getMetadata());
					IBlockState place = heldBlock.getBlock().getStateForPlacement(world, pos, player.getHorizontalFacing(), hitX, hitY, hitZ, meta, player, player.getActiveHand());

					world.destroyBlock(pos, false);
					if(place.getBlock().canPlaceBlockAt(world, pos))
						world.setBlockState(pos, place);
					if(!Utils.isCreative(player))
					{
						Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_TEMPORARY));
						heldItem.shrink(1);
					}

					return true;
				}

				return false;
			}

			return false;
		}

		return false;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(!Utils.isCreative(player))
			Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_TEMPORARY));
	}
}
