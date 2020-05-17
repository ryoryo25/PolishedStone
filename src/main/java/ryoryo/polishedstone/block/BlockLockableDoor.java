package ryoryo.polishedstone.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.block.BlockBaseDoor;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.ToolType;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public class BlockLockableDoor extends BlockBaseDoor {
	// keyを持ってないと開けられない
	public BlockLockableDoor() {
		super(Material.IRON, "lockable", PSV2Core.TAB_MOD, SoundType.METAL, false, true);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setHarvestLevel(ToolType.PICKAXE.getToolClass(), ToolMaterial.WOOD.getHarvestLevel());
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if(entity instanceof EntityPlayer)
			return true;
		else
			return false;
	}

	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		List<ItemStack> items = Utils.getHeldItemStacks(player);

		for(ItemStack stack : items) {
			if(!stack.isEmpty() && ((stack.getItem() == Register.ITEM_MATERIAL && stack.getItemDamage() == 17) || Block.getBlockFromItem(stack.getItem()) == this)) {
				return 0.083333336F;
			}
		}

		return super.getBlockHardness(state, world, pos);
	}

	// PropertyBoolでLOCKとかもいいかも
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		List<ItemStack> items = Utils.getHeldItemStacks(player);

		for(ItemStack stack : items) {
			if(!stack.isEmpty() && stack.getItem() == Register.ITEM_MATERIAL && stack.getItemDamage() == 17) {
				return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
			}

		}

		return false;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			BlockPos posd = pos.down();
			IBlockState stated = world.getBlockState(posd);

			if(stated.getBlock() != this) {
				world.setBlockToAir(pos);
			} else if(block != this) {
				stated.neighborChanged(world, posd, block, fromPos);
			}
		} else {
			boolean flag1 = false;
			BlockPos posu = pos.up();
			IBlockState stateu = world.getBlockState(posu);

			if(stateu.getBlock() != this) {
				world.setBlockToAir(pos);
				flag1 = true;
			}

			if(!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP)) {
				world.setBlockToAir(pos);
				flag1 = true;

				if(stateu.getBlock() == this) {
					world.setBlockToAir(posu);
				}
			}

			if(flag1) {
				if(!world.isRemote) {
					this.dropBlockAsItem(world, pos, state, 0);
				}
			}
		}
	}
}
