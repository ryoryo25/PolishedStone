package ryoryo.polishedstone.util;

import net.minecraft.util.ResourceLocation;
import ryoryo.polishedlib.util.EnumColor;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.block.BlockDecoration;
import ryoryo.polishedstone.block.BlockDummyCable;
import ryoryo.polishedstone.block.BlockIronPlate;
import ryoryo.polishedstone.block.BlockLamp;
import ryoryo.polishedstone.block.BlockMetal;
import ryoryo.polishedstone.block.BlockModStoneSlab;
import ryoryo.polishedstone.block.BlockNewFlower;
import ryoryo.polishedstone.block.BlockNewGravel;
import ryoryo.polishedstone.block.BlockNewOre;
import ryoryo.polishedstone.block.BlockNewPath;
import ryoryo.polishedstone.block.BlockPavingStone;
import ryoryo.polishedstone.block.BlockVendingMachine;
import ryoryo.polishedstone.block.BlockVoidTeleporter;
import ryoryo.polishedstone.block.BlockDecoration.BlockType;
import ryoryo.polishedstone.item.ItemMaterial;
import ryoryo.polishedstone.item.ItemTweakedDye;
import ryoryo.polishedstone.itemblock.ItemBlockBlackQuartz;
import ryoryo.polishedstone.itemblock.ItemBlockCompressedBookshelf;
import ryoryo.polishedstone.itemblock.ItemBlockLateralGroove;
import ryoryo.polishedstone.itemblock.ItemBlockPolishedStone;
import ryoryo.polishedstone.itemblock.ItemBlockThreePillars;

public class Locations
{
	public static final ResourceLocation POLISHED_STONE = Utils.makeModLocation("polished_stone");
	public static final ResourceLocation[] POISHED_STONES = new ResourceLocation[ItemBlockPolishedStone.NAMES.length];

	public static final ResourceLocation COLORED_LIGHT = Utils.makeModLocation("colored_light");
	public static final ResourceLocation[] COLORED_LIGHTS = new ResourceLocation[EnumColor.getLength()];

	public static final ResourceLocation NEW_PATH = Utils.makeModLocation("new_path");
	public static final ResourceLocation[] NEW_PATHS = new ResourceLocation[BlockNewPath.PathType.getLength()];

	public static final ResourceLocation NEW_FLOWER = Utils.makeModLocation("new_flower");
	public static final ResourceLocation[] NEW_FLOWERS = new ResourceLocation[BlockNewFlower.NewFlowerType.getLength()];

	public static final ResourceLocation GLOWSTONE_GENE = Utils.makeModLocation("glowstone_generator");
	public static final ResourceLocation[] GLOWSTONE_GENES = new ResourceLocation[8];

	public static final ResourceLocation VENDING_MACHINE = Utils.makeModLocation("vending_machine");
	public static final ResourceLocation[] VENGING_MACHINES = new ResourceLocation[BlockVendingMachine.TexType.getLength()];

	public static final ResourceLocation DECORATION = Utils.makeModLocation("decoration_block");
	public static final ResourceLocation[] DECORATIONS = new ResourceLocation[BlockType.getLength()];

	public static final ResourceLocation METAL = Utils.makeModLocation("metal");
	public static final ResourceLocation[] METALS = new ResourceLocation[BlockMetal.MaterialType.getLength()];

	public static final ResourceLocation BLACK_QUARTZ = Utils.makeModLocation("black_quartz");
	public static final ResourceLocation[] BLACK_QUARTZS = new ResourceLocation[ItemBlockBlackQuartz.NAMES.length];

	public static final ResourceLocation NEW_ORE = Utils.makeModLocation("new_ore");
	public static final ResourceLocation[] NEW_ORES = new ResourceLocation[BlockNewOre.MaterialType.getLength()];

	public static final ResourceLocation IRON_PLATE = Utils.makeModLocation("iron_plate");
	public static final ResourceLocation[] IRON_PLATES = new ResourceLocation[BlockIronPlate.PlateType.getLength()];

	public static final ResourceLocation FAUCET = Utils.makeModLocation("faucet");
	public static final ResourceLocation[] FAUCETS = new ResourceLocation[2];

	public static final ResourceLocation LATERAL_GROOVE = Utils.makeModLocation("lateral_groove");
	public static final ResourceLocation[] LATERAL_GROOVES = new ResourceLocation[ItemBlockLateralGroove.NAMES.length];

	public static final ResourceLocation NEW_GRAVEL = Utils.makeModLocation("new_gravel");
	public static final ResourceLocation[] NEW_GRAVELS = new ResourceLocation[BlockNewGravel.EnumType.getLength()];

	public static final ResourceLocation PAVING_STONE = Utils.makeModLocation("paving_stone");
	public static final ResourceLocation[] PAVING_STONES = new ResourceLocation[BlockPavingStone.EnumType.getLength()];

	public static final ResourceLocation COMPRESSED_BOOKSHELF = Utils.makeModLocation("compressed_bookshelf");
	public static final ResourceLocation[] COMPRESSED_BOOKSHELFS = new ResourceLocation[ItemBlockCompressedBookshelf.NAMES.length];

	public static final ResourceLocation THREE_PILLARS = Utils.makeModLocation("three_pillars");
	public static final ResourceLocation[] THREE_PILLARSS = new ResourceLocation[ItemBlockThreePillars.NAMES.length];

	public static final ResourceLocation DUMMY_CABLE = Utils.makeModLocation("dummy_cable");
	public static final ResourceLocation[] DUMMY_CABLES = new ResourceLocation[BlockDummyCable.EnumType.getLength()];

	public static final ResourceLocation POLISHED_STONE_COLORED = Utils.makeModLocation("polished_stone_colored");
	public static final ResourceLocation[] POLISHED_STONE_COLOREDS = new ResourceLocation[EnumColor.getLength()];

	public static final ResourceLocation LAMP = Utils.makeModLocation("lamp");
	public static final ResourceLocation[] LAMPS = new ResourceLocation[BlockLamp.MaterialType.getLength()];

	public static final ResourceLocation CHROMA_KEY_BACK = Utils.makeModLocation("chroma_key_back");
	public static final ResourceLocation[] CHROMA_KEY_BACKS = new ResourceLocation[EnumColor.getLength()];

	public static final ResourceLocation STONE_SLAB = Utils.makeModLocation("stone_slab");
	public static final ResourceLocation[] STONE_SLABS = new ResourceLocation[BlockModStoneSlab.Type.getLength()];

	public static final ResourceLocation DOUBLE_STONE_SLAB = Utils.makeModLocation("double_stone_slab");
	public static final ResourceLocation[] DOUBLE_STONE_SLABS = new ResourceLocation[BlockModStoneSlab.Type.getLength()];

	public static final ResourceLocation VOID_TELEPORTER = Utils.makeModLocation("void_teleporter");
	public static final ResourceLocation[] VOID_TELEPORTERS = new ResourceLocation[BlockVoidTeleporter.VoidType.getLength()];

	public static final ResourceLocation TWEAKED_DYE = Utils.makeModLocation("tweaked_dye");
	public static final ResourceLocation[] TWEAKED_DYES = new ResourceLocation[ItemTweakedDye.NAMES.length];

	public static final ResourceLocation MATERIAL = Utils.makeModLocation("material");
	public static final ResourceLocation[] MATERIALS = new ResourceLocation[ItemMaterial.NAMES.length];

	static
	{
		for(int i = 0; i < ItemBlockPolishedStone.NAMES.length; i++)
		{
			POISHED_STONES[i] = new ResourceLocation(POLISHED_STONE.toString() + "_" + ItemBlockPolishedStone.NAMES[i]);
		}

		for(int i = 0; i < EnumColor.getLength(); i++)
		{
			COLORED_LIGHTS[i] = new ResourceLocation(COLORED_LIGHT.toString() + "_" + EnumColor.COLOR_WOOL[i]);
			POLISHED_STONE_COLOREDS[i] = new ResourceLocation(POLISHED_STONE_COLORED.toString() + "_" + EnumColor.COLOR_WOOL[i]);
			CHROMA_KEY_BACKS[i] = new ResourceLocation(CHROMA_KEY_BACK.toString() + "_" + EnumColor.COLOR_WOOL[i]);
		}

		for(int i = 0; i < BlockNewPath.PathType.getLength(); i++)
		{
			NEW_PATHS[i] = new ResourceLocation(NEW_PATH.toString() + "_" + BlockNewPath.PathType.NAMES[i]);
		}

		for(int i = 0; i < BlockNewFlower.NewFlowerType.getLength(); i++)
		{
			NEW_FLOWERS[i] = new ResourceLocation(NEW_FLOWER.toString() + "_" + BlockNewFlower.NewFlowerType.NAMES[i]);
		}

		for(int i = 0; i < 8; i++)
		{
			GLOWSTONE_GENES[i] = new ResourceLocation(GLOWSTONE_GENE.toString() + "_" + i);
		}

		for(int i = 0; i < BlockVendingMachine.TexType.getLength(); i++)
		{
			VENGING_MACHINES[i] = new ResourceLocation(VENDING_MACHINE.toString() + "_" + BlockVendingMachine.TexType.NAMES[i]);
		}

		for(int i = 0; i < BlockType.getLength(); i++)
		{
			DECORATIONS[i] = new ResourceLocation(DECORATION.toString() + "_" + BlockDecoration.BlockType.NAMES[i]);
		}

		for(int i = 0; i < BlockMetal.MaterialType.getLength(); i++)
		{
			METALS[i] = new ResourceLocation(METAL.toString() + "_" + BlockMetal.MaterialType.NAMES[i]);
		}

		for(int i = 0; i < ItemBlockBlackQuartz.NAMES.length; i++)
		{
			BLACK_QUARTZS[i] = new ResourceLocation(BLACK_QUARTZ.toString() + "_" + ItemBlockBlackQuartz.NAMES[i]);
		}

		for(int i = 0; i < BlockNewOre.MaterialType.getLength(); i++)
		{
			NEW_ORES[i] = new ResourceLocation(NEW_ORE.toString() + "_" + BlockNewOre.MaterialType.NAMES[i]);
		}

		for(int i = 0; i < BlockIronPlate.PlateType.getLength(); i++)
		{
			IRON_PLATES[i] = new ResourceLocation(IRON_PLATE.toString() + "_" + BlockIronPlate.PlateType.NAMES[i]);
		}

		for(int i = 0; i < 2; i++)
		{
			FAUCETS[i] = new ResourceLocation(FAUCET.toString() + "_" + i);
		}

		for(int i = 0; i < ItemBlockLateralGroove.NAMES.length; i++)
		{
			LATERAL_GROOVES[i] = new ResourceLocation(LATERAL_GROOVE.toString() + "_" + ItemBlockLateralGroove.NAMES[i]);
		}

		for(int i = 0; i < BlockNewGravel.EnumType.getLength(); i++)
		{
			NEW_GRAVELS[i] = new ResourceLocation(NEW_GRAVEL.toString() + "_" + BlockNewGravel.EnumType.NAMES[i]);
		}

		for(int i = 0; i < BlockPavingStone.EnumType.getLength(); i++)
		{
			PAVING_STONES[i] = new ResourceLocation(PAVING_STONE.toString() + "_" + BlockPavingStone.EnumType.NAMES[i]);
		}

		for(int i = 0; i < ItemBlockCompressedBookshelf.NAMES.length; i++)
		{
			COMPRESSED_BOOKSHELFS[i] = new ResourceLocation(COMPRESSED_BOOKSHELF.toString() + "_" + ItemBlockCompressedBookshelf.NAMES[i]);
		}

		for(int i = 0; i < ItemBlockThreePillars.NAMES.length; i++)
		{
			THREE_PILLARSS[i] = new ResourceLocation(THREE_PILLARS.toString() + "_" + ItemBlockThreePillars.NAMES[i]);
		}

		for(int i = 0; i < BlockDummyCable.EnumType.getLength(); i++)
		{
			DUMMY_CABLES[i] = new ResourceLocation(DUMMY_CABLE.toString() + "_" + BlockDummyCable.EnumType.NAMES[i]);
		}

		for(int i = 0; i < BlockLamp.MaterialType.getLength(); i++)
		{
			LAMPS[i] = new ResourceLocation(LAMP.toString() + "_" + BlockLamp.MaterialType.NAMES[i]);
		}

		for(int i = 0; i < BlockModStoneSlab.Type.getLength(); i++)
		{
			STONE_SLABS[i] = new ResourceLocation(STONE_SLAB.toString() + "_" + BlockModStoneSlab.Type.NAMES[i]);
			DOUBLE_STONE_SLABS[i] = new ResourceLocation(DOUBLE_STONE_SLAB.toString() + "_" + BlockModStoneSlab.Type.NAMES[i]);
		}

		for(int i = 0; i < BlockVoidTeleporter.VoidType.getLength(); i++)
		{
			VOID_TELEPORTERS[i] = new ResourceLocation(VOID_TELEPORTER.toString() + "_" + BlockVoidTeleporter.VoidType.NAMES[i]);
		}

		for(int i = 0; i < ItemTweakedDye.NAMES.length; i++)
		{
			TWEAKED_DYES[i] = new ResourceLocation(TWEAKED_DYE.toString() + "_" + ItemTweakedDye.NAMES[i]);
		}

		for(int i = 0; i < ItemMaterial.NAMES.length; i++)
		{
			MATERIALS[i] = new ResourceLocation(MATERIAL.toString() + "_" + ItemMaterial.NAMES[i]);
		}
	}
}
