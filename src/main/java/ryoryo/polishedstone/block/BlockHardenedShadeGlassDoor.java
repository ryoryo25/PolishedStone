package ryoryo.polishedstone.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.block.BlockBaseDoor;
import ryoryo.polishedlib.util.LibTool;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public class BlockHardenedShadeGlassDoor extends BlockBaseDoor {
	public BlockHardenedShadeGlassDoor() {
		super(Material.GLASS, "hardened_shade_glass", PSV2Core.TAB_MOD, SoundType.GLASS, false, true);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		this.setLightOpacity(255);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public boolean canSpawnInBlock() {
		return false;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type) {
		return false;
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
			if(!stack.isEmpty() && (Block.getBlockFromItem(stack.getItem()) == Register.BLOCK_HARDENED_SHADE_GLASS || Block.getBlockFromItem(stack.getItem()) == this)) {
				return 0.083333336F;
			}
		}

		return super.getBlockHardness(state, world, pos);
	}
}