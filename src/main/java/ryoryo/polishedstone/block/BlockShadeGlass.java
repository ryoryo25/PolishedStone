package ryoryo.polishedstone.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.ToolType;
import ryoryo.polishedstone.Register;

public class BlockShadeGlass extends BlockModBase {
	private boolean unbreakable;

	public BlockShadeGlass(String name, boolean unbreakable) {
		super(Material.GLASS, name, SoundType.GLASS);
		if(unbreakable) {
			this.setBlockUnbreakable();
			this.setResistance(6000000.0F);
		} else
			this.setHardness(0.3F);
		this.setHarvestLevel(ToolType.PICKAXE.getToolClass(), ToolMaterial.WOOD.getHarvestLevel());
		this.setLightOpacity(255);
		this.unbreakable = unbreakable;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
		Block block = iblockstate.getBlock();

		if(this == Register.BLOCK_SHADE_GLASS || this == Register.BLOCK_HARDENED_SHADE_GLASS) {
			if(blockState != iblockstate) {
				return true;
			}

			if(block == this) {
				return false;
			}
		}

		return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if(this.unbreakable) {
			if(entity instanceof EntityPlayer)
				return true;
			else
				return false;
		} else
			return true;
	}

	@Override
	public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
		List<ItemStack> items = Utils.getHeldItemStacks(player);

		for(ItemStack stack : items) {
			if(!stack.isEmpty() && (Block.getBlockFromItem(stack.getItem()) == Register.BLOCK_HARDENED_SHADE_GLASS || Block.getBlockFromItem(stack.getItem()) == Register.BLOCK_HARDENED_SHADE_GLASS_DOOR)) {
				return 0.083333336F;
			}
		}

		return super.getBlockHardness(state, world, pos);
	}
}
