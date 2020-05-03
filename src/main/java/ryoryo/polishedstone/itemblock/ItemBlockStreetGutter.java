package ryoryo.polishedstone.itemblock;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import ryoryo.polishedlib.itemblock.ItemBlockMeta;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumSimpleFacing;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.block.BlockStreetGutter;
import ryoryo.polishedstone.block.BlockStreetGutter.EnumType;

public class ItemBlockStreetGutter extends ItemBlockMeta {
	public static final String[] NAMES = new String[] {
			EnumType.NORMAL_NS.toString(),
			EnumType.POLISHED_NS.toString(),
			EnumType.MESH_NS.toString(),
			EnumType.CENTRAL_NORMAL.toString(),
			EnumType.CENTRAL_POLISHED.toString(),
	};

	public ItemBlockStreetGutter() {
		super(Register.BLOCK_STREET_GUTTER, NAMES);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		RayTraceResult raytraceresult = this.rayTrace(world, player, true);

		if(raytraceresult == null) {
			return EnumActionResult.PASS;
		}
		else {
			if(raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
				BlockPos posr = raytraceresult.getBlockPos();

				if(!world.isBlockModifiable(player, posr) || !player.canPlayerEdit(posr.offset(raytraceresult.sideHit), raytraceresult.sideHit, stack)) {
					return EnumActionResult.FAIL;
				}

				BlockPos posu = posr.up();

				if(world.isAirBlock(posu)) {
					int meta = stack.getMetadata();
					world.setBlockState(posu, getState(player.getHorizontalFacing(), meta), 11);
					if(!Utils.isCreative(player)) {
						stack.shrink(1);
					}

					SoundType soundtype = world.getBlockState(posu).getBlock().getSoundType(world.getBlockState(posu), world, posu, player);
					world.playSound(player, posu, meta == 2 ? SoundEvents.BLOCK_METAL_PLACE : SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					return EnumActionResult.SUCCESS;
				}

			}

			return EnumActionResult.FAIL;
		}
	}

	private static IBlockState getState(EnumFacing facing, int meta) {
		IBlockState state = Register.BLOCK_STREET_GUTTER.getDefaultState();
		EnumSimpleFacing facings = EnumSimpleFacing.convertToNormalFacing(facing);

		switch(facings) {
			case NORTH:
			default:
				switch(meta) {
					case 0:
					default:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.NORMAL_NS);
					case 1:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.POLISHED_NS);
					case 2:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.MESH_NS);
					case 3:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.CENTRAL_NORMAL);
					case 4:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.CENTRAL_POLISHED);
				}
			case WEST:
				switch(meta) {
					case 0:
					default:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.NORMAL_WE);
					case 1:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.POLISHED_WE);
					case 2:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.MESH_WE);
					case 3:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.CENTRAL_NORMAL);
					case 4:
						return state.withProperty(BlockStreetGutter.TYPE, EnumType.CENTRAL_POLISHED);
				}
		}
		// .withProperty(BlockLateralGroove.TYPE, meta == 0 ? EnumType.NORMAL :
		// (meta == 1 ? EnumType.WHITE : (meta == 2 ? EnumType.MESH :
		// EnumType.NORMAL)))
	}
}
