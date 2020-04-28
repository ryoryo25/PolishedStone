package ryoryo.polishedstone.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;

public class RightClickBlockEvent
{
	private static final Map<IBlockState, IBlockState> toPath = Maps.newHashMap();
	private static final Map<IBlockState, IBlockState> toOriginal = Maps.newHashMap();

	private static final Map<IBlockState, IBlockState> reduce = new HashMap<IBlockState, IBlockState>();

	private static final Map<IBlockState, ItemStack> toItem = new HashMap<IBlockState, ItemStack>();

	public RightClickBlockEvent()
	{
		//map initialization
		toPath.put(Blocks.DIRT.getDefaultState(), Register.BLOCK_NEW_PATH.getDefaultState());
		toPath.put(Blocks.DIRT.getStateFromMeta(1), Register.BLOCK_NEW_PATH.getStateFromMeta(1));
		toPath.put(Blocks.DIRT.getStateFromMeta(2), Register.BLOCK_NEW_PATH.getStateFromMeta(2));
		toPath.put(Blocks.GRAVEL.getDefaultState(), Register.BLOCK_NEW_PATH.getStateFromMeta(3));
		toPath.put(Blocks.SAND.getDefaultState(), Register.BLOCK_NEW_PATH.getStateFromMeta(4));
		toPath.put(Blocks.SAND.getStateFromMeta(1), Register.BLOCK_NEW_PATH.getStateFromMeta(5));
		toPath.put(Blocks.CLAY.getDefaultState(), Register.BLOCK_NEW_PATH.getStateFromMeta(6));
		toPath.put(Blocks.SOUL_SAND.getDefaultState(), Register.BLOCK_NEW_PATH.getStateFromMeta(7));
		toPath.put(Register.BLOCK_NEW_GRAVEL.getDefaultState(), Register.BLOCK_NEW_PATH.getStateFromMeta(8));
		toPath.put(Register.BLOCK_NEW_GRAVEL.getStateFromMeta(1), Register.BLOCK_NEW_PATH.getStateFromMeta(9));

		toOriginal.put(Blocks.GRASS_PATH.getDefaultState(), Blocks.GRASS.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getDefaultState(), Blocks.DIRT.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(1), Blocks.DIRT.getStateFromMeta(1));
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(2), Blocks.DIRT.getStateFromMeta(2));
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(3), Blocks.GRAVEL.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(4), Blocks.SAND.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(5), Blocks.SAND.getStateFromMeta(1));
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(6), Blocks.CLAY.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(7), Blocks.SOUL_SAND.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(8), Register.BLOCK_NEW_GRAVEL.getDefaultState());
		toOriginal.put(Register.BLOCK_NEW_PATH.getStateFromMeta(9), Register.BLOCK_NEW_GRAVEL.getStateFromMeta(1));

		reduce.put(Blocks.SNOW.getDefaultState(), Blocks.SNOW_LAYER.getStateFromMeta(6));
		for(int i = 7; i >= 1; i--)
			reduce.put(Blocks.SNOW_LAYER.getStateFromMeta(i), Blocks.SNOW_LAYER.getStateFromMeta(i - 1));
		reduce.put(Blocks.SNOW_LAYER.getStateFromMeta(0), Blocks.AIR.getDefaultState());

		for(int i = 0; i < 9; i++)
			toItem.put(Blocks.RED_FLOWER.getStateFromMeta(i), new ItemStack(Blocks.RED_FLOWER, 1, i));
		toItem.put(Blocks.YELLOW_FLOWER.getDefaultState(), new ItemStack(Blocks.YELLOW_FLOWER));
		toItem.put(Blocks.DEADBUSH.getDefaultState(), new ItemStack(Blocks.DEADBUSH));
		Blocks.VINE.getBlockState().getValidStates().forEach(vine -> toItem.put(vine, new ItemStack(Blocks.VINE)));
		toItem.put(Blocks.WATERLILY.getDefaultState(), new ItemStack(Blocks.WATERLILY));
		for(int i = 0; i < 2; i++)
			toItem.put(Register.BLOCK_NEW_FLOWER.getStateFromMeta(i), new ItemStack(Register.BLOCK_NEW_FLOWER, 1, i));
	}

	@SubscribeEvent
	public void onBlockRightClickWithItem(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		BlockPos pos = event.getPos();
		ItemStack held = event.getItemStack();
		World world = event.getWorld();
		IBlockState state = world.getBlockState(pos);
		EnumHand hand = event.getHand();
		Random random = new Random();

		if(player != null && !held.isEmpty() && held.getItem() != null)
		{
			//道フロック作るやつ
			if(held.getItem() instanceof ItemSpade)
				EventHelper.createPath(toPath, toOriginal, world, pos, state, player, event, hand, held);

			//雪のレイヤーを削る
			if(held.getItem() instanceof ItemSpade)
				EventHelper.reduceSnowLayer(reduce, world, pos, state, player, event, hand, held);

			//背の高い花みたいに普通の花もできるように
			if(held.getItem() instanceof ItemDye && held.getMetadata() == EnumDyeColor.WHITE.getDyeDamage())
				EventHelper.copyPlants(toItem, world, pos, state, player, event, hand, held, random);
		}
	}

	@SubscribeEvent
	public void onBlockRightClickEmpty(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		BlockPos pos = event.getPos();
		World world = event.getWorld();
		IBlockState state = world.getBlockState(pos);

		if(player != null)
		{
			//リスポーン地点をベッド右クリック時に夜じゃなくてもセット
			if(!world.isRemote && world.provider.canRespawnHere() && world.getBiomeForCoordsBody(pos) != Biomes.HELL && world.getBiomeForCoordsBody(pos) != Biomes.SKY && world.provider.isSurfaceWorld() && player.isEntityAlive() && state.getBlock() instanceof BlockBed)
			{
				player.setSpawnPoint(pos, false);
				event.setCanceled(true);
				Utils.sendChat(player, "Setted Respawn Point!");
			}
		}
	}
}
