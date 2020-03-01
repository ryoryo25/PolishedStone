package ryoryo.polishedstone.config;

import java.io.File;
import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ryoryo.polishedlib.util.NumericalConstant;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.client.particle.ParticleRegistry;
import ryoryo.polishedstone.util.References;

//@Config(modid = LibMisc.MOD_ID)
public class ModConfig
{
	private static Configuration config;

	public ModConfig(File configFile)
	{
		config = new Configuration(configFile);
		loadConfigs();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.getModID().equals(References.MOD_ID))
			loadConfigs();
	}

	private void loadConfigs()
	{
		try
		{
			generalConfigs(EnumConfigCategory.GENERAL.getName());
			worldGenConfigs(EnumConfigCategory.WORLD_GEN.getName());
			mobDropConfigs(EnumConfigCategory.MOB_DROP.getName());
			clientConfigs(EnumConfigCategory.CLIENT.getName());
			puwConfigs(EnumConfigCategory.PUW.getName());
		}
		catch(Exception e)
		{
			PSV2Core.logger.addError("Error loading config.");
		}
		finally
		{
			if(config.hasChanged())
				config.save();
		}
	}

	public Configuration getConfig()
	{
		return config;
	}

	//General--------------------------------------------------------------------------------------
	public static int glowstoneGen;

	public static float creativeFlySpeedMultiply;
	public static boolean extendReach;
	public static double extraReachDistance;

	public static int slimeRemovalTorchRange;

	public static float harnessCheckDistance;

	public static float regenTimes;

	public static boolean notUseLight;

	public static boolean isEraseBlockCount;
	public static boolean eraseBedrock;

	public static boolean waterOriginDelete;

	public static boolean changeWoodToolsDurability;
	public static boolean changeStoneToolsDurability;
	public static boolean changeIronToolsDurability;
	public static boolean changeGoldToolsDurability;
	public static boolean changeDiamondToolsDurability;

	public static boolean plantSaplingWhenDespawn;

	public static boolean unifyOres;

	public static List<String> increasingResistance;

	public static boolean connectToSelf;
	public static boolean connectToSolidBlocks;
	public static List<String> connectableBlock;

	public static boolean eggToChicken;

	public static boolean infinityLava;
	public static boolean waterDontEvaporate;

	public static boolean sunBurnsBabyZombies;
	public static boolean sunBurnsCreepers;
	public static boolean deathExplosionDestroyTerrain;

	public static List<String> startingInventory;

	public static boolean instantItemPickUp;

	public static List<String> fenceBlock;

	public static int invincibleSwordAOERudius;

	public void generalConfigs(String general)
	{
		glowstoneGen = config.getInt("GlowstoneGen", general, 3, 0, NumericalConstant.INT_MAX, "Number of Glowstones to generate at a time.");

		creativeFlySpeedMultiply = config.getFloat("CreativeFlySpeedMultiply", general, 3.0F, 1.0F, NumericalConstant.FLOAT_MAX, "How much multiply for default value.");
		extendReach = config.getBoolean("ExtendReach", general, true, "IF true, your reach will extend.");

		slimeRemovalTorchRange = config.getInt("SlimeRemovalTorchRange", general, 64, 0, NumericalConstant.INT_MAX, "Range of Slime Removal Torch");

		harnessCheckDistance = config.getFloat("HarnessCheckDistance", general, 5.0F, 0.0F, NumericalConstant.FLOAT_MAX, "When the Safety Belt operates");

		regenTimes = config.getFloat("RegenTimes", general, 3.0F, 0.0F, NumericalConstant.FLOAT_MAX, "How much regenerate when use Heart of Regeneration");

		notUseLight = config.getBoolean("NotUseLightBlock", general, false, "Floodlight block does not put a invisible light block.");

		isEraseBlockCount = config.getBoolean("IsBlockCount", general, false, "Display the number of erase blocks at the time of explosion in the chat field.");
		eraseBedrock = config.getBoolean("EraseBedrock", general, true, "Wheather Eraser Bombs erase Bedrocks.");

		waterOriginDelete = config.getBoolean("WaterOriginDelete", general, true, "The source of a river is deleted.");

		changeWoodToolsDurability = config.getBoolean("ChangeWoodToolsDurability", general, true, "Change wood tools durability 60 to 8.");
		changeStoneToolsDurability = config.getBoolean("ChangeStoneToolsDurability", general, true, "Change stone tools durability 132 to 32.");
		changeIronToolsDurability = config.getBoolean("ChangeIronToolsDurability", general, true, "Change iron tools durability 251 to 1024.");
		changeGoldToolsDurability = config.getBoolean("ChangeGoldToolsDurability", general, true, "Change gold tools durability 33 to 32.");
		changeDiamondToolsDurability = config.getBoolean("ChangeDiamondToolsDurability", general, true, "Change diamond tools durability 1562 to 8192.");

		plantSaplingWhenDespawn = config.getBoolean("PlantSaplingWhenDespawn", general, true, "Plants saplings when they despawn.");

		unifyOres = config.getBoolean("UnifyOres", general, true, "Unify ores depends on Forge's OreDictionary when they drop");

		increasingResistance = Utils.getStringList(config, "IncreasingResistance", general, new String[]
		{}, "Increases block resistance to 10000.(cf. obsidian's default resistance:2000) Please add it following this format.(e.g. minecraft:stone)");

		connectToSelf = config.getBoolean("ConnectToSelf", general, true, "Make lattice block connect vertically to other lattice blocks");
		connectToSolidBlocks = config.getBoolean("ConnectToSolidBlocks", general, false, "Make lattice block connect vertically to any solid block");

		connectableBlock = Utils.getStringList(config, "ConnectTo", general, new String[]
		{ Blocks.ACTIVATOR_RAIL.getRegistryName().toString(), Blocks.DETECTOR_RAIL.getRegistryName().toString(), Blocks.GOLDEN_RAIL.getRegistryName().toString(), Blocks.RAIL.getRegistryName().toString(), Blocks.OAK_FENCE.getRegistryName().toString(), Blocks.DARK_OAK_FENCE.getRegistryName().toString(), Blocks.JUNGLE_FENCE.getRegistryName().toString(),
				Blocks.ACACIA_FENCE.getRegistryName().toString(), Blocks.BIRCH_FENCE.getRegistryName().toString(), Blocks.SPRUCE_FENCE.getRegistryName().toString(), Blocks.NETHER_BRICK_FENCE.getRegistryName().toString(), Blocks.TORCH.getRegistryName().toString(), Blocks.REDSTONE_TORCH.getRegistryName().toString(), Blocks.STANDING_SIGN.getRegistryName().toString(),
				Blocks.LEVER.getRegistryName().toString(), Blocks.WOODEN_PRESSURE_PLATE.getRegistryName().toString(), Blocks.STONE_PRESSURE_PLATE.getRegistryName().toString(), Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE.getRegistryName().toString(), Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE.getRegistryName().toString(), Blocks.CARPET.getRegistryName().toString(),
				Blocks.FLOWER_POT.getRegistryName().toString(), Blocks.DAYLIGHT_DETECTOR.getRegistryName().toString(), Blocks.DAYLIGHT_DETECTOR_INVERTED.getRegistryName().toString(), Blocks.SKULL.getRegistryName().toString() },
				"List of blocks that lattice will attach to vertically.");

		eggToChicken = config.getBoolean("EggToChicken", general, true, "Chickens hatch when Egg being disappeared");

		infinityLava = config.getBoolean("InfinityLava", general, false, "Like water, also makes lava possible to connect and increase.");
		waterDontEvaporate = config.getBoolean("waterDontEvaporate", general, false, "Makes water don't evaporate in nether");

		sunBurnsBabyZombies = config.getBoolean("SunBurnsBabyZombies", general, true, "Sun burns baby zombies");
		sunBurnsCreepers = config.getBoolean("SunBurnsCreepers", general, true, "Sun burns creepers");
		deathExplosionDestroyTerrain = config.getBoolean("DeathExplosionDestroyTerrain", general, false, "Creeper's death explosion destroys terrain");

		startingInventory = Utils.getStringList(config, "StartingInventory", general, new String[]
		{}, "Gives items to players when they first entered the world.");

		instantItemPickUp = config.getBoolean("InstantItemPickUp", general, true, "Picks up items instantly when items dropped.");

		fenceBlock = Utils.getStringList(config, "FenceBlock", general, new String[]
		{}, "Blocks regarded as a fence.");

		invincibleSwordAOERudius = config.getInt("InvincibleSwordAOERudius", general, 4, 0, NumericalConstant.INT_MAX, "Rudius of Invincible Sword's range attack.");
	}

	//World Gen------------------------------------------------------------------------------------
	public static boolean emeraldGen;
	public static int emeraldGenCluster;
	public static int emeraldGenChance;

	public static boolean clayGenUnderground;
	public static int clayGenUndergroundCluster;
	public static int clayGenUndergroundChance;

	public static boolean clayGenUndersea;
	public static int clayGenUnderseaCluster;
	public static int clayGenUnderseaChance;

	public static boolean sandGenUnderground;
	public static int sandGenUndergroundCluster;
	public static int sandGenUndergourndChance;

	public static boolean sandGenUndersea;
	public static int sandGenUnderseaCluster;
	public static int sandGenUnderseaChance;

	public static boolean dirtGenUndersea;
	public static int dirtGenUnderseaCluster;
	public static int dirtGenUnderseaChance;

	public void worldGenConfigs(String worldGen)
	{
		emeraldGen = config.getBoolean("EmeraldGen", worldGen, true, "Wheather generate Emerald anywhere.");
		emeraldGenCluster = config.getInt("EmeraldGenCluster", worldGen, 5, 0, NumericalConstant.INT_MAX, "How big is the cluster.");
		emeraldGenChance = config.getInt("EmeraldGenChance", worldGen, 2, 0, NumericalConstant.INT_MAX, "Chance of generate");

		clayGenUnderground = config.getBoolean("clayGenUnderground", worldGen, true, "Wheather generate Clay underground.");
		clayGenUndergroundCluster = config.getInt("ClayGenUndergroundCluster", worldGen, 20, 0, NumericalConstant.INT_MAX, "The max size of the cluster");
		clayGenUndergroundChance = config.getInt("ClayGenUndergroundChance", worldGen, 3, 0, NumericalConstant.INT_MAX, "Chance of generate");

		clayGenUndersea = config.getBoolean("clayGenUndersea", worldGen, true, "Wheather generate Clay undersea.");
		clayGenUnderseaCluster = config.getInt("ClayGenUnderseaCluster", worldGen, 32, 0, NumericalConstant.INT_MAX, "The max size of the cluster");
		clayGenUnderseaChance = config.getInt("ClayGenUnderseaChance", worldGen, 65, 0, NumericalConstant.INT_MAX, "Chance of generate");

		sandGenUnderground = config.getBoolean("sandGenUnderground", worldGen, true, "Wheather generate Sand underground.");
		sandGenUndergroundCluster = config.getInt("SandGenUndergroundCluster", worldGen, 20, 0, NumericalConstant.INT_MAX, "The max size of the cluster");
		sandGenUndergourndChance = config.getInt("SandGenUndergroundChance", worldGen, 3, 0, NumericalConstant.INT_MAX, "Chance of generate");

		clayGenUndersea = config.getBoolean("clayGenUndersea", worldGen, true, "Wheather generate Sand undersea.");
		sandGenUnderseaCluster = config.getInt("SandGenUnderseaCluster", worldGen, 22, 0, NumericalConstant.INT_MAX, "The max size of the cluster");
		sandGenUnderseaChance = config.getInt("SandGenUnderseaChance", worldGen, 45, 0, NumericalConstant.INT_MAX, "Chance of generate");

		dirtGenUndersea = config.getBoolean("dirtGenUndersea", worldGen, true, "Wheather generate Dirt undersea");
		dirtGenUnderseaCluster = config.getInt("DirtGenUnderseaCluster", worldGen, 18, 0, NumericalConstant.INT_MAX, "The max size of the cluster");
		dirtGenUnderseaChance = config.getInt("DirtGenUnderseaChance", worldGen, 30, 0, NumericalConstant.INT_MAX, "Chance of generate");
	}

	//Mob Drop-------------------------------------------------------------------------------------
	public static int cowMeatMax;
	public static int cowMeatMin;

	public static int sheepMeatMax;
	public static int sheepMeatMin;

	public static int pigMeatMax;
	public static int pigMeatMin;

	public static int chickenMeatMax;
	public static int chickenMeatMin;

	public static int chickenFeatherMax;
	public static int chickenFeatherMin;

	public static int rabbitMeatMax;
	public static int rabbitMeatMin;

	public static int rabbitHideMax;
	public static int rabbitHideMin;

	public static int rabbitFootMax;
	public static int rabbitFootMin;

	public static int spiderStringMax;
	public static int spiderStringMin;

	public static int blazeFireChargeMax;
	public static int blazeFireChargeMin;

	public static int blazeRodMax;
	public static int blazeRodMin;

	public static int witherCoalMax;
	public static int witherCoalMin;

	public static int witchBonesMax;
	public static int witchBonesMin;

	public static int skeletonBonesMax;
	public static int skeletonBonesMin;

	public static int zombieBonesMax;
	public static int zombieBonesMin;

	public static int villagerBoneMax;
	public static int villagerBoneMin;

	public static int sheepBoneMax;
	public static int sheepBoneMin;

	public static int cowBoneMax;
	public static int cowBoneMin;

	public static int pigBoneMax;
	public static int pigBoneMin;

	public static int chickenBoneMax;
	public static int chickenBoneMin;

	public static int horseBoneMax;
	public static int horseBoneMin;

	public static int rabbitBoneMax;
	public static int rabbitBoneMin;

	public static int cowLeatherMax;
	public static int cowLeatherMin;

	public static int sheepLeatherMax;
	public static int sheepLeatherMin;

	public static int horseLeatherMax;
	public static int horseLeatherMin;

	public static int bowEnemiesArrowMax;
	public static int bowEnemiesArrowMin;

	public static int villagerEmeraldMax;
	public static int villagerEmeraldMin;

	public static int silverfishSilverMax;
	public static int silverfishSilverMin;

	public void mobDropConfigs(String mobDrop)
	{
		cowMeatMax = config.getInt("CowMeatMax", mobDrop, 16, 0, NumericalConstant.INT_MAX, "Max value of meats dropped by a cow.");
		cowMeatMin = config.getInt("CowMeatMin", mobDrop, 10, 0, NumericalConstant.INT_MAX, "Min value of meats dropped by a cow.");

		sheepMeatMax = config.getInt("SheepMeatMax", mobDrop, 10, 0, NumericalConstant.INT_MAX, "Max value of meats dropped by a sheep.");
		sheepMeatMin = config.getInt("SheepMeatMin", mobDrop, 6, 0, NumericalConstant.INT_MAX, "Min value of meats dropped by a sheep.");

		pigMeatMax = config.getInt("PigMeatMax", mobDrop, 16, 0, NumericalConstant.INT_MAX, "Max value of meats dropped by a pig.");
		pigMeatMin = config.getInt("PigMeatMin", mobDrop, 10, 0, NumericalConstant.INT_MAX, "Min value of meats dropped by a pig.");

		chickenMeatMax = config.getInt("ChickenMeatMax", mobDrop, 4, 0, NumericalConstant.INT_MAX, "Max value of meats dropped by a chicken.");
		chickenMeatMin = config.getInt("ChickenMeatMin", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Min value of meats dropped by a chicken.");

		chickenFeatherMax = config.getInt("ChickenFeatherMax", mobDrop, 20, 0, NumericalConstant.INT_MAX, "Max value of feathers dropped by a chicken.");
		chickenFeatherMin = config.getInt("ChickenFeatherMin", mobDrop, 10, 0, NumericalConstant.INT_MAX, "Min value of feathers dropped by a chicken.");

		rabbitMeatMax = config.getInt("RabbitMeatMax", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Max value of meats dropped by a rabbit.");
		rabbitMeatMin = config.getInt("RabbitMeatMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of meats dropped by a rabbit.");

		rabbitHideMax = config.getInt("RabbitHideMax", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Max value of hides dropped by a rabbit.");
		rabbitHideMin = config.getInt("RabbitHideMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of hides dropped by a rabbit.");

		rabbitFootMax = config.getInt("RabbitFootMax", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Max value of feet dropped by a rabbit.");
		rabbitFootMin = config.getInt("RabbitFootMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of feet dropped by a rabbit.");

		spiderStringMax = config.getInt("SpiderStringMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of strings dropped by a spider.");
		spiderStringMin = config.getInt("SpiderStringMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of strings dropped by a spider.");

		blazeFireChargeMax = config.getInt("BlazeFireChargeMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of fire charges dropped by a blaze.");
		blazeFireChargeMin = config.getInt("BlazeFireChargeMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of fire charges dropped by a blaze.");

		blazeRodMax = config.getInt("BlazeRodMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of blaze rods dropped by a blaze.");
		blazeRodMin = config.getInt("BlazeRodMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of blaze rods dropped by a blaze.");

		witherCoalMax = config.getInt("WitherCoalMax", mobDrop, 6, 0, NumericalConstant.INT_MAX, "Max value of coals dropped by a wither skeleton.");
		witherCoalMin = config.getInt("WitherCoalMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of coals dropped by a wither skeleton.");

		witchBonesMax = config.getInt("WitchBonesMax", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a witch.");
		witchBonesMin = config.getInt("WitchBonesMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a witch.");

		skeletonBonesMax = config.getInt("SkeletonBonesMax", mobDrop, 4, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a skeleton.");
		skeletonBonesMin = config.getInt("SkeletonBonesMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a skeleton.");

		zombieBonesMax = config.getInt("ZombieBonesMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a zombie.");
		zombieBonesMin = config.getInt("ZombieBonesMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a zombie.");

		villagerBoneMax = config.getInt("VillagerBoneMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a villager.");
		villagerBoneMin = config.getInt("VillagerBoneMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a villager.");

		sheepBoneMax = config.getInt("SheepBoneMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a sheep.");
		sheepBoneMin = config.getInt("SheepBoneMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a sheep.");

		cowBoneMax = config.getInt("CowBoneMax", mobDrop, 8, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a cow.");
		cowBoneMin = config.getInt("CowBoneMin", mobDrop, 4, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a cow.");

		pigBoneMax = config.getInt("PigBoneMax", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a pig.");
		pigBoneMin = config.getInt("PigBoneMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a pig.");

		chickenBoneMax = config.getInt("ChickenBoneMax", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a chicken.");
		chickenBoneMin = config.getInt("ChickenBoneMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a chicken.");

		horseBoneMax = config.getInt("HorseBoneMax", mobDrop, 5, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a horse.");
		horseBoneMin = config.getInt("HorseBoneMin", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a horse.");

		rabbitBoneMax = config.getInt("RabbitBoneMax", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Max value of bones dropped by a rabbit.");
		rabbitBoneMin = config.getInt("RabbitBoneMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of bones dropped by a rabbit.");

		cowLeatherMax = config.getInt("CowLeatherMax", mobDrop, 8, 0, NumericalConstant.INT_MAX, "Max value of leathers dropped by a cow.");
		cowLeatherMin = config.getInt("CowLeatherMin", mobDrop, 4, 0, NumericalConstant.INT_MAX, "Min value of leathers dropped by a cow.");

		sheepLeatherMax = config.getInt("SheepLeatherMax", mobDrop, 2, 0, NumericalConstant.INT_MAX, "Max value of leathers dropped by a sheep.");
		sheepLeatherMin = config.getInt("SheepLeatherMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of leathers dropped by a sheep.");

		horseLeatherMax = config.getInt("HorseLeatherMax", mobDrop, 3, 0, NumericalConstant.INT_MAX, "Max value of leathers dropped by a horse.");
		horseLeatherMin = config.getInt("HorseLeatherMin", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Min value of leathers dropped by a horse.");

		bowEnemiesArrowMax = config.getInt("BowEnemiesArrowMax", mobDrop, 12, 0, NumericalConstant.INT_MAX, "Max value of arrows dropped by enemies.");
		bowEnemiesArrowMin = config.getInt("BowEnemiesArrowMin", mobDrop, 4, 0, NumericalConstant.INT_MAX, "Min value of arrows dropped by enemies.");

		villagerEmeraldMax = config.getInt("VillagerEmeraldMax", mobDrop, 4, 0, NumericalConstant.INT_MAX, "Max value of emeralds dropped by a villager.");
		villagerEmeraldMin = config.getInt("VillagerEmeraldMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of emeralds dropped by a villager.");

		silverfishSilverMax = config.getInt("SilverfishSilverMax", mobDrop, 1, 0, NumericalConstant.INT_MAX, "Max value of silver nuggets dropped by a silverfish.");
		silverfishSilverMax = config.getInt("SilverfishSilverMin", mobDrop, 0, 0, NumericalConstant.INT_MAX, "Min value of silver nuggets dropped by a silverfish.");
	}

	//Client---------------------------------------------------------------------------------------
	public static int particleIdDummyBarrier;

	public void clientConfigs(String client)
	{
		particleIdDummyBarrier = config.getInt("ParticleIdDummyBarrier", client, ParticleRegistry.PARTICLE_ID_DUMMY_BARRIER_DEFAULT, 0, NumericalConstant.INT_MAX, "Particle ID of Dummy Barrier");
	}

	//Pick Up Widely-------------------------------------------------------------------------------
	public static boolean startModePUW;
	public static float horizontalRangePUW;
	public static float verticalRangePUW;
	public static boolean pickUpXpOrbPUW;
	public static boolean pickUpArrowPUW;

	public void puwConfigs(String puw)
	{
		startModePUW = config.getBoolean("PickUpWidelyStartMode", puw, true, "Start mode of pick up widely");
		horizontalRangePUW = config.getFloat("PickUpWidelyRangeHorizontal", puw, 5.0F, 0F, NumericalConstant.FLOAT_MAX, "Horizontal range of pick up widely");
		verticalRangePUW = config.getFloat("PickUpWidelyRangeVertical", puw, 5.0F, 0.F, NumericalConstant.FLOAT_MAX, "Vertical range of pick up widely");
		pickUpXpOrbPUW = config.getBoolean("PickUpWidelyPickUpXpOrb", puw, true, "Whether pick up XP orbs.");
		pickUpArrowPUW = config.getBoolean("PickUpWidelyPickUpArrow", puw, true, "Whether pick up arrows.");
	}

	//	@Config.Comment("I say fuck you")
	//	public static boolean test = true;

	public static enum EnumConfigCategory
	{
		GENERAL("general", "General Settings"),
		WORLD_GEN("world_gen", "World Generation Settings"),
		MOB_DROP("mob_drop", "Mob Drop Settings"),
		CLIENT("client", "Client Settings"),
		PUW("pick_up_widely", "PickUpWidly Settings"),;

		public final String name;
		public final String comment;

		EnumConfigCategory(String name, String comment)
		{
			this.name = name;
			this.comment = comment;
		}

		public String getName()
		{
			return this.name;
		}

		public String getComment()
		{
			return this.comment;
		}

		public static int getLength()
		{
			return EnumConfigCategory.values().length;
		}
	}
}
