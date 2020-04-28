package ryoryo.polishedstone.world.gen;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.ModCompat;

public class LavaLakeGenHandler
{
	@SubscribeEvent
	public void initLakeGen(PopulateChunkEvent.Populate event)
	{
		//森林バイオームとかに溶岩湖が出来ないように。
		if(!ModCompat.COMPAT_WILDFIRE)
		{
			if(event.getType() == Populate.EventType.LAVA && event.getResult() == Result.DEFAULT)
			{
				BlockPos pos = new BlockPos(event.getChunkX() * 16 + 8, 64, event.getChunkZ() * 16 + 8);
				Biome biome = event.getWorld().getBiomeForCoordsBody(pos);
				if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE))
					event.setResult(Result.DENY);
			}
		}
		else
			PSV2Core.LOGGER.info("No More Forest Fire is loaded.");
	}

	@SubscribeEvent
	public void initFluid(DecorateBiomeEvent.Decorate event)
	{
		//森林バイオームとかに溶岩湖が出来ないように。
		if(!ModCompat.COMPAT_WILDFIRE)
		{
			if(event.getType() == Decorate.EventType.LAKE_LAVA && event.getResult() == Result.DEFAULT)
			{
				BlockPos pos = event.getPlacementPos() != null ? event.getPlacementPos() : event.getChunkPos().getBlock(0, 0, 0);
				Biome biome = event.getWorld().getBiomeForCoordsBody(pos);
				if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE))
					event.setResult(Result.DENY);
			}
		}
		else
			PSV2Core.LOGGER.info("No More Forest Fire is loaded.");
	}
}