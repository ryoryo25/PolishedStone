package ryoryo.polishedstone;

import static net.minecraftforge.oredict.OreDictionary.*;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import ryoryo.polishedlib.item.ItemBaseArmor;
import ryoryo.polishedlib.itemblock.ItemBlockDoor;
import ryoryo.polishedlib.itemblock.ItemBlockMeta;
import ryoryo.polishedlib.util.ArithmeticUtils;
import ryoryo.polishedlib.util.LibPotionId;
import ryoryo.polishedlib.util.LibTool;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumArmorType;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedlib.util.enums.EnumPlanks;
import ryoryo.polishedstone.block.BlockAnchorBolt;
import ryoryo.polishedstone.block.BlockBlackQuartz;
import ryoryo.polishedstone.block.BlockChromaKeyBack;
import ryoryo.polishedstone.block.BlockColoredLight;
import ryoryo.polishedstone.block.BlockCompressedBookshelf;
import ryoryo.polishedstone.block.BlockDecoration;
import ryoryo.polishedstone.block.BlockDecoration.BlockType;
import ryoryo.polishedstone.block.BlockDummyBarrier;
import ryoryo.polishedstone.block.BlockDummyCable;
import ryoryo.polishedstone.block.BlockEarthenPipe;
import ryoryo.polishedstone.block.BlockFaucet;
import ryoryo.polishedstone.block.BlockFenceSlab;
import ryoryo.polishedstone.block.BlockFluorescentLight;
import ryoryo.polishedstone.block.BlockGlowstoneGenerator;
import ryoryo.polishedstone.block.BlockHardenedShadeGlassDoor;
import ryoryo.polishedstone.block.BlockInvisibleButton;
import ryoryo.polishedstone.block.BlockIronChain;
import ryoryo.polishedstone.block.BlockIronLadder;
import ryoryo.polishedstone.block.BlockIronPlate;
import ryoryo.polishedstone.block.BlockIronPlateStairs;
import ryoryo.polishedstone.block.BlockLamp;
import ryoryo.polishedstone.block.BlockLattice;
import ryoryo.polishedstone.block.BlockLockableDoor;
import ryoryo.polishedstone.block.BlockMetal;
import ryoryo.polishedstone.block.BlockModBaseDoor;
import ryoryo.polishedstone.block.BlockModBaseWall;
import ryoryo.polishedstone.block.BlockModCrops;
import ryoryo.polishedstone.block.BlockModStoneSlab;
import ryoryo.polishedstone.block.BlockNewFlower;
import ryoryo.polishedstone.block.BlockNewGravel;
import ryoryo.polishedstone.block.BlockNewOre;
import ryoryo.polishedstone.block.BlockNewPath;
import ryoryo.polishedstone.block.BlockPavingStone;
import ryoryo.polishedstone.block.BlockPolishedStone;
import ryoryo.polishedstone.block.BlockPolishedStoneColored;
import ryoryo.polishedstone.block.BlockRunningWater;
import ryoryo.polishedstone.block.BlockRustyRail;
import ryoryo.polishedstone.block.BlockRustyWood;
import ryoryo.polishedstone.block.BlockSafetyFence;
import ryoryo.polishedstone.block.BlockShadeGlass;
import ryoryo.polishedstone.block.BlockSlabRail;
import ryoryo.polishedstone.block.BlockSlimeRemovalTorch;
import ryoryo.polishedstone.block.BlockStepladder;
import ryoryo.polishedstone.block.BlockStreetGutter;
import ryoryo.polishedstone.block.BlockTemporary;
import ryoryo.polishedstone.block.BlockTest;
import ryoryo.polishedstone.block.BlockThreePillars;
import ryoryo.polishedstone.block.BlockVendingMachine;
import ryoryo.polishedstone.block.BlockVendingMachine.TexType;
import ryoryo.polishedstone.block.BlockVoidTeleporter;
import ryoryo.polishedstone.block.BlockYellowLine;
import ryoryo.polishedstone.client.particle.ParticleRegistry;
import ryoryo.polishedstone.client.render.entity.RenderZabuton;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.entity.EntityZabuton;
import ryoryo.polishedstone.event.DispenseZabutonHandler;
import ryoryo.polishedstone.event.EventHelper;
import ryoryo.polishedstone.event.ModClientEventHandler;
import ryoryo.polishedstone.event.ModEventHandler;
import ryoryo.polishedstone.event.ModLivingDropsEvent;
import ryoryo.polishedstone.event.ModLivingEvent;
import ryoryo.polishedstone.event.ModPlayerInteractEvent;
import ryoryo.polishedstone.event.PlayerMoveSpeedHandler;
import ryoryo.polishedstone.event.RightClickBlockEvent;
import ryoryo.polishedstone.item.ItemBedrockCore;
import ryoryo.polishedstone.item.ItemDamageFood;
import ryoryo.polishedstone.item.ItemInvincibleBow;
import ryoryo.polishedstone.item.ItemInvincibleSword;
import ryoryo.polishedstone.item.ItemMaterial;
import ryoryo.polishedstone.item.ItemModBase;
import ryoryo.polishedstone.item.ItemModBaseFood;
import ryoryo.polishedstone.item.ItemModSeedFood;
import ryoryo.polishedstone.item.ItemModSeeds;
import ryoryo.polishedstone.item.ItemNewDye;
import ryoryo.polishedstone.item.ItemRandomBox;
import ryoryo.polishedstone.item.ItemRegenerate;
import ryoryo.polishedstone.item.ItemSolidMilk;
import ryoryo.polishedstone.item.ItemSpinachCan;
import ryoryo.polishedstone.item.ItemTweakedDye;
import ryoryo.polishedstone.item.ItemZabuton;
import ryoryo.polishedstone.item.armor.ItemArmorInvincible;
import ryoryo.polishedstone.item.armor.ItemArmorSafetyClothes;
import ryoryo.polishedstone.itemblock.ItemBlockBlackQuartz;
import ryoryo.polishedstone.itemblock.ItemBlockCompressedBookshelf;
import ryoryo.polishedstone.itemblock.ItemBlockDummyBarrier;
import ryoryo.polishedstone.itemblock.ItemBlockFaucet;
import ryoryo.polishedstone.itemblock.ItemBlockPolishedStone;
import ryoryo.polishedstone.itemblock.ItemBlockStreetGutter;
import ryoryo.polishedstone.itemblock.ItemBlockTemporary;
import ryoryo.polishedstone.itemblock.ItemBlockThreePillars;
import ryoryo.polishedstone.itemblock.ItemBlockVendingMachine;
import ryoryo.polishedstone.network.PacketHandler;
import ryoryo.polishedstone.tileenttiy.TileEntitySlimeRemovalTorch;
import ryoryo.polishedstone.util.ArmorMaterials;
import ryoryo.polishedstone.util.EnumCropType;
import ryoryo.polishedstone.util.LibEntityId;
import ryoryo.polishedstone.util.LibKey;
import ryoryo.polishedstone.util.LibTEId;
import ryoryo.polishedstone.util.ModCompat;
import ryoryo.polishedstone.util.RecipeUtils;
import ryoryo.polishedstone.util.References;
import ryoryo.polishedstone.world.dimension.DimensionRegistry;
import ryoryo.polishedstone.world.gen.BiomeDecorationHandler;
import ryoryo.polishedstone.world.gen.LavaLakeGenHandler;
import ryoryo.polishedstone.world.gen.WorldGenBlock;

public class Register
{
	//Blocks
	public static final Block BLOCK_BRICK_WALL = new BlockModBaseWall(Blocks.BRICK_BLOCK);
	public static final Block BLOCK_GRANITE_WALL = new BlockModBaseWall(Blocks.STONE/*.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE).getBlock()*/, "granite");
	public static final Block BLOCK_SMOOTH_GRANITE_WALL = new BlockModBaseWall(Blocks.STONE, "smooth_granite");
	public static final Block BLOCK_DIORITE_WALL = new BlockModBaseWall(Blocks.STONE, "diorite");
	public static final Block BLOCK_SMOOTH_DIORITE_WALL = new BlockModBaseWall(Blocks.STONE, "smooth_diorite");
	public static final Block BLOCK_ANDESITE_WALL = new BlockModBaseWall(Blocks.STONE, "andesite");
	public static final Block BLOCK_SMOOTH_ANDESITE_WALL = new BlockModBaseWall(Blocks.STONE, "smooth_andesite");
	public static final Block BLOCK_POLISHED_STONE = new BlockPolishedStone();
	public static final Block BLOCK_COLORED_LIGHT = new BlockColoredLight();
	public static final Block BLOCK_NEW_GRAVEL = new BlockNewGravel();
	public static final Block BLOCK_NEW_PATH = new BlockNewPath();
	public static final Block BLOCK_NEW_FLOWER = new BlockNewFlower();
	public static final Block BLOCK_GLOWSTONE_GENERATOR = new BlockGlowstoneGenerator();
	public static final Block BLOCK_DUMMY_BARRIER = new BlockDummyBarrier();
	public static final Block BLOCK_BLACK_QUARTZ = new BlockBlackQuartz();
//	public static final Block BLOCK_SS_BLACK_QUARTZ_BLOCK = new BlockSmallStairs(BLOCK_BLACK_QUARTZ.getDefaultState(), "black_quartz_block");
	public static final Block BLOCK_VM_NORMAL = new BlockVendingMachine(TexType.NORMAL.getName());
	public static final Block BLOCK_VM_COCA_COLA = new BlockVendingMachine(TexType.COCA_COLA.getName());
	public static final Block BLOCK_VM_SUNTORY = new BlockVendingMachine(TexType.SUNTORY.getName());
	public static final Block BLOCK_VM_DY_DO = new BlockVendingMachine(TexType.DYDO.getName());
	public static final Block BLOCK_VM_ITO_EN = new BlockVendingMachine(TexType.ITO_EN.getName());
	public static final Block BLOCK_VM_SUICA = new BlockVendingMachine(TexType.SUICA.getName());
	public static final Block BLOCK_VM_MAX_COFFEE = new BlockVendingMachine(TexType.MAX_COFFEE.getName());
	public static final Block BLOCK_LOCKER = new BlockVendingMachine(TexType.LOCKER.getName());
	public static final Block BLOCK_YELLOW_LINE = new BlockYellowLine();
	public static final Block BLOCK_DECORATION = new BlockDecoration();
	public static final Block BLOCK_INVISIBLE_BUTTON = new BlockInvisibleButton();
	public static final Block BLOCK_RUSTY_RAIL1 = new BlockRustyRail(1);
	public static final Block BLOCK_RUSTY_RAIL2 = new BlockRustyRail(2);
	public static final Block BLOCK_RUSTY_RAIL3 = new BlockRustyRail(3);
	public static final Block BLOCK_RUSTY_WOOD = new BlockRustyWood();
	public static final Block BLOCK_METAL = new BlockMetal();
	public static final Block BLOCK_SLIME_REMOVAL_TORCH = new BlockSlimeRemovalTorch();
	public static final Block BLOCK_NEW_ORE = new BlockNewOre();
	public static final Block BLOCK_IRON_CHAIN = new BlockIronChain();
	public static final Block BLOCK_ANCHOR_BOLT = new BlockAnchorBolt();
	public static final Block BLOCK_IRON_PLATE = new BlockIronPlate();
	public static final Block BLOCK_IRON_PLATE_STAIRS = new BlockIronPlateStairs();
	public static final Block BLOCK_SAFETY_FENCE = new BlockSafetyFence();
	public static final Block BLOCK_IRON_LADDER = new BlockIronLadder();
	public static final Block BLOCK_STEPLADDER = new BlockStepladder();
//	public static final Block blockFloodLight = new BlockFloodLight();
//	public static final Block blockFloodLightPart = new BlockFloodLightPart();
	public static final Block BLOCK_FAUCET = new BlockFaucet();
	public static final Block BLOCK_RUNNING_WATER = new BlockRunningWater();
	public static final Block BLOCK_LATTICE_OAK = new BlockLattice(Blocks.PLANKS, "oak");
	public static final Block BLOCK_LATTICE_SPRUCE = new BlockLattice(Blocks.PLANKS, "spruce");
	public static final Block BLOCK_LATTICE_BIRCH = new BlockLattice(Blocks.PLANKS, "birch");
	public static final Block BLOCK_LATTICE_JUNGLE = new BlockLattice(Blocks.PLANKS, "jungle");
	public static final Block BLOCK_LATTICE_ACACIA = new BlockLattice(Blocks.PLANKS, "acacia");
	public static final Block BLOCK_LATTICE_DARK_OAK = new BlockLattice(Blocks.PLANKS, "dark_oak");
	public static final Block BLOCK_LATTICE_NETHER_BRICK = new BlockLattice(Blocks.NETHER_BRICK, "nether_brick");
	public static final Block BLOCK_FENCE_SLAB_OAK = new BlockFenceSlab(Blocks.PLANKS, "oak");
	public static final Block BLOCK_FENCE_SLAB_SPRUCE = new BlockFenceSlab(Blocks.PLANKS, "spruce");
	public static final Block BLOCK_FENCE_SLAB_BIRCH = new BlockFenceSlab(Blocks.PLANKS, "birch");
	public static final Block BLOCK_FENCE_SLAB_JUNGLE = new BlockFenceSlab(Blocks.PLANKS, "jungle");
	public static final Block BLOCK_FENCE_SLAB_ACACIA = new BlockFenceSlab(Blocks.PLANKS, "acacia");
	public static final Block BLOCK_FENCE_SLAB_DARK_OAK = new BlockFenceSlab(Blocks.PLANKS, "dark_oak");
	public static final Block BLOCK_FENCE_SLAB_NETHER_BRICK = new BlockFenceSlab(Blocks.NETHER_BRICK, "nether_brick");
	public static final Block BLOCK_STREET_GUTTER = new BlockStreetGutter();
	public static final Block BLOCK_FL_DARK = new BlockFluorescentLight("dark", 0.25F);
	public static final Block BLOCK_FL_BRIGHT = new BlockFluorescentLight("bright", 0.5F);
	public static final Block BLOCK_FL_BRIGHTER = new BlockFluorescentLight("brighter", 1.0F);
	public static final Block BLOCK_TEMPORARY = new BlockTemporary();
	public static final Block BLOCK_SLAB_RAIL = new BlockSlabRail();
	public static final Block BLOCK_PAVING_STONE = new BlockPavingStone();
	public static final Block BLOCK_SHADE_GLASS = new BlockShadeGlass("shade_glass", false);
	public static final Block BLOCK_HARDENED_SHADE_GLASS = new BlockShadeGlass("hardened_shade_glass", true);
	public static final Block BLOCK_COMPRESSED_BOOKSHELF = new BlockCompressedBookshelf();
	public static final Block BLOCK_THREE_PILLARS = new BlockThreePillars();
	public static final Block BLOCK_EARTHEN_PIPE = new BlockEarthenPipe();
	public static final Block BLOCK_HARDENED_SHADE_GLASS_DOOR = new BlockHardenedShadeGlassDoor();
	public static final Block BLOCK_LOCKABLE_DOOR = new BlockLockableDoor();
	public static final Block BLOCK_DUMMY_CABLE = new BlockDummyCable();
	public static final Block BLOCK_POLISHED_STONE_COLORED = new BlockPolishedStoneColored();
	public static final Block BLOCK_LAMP = new BlockLamp();
	public static final Block BLOCK_GLASS_OAK_DOOR = new BlockModBaseDoor("glass_oak");
	public static final Block BLOCK_GLASS_SPRUCE_DOOR = new BlockModBaseDoor("glass_spruce");
	public static final Block BLOCK_GLASS_BIRCH_DOOR = new BlockModBaseDoor("glass_birch");
	public static final Block BLOCK_GLASS_JUNGLE_DOOR = new BlockModBaseDoor("glass_jungle");
	public static final Block BLOCK_GLASS_ACACIA_DOOR = new BlockModBaseDoor("glass_acacia");
	public static final Block BLOCK_GLASS_DARK_OAK_DOOR = new BlockModBaseDoor("glass_dark_oak");
	public static final Block BLOCK_GLASS_IRON_DOOR = new BlockModBaseDoor(Material.IRON, "glass_iron", SoundType.METAL);
	public static final Block BLOCK_CHROMA_KEY_BACK = new BlockChromaKeyBack();
	public static final BlockSlab BLOCK_STONE_SLAB = new BlockModStoneSlab.BlockModStoneSlabHalf();
	public static final BlockSlab BLOCK_DOUBLE_STONE_SLAB = new BlockModStoneSlab.BlockModStoneSlabDouble();
	public static final Block BLOCK_CROP_ONION = new BlockModCrops(EnumCropType.ONION);
	public static final Block BLOCK_CROP_JAPANESE_RADISH = new BlockModCrops(EnumCropType.JAPANESE_RADISH);
	public static final Block BLOCK_CROP_CABBAGE = new BlockModCrops(EnumCropType.CABBAGE);
	public static final Block BLOCK_CROP_STICKY_RICE = new BlockModCrops(EnumCropType.STICKY_RICE);
	public static final Block BLOCK_CROP_TOMATO = new BlockModCrops(EnumCropType.TOMATO);
	public static final Block BLOCK_CROP_EGGPLANT = new BlockModCrops(EnumCropType.EGGPLANT);
	public static final Block BLOCK_VOID_TELEPORTER = new BlockVoidTeleporter();

	//Items
	public static final Item ITEM_TWEAKED_DYE = new ItemTweakedDye();
	public static final Item ITEM_MATERIAL = new ItemMaterial();
	public static final Item ITEM_INSTANT_FOOD = new ItemModBaseFood(2, 2.0F, false, "instant_food", PSV2Core.TAB_MOD, 4);
	public static final Item ITEM_RAW_MEAT_WITH_BONE = new ItemModBaseFood(5, 2.4F, true, "raw_meat_with_bone", PSV2Core.TAB_MOD, 128);
	public static final Item ITEM_COOKED_MEAT_WITH_BONE = new ItemModBaseFood(20, 20F, true, "cooked_meat_with_bone", PSV2Core.TAB_MOD, 128).setPotionEffect(new PotionEffect(Potion.getPotionById(LibPotionId.SATURATION), (int) ArithmeticUtils.minuteToTick(2F), 2), 1.0F);
	public static final Item ITEM_SPINACH_CAN = new ItemSpinachCan();
	public static final Item ITEM_SOLID_MILK = new ItemSolidMilk();
	public static final Item ITEM_REGENERATE = new ItemRegenerate();
	public static final ItemArmor ITEM_ARMOR_DECORATIVE_HAZMAT_HELMET = new ItemBaseArmor(ArmorMaterials.DECORATIVE_HAZMAT, EnumArmorType.HELMET, "decorative_hazmat", PSV2Core.TAB_MOD);
	public static final ItemArmor ITEM_ARMOR_DECORATIVE_HAZMAT_CHEST = new ItemBaseArmor(ArmorMaterials.DECORATIVE_HAZMAT, EnumArmorType.CHESTPLATE, "decorative_hazmat", PSV2Core.TAB_MOD);
	public static final ItemArmor ITEM_ARMOR_DECORATIVE_HAZMAT_LEG = new ItemBaseArmor(ArmorMaterials.DECORATIVE_HAZMAT, EnumArmorType.LEGGINGS, "decorative_hazmat", PSV2Core.TAB_MOD);
	public static final ItemArmor ITEM_ARMOR_DECORATIVE_HAZMAT_BOOTS = new ItemBaseArmor(ArmorMaterials.DECORATIVE_HAZMAT, EnumArmorType.BOOTS, "decorative_hazmat", PSV2Core.TAB_MOD);
	public static final ItemArmor ITEM_ARMOR_SAFETY_MET = new ItemArmorSafetyClothes(EnumArmorType.HELMET);
	public static final ItemArmor ITEM_ARMOR_WORK_CLOTHES = new ItemArmorSafetyClothes(EnumArmorType.CHESTPLATE);
	public static final ItemArmor ITEM_ARMOR_SAFETY_BELT = new ItemArmorSafetyClothes(EnumArmorType.LEGGINGS);
	public static final ItemArmor ITEM_ARMOR_SAFETY_BOOTS = new ItemArmorSafetyClothes(EnumArmorType.BOOTS);
	public static final ItemArmor ITEM_ARMOR_LIFE_JACKET = new ItemBaseArmor(ArmorMaterials.LIFE_JACKET, EnumArmorType.CHESTPLATE, "life_jacket", PSV2Core.TAB_MOD);
	public static final Item ITEM_INVINCIBLE_SWORD = new ItemInvincibleSword();
	public static final Item ITEM_INVINCIBLE_BOW = new ItemInvincibleBow();
	public static final Item ITEM_BEDROCK_CORE = new ItemBedrockCore();
	public static final Item ITEM_RANDOM_BOX = new ItemRandomBox();
	public static final Item ITEM_NEW_DYE = new ItemNewDye();
	public static final Item ITEM_ZABUTON = new ItemZabuton();
	public static final ItemArmor ITEM_ARMOR_INVINCIBLE_HELMET = new ItemArmorInvincible(EnumArmorType.HELMET);
	public static final ItemArmor ITEM_ARMOR_INVINCIBLE_CHEST = new ItemArmorInvincible(EnumArmorType.CHESTPLATE);
	public static final ItemArmor ITEM_ARMOR_INVINCIBLE_LEG = new ItemArmorInvincible(EnumArmorType.LEGGINGS);
	public static final ItemArmor ITEM_ARMOR_INVINCIBLE_BOOTS = new ItemArmorInvincible(EnumArmorType.BOOTS);
	public static final Item ITEM_DAMAGE_FOOD_MINI = new ItemDamageFood("damage_food_mini", 16);
	public static final Item ITEM_DAMAGE_FOOD_NORMAL = new ItemDamageFood("damage_food_normal", 64);
	public static final Item ITEM_DAMAGE_FOOD_BIG = new ItemDamageFood("damage_food_big", 256);
	public static final Item ITEM_DAMAGE_FOOD_HUGE = new ItemDamageFood("damage_food_huge", 1024);
	public static final Item ITEM_SEED_ONION = new ItemModSeeds(EnumCropType.ONION);
	public static final Item ITEM_SEED_JAPANESE_RADISH = new ItemModSeeds(EnumCropType.JAPANESE_RADISH);
	public static final Item ITEM_SEED_CABBAGE = new ItemModSeeds(EnumCropType.CABBAGE);
	public static final Item ITEM_SEED_TOMATO = new ItemModSeeds(EnumCropType.TOMATO);
	public static final Item ITEM_SEED_EGGPLANT = new ItemModSeeds(EnumCropType.EGGPLANT);
	public static final Item ITEM_ONION = new ItemModBase(EnumCropType.ONION.getName());
	public static final Item ITEM_JAPANESE_RADISH = new ItemModBase(EnumCropType.JAPANESE_RADISH.getName());
	public static final Item ITEM_CABBAGE = new ItemModBase(EnumCropType.CABBAGE.getName());
	public static final Item ITEM_STICKY_RICE = new ItemModSeedFood(EnumCropType.STICKY_RICE, 1, 0.2F);
	public static final Item ITEM_TOMATO = new ItemModBase(EnumCropType.TOMATO.getName());
	public static final Item ITEM_EGGPLANT = new ItemModBase(EnumCropType.EGGPLANT.getName());

	public static final Block BLOCK_TEST = new BlockTest();

	public static final SoundEvent SOUND_IRON_CHAIN = new SoundEvent(new ResourceLocation(References.MOD_ID, "chain"));
	public static final SoundEvent SOUND_GUN_FIRE = new SoundEvent(new ResourceLocation(References.MOD_ID, "gun_fire"));

	public static void preInit()
	{
		String vm = "vending_machine_";
		//Blocks Registry
		PSV2Core.REGISTER.registerBlock(BLOCK_BRICK_WALL, "brick_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_GRANITE_WALL, "granite_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_SMOOTH_GRANITE_WALL, "smooth_granite_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_DIORITE_WALL, "diorite_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_SMOOTH_DIORITE_WALL, "smooth_diorite_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_ANDESITE_WALL, "andesite_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_SMOOTH_ANDESITE_WALL, "smooth_andesite_wall");
		PSV2Core.REGISTER.registerBlock(BLOCK_POLISHED_STONE, new ItemBlockPolishedStone(), "polished_stone", ItemBlockPolishedStone.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_COLORED_LIGHT, new ItemBlockMeta(BLOCK_COLORED_LIGHT, EnumColor.COLOR_WOOL), "colored_light", EnumColor.COLOR_WOOL);
		PSV2Core.REGISTER.registerBlock(BLOCK_NEW_PATH, new ItemBlockMeta(BLOCK_NEW_PATH, BlockNewPath.PathType.NAMES), "new_path", BlockNewPath.PathType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_NEW_FLOWER, new ItemBlockMeta(BLOCK_NEW_FLOWER, BlockNewFlower.NewFlowerType.NAMES), "new_flower", BlockNewFlower.NewFlowerType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_GLOWSTONE_GENERATOR, new ItemBlock(BLOCK_GLOWSTONE_GENERATOR), "glowstone_generator", 8);
		PSV2Core.REGISTER.registerBlock(BLOCK_DUMMY_BARRIER, "dummy_barrier");
		PSV2Core.REGISTER.registerBlock(BLOCK_BLACK_QUARTZ, new ItemBlockBlackQuartz(), "black_quartz", ItemBlockBlackQuartz.NAMES);
//		PSV2Core.REGISTER.registerSmallStairs(BLOCK_SS_BLACK_QUARTZ_BLOCK, "black_quartz_block");
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_NORMAL, new ItemBlockVendingMachine(BLOCK_VM_NORMAL), vm + TexType.NORMAL.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_COCA_COLA, new ItemBlockVendingMachine(BLOCK_VM_COCA_COLA), vm + TexType.COCA_COLA.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_SUNTORY, new ItemBlockVendingMachine(BLOCK_VM_SUNTORY), vm + TexType.SUNTORY.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_DY_DO, new ItemBlockVendingMachine(BLOCK_VM_DY_DO), vm + TexType.DYDO.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_ITO_EN, new ItemBlockVendingMachine(BLOCK_VM_ITO_EN), vm + TexType.ITO_EN.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_SUICA, new ItemBlockVendingMachine(BLOCK_VM_SUICA), vm + TexType.SUICA.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_VM_MAX_COFFEE, new ItemBlockVendingMachine(BLOCK_VM_MAX_COFFEE), vm + TexType.MAX_COFFEE.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_LOCKER, new ItemBlockVendingMachine(BLOCK_LOCKER), vm + TexType.LOCKER.getName());
		PSV2Core.REGISTER.registerBlock(BLOCK_YELLOW_LINE, "yellow_line");
		PSV2Core.REGISTER.registerBlock(BLOCK_DECORATION, new ItemBlockMeta(Register.BLOCK_DECORATION, BlockType.NAMES), "decoration_block", BlockDecoration.BlockType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_INVISIBLE_BUTTON, "invisible_button");
		PSV2Core.REGISTER.registerBlock(BLOCK_RUSTY_RAIL1, "rusty_rail_1");
		PSV2Core.REGISTER.registerBlock(BLOCK_RUSTY_RAIL2, "rusty_rail_2");
		PSV2Core.REGISTER.registerBlock(BLOCK_RUSTY_RAIL3, "rusty_rail_3");
		PSV2Core.REGISTER.registerBlock(BLOCK_RUSTY_WOOD, "rusty_wood");
		PSV2Core.REGISTER.registerBlock(BLOCK_METAL, new ItemBlockMeta(BLOCK_METAL, BlockMetal.MaterialType.NAMES), "metal", BlockMetal.MaterialType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_SLIME_REMOVAL_TORCH, "slime_removal_torch");
		PSV2Core.REGISTER.registerBlock(BLOCK_NEW_ORE, new ItemBlockMeta(BLOCK_NEW_ORE, BlockNewOre.MaterialType.NAMES), "new_ore", BlockNewOre.MaterialType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_IRON_CHAIN, "iron_chain");
		PSV2Core.REGISTER.registerBlock(BLOCK_ANCHOR_BOLT, "anchor_bolt");
		PSV2Core.REGISTER.registerBlock(BLOCK_IRON_PLATE, new ItemBlockMeta(BLOCK_IRON_PLATE, BlockIronPlate.PlateType.NAMES), "iron_plate", BlockIronPlate.PlateType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_IRON_PLATE_STAIRS, "iron_plate_stairs");
		PSV2Core.REGISTER.registerBlock(BLOCK_SAFETY_FENCE, "safety_fence");
		PSV2Core.REGISTER.registerBlock(BLOCK_IRON_LADDER, "iron_ladder");
		PSV2Core.REGISTER.registerBlock(BLOCK_STEPLADDER, "stepladder");
//		PSV2Core.REGISTER.registerBlock(blockFloodLight, "flood_light");
		PSV2Core.REGISTER.registerBlock(BLOCK_FAUCET, new ItemBlockFaucet(), "faucet", 2);
		PSV2Core.REGISTER.registerBlock(BLOCK_RUNNING_WATER, "running_water");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_OAK, "lattice_oak");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_SPRUCE, "lattice_spruce");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_BIRCH, "lattice_birch");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_JUNGLE, "lattice_jungle");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_ACACIA, "lattice_acacia");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_DARK_OAK, "lattice_dark_oak");
		PSV2Core.REGISTER.registerBlock(BLOCK_LATTICE_NETHER_BRICK, "lattice_nether_brick");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_OAK, "fence_slab_oak");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_SPRUCE, "fence_slab_spruce");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_BIRCH, "fence_slab_birch");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_JUNGLE, "fence_slab_jungle");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_ACACIA, "fence_slab_acacia");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_DARK_OAK, "fence_slab_dark_oak");
		PSV2Core.REGISTER.registerBlock(BLOCK_FENCE_SLAB_NETHER_BRICK, "fence_slab_nether_brick");
		PSV2Core.REGISTER.registerBlock(BLOCK_STREET_GUTTER, new ItemBlockStreetGutter(), "street_gutter", ItemBlockStreetGutter.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_FL_DARK, "fluorescent_light_dark");
		PSV2Core.REGISTER.registerBlock(BLOCK_FL_BRIGHT, "fluorescent_light_bright");
		PSV2Core.REGISTER.registerBlock(BLOCK_FL_BRIGHTER, "fluorescent_light_brighter");
		PSV2Core.REGISTER.registerBlock(BLOCK_TEMPORARY, new ItemBlockTemporary(), "temporary_block");
		PSV2Core.REGISTER.registerBlock(BLOCK_SLAB_RAIL, "slab_rail");
		PSV2Core.REGISTER.registerBlock(BLOCK_NEW_GRAVEL, new ItemBlockMeta(BLOCK_NEW_GRAVEL, BlockNewGravel.EnumType.NAMES), "new_gravel", BlockNewGravel.EnumType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_PAVING_STONE, new ItemBlockMeta(BLOCK_PAVING_STONE, BlockPavingStone.EnumType.NAMES), "paving_stone", BlockPavingStone.EnumType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_SHADE_GLASS, "shade_glass");
		PSV2Core.REGISTER.registerBlock(BLOCK_HARDENED_SHADE_GLASS, "hardened_shade_glass");
		PSV2Core.REGISTER.registerBlock(BLOCK_COMPRESSED_BOOKSHELF, new ItemBlockCompressedBookshelf(), "compressed_bookshelf", ItemBlockCompressedBookshelf.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_THREE_PILLARS, new ItemBlockThreePillars(), "three_pillars", ItemBlockThreePillars.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_EARTHEN_PIPE, "earthen_pipe");
		PSV2Core.REGISTER.registerBlock(BLOCK_HARDENED_SHADE_GLASS_DOOR, new ItemBlockDoor(BLOCK_HARDENED_SHADE_GLASS_DOOR), "hardened_shade_glass_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_LOCKABLE_DOOR, new ItemBlockDoor(BLOCK_LOCKABLE_DOOR), "lockable_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_DUMMY_CABLE, new ItemBlockMeta(BLOCK_DUMMY_CABLE, BlockDummyCable.EnumType.NAMES), "dummy_cable", BlockDummyCable.EnumType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_POLISHED_STONE_COLORED, new ItemBlockMeta(BLOCK_POLISHED_STONE_COLORED, EnumColor.COLOR_WOOL), "polished_stone_colored", EnumColor.COLOR_WOOL);
		PSV2Core.REGISTER.registerBlock(BLOCK_LAMP, new ItemBlockMeta(BLOCK_LAMP, BlockLamp.MaterialType.NAMES), "lamp", BlockLamp.MaterialType.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_OAK_DOOR, new ItemBlockDoor(BLOCK_GLASS_OAK_DOOR), "glass_oak_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_SPRUCE_DOOR, new ItemBlockDoor(BLOCK_GLASS_SPRUCE_DOOR), "glass_spruce_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_BIRCH_DOOR, new ItemBlockDoor(BLOCK_GLASS_BIRCH_DOOR), "glass_birch_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_JUNGLE_DOOR, new ItemBlockDoor(BLOCK_GLASS_JUNGLE_DOOR), "glass_jungle_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_ACACIA_DOOR, new ItemBlockDoor(BLOCK_GLASS_ACACIA_DOOR), "glass_acacia_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_DARK_OAK_DOOR, new ItemBlockDoor(BLOCK_GLASS_DARK_OAK_DOOR), "glass_dark_oak_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_GLASS_IRON_DOOR, new ItemBlockDoor(BLOCK_GLASS_IRON_DOOR), "glass_iron_door");
		PSV2Core.REGISTER.registerBlock(BLOCK_CHROMA_KEY_BACK, new ItemBlockMeta(BLOCK_CHROMA_KEY_BACK, EnumColor.COLOR_WOOL), "chroma_key_back", EnumColor.COLOR_WOOL);
		PSV2Core.REGISTER.registerBlock(BLOCK_STONE_SLAB, (ItemBlock) new ItemSlab(BLOCK_STONE_SLAB, BLOCK_STONE_SLAB, BLOCK_DOUBLE_STONE_SLAB).setUnlocalizedName("stone_slab"), "stone_slab", BlockModStoneSlab.Type.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_DOUBLE_STONE_SLAB, (ItemBlock) new ItemBlockMeta(BLOCK_DOUBLE_STONE_SLAB, BlockModStoneSlab.Type.NAMES), "double_stone_slab", BlockModStoneSlab.Type.NAMES);
		PSV2Core.REGISTER.registerBlock(BLOCK_CROP_ONION, "crop_onion");
		PSV2Core.REGISTER.registerBlock(BLOCK_CROP_JAPANESE_RADISH, "crop_japanese_radish");
		PSV2Core.REGISTER.registerBlock(BLOCK_CROP_CABBAGE, "crop_cabbage");
		PSV2Core.REGISTER.registerBlock(BLOCK_CROP_STICKY_RICE, "crop_sticky_rice");
		PSV2Core.REGISTER.registerBlock(BLOCK_CROP_TOMATO, "crop_tomato");
		PSV2Core.REGISTER.registerBlock(BLOCK_CROP_EGGPLANT, "crop_eggplant");
		PSV2Core.REGISTER.registerBlock(BLOCK_VOID_TELEPORTER, new ItemBlockMeta(BLOCK_VOID_TELEPORTER, BlockVoidTeleporter.VoidType.NAMES), "void_teleporter", BlockVoidTeleporter.VoidType.NAMES);

//		PSV2Core.REGISTER.registerBlock(BLOCK_TEST, "test_block");


		String dh = "decorative_hazmat_";
		String sc = "safety_clothes_";
		String ia = "invincible_armor_";
		//Items Registry
		PSV2Core.REGISTER.registerItem(ITEM_TWEAKED_DYE, "tweaked_dye", ItemTweakedDye.NAMES);
		PSV2Core.REGISTER.registerItem(ITEM_MATERIAL, "material", ItemMaterial.NAMES);
		PSV2Core.REGISTER.registerItem(ITEM_INSTANT_FOOD, "instant_food");
		PSV2Core.REGISTER.registerItem(ITEM_RAW_MEAT_WITH_BONE, "meat_with_bone_raw");
		PSV2Core.REGISTER.registerItem(ITEM_COOKED_MEAT_WITH_BONE, "meat_with_bone_cooked");
		PSV2Core.REGISTER.registerItem(ITEM_SPINACH_CAN, "spinach_can");
		PSV2Core.REGISTER.registerItem(ITEM_SOLID_MILK, "solid_milk");
		PSV2Core.REGISTER.registerItem(ITEM_REGENERATE, "heart_of_regeneration");
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_DECORATIVE_HAZMAT_HELMET, dh + EnumArmorType.HELMET.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_DECORATIVE_HAZMAT_CHEST, dh + EnumArmorType.CHESTPLATE.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_DECORATIVE_HAZMAT_LEG, dh + EnumArmorType.LEGGINGS.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_DECORATIVE_HAZMAT_BOOTS, dh + EnumArmorType.BOOTS.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_SAFETY_MET, sc + EnumArmorType.HELMET.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_WORK_CLOTHES, sc + EnumArmorType.CHESTPLATE.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_SAFETY_BELT, sc + EnumArmorType.LEGGINGS.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_SAFETY_BOOTS, sc + EnumArmorType.BOOTS.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_LIFE_JACKET, "life_jacket");
		PSV2Core.REGISTER.registerItem(ITEM_INVINCIBLE_SWORD, "invincible_sword");
		PSV2Core.REGISTER.registerItem(ITEM_INVINCIBLE_BOW, "invincible_bow");
		PSV2Core.REGISTER.registerItem(ITEM_BEDROCK_CORE, "bedrock_core");
		PSV2Core.REGISTER.registerItem(ITEM_RANDOM_BOX, "random_box");
//		PSV2Core.REGISTER.registerItem(itemNewDye, "new_dye", EnumColor.getLength());
		PSV2Core.REGISTER.registerItem(ITEM_ZABUTON, "zabuton", 16);
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_INVINCIBLE_HELMET, ia + EnumArmorType.HELMET.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_INVINCIBLE_CHEST, ia + EnumArmorType.CHESTPLATE.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_INVINCIBLE_LEG, ia + EnumArmorType.LEGGINGS.getName());
		PSV2Core.REGISTER.registerItem(ITEM_ARMOR_INVINCIBLE_BOOTS, ia + EnumArmorType.BOOTS.getName());
		PSV2Core.REGISTER.registerItem(ITEM_DAMAGE_FOOD_MINI, "damage_food_mini");
		PSV2Core.REGISTER.registerItem(ITEM_DAMAGE_FOOD_NORMAL, "damage_food_normal");
		PSV2Core.REGISTER.registerItem(ITEM_DAMAGE_FOOD_BIG, "damage_food_big");
		PSV2Core.REGISTER.registerItem(ITEM_DAMAGE_FOOD_HUGE, "damage_food_huge");
		PSV2Core.REGISTER.registerItem(ITEM_SEED_ONION, "seed_onion");
		PSV2Core.REGISTER.registerItem(ITEM_SEED_JAPANESE_RADISH, "seed_japanese_radish");
		PSV2Core.REGISTER.registerItem(ITEM_SEED_CABBAGE, "seed_cabbage");
		PSV2Core.REGISTER.registerItem(ITEM_SEED_TOMATO, "seed_tomato");
		PSV2Core.REGISTER.registerItem(ITEM_SEED_EGGPLANT, "seed_eggplant");
		PSV2Core.REGISTER.registerItem(ITEM_ONION, "onion");
		PSV2Core.REGISTER.registerItem(ITEM_JAPANESE_RADISH, "japanese_radish");
		PSV2Core.REGISTER.registerItem(ITEM_CABBAGE, "cabbage");
		PSV2Core.REGISTER.registerItem(ITEM_STICKY_RICE, "sticky_rice");
		PSV2Core.REGISTER.registerItem(ITEM_TOMATO, "tomato");
		PSV2Core.REGISTER.registerItem(ITEM_EGGPLANT, "eggplant");

		//Entity Registry
		PSV2Core.REGISTER.registerModEntity(EntityZabuton.class, "zabuton", LibEntityId.ENTITY_ID_ZABUTON, PSV2Core.INSTANCE, 80, 3, true);

		//Tile Entity Registry
		GameRegistry.registerTileEntity(TileEntitySlimeRemovalTorch.class, LibTEId.ID_SLIME_REMOVAL_TORCH);

		//Dispenser Registry
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ITEM_ZABUTON, new DispenseZabutonHandler());

		//Dimension Registry
		DimensionRegistry.register();

		BlockLattice.connectables.add(BLOCK_LATTICE_OAK);
		BlockLattice.connectables.add(BLOCK_LATTICE_SPRUCE);
		BlockLattice.connectables.add(BLOCK_LATTICE_BIRCH);
		BlockLattice.connectables.add(BLOCK_LATTICE_JUNGLE);
		BlockLattice.connectables.add(BLOCK_LATTICE_ACACIA);
		BlockLattice.connectables.add(BLOCK_LATTICE_DARK_OAK);
		BlockLattice.connectables.add(BLOCK_LATTICE_NETHER_BRICK);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_OAK);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_SPRUCE);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_BIRCH);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_JUNGLE);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_ACACIA);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_DARK_OAK);
		BlockLattice.connectables.add(BLOCK_FENCE_SLAB_NETHER_BRICK);

		//硬度とか変更
		Blocks.BEDROCK.setHardness(50.0F).setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_DIAMOND);
		Blocks.END_PORTAL_FRAME.setHardness(50.0F).setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_DIAMOND);
		Blocks.LEAVES.setHarvestLevel(LibTool.TOOL_CLASS_AXE, LibTool.LEVEL_WOOD);
		Blocks.LEAVES2.setHarvestLevel(LibTool.TOOL_CLASS_AXE, LibTool.LEVEL_WOOD);
		Blocks.WATERLILY.setHardness(0.2F);
		Blocks.COBBLESTONE.setHardness(1.5F);
		Blocks.MOSSY_COBBLESTONE.setHardness(1.5F);
		Blocks.COBBLESTONE_WALL.setHardness(1.5F);
		Blocks.DOUBLE_STONE_SLAB.setHardness(1.5F);
		Blocks.DOUBLE_STONE_SLAB2.setHardness(1.5F);
		Blocks.STONE_SLAB.setHardness(1.5F);
		Blocks.STONE_SLAB2.setHardness(1.5F);

		//クリエイティブタブに登録
		Blocks.COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
		Blocks.CHAIN_COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
		Blocks.REPEATING_COMMAND_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
		Blocks.MOB_SPAWNER.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.DRAGON_EGG.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.PORTAL.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.END_PORTAL.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.END_GATEWAY.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.WATER.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.LAVA.setCreativeTab(CreativeTabs.DECORATIONS);
		Blocks.GRASS_PATH.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		Blocks.STRUCTURE_BLOCK.setCreativeTab(CreativeTabs.REDSTONE);
		Blocks.STRUCTURE_VOID.setCreativeTab(CreativeTabs.REDSTONE);
		Blocks.BARRIER.setCreativeTab(CreativeTabs.REDSTONE);
		Blocks.FARMLAND.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		//適正ツール変更
		Blocks.GLASS.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		Blocks.GLASS_PANE.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		Blocks.STAINED_GLASS.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		Blocks.STAINED_GLASS_PANE.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		Blocks.PISTON.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);
		Blocks.STICKY_PISTON.setHarvestLevel(LibTool.TOOL_CLASS_PICKAXE, LibTool.LEVEL_WOOD);

		//スタックサイズ変更
		Items.EGG.setMaxStackSize(64);
		Items.BUCKET.setMaxStackSize(64);
		Items.SIGN.setMaxStackSize(64);
		Items.ENDER_PEARL.setMaxStackSize(64);
		Items.SADDLE.setMaxStackSize(64);
		Items.DIAMOND_HORSE_ARMOR.setMaxStackSize(64);
		Items.GOLDEN_HORSE_ARMOR.setMaxStackSize(64);
		Items.IRON_HORSE_ARMOR.setMaxStackSize(64);
		Items.MINECART.setMaxStackSize(64);
		Items.CHEST_MINECART.setMaxStackSize(64);
		Items.COMMAND_BLOCK_MINECART.setMaxStackSize(64);
		Items.FURNACE_MINECART.setMaxStackSize(64);
		Items.HOPPER_MINECART.setMaxStackSize(64);
		Items.TNT_MINECART.setMaxStackSize(64);
		Items.BOAT.setMaxStackSize(64);
		Items.CAKE.setMaxStackSize(64);
		Items.BED.setMaxStackSize(64);
		Items.EXPERIENCE_BOTTLE.setMaxStackSize(64);
		Items.SNOWBALL.setMaxStackSize(64);

		//スポーンエッグにmobを新しく追加
		//TODO ウィザスケのスポーンエッグ
		Utils.addSpawnEgg(EntityGiantZombie.class, 0x00AFAF, 0x799C65);//Giant
		Utils.addSpawnEgg(EntityDragon.class, 0x161616, 0x00003A);//Ender Dragon
		Utils.addSpawnEgg(EntityWither.class, 0x141414, 0xC1C1C1);//Wither
		Utils.addSpawnEgg(EntitySnowman.class, 0xEEFFFF, 0xE3901D);//Snow Golem
		Utils.addSpawnEgg(EntityIronGolem.class, 0xE0E0E0, 0xCACACA);//Iron Golem

		OreDictionary.registerOre("blockPumpkin", Blocks.PUMPKIN);
		OreDictionary.registerOre("blockPumpkin", Blocks.LIT_PUMPKIN);
		OreDictionary.registerOre("torch", Blocks.TORCH);
		OreDictionary.registerOre("blockWool", new ItemStack(Blocks.WOOL, 1, WILDCARD_VALUE));
		OreDictionary.registerOre("wool", new ItemStack(Blocks.WOOL, 1, WILDCARD_VALUE));
		OreDictionary.registerOre("cloth", new ItemStack(Blocks.WOOL, 1, WILDCARD_VALUE));
		OreDictionary.registerOre("mushroom", Blocks.BROWN_MUSHROOM);
		OreDictionary.registerOre("mushroomBrown", Blocks.BROWN_MUSHROOM);
		OreDictionary.registerOre("cropMushroom", Blocks.BROWN_MUSHROOM);
		OreDictionary.registerOre("cropMushroomBrown", Blocks.BROWN_MUSHROOM);
		OreDictionary.registerOre("mushroom", Blocks.RED_MUSHROOM);
		OreDictionary.registerOre("mushroomRed", Blocks.RED_MUSHROOM);
		OreDictionary.registerOre("cropMushroom", Blocks.RED_MUSHROOM);
		OreDictionary.registerOre("cropMushroomRed", Blocks.RED_MUSHROOM);
		for(int i = 0; i < 16; i++)
		{
			OreDictionary.registerOre(EnumColor.byMeta(i).getWoolOreName(), new ItemStack(Blocks.WOOL, 1, i));
		}
		OreDictionary.registerOre("blockTallgrass", new ItemStack(Blocks.TALLGRASS, 1, WILDCARD_VALUE));//普通の草
		OreDictionary.registerOre("blockTallgrass", new ItemStack(Blocks.DOUBLE_PLANT, 1, 2));//背の高い草
		OreDictionary.registerOre("blockTallgrass", new ItemStack(Blocks.DOUBLE_PLANT, 1, 3));//背の高いシダ
		OreDictionary.registerOre("coal", new ItemStack(Items.COAL, 1, 0));
		OreDictionary.registerOre("itemCoal", new ItemStack(Items.COAL, 1, 0));
		OreDictionary.registerOre("charcoal", new ItemStack(Items.COAL, 1, 1));
		OreDictionary.registerOre("itemCharcoal", new ItemStack(Items.COAL, 1, 1));

		//Mod Blocks
		OreDictionary.registerOre("stone", BLOCK_POLISHED_STONE);
		OreDictionary.registerOre("stonePolished", BLOCK_POLISHED_STONE);

		//Mod Items
		OreDictionary.registerOre("dye", new ItemStack(ITEM_TWEAKED_DYE, 1, WILDCARD_VALUE));
		OreDictionary.registerOre("dyeBlack", new ItemStack(ITEM_TWEAKED_DYE, 1, 0));
		OreDictionary.registerOre("dyeBrown", new ItemStack(ITEM_TWEAKED_DYE, 1, 1));
		OreDictionary.registerOre("dyeBlue", new ItemStack(ITEM_TWEAKED_DYE, 1, 2));
		OreDictionary.registerOre("dyeWhite", new ItemStack(ITEM_TWEAKED_DYE, 1, 3));
		OreDictionary.registerOre("dyeYellow", new ItemStack(ITEM_TWEAKED_DYE, 1, 4));
		OreDictionary.registerOre("dyeRed", new ItemStack(ITEM_TWEAKED_DYE, 1, 5));
		OreDictionary.registerOre("dustCadmium", new ItemStack(ITEM_TWEAKED_DYE, 1, 4));
		OreDictionary.registerOre("cadmium", new ItemStack(ITEM_TWEAKED_DYE, 1, 4));
		OreDictionary.registerOre("crystalCinnabar", new ItemStack(ITEM_TWEAKED_DYE, 1, 5));
		OreDictionary.registerOre("cinnabar", new ItemStack(ITEM_TWEAKED_DYE, 1, 5));
		OreDictionary.registerOre("ingotEnderSteel", new ItemStack(ITEM_MATERIAL, 1, 0));
		OreDictionary.registerOre("blockCharcoal", new ItemStack(BLOCK_METAL, 1, BlockMetal.MaterialType.CHARCOAL.getMeta()));
		OreDictionary.registerOre("gemQuartzBlack", new ItemStack(ITEM_MATERIAL, 1, 18));
		OreDictionary.registerOre("gemQuartzPurple", new ItemStack(ITEM_MATERIAL, 1, 19));

		if(!ModConfig.fenceBlock.isEmpty())
		{
			for(String loc : ModConfig.fenceBlock)
			{
				Block block = Block.REGISTRY.getObject(new ResourceLocation(loc));
				EventHelper.fences.add(block);
			}
		}

		//Packet
		PacketHandler.init();
	}

	public static void init()
	{
		/**
		 * レシピ記述方統一のための留意点
		 * 		基本アイテムの頭文字を使う 使う文字はアイテムなら小文字、ブロックなら大文字
		 * 命名法
		 * 		題名みたいな感じにわかりやすく
		 * 		基本toでinputとoutputをつなぐ
		 * 		input(output)が複数ある場合は_でつなぐ
		 */
		//レシピ
		//バニラサポート
		RecipeUtils.addRecipe("core_to_bedrock", new ItemStack(Blocks.BEDROCK, 8), "PPP", "PbP", "PPP", 'P', new ItemStack(BLOCK_POLISHED_STONE, 1, 0), 'b', new ItemStack(ITEM_BEDROCK_CORE, 1, WILDCARD_VALUE));
		RecipeUtils.addShapelessRecipe("glowstone_to_dusts", new ItemStack(Items.GLOWSTONE_DUST, 4), Blocks.GLOWSTONE);
		RecipeUtils.addShapelessRecipe("quartz_block_to_items",new ItemStack(Items.QUARTZ, 4), Blocks.QUARTZ_BLOCK);
		RecipeUtils.addShapelessRecipe("all_glasses_to_normal_glass", new ItemStack(Blocks.GLASS, 1), "blockGlass");
		RecipeUtils.addShapelessRecipe("all_glass_panes_to_normal_glass_pane", new ItemStack(Blocks.GLASS_PANE, 1), "paneGlass");
		RecipeUtils.addShapelessRecipe("all_wools_to_white_wool", new ItemStack(Blocks.WOOL, 1, 0), "blockWool");
		RecipeUtils.addRecipe("strings_to_webs", new ItemStack(Blocks.WEB, 5), "s s", " s ", "s s", 's', Items.STRING);
		RecipeUtils.addShapelessRecipe("wool_to_strings", new ItemStack(Items.STRING, 4), "blockWool");
		RecipeUtils.addShapelessRecipe("brown_mushrooms_to_mushroom_stew", new ItemStack(Items.MUSHROOM_STEW, 1), Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.BROWN_MUSHROOM, Items.BOWL);
		RecipeUtils.addShapelessRecipe("red_mushrooms_to_mushroom_stew", new ItemStack(Items.MUSHROOM_STEW, 1), Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM, Blocks.RED_MUSHROOM, Items.BOWL);
		RecipeUtils.addRecipe("lapis_book_to_normal_enchanted_book", new ItemStack(Items.ENCHANTED_BOOK, 1), "lll", "lbl", "lll", 'l', new ItemStack(Items.DYE, 1, EnumColor.BLUE.getDyeNumber()), 'b', Items.BOOK);
		RecipeUtils.addRecipe("wither_to_egg", Utils.getSpawnEggItemStack(EntityWither.class, 1), "sss", "SSS", " S ", 's', new ItemStack(Items.SKULL, 1, 1), 'S', Blocks.SOUL_SAND);
		RecipeUtils.addRecipe("snow_golem_to_egg", Utils.getSpawnEggItemStack(EntitySnowman.class, 1), "P", "S", "S", 'P', "blockPumpkin", 'S', Blocks.SNOW);
		RecipeUtils.addRecipe("iron_golem_to_egg", Utils.getSpawnEggItemStack(EntityIronGolem.class, 1), " P ", "III", " I ", 'P', "blockPumpkin", 'I', Blocks.IRON_BLOCK);
		RecipeUtils.addRecipe("easier_dispenser", new ItemStack(Blocks.DISPENSER, 1), "CCC", "CbC", "CrC", 'C', "cobblestone", 'b', new ItemStack(Items.BOW, 1, WILDCARD_VALUE), 'r', "dustRedstone");
		RecipeUtils.addShapelessRecipe("dropper_bow_to_dispenser", new ItemStack(Blocks.DISPENSER, 1), Blocks.DROPPER, new ItemStack(Items.BOW, 1, WILDCARD_VALUE));
		RecipeUtils.addRecipe("easier_carrot_stick", new ItemStack(Items.CARROT_ON_A_STICK, 1), "f ", " c", 'f', new ItemStack(Items.FISHING_ROD, 1, WILDCARD_VALUE), 'c', Items.CARROT);
		RecipeUtils.addRecipe("lever_to_piston", new ItemStack(Blocks.PISTON, 1), "PPP", "CiC", "ClC", 'P', "plankWood", 'C', "cobblestone", 'i', "ingotIron", 'l', Blocks.LEVER);
		RecipeUtils.addRecipe("ore_dict_beacon", new ItemStack(Blocks.BEACON, 1), "GGG", "GnG", "OOO", 'G', "blockGlass", 'n', Items.NETHER_STAR, 'O', "obsidian");
		RecipeUtils.addShapelessRecipe("shapeless_lit_pumpkin", new ItemStack(Blocks.LIT_PUMPKIN, 1), Blocks.PUMPKIN, Blocks.TORCH);
		RecipeUtils.addShapelessRecipe("ore_dict_shapeless_lit_pumpkin", new ItemStack(Blocks.LIT_PUMPKIN, 1), "blockPumpkin", "torch");
		RecipeUtils.addShapelessRecipe("snow_block_to_balls", new ItemStack(Items.SNOWBALL, 4), Blocks.SNOW);
		RecipeUtils.addRecipe("golden_apples_to_enchanted", new ItemStack(Items.GOLDEN_APPLE, 1, 1), "ggg", "ggg", "ggg", 'g', Items.GOLDEN_APPLE);
		RecipeUtils.addShapelessRecipe("wall_vine_to_mossy_wall", new ItemStack(Blocks.COBBLESTONE_WALL, 1, 1), new ItemStack(Blocks.COBBLESTONE_WALL, 1, 0), Blocks.VINE);
		RecipeUtils.addShapelessRecipe("tallgrass_dirt_to_grass_", new ItemStack(Blocks.GRASS, 1), "blockTallgrass", Blocks.DIRT);
		RecipeUtils.addShapelessRecipe("bottle_ender_eye_to_exp_bottle", new ItemStack(Items.EXPERIENCE_BOTTLE, 3), Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.GLASS_BOTTLE, Items.ENDER_EYE);
		RecipeUtils.addRecipe("blaze_rod_powder_to_torch", new ItemStack(Blocks.TORCH, 32), "p", "r", 'p', Items.BLAZE_POWDER, 'r', Items.BLAZE_ROD);
		RecipeUtils.addRecipe("blaze_rod_coals_to_torch", new ItemStack(Blocks.TORCH, 16), "c", "r", 'c', Items.COAL, 'r', Items.BLAZE_ROD);
		RecipeUtils.addRecipe("blaze_rod_coal_to_torch", new ItemStack(Blocks.TORCH, 16), "c", "r", 'c', "coal", 'r', Items.BLAZE_ROD);
		RecipeUtils.addRecipe("blaze_rod_charcoal_to_torch", new ItemStack(Blocks.TORCH, 16), "c", "r", 'c', "charcoal", 'r', Items.BLAZE_ROD);
		RecipeUtils.addRecipe("cobble_ender_pearl_to_end_stone", new ItemStack(Blocks.END_STONE, 8), "CCC", "CeC", "CCC", 'C', "cobblestone", 'e', Items.ENDER_PEARL);
		RecipeUtils.addRecipe("end_things_to_end_portal_frame", new ItemStack(Blocks.END_PORTAL_FRAME, 1), " e ", "ppp", "EEE", 'e', Items.ENDER_EYE, 'p', Items.ENDER_PEARL, 'E', Blocks.END_STONE);
		RecipeUtils.addRecipe("tallgrass_to_waterlily", new ItemStack(Blocks.WATERLILY, 2), "T T", "TTT", " T ", 'T', "blockTallgrass");
		RecipeUtils.addShapelessRecipe("disassembly_melon", new ItemStack(Items.MELON, 9), Blocks.MELON_BLOCK);
		//苗木
		RecipeUtils.addShapelessRecipe("brown_to_spruce_sapling", new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Items.DYE, 1, EnumColor.BROWN.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("white_to_birch_sapling", new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("pink_to_jungle_sapling", new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Items.DYE, 1, EnumColor.PINK.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("red_to_acacia_sapling", new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Items.DYE, 1, EnumColor.RED.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("black_to_dark_oak_sapling", new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(Blocks.SAPLING, 1, 0), new ItemStack(Items.DYE, 1, EnumColor.BLACK.getDyeNumber()));
		//赤砂
		RecipeUtils.addShapelessRecipe("red_dye_to_red_sand", new ItemStack(Blocks.SAND, 1, 1), new ItemStack(Blocks.SAND, 1, 0), new ItemStack(Items.DYE, 1, EnumColor.RED.getDyeNumber()));
		//花
		RecipeUtils.addShapelessRecipe("poppy_yellow_to_sun_flower", new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Blocks.RED_FLOWER, 1, 0), EnumColor.YELLOW.getDyeOreName());
		RecipeUtils.addShapelessRecipe("poppy_magenta_to_syringa", new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Blocks.RED_FLOWER, 1, 0), EnumColor.MAGENTA.getDyeOreName());
		RecipeUtils.addShapelessRecipe("poppy_red_to_rose", new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Blocks.RED_FLOWER, 1, 0), EnumColor.RED.getDyeOreName());
		RecipeUtils.addShapelessRecipe("poppy_pink_to_paeonia", new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Blocks.RED_FLOWER, 1, 0), EnumColor.PINK.getDyeOreName());
		//金床壊れかけ
		RecipeUtils.addRecipe("fewer_iron_to_chipped_anvil", new ItemStack(Blocks.ANVIL, 1, 1), " II", " i ", "iii", 'I', Blocks.IRON_BLOCK, 'i', Items.IRON_INGOT);
		RecipeUtils.addRecipe("fewest_iron_to_damaged_anvil", new ItemStack(Blocks.ANVIL, 1, 2), "  I", " i ", "iii", 'I', Blocks.IRON_BLOCK, 'i', Items.IRON_INGOT);
		//ショートカットレシピ
		//各種原木x3 → 各種半ブロックx24
		for(int i = 0; i < 6; i++)
		{
			RecipeUtils.addRecipe("log_" + EnumPlanks.byMeta(i).getName() + "_to_slab_" + EnumPlanks.byMeta(i).getName(), new ItemStack(Blocks.WOODEN_SLAB, 24, i), "LLL", 'L', EnumPlanks.getLogStackByPlanksMeta(1, i));
		}
		//原木x8 → チェストx4
		RecipeUtils.addRecipe("logs_to_chests", new ItemStack(Blocks.CHEST, 4), "LLL", "L L", "LLL", 'L', "logWood");
		//木材x7 → はしごx6
		RecipeUtils.addRecipe("planks_to_ladders", new ItemStack(Blocks.LADDER, 6), "L L", "LLL", "L L", 'L', "plankWood");
		//原木x7 → はしごx24
		RecipeUtils.addRecipe("logs_ladders", new ItemStack(Blocks.LADDER, 24), "L L", "LLL", "L L", 'L', "logWood");
		//原木x6 → トラップドアx8
		RecipeUtils.addRecipe("logs_to_trapdoors", new ItemStack(Blocks.TRAPDOOR, 8), "   ", "LLL", "LLL", 'L', "logWood");
		//原木x2 → 棒x16
		RecipeUtils.addRecipe("logs_to_sticks", new ItemStack(Items.STICK, 16), "L", "L", 'L', "logWood");
		//原木x3 → ボウルx16
		RecipeUtils.addRecipe("logs_to_bowls", new ItemStack(Items.BOWL, 16), "L L", " L ", 'L', "logWood");
		//サトウキビx3, 革 → 本
		RecipeUtils.addShapelessRecipe("reeds_to_book", new ItemStack(Items.BOOK), Items.REEDS, Items.REEDS, Items.REEDS, Items.LEATHER);
		//赤石x2, 光石x2 → レッドストーンランプ
		RecipeUtils.addShapelessRecipe("red_glow_dusts_to_redstone_lump", new ItemStack(Blocks.REDSTONE_LAMP), "dustRedstone", "dustRedstone", "dustRedstone", "dustRedstone", "dustGlowstone", "dustGlowstone", "dustGlowstone", "dustGlowstone");
		//階段
//		Block[] stairs = new Block[]
//		{ Blocks.OAK_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.BIRCH_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS };
//		for(int i = 0; i < 6; i++)
//		{
//			Utils.addMiniRecipeStairs(stairs[i], new ItemStack(Blocks.PLANKS, 1, i));
//		}
//		Utils.addMiniRecipeStairs(Blocks.STONE_STAIRS, new ItemStack(Blocks.COBBLESTONE));
//		Utils.addMiniRecipeStairs(Blocks.BRICK_STAIRS, new ItemStack(Blocks.BRICK_BLOCK));
//		Utils.addMiniRecipeStairs(Blocks.STONE_BRICK_STAIRS, new ItemStack(Blocks.STONEBRICK, 1, W_V));
//		Utils.addMiniRecipeStairs(Blocks.NETHER_BRICK_STAIRS, new ItemStack(Blocks.NETHER_BRICK));
//		Utils.addMiniRecipeStairs(Blocks.SANDSTONE_STAIRS, new ItemStack(Blocks.SANDSTONE));
//		Utils.addMiniRecipeStairs(Blocks.RED_SANDSTONE_STAIRS, new ItemStack(Blocks.RED_SANDSTONE));
//		Utils.addMiniRecipeStairs(Blocks.QUARTZ_STAIRS, new ItemStack(Blocks.QUARTZ_BLOCK));
		//砂利から火打石落とさないようにしてるから砂利一つクラフトで火打石一つ
		RecipeUtils.addShapelessRecipe("gravel_to_flint", new ItemStack(Items.FLINT), Blocks.GRAVEL);

		//Mod Blocks
		RecipeUtils.addRecipe("stones_to_polished_stone", new ItemStack(BLOCK_POLISHED_STONE, 2, 0), "S ", " S", 'S', Blocks.STONE);
		RecipeUtils.addShapelessRecipe("lit_off_polished_stone", new ItemStack(BLOCK_POLISHED_STONE, 1, 1), new ItemStack(BLOCK_POLISHED_STONE, 1, 0));
		for(int i = 0; i < 16; i++)
		{
			RecipeUtils.addRecipe("light_" + EnumColor.byMeta(i).getName(), new ItemStack(BLOCK_COLORED_LIGHT, 8, i), "GdG", "GgG", "GiG", 'G', "blockGlass", 'd', EnumColor.byMeta(i).getDyeOreName(), 'g', Blocks.GLOWSTONE, 'i', Items.IRON_INGOT);
		}
//		RecipeUtils.addRecipeSmallStairs("black_quartz", BLOCK_SS_BLACK_QUARTZ_BLOCK, new ItemStack(BLOCK_BLACK_QUARTZ, 1, BlockQuartz.EnumType.DEFAULT.getMetadata()));
		RecipeUtils.addRecipe("glowstone_generator", new ItemStack(BLOCK_GLOWSTONE_GENERATOR, 1), "dld", "SSS", "dSd", 'd', Items.DIAMOND, 'l', new ItemStack(Items.DYE, 1, EnumColor.BLUE.getDyeNumber()), 'S', Blocks.STONE);
		RecipeUtils.addRecipeVendingMachine("normal", BLOCK_VM_NORMAL, EnumColor.WHITE);
		RecipeUtils.addRecipeVendingMachine("coca_cola", BLOCK_VM_COCA_COLA, EnumColor.RED);
		RecipeUtils.addRecipeVendingMachine("suntory", BLOCK_VM_SUNTORY, EnumColor.BLUE);
		RecipeUtils.addRecipeVendingMachine("dy_do", BLOCK_VM_DY_DO, EnumColor.ORANGE);
		RecipeUtils.addRecipeVendingMachine("ito_en", BLOCK_VM_ITO_EN, EnumColor.GREEN);
		RecipeUtils.addRecipeVendingMachine("suica", BLOCK_VM_SUICA, EnumColor.LIME);
		RecipeUtils.addRecipeVendingMachine("max_coffee", BLOCK_VM_MAX_COFFEE, EnumColor.YELLOW);
		RecipeUtils.addRecipe("locker", new ItemStack(BLOCK_LOCKER, 1), "gl", "ii", "ii", 'g', EnumColor.GRAY.getDyeOreName(), 'l', EnumColor.LIGHT_GRAY.getDyeOreName(), 'i', "ingotIron");
		RecipeUtils.addShapelessRecipe("yellow_line", new ItemStack(BLOCK_YELLOW_LINE, 1), "stone", EnumColor.YELLOW.getDyeOreName());
		RecipeUtils.addRecipe("dummy_solar", new ItemStack(BLOCK_METAL, 4, BlockMetal.MaterialType.DUMMY_SOLAR.getMeta()), "bGb", "GbG", "did", 'b', EnumColor.BLUE.getDyeOreName(), 'G', "blockGlass", 'd', EnumColor.BLACK.getDyeOreName(), 'i', "ingotIron");
		RecipeUtils.addRecipe("block_of_charcoal", new ItemStack(BLOCK_METAL, 1, BlockMetal.MaterialType.CHARCOAL.getMeta()), "ccc", "ccc", "ccc", 'c', new ItemStack(Items.COAL, 1, 1));
		RecipeUtils.addShapelessRecipe("invisible_button", new ItemStack(BLOCK_INVISIBLE_BUTTON, 1), "ingotEnderSteel", "dustRedstone");
		RecipeUtils.addShapelessRecipe("rusty_rail_1", new ItemStack(BLOCK_RUSTY_RAIL1, 1), Blocks.RAIL, Items.WATER_BUCKET);
		RecipeUtils.addShapelessRecipe("rusty_rail_2", new ItemStack(BLOCK_RUSTY_RAIL2, 1), BLOCK_RUSTY_RAIL1, Items.WATER_BUCKET);
		RecipeUtils.addShapelessRecipe("rusty_rail_3", new ItemStack(BLOCK_RUSTY_RAIL3, 1), BLOCK_RUSTY_RAIL2, Items.WATER_BUCKET);
		RecipeUtils.addShapelessRecipe("rusty_wood", new ItemStack(BLOCK_RUSTY_WOOD, 1), BLOCK_RUSTY_RAIL3, Items.WATER_BUCKET);
		RecipeUtils.addRecipe("slab_rail", new ItemStack(BLOCK_SLAB_RAIL, 16), "i i", "iPi", "i i", 'i', "ingotIron", 'P', new ItemStack(BLOCK_PAVING_STONE, 1, BlockPavingStone.EnumType.NORMAL.getMeta()));
		RecipeUtils.addRecipeWall("brick", BLOCK_BRICK_WALL, new ItemStack(Blocks.BRICK_BLOCK));
		RecipeUtils.addRecipeWall("granite", BLOCK_GRANITE_WALL, new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.GRANITE.getMetadata()));
		RecipeUtils.addRecipeWall("smooth_granite", BLOCK_SMOOTH_GRANITE_WALL, new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.GRANITE_SMOOTH.getMetadata()));
		RecipeUtils.addRecipeWall("diorite", BLOCK_DIORITE_WALL, new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.DIORITE.getMetadata()));
		RecipeUtils.addRecipeWall("smooth_diorite", BLOCK_SMOOTH_DIORITE_WALL, new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.DIORITE_SMOOTH.getMetadata()));
		RecipeUtils.addRecipeWall("andesite", BLOCK_ANDESITE_WALL, new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.ANDESITE.getMetadata()));
		RecipeUtils.addRecipeWall("smooth_andesite", BLOCK_SMOOTH_ANDESITE_WALL, new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.ANDESITE_SMOOTH.getMetadata()));
		RecipeUtils.addRecipeFenceSlab(BLOCK_FENCE_SLAB_OAK, EnumPlanks.OAK);
		RecipeUtils.addRecipeFenceSlab(BLOCK_FENCE_SLAB_SPRUCE, EnumPlanks.SPRUCE);
		RecipeUtils.addRecipeFenceSlab(BLOCK_FENCE_SLAB_BIRCH, EnumPlanks.BIRCH);
		RecipeUtils.addRecipeFenceSlab(BLOCK_FENCE_SLAB_JUNGLE, EnumPlanks.JUNGLE);
		RecipeUtils.addRecipeFenceSlab(BLOCK_FENCE_SLAB_ACACIA, EnumPlanks.ACACIA);
		RecipeUtils.addRecipeFenceSlab(BLOCK_FENCE_SLAB_DARK_OAK, EnumPlanks.DARK_OAK);
		RecipeUtils.addRecipeFenceSlab("nether_brick", BLOCK_FENCE_SLAB_NETHER_BRICK, new ItemStack(Blocks.NETHER_BRICK));
		RecipeUtils.addRecipeLatticePlanks(BLOCK_LATTICE_OAK, EnumPlanks.OAK);
		RecipeUtils.addRecipeLatticePlanks(BLOCK_LATTICE_SPRUCE, EnumPlanks.SPRUCE);
		RecipeUtils.addRecipeLatticePlanks(BLOCK_LATTICE_BIRCH, EnumPlanks.BIRCH);
		RecipeUtils.addRecipeLatticePlanks(BLOCK_LATTICE_JUNGLE, EnumPlanks.JUNGLE);
		RecipeUtils.addRecipeLatticePlanks(BLOCK_LATTICE_ACACIA, EnumPlanks.ACACIA);
		RecipeUtils.addRecipeLatticePlanks(BLOCK_LATTICE_DARK_OAK, EnumPlanks.DARK_OAK);
		RecipeUtils.addRecipe("lattice_nether_brick", new ItemStack(BLOCK_LATTICE_NETHER_BRICK, 8), "NN", "NN", "NN", 'N', Blocks.NETHER_BRICK);
		RecipeUtils.addRecipe("fluorescent_light_dark", new ItemStack(BLOCK_FL_DARK, 4), "GgG", 'G', "blockGlass", 'g', "dustGlowstone");
		RecipeUtils.addRecipe("fluorescent_light_bright", new ItemStack(BLOCK_FL_BRIGHT, 4), " g ", "GgG", 'G', "blockGlass", 'g', "dustGlowstone");
		RecipeUtils.addRecipe("fluorescent_light_brighter", new ItemStack(BLOCK_FL_BRIGHTER, 4), " g ", "GgG", " g ", 'G', "blockGlass", 'g', "dustGlowstone");
		RecipeUtils.addShapelessRecipe("shade_glass", new ItemStack(BLOCK_SHADE_GLASS, 1), "blockGlass", EnumColor.BLACK.getDyeOreName());
		RecipeUtils.addRecipe("hardened_shade_glass", new ItemStack(BLOCK_HARDENED_SHADE_GLASS, 8), "SSS", "SBS", "SSS", 'S', BLOCK_SHADE_GLASS, 'B', Blocks.BEDROCK);
		RecipeUtils.addRecipe("hardened_shade_glass_door", new ItemStack(BLOCK_HARDENED_SHADE_GLASS_DOOR, 1), "HH", "HH", "HH", 'H', BLOCK_HARDENED_SHADE_GLASS);
		RecipeUtils.addShapelessRecipe("paving_gravel", new ItemStack(BLOCK_NEW_GRAVEL, 2, BlockNewGravel.EnumType.PAVING.getMeta()), new ItemStack(Blocks.DIRT, 1, BlockDirt.DirtType.DIRT.getMetadata()), Blocks.GRAVEL);
		RecipeUtils.addShapelessRecipe("paving_stone", new ItemStack(BLOCK_PAVING_STONE, 2, BlockPavingStone.EnumType.NORMAL.getMeta()), new ItemStack(Blocks.STONE, 1, BlockStone.EnumType.STONE.getMetadata()), new ItemStack(BLOCK_NEW_GRAVEL, 1, BlockNewGravel.EnumType.PAVING.getMeta()));
		RecipeUtils.addRecipe("powered_paving_stone", new ItemStack(BLOCK_PAVING_STONE, 1, BlockPavingStone.EnumType.POWERED.getMeta()), "r", "P", 'r', "dustRedstone", 'P', new ItemStack(BLOCK_PAVING_STONE, 1, BlockPavingStone.EnumType.NORMAL.getMeta()));
		RecipeUtils.addRecipe("three_pillars_normal", new ItemStack(BLOCK_THREE_PILLARS, 16, BlockThreePillars.EnumShape.NORMAL_UP_SINGLE.getMetaDummy()), "S S", "S S", 'S', "stone");
		RecipeUtils.addRecipe("three_pillars_black", new ItemStack(BLOCK_THREE_PILLARS, 16, BlockThreePillars.EnumShape.BLACK_UP_SINGLE.getMetaDummy()), "SbS", "SbS", 'S', "stone", 'b', EnumColor.BLACK.getDyeOreName());
		RecipeUtils.addRecipe("lockable_door", new ItemStack(BLOCK_LOCKABLE_DOOR, 1), "ii", "ik", "ii", 'i', "ingotIron", 'k', new ItemStack(ITEM_MATERIAL, 1, 17));
		RecipeUtils.addRecipeGlassDoorPlanks(BLOCK_GLASS_OAK_DOOR, EnumPlanks.OAK);
		RecipeUtils.addRecipeGlassDoorPlanks(BLOCK_GLASS_SPRUCE_DOOR, EnumPlanks.SPRUCE);
		RecipeUtils.addRecipeGlassDoorPlanks(BLOCK_GLASS_BIRCH_DOOR, EnumPlanks.BIRCH);
		RecipeUtils.addRecipeGlassDoorPlanks(BLOCK_GLASS_JUNGLE_DOOR, EnumPlanks.JUNGLE);
		RecipeUtils.addRecipeGlassDoorPlanks(BLOCK_GLASS_ACACIA_DOOR, EnumPlanks.ACACIA);
		RecipeUtils.addRecipeGlassDoorPlanks(BLOCK_GLASS_DARK_OAK_DOOR, EnumPlanks.DARK_OAK);
		RecipeUtils.addRecipe("glass_iron_door", new ItemStack(BLOCK_GLASS_IRON_DOOR, 3), "ii", "BB", "ii", 'i', "ingotIron", 'B', Blocks.IRON_BARS);
		for(int i = 0; i < EnumColor.getLength(); i++)
		{
			RecipeUtils.addShapelessRecipe("colored_polished_stone_" + EnumColor.byMeta(i).getName(), new ItemStack(BLOCK_POLISHED_STONE_COLORED, 1, i), EnumColor.byMeta(i).getDyeOreName(), new ItemStack(BLOCK_POLISHED_STONE, 1, 0));
			RecipeUtils.addRecipe("chroma_key_back_" + EnumColor.byMeta(i).getName(), new ItemStack(BLOCK_CHROMA_KEY_BACK, 9, i), "CCC", "CCC", "CCC", 'C', new ItemStack(Blocks.CONCRETE, 1, i));
		}
		RecipeUtils.addRecipe("iron_chain", new ItemStack(BLOCK_IRON_CHAIN, 16), "i", "i", "i", 'i', "ingotIron");
		RecipeUtils.addRecipe("anchor_bolt", new ItemStack(BLOCK_ANCHOR_BOLT, 1), "iI", 'i', "ingotIron", 'I', BLOCK_IRON_CHAIN);
		RecipeUtils.addRecipe("iron_plate_normal", new ItemStack(BLOCK_IRON_PLATE, 9, 0), " I ", "iii", 'I', BLOCK_IRON_CHAIN, 'i', "ingotIron");
		RecipeUtils.addShapelessRecipe("iron_plate_rusty", new ItemStack(BLOCK_IRON_PLATE, 8, 1), Items.WATER_BUCKET, new ItemStack(BLOCK_IRON_PLATE, 1, 0), new ItemStack(BLOCK_IRON_PLATE, 1, 0), new ItemStack(BLOCK_IRON_PLATE, 1, 0), new ItemStack(BLOCK_IRON_PLATE, 1, 0),
				new ItemStack(BLOCK_IRON_PLATE, 1, 0), new ItemStack(BLOCK_IRON_PLATE, 1, 0), new ItemStack(BLOCK_IRON_PLATE, 1, 0), new ItemStack(BLOCK_IRON_PLATE, 1, 0));
		RecipeUtils.addRecipe("iron_plate_stairs", new ItemStack(BLOCK_IRON_PLATE_STAIRS, 3), "P  ", " P ", "  P", 'P', new ItemStack(BLOCK_IRON_PLATE, 1, 0));
		RecipeUtils.addRecipe("iron_ladder", new ItemStack(BLOCK_IRON_LADDER, 7), "P P", "PPP", "P P", 'P', new ItemStack(BLOCK_IRON_PLATE, 1, 0));
		RecipeUtils.addRecipe("safety_fence", new ItemStack(BLOCK_SAFETY_FENCE, 6), "PPP", "PPP", 'P', new ItemStack(BLOCK_IRON_PLATE, 1, 0));
		RecipeUtils.addRecipe("stepladder", new ItemStack(BLOCK_STEPLADDER, 1), "iLi", "iLi", "iLi", 'i', "ingotIron", 'L', Blocks.LADDER);
		RecipeUtils.addRecipe("faucet_0", new ItemStack(BLOCK_FAUCET, 1, 0), "rri", "iii", "iri", 'r', "dustRedstone", 'i', "ingotIron");
		RecipeUtils.addRecipe("faucet_1", new ItemStack(BLOCK_FAUCET, 1, 1), "iii", "iri", "rri", 'r', "dustRedstone", 'i', "ingotIron");
		RecipeUtils.addRecipe("compressed_bookshelf_x1", new ItemStack(BLOCK_COMPRESSED_BOOKSHELF, 1, 0), "BBB", "B B", "BBB", 'B', Blocks.BOOKSHELF);
		RecipeUtils.addRecipe("compressed_bookshelf_x2", new ItemStack(BLOCK_COMPRESSED_BOOKSHELF, 1, 1), "BB", 'B', new ItemStack(BLOCK_COMPRESSED_BOOKSHELF, 1, 0));
		RecipeUtils.addRecipe("black_quartz_normal", new ItemStack(BLOCK_BLACK_QUARTZ, 1, 0), "bb", "bb", 'b', "gemQuartzBlack");
		RecipeUtils.addShapelessRecipe("disassembly_black_quartz", new ItemStack(ITEM_MATERIAL, 4, 18), new ItemStack(BLOCK_BLACK_QUARTZ, 1, 0));
		RecipeUtils.addRecipe("black_quartz_lines", new ItemStack(BLOCK_BLACK_QUARTZ, 2, 2), "B", "B", 'B', new ItemStack(BLOCK_BLACK_QUARTZ, 1, 0));

		//Mod Items
		RecipeUtils.addShapelessRecipe("tweaked_dye_blue", new ItemStack(ITEM_TWEAKED_DYE, 1, 2), new ItemStack(BLOCK_NEW_FLOWER, 1, 0));
		RecipeUtils.addShapelessRecipe("tweaked_dye_black", new ItemStack(ITEM_TWEAKED_DYE, 1, 0), new ItemStack(BLOCK_NEW_FLOWER, 1, 1));
		RecipeUtils.addShapelessRecipe("ink_to_tweaked_dye_black", new ItemStack(ITEM_TWEAKED_DYE, 4, 0), new ItemStack(Items.DYE, 1, EnumColor.BLACK.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("cacao_to_tweaked_dye_brown", new ItemStack(ITEM_TWEAKED_DYE, 16, 1), new ItemStack(Items.DYE, 1, EnumColor.BROWN.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("lapis_to_tweaked_dye_blue", new ItemStack(ITEM_TWEAKED_DYE, 32, 2), new ItemStack(Items.DYE, 1, EnumColor.BLUE.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("bone_meals_to_tweaked_dye_white", new ItemStack(ITEM_TWEAKED_DYE, 4, 3), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getDyeNumber()));
		RecipeUtils.addShapelessRecipe("red_green_to_brown", new ItemStack(ITEM_TWEAKED_DYE, 2, 1), EnumColor.RED.getDyeOreName(), EnumColor.GREEN.getDyeOreName());
		RecipeUtils.addShapelessRecipe("red_yellow_blue_to_brown", new ItemStack(ITEM_TWEAKED_DYE, 4, 1), EnumColor.RED.getDyeOreName(), EnumColor.RED.getDyeOreName(), EnumColor.BLUE.getDyeOreName(), EnumColor.YELLOW.getDyeOreName());
		for(int i = 0; i < EnumColor.getLength(); i++)
		{
			RecipeUtils.addRecipe("zabuton_" + EnumColor.byDyeDamage(i).getName(), new ItemStack(ITEM_ZABUTON, 1, i), "s ", "WW", 's', Items.STRING, 'W', new ItemStack(Blocks.WOOL, 1, EnumColor.byDyeDamage(i).getWoolNumber()));
		}
		RecipeUtils.addShapelessRecipe("empty_spawn_egg", new ItemStack(Items.SPAWN_EGG, 8), Items.IRON_INGOT, Items.IRON_INGOT, Items.REDSTONE);
		RecipeUtils.addRecipe("material_railway_rusty_x4", new ItemStack(ITEM_MATERIAL, 1, 15), "rr", "rr", 'r', new ItemStack(ITEM_MATERIAL, 1, 14));
		RecipeUtils.addRecipe("material_railway_rusty_x16", new ItemStack(ITEM_MATERIAL, 1, 16), "rr", "rr", 'r', new ItemStack(ITEM_MATERIAL, 1, 15));
		RecipeUtils.addRecipe("key", new ItemStack(ITEM_MATERIAL, 1, 17), "ggg", "  g", 'g', "ingotGold");
		RecipeUtils.addRecipe("decorative_hazmat_helmet", new ItemStack(ITEM_ARMOR_DECORATIVE_HAZMAT_HELMET, 1), " o ", "sGs", "sBs", 'o', EnumColor.ORANGE.getDyeOreName(), 's', Items.SLIME_BALL, 'G', "blockGlass", 'B', Blocks.IRON_BARS);
		RecipeUtils.addRecipe("decorative_hazmat_chest", new ItemStack(ITEM_ARMOR_DECORATIVE_HAZMAT_CHEST, 1), "s s", "sos", "sos", 's', Items.SLIME_BALL, 'o', EnumColor.ORANGE.getDyeOreName());
		RecipeUtils.addRecipe("decorative_hazmat_leg", new ItemStack(ITEM_ARMOR_DECORATIVE_HAZMAT_LEG, 1), "sos", "s s", "s s", 's', Items.SLIME_BALL, 'o', EnumColor.ORANGE.getDyeOreName());
		RecipeUtils.addRecipe("decorative_hazmat_boots", new ItemStack(ITEM_ARMOR_DECORATIVE_HAZMAT_BOOTS, 1), "s s", "s s", "sWs", 's', Items.SLIME_BALL, 'W', "blockWool");
		RecipeUtils.addRecipeLamp(BlockLamp.MaterialType.STONE, new ItemStack(Blocks.STONE, 1, 0));
		RecipeUtils.addRecipeLamp(BlockLamp.MaterialType.BRICK, new ItemStack(Blocks.BRICK_BLOCK, 1));
		RecipeUtils.addRecipeLamp(BlockLamp.MaterialType.IRON, "ingotIron");
		RecipeUtils.addRecipeLamp(BlockLamp.MaterialType.LAPIS, "gemLapis");
		RecipeUtils.addRecipe("safety_helmet", new ItemStack(ITEM_ARMOR_SAFETY_MET, 1), "yly", "sWs", 'y', EnumColor.YELLOW.getDyeOreName(), 'l', Items.LEATHER_HELMET, 's', Items.STRING, 'W', "blockWool");
		RecipeUtils.addRecipe("safety_chest", new ItemStack(ITEM_ARMOR_WORK_CLOTHES, 1), "C C", "CCC", "lll", 'C', EnumColor.CYAN.getWoolOreName(), 'l', Items.LEATHER);
		RecipeUtils.addRecipe("safety_leg", new ItemStack(ITEM_ARMOR_SAFETY_BELT, 1), "lll", "  I", 'l', Items.LEATHER, 'I', BLOCK_IRON_CHAIN);
		RecipeUtils.addRecipe("safety_boots", new ItemStack(ITEM_ARMOR_SAFETY_BOOTS, 1), "lbl", "i i", 'l', Items.LEATHER, 'b', EnumColor.BLACK.getDyeOreName(), 'i', "ingotIron");
		RecipeUtils.addRecipe("life_jacket", new ItemStack(ITEM_ARMOR_LIFE_JACKET, 1), "P P", "lll", "PPP", 'P', "plankWood", 'l', Items.LEATHER);

		//精錬レシピ
		GameRegistry.addSmelting(Items.ROTTEN_FLESH, new ItemStack(Items.LEATHER, 1), 0.05F);
		GameRegistry.addSmelting(Items.DIAMOND, new ItemStack(Items.COAL, 1), 0.05F);
		GameRegistry.addSmelting(Blocks.DIAMOND_BLOCK, new ItemStack(Blocks.COAL_BLOCK, 1), 0.05F);
		GameRegistry.addSmelting(Blocks.LEAVES, new ItemStack(Blocks.DIRT, 1), 0F);
		GameRegistry.addSmelting(Blocks.LEAVES2, new ItemStack(Blocks.DIRT, 1), 0F);
		GameRegistry.addSmelting(Blocks.BROWN_MUSHROOM_BLOCK, new ItemStack(Blocks.MYCELIUM, 1), 0F);
		GameRegistry.addSmelting(Blocks.RED_MUSHROOM_BLOCK, new ItemStack(Blocks.MYCELIUM, 1), 0F);
		GameRegistry.addSmelting(Blocks.SAPLING, new ItemStack(Blocks.DIRT, 1), 0F);

		GameRegistry.addSmelting(new ItemStack(ITEM_MATERIAL, 1, 16), new ItemStack(Items.IRON_INGOT, 1), 0F);

		//醸造レシピ
//		BrewingRecipeRegistry.addRecipe(new ItemStack(Items.WATER_BUCKET, 1), new ItemStack(Items.DYE, 1, EnumColor.RED.getDyeNumber()), new ItemStack(Items.LAVA_BUCKET, 1));

		//TODO
		//			GameRegistry.addSmelting(new ItemStack(Blocks.NETHERRACK, 4), new ItemStack(Blocks.NETHER_BRICK, 1), 0F);
		//TODO GameRegistry.addSmelting(new ItemStack(Items.iron_axe, 1, WILDCARD_VALUE), new ItemStack(Items.iron_ingot, 1), 0.005F);

		//BotaniaAPI.registerRuneAltarRecipe(output, mana, inputs);
		//CraftingHandlers.pulverizer.addRecipe(energy, input, primaryOutput, secondaryOutput, secondaryChance, overwrite);
		//ThaumcraftApi.addInfusionCraftingRecipe(research, result, instability, aspects, input, recipe);
		//ActuallyAdditionsAPI.addCrusherRecipe(input, outputOne);

		//燃料登録
		//200tick = 1second
		Utils.addFuel(16000, new ItemStack(BLOCK_METAL, 1, 7));
		Utils.addFuel(2400, new ItemStack(Items.MAGMA_CREAM));
		Utils.addFuel(1500, new ItemStack(Blocks.HAY_BLOCK));
		Utils.addFuel(1200, new ItemStack(Items.BLAZE_POWDER));
		Utils.addFuel(200, new ItemStack(Blocks.DEADBUSH));
		Utils.addFuel(150, new ItemStack(Items.WHEAT));
		Utils.addFuel(100, new ItemStack(Blocks.LEAVES, 1, WILDCARD_VALUE), new ItemStack(Blocks.LEAVES2, 1, WILDCARD_VALUE), new ItemStack(Blocks.TALLGRASS), new ItemStack(Items.REEDS));
		Utils.addFuel(50, new ItemStack(Items.WHEAT_SEEDS), new ItemStack(Items.MELON_SEEDS), new ItemStack(Items.PUMPKIN_SEEDS));

		//mobスポーン追加
		Utils.addSpawnEntity(EntityGiantZombie.class, 1, 0, 1, EnumCreatureType.MONSTER, Biomes.PLAINS);

		//生成
		GameRegistry.registerWorldGenerator(new WorldGenBlock(), 1);

		//パーティクル
		ParticleRegistry.register();
	}

	public static void postInit()
	{
		//			Utils.removeRecipe(new ItemStack(Blocks.stone_stairs));
//		Utils.removeSmeltingRecipe(new ItemStack(Blocks.LOG, 1, 0), new ItemStack(Items.COAL, 1, 1));
		if(!ModConfig.connectableBlock.isEmpty())
		{
			for(String loc : ModConfig.connectableBlock)
			{
				Block block = Block.REGISTRY.getObject(new ResourceLocation(loc));
				BlockLattice.connectables.add(block);
			}
		}

		//configで追加された奴の爆破耐性を10000に
		if(!ModConfig.increasingResistance.isEmpty())
		{
			for(String loc : ModConfig.increasingResistance)
			{
				Block block = Block.REGISTRY.getObject(new ResourceLocation(loc));
				block.setResistance(10000.0F);
				PSV2Core.LOGGER.info("Setted " + loc + "'s resistance to 10000!");
			}
		}

		//耐久値変更
		if(ModConfig.changeWoodToolsDurability)
		{
			Items.WOODEN_AXE.setMaxDamage(8);
			Items.WOODEN_HOE.setMaxDamage(8);
			Items.WOODEN_PICKAXE.setMaxDamage(8);
			Items.WOODEN_SHOVEL.setMaxDamage(8);
			Items.WOODEN_SWORD.setMaxDamage(8);
		}
		if(ModConfig.changeStoneToolsDurability)
		{
			Items.STONE_AXE.setMaxDamage(32);
			Items.STONE_HOE.setMaxDamage(32);
			Items.STONE_PICKAXE.setMaxDamage(32);
			Items.STONE_SHOVEL.setMaxDamage(32);
			Items.STONE_SWORD.setMaxDamage(32);
		}
		if(ModConfig.changeIronToolsDurability)
		{
			Items.IRON_AXE.setMaxDamage(1024);
			Items.IRON_HOE.setMaxDamage(1024);
			Items.IRON_PICKAXE.setMaxDamage(1024);
			Items.IRON_SHOVEL.setMaxDamage(1024);
			Items.IRON_SWORD.setMaxDamage(1024);
		}
		if(ModConfig.changeGoldToolsDurability)
		{
			Items.GOLDEN_AXE.setMaxDamage(32);
			Items.GOLDEN_HOE.setMaxDamage(32);
			Items.GOLDEN_PICKAXE.setMaxDamage(32);
			Items.GOLDEN_SHOVEL.setMaxDamage(32);
			Items.GOLDEN_SWORD.setMaxDamage(32);
		}
		if(ModConfig.changeDiamondToolsDurability)
		{
			Items.DIAMOND_AXE.setMaxDamage(8192);
			Items.DIAMOND_HOE.setMaxDamage(8192);
			Items.DIAMOND_PICKAXE.setMaxDamage(8192);
			Items.DIAMOND_SHOVEL.setMaxDamage(8192);
			Items.DIAMOND_SWORD.setMaxDamage(8192);
		}

		if(!ModCompat.COMPAT_QUARK)
		{
			Utils.addFuel(400, new ItemStack(Blocks.TORCH));
			Utils.addSpawnEntity(EntityBlaze.class, 15, 1, 4, EnumCreatureType.MONSTER, Biomes.HELL);
		}
		else
			PSV2Core.LOGGER.info("Quark is installed, so skip register.");

		if(Utils.isOreDictLoaded("ingotTin"))
		{
			RecipeUtils.addRecipe("tin_to_bucket", new ItemStack(Items.BUCKET, 1), "t t", " t ", 't', "ingotTin");
			RecipeUtils.addRecipe("tin_to_shears", new ItemStack(Items.SHEARS, 1), " t", "t ", 't', "ingotTin");
			RecipeUtils.addRecipe("tin_to_piston", new ItemStack(Blocks.PISTON, 1), "PPP", "CtC", "CrC", 'P', "plankWood", 'C', "cobblestone", 't', "ingotTin", 'r', "dustRedstone");
			RecipeUtils.addRecipe("tin_to_minecart", new ItemStack(Items.MINECART, 1), "t t", "ttt", 't', "ingotTin");
		}
		if(Utils.isOreDictLoaded("ingotCopper"))
		{
			RecipeUtils.addRecipe("copper_to_bucket", new ItemStack(Items.BUCKET, 1), "c c", " c ", 'c', "ingotCopper");
			RecipeUtils.addRecipe("copper_to_shears", new ItemStack(Items.SHEARS, 1), " c", "c ", 'c', "ingotCopper");
			RecipeUtils.addRecipe("copper_to_piston", new ItemStack(Blocks.PISTON, 1), "PPP", "CcC", "CrC", 'P', "plankWood", 'C', "cobblestone", 'c', "ingotCopper", 'r', "dustRedstone");
			RecipeUtils.addRecipe("copper_to_minecart", new ItemStack(Items.MINECART, 1), "c c", "ccc", 'c', "ingotCopper");
		}
		if(Utils.isOreDictLoaded("ingotLead"))
		{
			RecipeUtils.addRecipe("lead_to_bucket", new ItemStack(Items.BUCKET, 1), "l l", " l ", 'l', "ingotLead");
			RecipeUtils.addRecipe("lead_to_shears", new ItemStack(Items.SHEARS, 1), " l", "l ", 'l', "ingotLead");
			RecipeUtils.addRecipe("lead_to_piston", new ItemStack(Blocks.PISTON, 1), "PPP", "ClC", "CrC", 'P', "plankWood", 'C', "cobblestone", 'l', "ingotLead", 'r', "dustRedstone");
			RecipeUtils.addRecipe("lead_to_minecart", new ItemStack(Items.MINECART, 1), "l l", "lll", 'l', "ingotLead");
		}
		if(Utils.isOreDictLoaded("ingotSilver"))
		{
			RecipeUtils.addRecipe("silver_to_bucket", new ItemStack(Items.BUCKET, 1), "s s", " s ", 's', "ingotSilver");
			RecipeUtils.addRecipe("silver_to_shears", new ItemStack(Items.SHEARS, 1), " s", "s ", 's', "ingotSilver");
			RecipeUtils.addRecipe("silver_to_piston", new ItemStack(Blocks.PISTON, 1), "PPP", "CsC", "CrC", 'P', "plankWood", 'C', "cobblestone", 's', "ingotSilver", 'r', "dustRedstone");
			RecipeUtils.addRecipe("silver_to_minecart", new ItemStack(Items.MINECART, 1), "s s", "sss", 's', "ingotSilver");
		}
		if(Utils.isOreDictLoaded("ingotBronze"))
		{
			RecipeUtils.addRecipe("iron_chain_bronze", new ItemStack(BLOCK_IRON_CHAIN, 16), "i", "i", "i", 'i', "ingotBronze");
			RecipeUtils.addRecipe("anchor_bolt_bronze", new ItemStack(BLOCK_ANCHOR_BOLT, 1), "iI", 'i', "ingotBronze", 'I', BLOCK_IRON_CHAIN);
			RecipeUtils.addRecipe("stepladder_bronze", new ItemStack(BLOCK_STEPLADDER, 1), "iLi", "iLi", "iLi", 'i', "ingotBronze", 'L', Blocks.LADDER);
		}

		//イベント登録
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(new ModLivingDropsEvent());
		MinecraftForge.EVENT_BUS.register(new ModPlayerInteractEvent());
		MinecraftForge.EVENT_BUS.register(new RightClickBlockEvent());
		MinecraftForge.EVENT_BUS.register(new ModLivingEvent());
		MinecraftForge.EVENT_BUS.register(new PlayerMoveSpeedHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new BiomeDecorationHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new LavaLakeGenHandler());
	}

	@SideOnly(Side.CLIENT)
	public static void preInitClient()
	{
		Utils.registerEntityRendering(EntityZabuton.class, new RenderZabuton.Factory());
	}

	@SideOnly(Side.CLIENT)
	public static void initClient()
	{
		Utils.registerItemColor(new ItemZabuton(), ITEM_ZABUTON);
//		Utils.registerItemColor(new ItemNewDye(), itemNewDye);

		Utils.registerItemBlockColor(new ItemBlockDummyBarrier(), BLOCK_DUMMY_BARRIER);

		Utils.registerKeyBinding(LibKey.KEY_FACING_ADJUSTMENT);
		Utils.registerKeyBinding(LibKey.KEY_THIRD_PERSON_CAMERA_DISTANCE);
		Utils.registerKeyBinding(LibKey.KEY_PICK_UP_WIDELY_TOGGLE);
	}

	@SideOnly(Side.CLIENT)
	public static void postInitClient()
	{
		MinecraftForge.EVENT_BUS.register(new ModClientEventHandler());
	}

	public static void botaniaPlugin()
	{
		//			if(Loader.isModLoaded("Botania"))
		//			{
		//				BotaniaAPI.registerRuneAltarRecipe(new ItemStack(Items.diamond, 1)/*ItemStackじゃないとだめ。*/, 20000, new ItemStack(Items.coal)/*ItemStackじゃないとだめ*/, new ItemStack(Items.coal), new ItemStack(Items.coal));
		//				BotaniaAPI.registerManaInfusionRecipe(new ItemStack(Items.diamond, 1), new ItemStack(Items.coal), 20000);
		//			}
	}
}
