package ryoryo.polishedstone.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.block.BlockNewFlower;
import ryoryo.polishedstone.block.BlockNewFlower.NewFlowerType;
import ryoryo.polishedstone.block.BlockNewPath;
import ryoryo.polishedstone.block.BlockSafetyFence;
import ryoryo.polishedstone.config.ModConfig;

public class EventHelper
{
	//fence jumper用
	public static List<Block> fences = new ArrayList<Block>();
	//pick up widely切り替え用
	public static boolean pickUpWidelyToggle = ModConfig.startModePUW;

	//mobドロップ用
	public static int getRandom(int max, int min)
	{
		Random random = new Random();
		if(min == 0 && max == 0)
			return 0;
		if(max == 0)
			return 0;
		if(min > max)
			return 0;

		return random.nextInt(max - min + 1) + min;
	}

	//赤ちゃんたくさん
	public static int getBabyNum(EntityLivingBase entity)
	{
		Random random = entity.getRNG();
		if(entity instanceof EntityChicken)
		{
			if(random.nextInt(32) == 0)
				return 3;

			return 1;
		}
		if(entity instanceof EntityCow)
		{
			if(random.nextInt(1000000) == 0)
				return 2;

			return 1;
		}
		if(entity instanceof EntityHorse)
		{
			if(random.nextInt(10000) == 0)
				return 2;

			return 1;
		}
		if(entity instanceof EntitySheep)
		{
			if(random.nextInt(3) == 0)
				return 2;

			return 1;
		}
		if(entity instanceof EntityPig)
		{
			return 8 + random.nextInt(6);
		}
		if(entity instanceof EntityWolf)
		{
			return 2 + random.nextInt(11);
		}
		if(entity instanceof EntityOcelot)
		{
			return random.nextInt(7);
		}
		if(entity instanceof EntityRabbit)
		{
			return random.nextInt(7);
		}
		if(entity instanceof EntityPolarBear)
		{
			return random.nextInt(2);
		}

		return 0;
	}

	//Fence Jumper用
	@SideOnly(Side.CLIENT)
	public static boolean isPlayerNextToFence(EntityPlayerSP player)
	{
		double x = player.posX - 1.0D;
		double y = player.posY;
		double z = player.posZ - 1.0D;
		World world = player.world;

		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(i != x || j != z)
				{
					Block block = world.getBlockState(new BlockPos(x + i, y, z + j)).getBlock();
					if(block instanceof BlockFence || block instanceof BlockFenceGate || block instanceof BlockWall || block instanceof BlockSafetyFence || fences.contains(block))
						return true;
				}
			}
		}

		return false;
	}

	/**
	 * シャベルで右クリックでPathにする、スニークで戻す
	 * @param world
	 * @param pos
	 * @param state
	 * @param player
	 * @param event
	 * @param hand
	 * @param held
	 */
	public static void createPath(World world, BlockPos pos, IBlockState state, EntityPlayer player, PlayerInteractEvent.RightClickBlock event, EnumHand hand, ItemStack held)
	{
		IBlockState stateu = world.getBlockState(pos.up());
		IBlockState target = null;
		if(stateu.getMaterial() == Material.AIR)
		{
			if(!player.isSneaking())
			{
				if(state.getBlock() == Blocks.DIRT && state.getBlock().getMetaFromState(state) == 0)
				{
					target = Register.BLOCK_NEW_PATH.getDefaultState();
				}
				else if(state.getBlock() == Blocks.DIRT && state.getBlock().getMetaFromState(state) == 1)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(1);
				}
				else if(state.getBlock() == Blocks.DIRT && state.getBlock().getMetaFromState(state) == 2/*Blocks.DIRT.getStateFromMeta(2).getBlock()*//* && state.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL*/)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(2);
				}
				else if(state.getBlock() == Blocks.GRAVEL)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(3);
				}
				else if(state.getBlock() == Blocks.SAND && state.getBlock().getMetaFromState(state) == 0)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(4);
				}
				else if(state.getBlock() == Blocks.SAND && state.getBlock().getMetaFromState(state) == 1)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(5);
				}
				else if(state.getBlock() == Blocks.CLAY)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(6);
				}
				else if(state.getBlock() == Blocks.SOUL_SAND)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(7);
				}
				else if(state.getBlock() == Register.BLOCK_NEW_GRAVEL && state.getBlock().getMetaFromState(state) == 0)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(8);
				}
				else if(state.getBlock() == Register.BLOCK_NEW_GRAVEL && state.getBlock().getMetaFromState(state) == 1)
				{
					target = Register.BLOCK_NEW_PATH.getStateFromMeta(9);
				}
			}
			if(player.isSneaking())
			{
				if(state.getBlock() == Blocks.GRASS_PATH)
				{
					event.setCanceled(true);
					target = Blocks.GRASS.getDefaultState();
				}
				else if(state.getBlock() == Blocks.GRASS)
				{
					event.setCanceled(true);
				}
				else if(state.getBlock() == Register.BLOCK_NEW_PATH)
				{
					switch(state.getValue(BlockNewPath.TYPE))
					{
					case DIRT:
					default:
						target = Blocks.DIRT.getDefaultState();
						break;
					case COARSE_DIRT:
						target = Blocks.DIRT.getStateFromMeta(1);
						break;
					case PODZOL:
						target = Blocks.DIRT.getStateFromMeta(2);
						break;
					case GRAVEL:
						target = Blocks.GRAVEL.getDefaultState();
						break;
					case SAND:
						target = Blocks.SAND.getDefaultState();
						break;
					case RED_SAND:
						target = Blocks.SAND.getStateFromMeta(1);
						break;
					case CLAY:
						target = Blocks.CLAY.getDefaultState();
						break;
					case SOUL_SAND:
						target = Blocks.SOUL_SAND.getDefaultState();
						break;
					case OLD_GRAVEL:
						target = Register.BLOCK_NEW_GRAVEL.getDefaultState();
						break;
					case PAVING_GRAVEL:
						target = Register.BLOCK_NEW_GRAVEL.getStateFromMeta(1);
						break;
					}
				}
			}
		}
		if(target != null)
		{
			player.swingArm(hand);
			world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if(!world.isRemote)
			{
				world.setBlockState(pos, target);
				held.damageItem(1, player);
				event.setUseItem(Result.ALLOW);
			}
		}
	}

	public static void reduceSnowLayer(World world, BlockPos pos, IBlockState state, EntityPlayer player, PlayerInteractEvent.RightClickBlock event, EnumHand hand, ItemStack held)
	{
		IBlockState target = null;

		if(world.getBlockState(pos.up()).getMaterial() == Material.AIR)
		{
			if(state.getBlock() == Blocks.SNOW)
			{
				target = Blocks.SNOW_LAYER.getStateFromMeta(6);
			}
			else if(state.getBlock() == Blocks.SNOW_LAYER)
			{
				switch(state.getBlock().getMetaFromState(state))
				{
				case 0:
				default:
					target = Blocks.AIR.getDefaultState();
					break;
				case 1:
					target = Blocks.SNOW_LAYER.getStateFromMeta(0);
					break;
				case 2:
					target = Blocks.SNOW_LAYER.getStateFromMeta(1);
					break;
				case 3:
					target = Blocks.SNOW_LAYER.getStateFromMeta(2);
					break;
				case 4:
					target = Blocks.SNOW_LAYER.getStateFromMeta(3);
					break;
				case 5:
					target = Blocks.SNOW_LAYER.getStateFromMeta(4);
					break;
				case 6:
					target = Blocks.SNOW_LAYER.getStateFromMeta(5);
					break;
				case 7:
					target = Blocks.SNOW_LAYER.getStateFromMeta(6);
					break;
				}
			}
		}
		if(target != null)
		{
			player.swingArm(hand);
			world.playSound(player, pos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F * 0.8F);
			if(!world.isRemote)
			{
				world.setBlockState(pos, target);
				held.damageItem(1, player);
				event.setUseItem(Result.ALLOW);
			}
		}
	}

	public static void copyPlants(World world, BlockPos pos, IBlockState state, EntityPlayer player, PlayerInteractEvent.RightClickBlock event, EnumHand hand, ItemStack held, Random random)
	{
		EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(held.getMetadata());

		//spawn bonemeal particles
		ItemDye.spawnBonemealParticles(world, pos, 15);

		if(!world.isRemote && enumdyecolor == EnumDyeColor.WHITE)
		{
			if(state.getBlock() == Blocks.RED_FLOWER)
			{
				switch((EnumFlowerType) state.getValue(Blocks.RED_FLOWER.getTypeProperty()))
				{
				case ALLIUM:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.ALLIUM.getMeta()));
					break;
				case BLUE_ORCHID:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.BLUE_ORCHID.getMeta()));
					break;
				case HOUSTONIA:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.HOUSTONIA.getMeta()));
					break;
				case ORANGE_TULIP:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.ORANGE_TULIP.getMeta()));
					break;
				case OXEYE_DAISY:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.OXEYE_DAISY.getMeta()));
					break;
				case PINK_TULIP:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.PINK_TULIP.getMeta()));
					break;
				case POPPY:
				default:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.POPPY.getMeta()));
					break;
				case RED_TULIP:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.RED_TULIP.getMeta()));
					break;
				case WHITE_TULIP:
					Block.spawnAsEntity(world, pos, new ItemStack(Blocks.RED_FLOWER, 1, EnumFlowerType.WHITE_TULIP.getMeta()));
					break;
				}
			}
			else if(state.getBlock() == Blocks.YELLOW_FLOWER)
			{
				Block.spawnAsEntity(world, pos, new ItemStack(Blocks.YELLOW_FLOWER/*, 1, EnumFlowerType.DANDELION.getMeta()*/));
			}
			else if(state.getBlock() == Register.BLOCK_NEW_FLOWER)
			{
				switch((NewFlowerType) state.getValue(BlockNewFlower.TYPE))
				{
				case TULIP_BLACK:
				default:
					Block.spawnAsEntity(world, pos, new ItemStack(Register.BLOCK_NEW_FLOWER, 1, NewFlowerType.TULIP_BLACK.getMeta()));
					break;
				case LAVENDER_SAGE:
					Block.spawnAsEntity(world, pos, new ItemStack(Register.BLOCK_NEW_FLOWER, 1, NewFlowerType.LAVENDER_SAGE.getMeta()));
					break;
				}
			}
			else if(state.getBlock() == Blocks.DEADBUSH)
			{
				Block.spawnAsEntity(world, pos, new ItemStack(Blocks.DEADBUSH));
			}
			else if(state.getBlock() == Blocks.VINE)
			{
				Block.spawnAsEntity(world, pos, new ItemStack(Blocks.VINE));
			}
			else if(state.getBlock() == Blocks.WATERLILY)
			{
				Block.spawnAsEntity(world, pos, new ItemStack(Blocks.WATERLILY));
			}

			if(!Utils.isCreative(player))
			{
				held.shrink(1);
			}
			event.setUseItem(Result.ALLOW);
		}
	}
}
