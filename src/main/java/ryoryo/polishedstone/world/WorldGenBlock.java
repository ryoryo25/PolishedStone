package ryoryo.polishedstone.world;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.util.ModCompat;

public class WorldGenBlock implements IWorldGenerator
{
	private WorldGenerator genEmerald;
	private WorldGenerator genClayUnderground;
	private WorldGenerator genClayUndersea;
	private WorldGenerator genSandUnderground;
	private WorldGenerator genSandUndersea;
	private WorldGenerator genDirtUndersea;

	public WorldGenBlock()
	{
		this.genEmerald = new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(), ModConfig.emeraldGenCluster);
		this.genClayUnderground = new WorldGenMinable(Blocks.CLAY.getDefaultState(), ModConfig.clayGenUndergroundCluster);
		this.genClayUndersea = new WorldGenMinable(Blocks.CLAY.getDefaultState(), ModConfig.clayGenUnderseaCluster, BlockMatcher.forBlock(Blocks.GRAVEL));
		this.genSandUnderground = new WorldGenMinable(Blocks.SAND.getDefaultState(), ModConfig.sandGenUndergroundCluster);
		this.genSandUndersea = new WorldGenMinable(Blocks.SAND.getDefaultState(), ModConfig.sandGenUnderseaCluster, BlockMatcher.forBlock(Blocks.GRAVEL));
		this.genDirtUndersea = new WorldGenMinable(Blocks.DIRT.getDefaultState(), ModConfig.dirtGenUnderseaCluster, BlockMatcher.forBlock(Blocks.GRAVEL));
	}

	private void undergroundGen(WorldGenerator generator, World world, Random random, int x, int z, int chancesToSpawn, int minHeight, int maxHeight)
	{
		if(minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		int heightDiff = maxHeight - minHeight;
		BlockPos pos;
		for(int i = 0; i < chancesToSpawn; i++)
		{
			int genX = x + random.nextInt(16);
			int genY = minHeight + random.nextInt(heightDiff);
			int genZ = z + random.nextInt(16);
			pos = new BlockPos(genX, genY, genZ);
			generator.generate(world, random, pos);
		}
	}

	private void underseaGen(WorldGenerator generator, World world, Random random, int x, int z, int chancesToSpawn, int minHeight, int maxHeight)
	{
		if(minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		int heightDiff = maxHeight - minHeight;
		BlockPos pos;
		Biome biome;
		for(int i = 0; i < chancesToSpawn; i++)
		{
			int genX = x + random.nextInt(16);
			int genY = minHeight + random.nextInt(heightDiff);
			int genZ = z + random.nextInt(16);
			pos = new BlockPos(genX, genY, genZ);
			biome = world.getBiome(pos);
			if(biome == Biomes.OCEAN || biome == Biomes.DEEP_OCEAN || biome == Biomes.FROZEN_OCEAN)
			{
				generator.generate(world, random, pos);
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX = chunkX << 4;
		chunkZ = chunkZ << 4;
		if(world.provider instanceof WorldProviderSurface/* world.provider.getDimension() == 0*/)
		{
//			generateClay(world, random, chunkX << 4, chunkZ << 4);
//			generateEmerald(world, random, chunkX << 4, chunkZ << 4);
			if(ModConfig.emeraldGen)
				undergroundGen(genEmerald, world, random, chunkX, chunkZ, ModConfig.emeraldGenChance, 0, 18);
			if(!ModCompat.COMPAT_QUARK && ModConfig.clayGenUnderground)
				undergroundGen(genClayUnderground, world, random, chunkX, chunkZ, ModConfig.clayGenUndergroundChance, 0, 60);
			if(ModConfig.sandGenUnderground)
				undergroundGen(genSandUnderground, world, random, chunkX, chunkZ, ModConfig.sandGenUndergourndChance, 0, 60);
			if(ModConfig.clayGenUndersea)
				underseaGen(genClayUndersea, world, random, chunkX, chunkZ, ModConfig.clayGenUnderseaChance, 20, 128);
			if(ModConfig.sandGenUndersea)
				underseaGen(genSandUndersea, world, random, chunkX, chunkZ, ModConfig.sandGenUnderseaChance, 20, 128);
			if(ModConfig.dirtGenUndersea)
				underseaGen(genDirtUndersea, world, random, chunkX, chunkZ, ModConfig.dirtGenUnderseaChance, 20, 128);
		}
	}
}
