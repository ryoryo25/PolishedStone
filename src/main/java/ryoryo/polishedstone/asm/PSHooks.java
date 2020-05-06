package ryoryo.polishedstone.asm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class PSHooks {
//	public static boolean isCactus(IBlockState plant) {
//		return plant.getBlock() == Blocks.CACTUS;
//	}
//
//	public static boolean canSustainPlant(Block block, IBlockState state) {
//		return block == Blocks.CACTUS || block == Blocks.SAND || (block == Blocks.DIRT && block.getMetaFromState(state) != 2);
//	}

	public static boolean canSustainPlant(Block block, IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		IBlockState plant = plantable.getPlant(world, pos.offset(direction));
		EnumPlantType plantType = plantable.getPlantType(world, pos.offset(direction));

		if(plant.getBlock() == Blocks.CACTUS)
			return block == Blocks.CACTUS || block == Blocks.SAND || (block == Blocks.DIRT && block.getMetaFromState(state) != 2);

		if(plant.getBlock() == Blocks.REEDS && block == Blocks.REEDS)
			return true;

		if(plantable instanceof BlockBush && (state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND))
			return true;

		switch(plantType) {
			case Desert:
				return block == Blocks.SAND || block == Blocks.HARDENED_CLAY || block == Blocks.STAINED_HARDENED_CLAY;
			case Nether:
				return block == Blocks.SOUL_SAND;
			case Crop:
				return block == Blocks.FARMLAND;
			case Cave:
				return state.isSideSolid(world, pos, EnumFacing.UP);
			case Plains:
				return block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND;
			case Water:
				return state.getMaterial() == Material.WATER && state.getValue(BlockLiquid.LEVEL) == 0;
			case Beach:
				boolean isBeach = block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.SAND;
				boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
						world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
						world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
						world.getBlockState(pos.south()).getMaterial() == Material.WATER);
				return isBeach && hasWater;
		}

		return false;
	}
}