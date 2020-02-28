package ryoryo.polishedstone.world.dimension;

import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderNightVoid extends WorldProvider
{
	@Override
	public void init()
	{
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkGeneratorVoid(this.world);
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks)
	{
		return 0.5F;
	}

	@Override
	public long getWorldTime()
    {
        return 18000L;
    }

	@Override
	public boolean isDaytime()
	{
		return false;
	}

	@Override
	public DimensionType getDimensionType()
	{
		return DimensionRegistry.NIGHT_VOID;
	}
}