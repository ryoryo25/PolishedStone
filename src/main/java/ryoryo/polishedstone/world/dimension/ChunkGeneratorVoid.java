package ryoryo.polishedstone.world.dimension;

import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkGeneratorVoid implements IChunkGenerator {
	private World world;

	public ChunkGeneratorVoid(World world) {
		this.world = world;
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();
		Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
		chunk.generateSkylightMap();

		byte[] abyte = chunk.getBiomeArray();

		for(int i1 = 0; i1 < abyte.length; ++i1) {
			abyte[i1] = (byte) Biome.getIdForBiome(Biomes.PLAINS);
		}

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		if(this.world.provider.getDimensionType() == DimensionRegistry.PERFECT_VOID) {
			return null;
		}
		else {
			Biome biome = this.world.getBiome(pos);
			return biome.getSpawnableList(creatureType);
		}
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
	}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}

}