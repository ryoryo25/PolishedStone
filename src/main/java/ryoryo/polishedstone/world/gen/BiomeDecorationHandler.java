package ryoryo.polishedstone.world.gen;

import java.util.Random;

import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockPotato;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ryoryo.polishedstone.config.ModConfig;

public class BiomeDecorationHandler
{
	@SubscribeEvent
	public void onWorldDecoration(DecorateBiomeEvent.Decorate event)
	{
		Result result = event.getResult();
		ChunkPos chunkPos = event.getChunkPos();
		BlockPos pos = event.getPlacementPos() != null ? event.getPlacementPos() : chunkPos.getBlock(0, 0, 0);
		World world = event.getWorld();
		Random random = event.getRand();

		if((result == Result.ALLOW || result == Result.DEFAULT) && event.getType() == EventType.FLOWERS)
		{
			tryGenerateCrops(ModConfig.wildPotatoGen, (BlockCrops) Blocks.POTATOES, BlockPotato.AGE, ModConfig.wildPotatoChance, ModConfig.wildPotatoPatch, world, pos, random);
			tryGenerateCrops(ModConfig.wildCarrotGen, (BlockCrops) Blocks.CARROTS, BlockCarrot.AGE, ModConfig.wildCarrotChance, ModConfig.wildCarrotPatch, world, pos, random);
			tryGenerateCrops(ModConfig.wildBeetrootGen, (BlockCrops) Blocks.BEETROOTS, BlockBeetroot.BEETROOT_AGE, ModConfig.wildBeetrootChance, ModConfig.wildBeetrootPatch, world, pos, random);
		}
	}

	public void tryGenerateCrops(boolean enabled, BlockCrops crops, PropertyInteger ageProperty, float chance, int amount, World world, BlockPos pos, Random random)
	{
		if(random.nextFloat() < chance)
		{
			for(int i = 0; i < amount; i++)
			{
				int genX = pos.getX() + random.nextInt(16) + 8;
				int genY = 0;
				int genZ = pos.getZ() + random.nextInt(16) + 8;
				BlockPos genPos = new BlockPos(genX, genY, genZ);
				genPos = world.getTopSolidOrLiquidBlock(genPos);
				BlockPos genPosd = genPos.down();

				if((!world.provider.isNether() || genPos.getY() < world.getHeight() - 1)
				&& world.isAirBlock(genPos)
				&& world.getBlockState(genPosd).getBlock() == Blocks.GRASS
				/*&& ((BlockCrops) Blocks.POTATOES).canBlockStay(world, pos, Blocks.POTATOES.getDefaultState())*/)
				{
					world.setBlockState(genPos, crops.getDefaultState().withProperty(ageProperty, crops.getMaxAge()), 2);
					world.setBlockState(genPosd, Blocks.FARMLAND.getDefaultState().withProperty(BlockFarmland.MOISTURE, 7), 2);
				}
			}
		}
	}
}