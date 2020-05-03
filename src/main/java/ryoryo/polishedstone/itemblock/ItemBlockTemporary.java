package ryoryo.polishedstone.itemblock;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;

public class ItemBlockTemporary extends ItemBlock {

	public ItemBlockTemporary() {
		super(Register.BLOCK_TEMPORARY);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult result = this.rayTrace(world, player, true);

		if(result == null)
			return EnumActionResult.PASS;

		else {
			if(result.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos rayPos = result.getBlockPos();

				if(!world.isBlockModifiable(player, rayPos) || !player.canPlayerEdit(rayPos.offset(result.sideHit), result.sideHit, stack)) {
					return EnumActionResult.FAIL;
				}

				BlockPos posu = rayPos.up();
				IBlockState state = world.getBlockState(rayPos);

				if(state.getMaterial() == Material.WATER || state.getMaterial() == Material.LAVA || state.getBlock() instanceof BlockLiquid || state.getBlock() instanceof BlockFluidBase) {
					if(world.isAirBlock(posu)) {
						SoundType soundtype = world.getBlockState(posu).getBlock().getSoundType(world.getBlockState(posu), world, posu, player);
						world.playSound(player, posu, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

						if(!world.isRemote) {
							world.setBlockState(posu, Register.BLOCK_TEMPORARY.getDefaultState(), 11);
							if(!Utils.isCreative(player))
								stack.shrink(1);
						}

						return EnumActionResult.SUCCESS;
					}
				}
				else {
					if(!world.getBlockState(pos).getBlock().isReplaceable(world, pos))
						pos = pos.offset(facing);

					if(stack.getCount() != 0 && player.canPlayerEdit(pos, facing, stack) && world.mayPlace(this.block, pos, false, facing, (Entity) null)) {
						if(placeBlockAt(stack, player, world, pos, facing, hitX, hitY, hitZ, Register.BLOCK_TEMPORARY.getDefaultState())) {
							SoundType soundtype = world.getBlockState(pos).getBlock().getSoundType(world.getBlockState(pos), world, pos, player);
							world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
							if(!Utils.isCreative(player))
								stack.shrink(1);

							return EnumActionResult.SUCCESS;
						}
					}
				}
			}

			return EnumActionResult.FAIL;
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if(world.isRemote) {
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}

		int x = (int) Math.floor(player.posX);
		int y = (int) Math.floor(player.posY + player.getEyeHeight());
		int z = (int) Math.floor(player.posZ);

		Vec3d look = player.getLookVec();

		EnumFacing side = EnumFacing.getFacingFromVector((float) look.x, (float) look.y, (float) look.z);
		switch(side) {
			case DOWN:
				y = (int) (Math.floor(player.getEntityBoundingBox().minY) - 1.0D);
				break;
			case UP:
				y = (int) (Math.ceil(player.getEntityBoundingBox().maxY) + 1.0D);
				break;
			case NORTH:
				z = (int) (Math.floor(player.getEntityBoundingBox().minZ) - 1.0D);
				break;
			case SOUTH:
				z = (int) (Math.floor(player.getEntityBoundingBox().maxZ) + 1.0D);
				break;
			case WEST:
				x = (int) (Math.floor(player.getEntityBoundingBox().minX) - 1.0D);
				break;
			case EAST:
				x = (int) (Math.floor(player.getEntityBoundingBox().maxX) + 1.0D);
		}

		BlockPos posp = new BlockPos(x, y, z);

		if(this.block.canPlaceBlockAt(world, posp)) {
			super.onItemUse(player, world, posp, hand, side, 0.0F, 0.0F, 0.0F);
		}

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
