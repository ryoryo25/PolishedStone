package ryoryo.polishedstone.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
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
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
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
	public static void createPath(Map<IBlockState, IBlockState> toPath, Map<IBlockState, IBlockState> toOriginal, World world, BlockPos pos, IBlockState state, EntityPlayer player, PlayerInteractEvent.RightClickBlock event, EnumHand hand, ItemStack held)
	{
		IBlockState stateu = world.getBlockState(pos.up());
		IBlockState target = null;

		//get target blockstate
		if(stateu.getMaterial() == Material.AIR)
		{
			if(!player.isSneaking())
				target = toPath.get(state);
			if(player.isSneaking())
			{
				target = toOriginal.get(state);

				//to prevent vanilla's function
				if(state.getBlock() == Blocks.GRASS_PATH || state.getBlock() == Blocks.GRASS)
					event.setCanceled(true);
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

	public static void reduceSnowLayer(Map<IBlockState, IBlockState> reduce, World world, BlockPos pos, IBlockState state, EntityPlayer player, PlayerInteractEvent.RightClickBlock event, EnumHand hand, ItemStack held)
	{
		Random random = new Random();
		IBlockState target = reduce.get(state);// default return null

		if(target != null)
		{
			player.swingArm(hand);
			world.playSound(player, pos, SoundEvents.BLOCK_SNOW_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F * 0.8F);
			if(!world.isRemote)
			{
				if(random.nextFloat() < 0.5)
					Block.spawnAsEntity(world, pos, new ItemStack(Items.SNOWBALL));
				world.setBlockState(pos, target);
				held.damageItem(1, player);
				event.setUseItem(Result.ALLOW);
			}
		}
	}

	public static void copyPlants(Map<IBlockState, ItemStack> toItem, World world, BlockPos pos, IBlockState state, EntityPlayer player, PlayerInteractEvent.RightClickBlock event, EnumHand hand, ItemStack held, Random random)
	{
		ItemStack item = toItem.get(state);

		if(item != null)
		{
			//spawn bonemeal particles
			ItemDye.spawnBonemealParticles(world, pos, 15);
			player.swingArm(hand);

			if(!world.isRemote)
			{
				Block.spawnAsEntity(world, pos, item);

				if(!Utils.isCreative(player))
					held.shrink(1);

				event.setUseItem(Result.ALLOW);
			}
		}
	}
}
