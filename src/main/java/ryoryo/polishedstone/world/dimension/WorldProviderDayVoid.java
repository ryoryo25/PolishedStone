package ryoryo.polishedstone.world.dimension;

import net.minecraft.init.Biomes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderDayVoid extends WorldProvider {
	@Override
	public void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderSingle(Biomes.PLAINS);
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorVoid(this.world);
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.0F;
	}

	@Override
	public long getWorldTime() {
		return 6000L;
	}

	@Override
	public boolean isDaytime() {
		return true;
	}

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.DAY_VOID;
	}
}