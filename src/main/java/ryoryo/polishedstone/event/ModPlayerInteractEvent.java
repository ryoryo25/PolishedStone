package ryoryo.polishedstone.event;

import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.item.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityMinecartEmpty;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ryoryo.polishedlib.util.LibEnchantId;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.util.ModCompat;

public class ModPlayerInteractEvent
{
	private static final Map<Item, Function<EntityMinecartEmpty, EntityMinecart>> insert = Maps.newHashMap();

	public ModPlayerInteractEvent()
	{
		insert.put(Item.getItemFromBlock(Blocks.CHEST), cart -> new EntityMinecartChest(cart.world, cart.posX, cart.posY, cart.posZ));
		insert.put(Item.getItemFromBlock(Blocks.COMMAND_BLOCK), cart -> new EntityMinecartCommandBlock(cart.world, cart.posX, cart.posY, cart.posZ));
		insert.put(Item.getItemFromBlock(Blocks.FURNACE), cart -> new EntityMinecartFurnace(cart.world, cart.posX, cart.posY, cart.posZ));
		insert.put(Item.getItemFromBlock(Blocks.HOPPER), cart -> new EntityMinecartHopper(cart.world, cart.posX, cart.posY, cart.posZ));
		insert.put(Item.getItemFromBlock(Blocks.TNT), cart -> new EntityMinecartTNT(cart.world, cart.posX, cart.posY, cart.posZ));
	}

	/**
	 * cancel to break bedrock at y=0
	 *
	 * @param event
	 */
	@SubscribeEvent
	public void cancelBreakBedrock(PlayerInteractEvent.LeftClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos).getBlock();

		if(block == Blocks.BEDROCK && pos.getY() == 0 && !Utils.isCreative(player))
		{
			event.setCanceled(true);
		}
	}

	//	@SubscribeEvent
	//	public void onBlockLeftClick(PlayerInteractEvent.LeftClickBlock event)
	//	{
	//		if(player.isSneaking())
	//		{
	//			int r = 10;
	//			int posX = pos.getX();
	//			int posY = pos.getY();
	//			int posZ = pos.getZ();
	//			//				player.swingArm(player.getActiveHand());
	//			IBlockState original = world.getBlockState(pos);
	//
	//			if(!world.isRemote)
	//			{
	//				for(int x = posX - r; x <= posX + r; x++)
	//				{
	//					for(int y = posY - r; y <= posY + r; y++)
	//					{
	//						for(int z = posZ - r; z <= posZ + r; z++)
	//						{
	//							//								world.destroyBlock(new BlockPos(x, y, z), false);
	//							BlockPos posn = new BlockPos(x, y, z);
	//							if(original == world.getBlockState(posn))
	//								world.destroyBlock(posn, !Utils.isCreative(player));
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}

	@SubscribeEvent
	public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		BlockPos pos = event.getPos();
		ItemStack held = event.getItemStack();
		World world = event.getWorld();
		IBlockState state = world.getBlockState(pos);
		EnumHand hand = event.getHand();
		Random random = new Random();

		if(player != null)
		{
			if(!held.isEmpty() && held.getItem() != null)
			{
				//テスト
				if(held.isItemEqual(new ItemStack(Items.COAL, 1, 0)))
				{
					BlockPattern eventPattern = FactoryBlockPattern.start().aisle(new String[] { "QOQ", "OQO", "QOQ" }).where('Q', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.QUARTZ_BLOCK.getDefaultState().getBlock()))).where('O', BlockWorldState.hasState(BlockStateMatcher.forBlock(Blocks.OBSIDIAN))).build();
					BlockPattern pattern = eventPattern;
					BlockPattern.PatternHelper patternhelper = pattern.match(world, pos);

					if(patternhelper != null && patternhelper.getForwards() == EnumFacing.DOWN)
					{
						player.swingArm(hand);
						world.playSound(player, pos, SoundEvents.ENTITY_GENERIC_EXPLODE/*ENTITY_LIGHTNING_IMPACT*/, SoundCategory.BLOCKS, 15.0F, 0.5F);

						if(state.getMaterial() != Material.AIR)
						{
							for(int i = 0; i < 30; ++i)
							{
								double d0 = random.nextGaussian() * 0.1D/*0.02D*/;
								double d1 = random.nextGaussian() * 0.1D;
								double d2 = random.nextGaussian() * 0.1D;
								world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * state.getBoundingBox(world, pos).maxY, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2, new int[0]);
								world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * state.getBoundingBox(world, pos).maxY, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2, new int[0]);
							}
						}
						else
						{
							for(int i1 = 0; i1 < 30; ++i1)
							{
								double d0 = random.nextGaussian() * 0.1D/*0.02D*/;
								double d1 = random.nextGaussian() * 0.1D;
								double d2 = random.nextGaussian() * 0.1D;
								world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * 1.0f, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2, new int[0]);
							}
						}

						if(!world.isRemote)
						{
							for(int k = 0; k < eventPattern.getPalmLength(); ++k)
							{
								for(int l = 0; l < eventPattern.getThumbLength(); ++l)
								{
									world.destroyBlock(patternhelper.translateOffset(k, l, 0).getPos(), false);
								}
							}
							Block.spawnAsEntity(world, new BlockPos(pos.getX(), pos.up().getY(), pos.getZ()), new ItemStack(Items.DIAMOND));

							if(!Utils.isCreative(player))
							{
								held.shrink(1);
							}
							event.setUseItem(Result.ALLOW);
						}
					}
				}

				if(held.getItem() == Items.STICK)
				{
					//					world.destroyBlock(pos, true);
					//					world.destroyBlock(pos.north(), true);
					//					world.destroyBlock(pos.south(), true);
					//					world.destroyBlock(pos.west(), true);
					//					world.destroyBlock(pos.east(), true);
					//					world.destroyBlock(pos.north().west(), true);
					//					world.destroyBlock(pos.east().north(), true);
					//					world.destroyBlock(pos.south().east(), true);
					//					world.destroyBlock(pos.west().south(), true);
					//					BlockPattern eventPattern = FactoryBlockPattern.start().aisle(new String[]
					//					{ "BBBBB", "BBBBB", "BBBBB", "BBBBB", "BBBBB", }).where('B', BlockWorldState.hasState(BlockStateMatcher.forBlock(world.getBlockState(pos).getBlock()))).build();
					//					BlockPattern pattern = eventPattern;
					//					BlockPattern.PatternHelper patternhelper = pattern.match(world, pos);
					//
					//					if(patternhelper != null)
					//					{
					//						player.swingArm(hand);
					//
					//						if(!world.isRemote)
					//						{
					//							for(int k = 0; k < eventPattern.getPalmLength(); ++k)
					//							{
					//								for(int l = 0; l < eventPattern.getThumbLength(); ++l)
					//								{
					//									world.destroyBlock(patternhelper.translateOffset(k, l, 0).getPos(), false);
					//								}
					//							}
					//							event.setUseItem(Result.ALLOW);
					//						}
					//					}

					//					int r = 5;
					//					int posX = pos.getX();
					//					int posY = pos.getY() + 10;
					//					int posZ = pos.getZ();
					//					player.swingArm(hand);
					//
					//					if(!world.isRemote)
					//					{
					//						for(int x = posX - r; x <= posX + r; x++)
					//						{
					//							for(int y = posY - r; y <= posY + r; y++)
					//							{
					//								for(int z = posZ - r; z <= posZ + r; z++)
					//								{
					////									world.destroyBlock(new BlockPos(x, y, z), false);
					//									BlockPos posn = new BlockPos(x, y, z);
					//									//空気ブロックならスルー
					//									if(world.isAirBlock(posn))
					//										world.setBlockState(posn, Blocks.DIRT.getDefaultState());
					//								}
					//							}
					//						}
					//
					//						event.setUseItem(Result.ALLOW);
					//					}
				}
				//TODO ハシゴを下から伸ばせるように
				//				if(held.getItem() == Item.getItemFromBlock(Blocks.LADDER) && state.getBlock() == Blocks.LADDER)
				//				{
				//					int meta = state.getBlock().getMetaFromState(state);
				//					int l = 0;
				//					l = this.thisLadderLength(world, pos);
				//					BlockPos posu = pos.up(l);
				//					if(l > 0 && world.isAirBlock(posu)/* && Blocks.LADDER.canPlaceBlockAt(world, posu)*/)
				//					{
				//						player.swingArm(hand);
				//						world.playSound(player, pos, SoundEvents.BLOCK_LADDER_PLACE, SoundCategory.BLOCKS, 1.0F, 0.8F);
				//						if(!world.isRemote)
				//						{
				//							world.setBlockState(posu, Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, state.getValue(BlockLadder.FACING)));
				//							if(!Utils.isCreative(player))
				//							{
				//								held.stackSize--;
				//							}
				//							event.setResult(Result.ALLOW);
				//						}
				//					}
				//				}
			}
			else
			{
			}
		}
	}

	//	private int thisLadderLength(World world, BlockPos pos)
	//	{
	//		Block block = world.getBlockState(pos).getBlock();
	//		if(block != Blocks.LADDER)
	//		{
	//			return 0;
	//		}
	//		else
	//		{
	//			boolean b = true;
	//			int i = 0;
	//			while(b == true)
	//			{
	//				Block target = world.getBlockState(pos.up(i)).getBlock();
	//				b = target == Blocks.LADDER;
	//				if(b)
	//				{
	//					i++;
	//				}
	//				if(i > 16 || pos.up(i).getY() > 255)
	//				{
	//					b = false;
	//				}
	//			}
	//			return i;
	//		}
	//	}

	@SubscribeEvent
	public void onItemRightClick(PlayerInteractEvent.RightClickItem event)
	{
		ItemStack held = event.getItemStack();
		EntityPlayer player = event.getEntityPlayer();
		World world = event.getWorld();
		BlockPos pos = event.getPos();

		if(player != null)
		{
			if(!held.isEmpty() && held.getItem() != null)
			{
				//				if(held.getItem() == Items.STICK)
				//				{
				//					player.addVelocity(player.motionX * 50, player.motionY * 50, player.motionZ * 50);
				//				}

				//ランダムでアイテムを召喚
				//				if(held.getItem() == Items.BONE)
				//				{
				//					Random random = new Random();
				//					Item item = Item.REGISTRY.getRandomObject(random);
				//					int mxdmg = item.getMaxDamage();
				//					ItemStack stack = new ItemStack(item, 1, mxdmg > 0 ? random.nextInt(Math.abs(mxdmg)) : 0);
				//					Utils.giveItemToPlayer(player, stack);
				//				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
	{
		EntityPlayer player = event.getEntityPlayer();
		Entity target = event.getTarget();
		ItemStack held = event.getItemStack();
		World world = event.getWorld();
		Random random = new Random();

		if(player != null && target != null && !held.isEmpty() && !world.isRemote)
		{
			//ハサミに幸運つけると羊毛が増えるように
			if(held.getItem() instanceof ItemShears && held.isItemEnchanted())
			{
				NBTTagList enchants = held.getEnchantmentTagList();
				if(target instanceof EntitySheep)
				{
					EntitySheep sheep = (EntitySheep) target;
					for(int i = 0; i < enchants.tagCount(); i++)
					{
						NBTTagCompound enchant = enchants.getCompoundTagAt(i);

						if(enchant.getInteger("id") == LibEnchantId.FORTUNE && sheep.isShearable(held, world, sheep.getPosition()))
						{
							int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, held);
							int quantity = random.nextInt(level) * (level + 2);
							sheep.entityDropItem(new ItemStack(Blocks.WOOL, quantity, sheep.getFleeceColor().getMetadata()), 1.0F);

							event.setResult(Result.ALLOW);
						}
					}
				}
			}

			//空のスポーンエッグをmobに右クリックでバニラのスポーンエッグがあるやつだったらそれをドロップする。
			if(held.getItem() == Items.SPAWN_EGG && (!held.hasTagCompound() || !held.getTagCompound().hasKey("EntityTag")))
			{
				if(target instanceof EntityLiving)
				{
					EntityLiving targetLiving = (EntityLiving) target;
					if(Utils.hasSpawnEgg(targetLiving))
					{
						if(!Utils.isCreative(player))
							held.shrink(1);

						ItemStack newEgg = Utils.getSpawnEggItemStack(targetLiving.getClass(), 1);
						targetLiving.entityDropItem(newEgg, 1.0F);
						targetLiving.setDead();

						event.setResult(Result.ALLOW);
					}
				}
			}
		}
	}

	//トロッコにTNTとか持って右クリックするとそのトロッコになるように
	@SubscribeEvent
	public void onInteractMinecart(MinecartInteractEvent event)
	{
		EntityPlayer player = event.getPlayer();
		ItemStack held = event.getItem();
		World world = player.world;
		EntityMinecart cart = event.getMinecart();

		if(!ModCompat.COMPAT_QUARK)
		{
			if(cart instanceof EntityMinecartEmpty && cart.getPassengers().isEmpty())
			{
				if(!held.isEmpty() && insert.containsKey(held.getItem()))
				{
					player.swingArm(event.getHand());
					cart.world.playSound(player, cart.posX, cart.posY, cart.posZ, SoundEvents.ITEM_ARMOR_EQUIP_IRON, SoundCategory.NEUTRAL, 0.5F, 1.0F);
					if(!world.isRemote)
					{
						cart.setDead();
						world.spawnEntity(insert.get(held.getItem()).apply((EntityMinecartEmpty) cart));

						event.setCanceled(true);
						if(!Utils.isCreative(player))
							held.shrink(1);

						event.setResult(Result.ALLOW);
					}
				}
			}
		}
		else
			PSV2Core.LOGGER.info("Quark is loaded.");
	}
}
