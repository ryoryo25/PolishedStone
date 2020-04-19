package ryoryo.polishedstone.event;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.oredict.OreDictionary;
import ryoryo.polishedlib.util.ArithmeticUtils;
import ryoryo.polishedlib.util.NumericalConstant;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.util.LibNBTTag;
import ryoryo.polishedstone.util.ModCompat;
import ryoryo.polishedstone.util.References;

public class ModEventHandler
{
	//岩盤の硬さを高さで調整。
	@SubscribeEvent
	public void digSpeed(PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player = event.getEntityPlayer();
		Block block = event.getState().getBlock();
		int y = event.getPos().getY();
		float original = event.getOriginalSpeed();

		if(block != null)
		{
			if(block == Blocks.BEDROCK)
			{
				if(y < 40 && y > 0)
					event.setNewSpeed(original * y);

				if(y >= 40)
					event.setNewSpeed(original * 40.0F);
			}

			//松明持ってたら砂とか早く壊せる
			if(block instanceof BlockFalling)
			{
				for(ItemStack stack : Utils.getHeldItemStacks(player))
				{
					if(!stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.TORCH))
						event.setNewSpeed(original * 20.0F);
				}
			}
		}
	}

	@SubscribeEvent
	public void onBlockDrops(HarvestDropsEvent event)
	{
		Block block = event.getState().getBlock();
		boolean isSilk = event.isSilkTouching();
		List<ItemStack> drops = event.getDrops();
		Iterator<ItemStack> iter = drops.iterator();

		if(block != null && !isSilk)
		{
			//グロウストーンを確実に4つドロップ。
			if(block instanceof BlockGlowstone)
			{
				boolean removed = false;
				while(iter.hasNext())
				{
					Item item = iter.next().getItem();
					if(item == Items.GLOWSTONE_DUST)
					{
						iter.remove();
						removed = true;
					}
				}

				if(removed)
					drops.add(new ItemStack(Items.GLOWSTONE_DUST, 4));
			}
			//砂利から火打石を落とさないように
			if(block == Blocks.GRAVEL)
			{
				boolean hasGravel = false;
				while(iter.hasNext())
				{
					Item item = iter.next().getItem();
					if(item == Items.FLINT)
						iter.remove();
					else if(item == Item.getItemFromBlock(Blocks.GRAVEL))
						hasGravel = true;
				}

				if(!hasGravel)
					drops.add(new ItemStack(Blocks.GRAVEL));
			}
		}
	}

	@SubscribeEvent
	public void onPlayerLogIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;

		Utils.addChat(player, TextFormatting.BLUE + "" + TextFormatting.BOLD + "[PolishedStone V2]:" + TextFormatting.RESET + " " + player.getName() + ", Hello World!");
		Utils.addChat(player, TextFormatting.BLUE + "" + TextFormatting.BOLD + "[PolishedStone V2]:" + TextFormatting.RESET + " " + "VERSION: " + References.getVersion());

		//TODO metaとかnbtとか
		NBTTagCompound tag = event.player.getEntityData();
		NBTTagCompound data = Utils.getTagCompound(tag, EntityPlayer.PERSISTED_NBT_TAG);

		if(!data.getBoolean(LibNBTTag.STARTING_INVENTORY) && !ModConfig.startingInventory.isEmpty())
		{
			for(String loc : ModConfig.startingInventory)
			{
				Item item = Item.REGISTRY.getObject(new ResourceLocation(loc));
				Utils.giveItemToPlayer(player, new ItemStack(item, 1));
			}

			data.setBoolean(LibNBTTag.STARTING_INVENTORY, true);
			tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		World world = player.world;

		//プレイヤーのリーチを調整。
		if(ModConfig.extendReach)
		{
			//5.0(default value) + extra reach
			if(Utils.isCreative(player))
			{
				//default -> 5
				//10ブロック先(間に9ブロック)まで届くように
				player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeAllModifiers();
				player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(References.EXTRA_REACH, "Weapon modifier", 5.0D, 0));
			}
			else if(Utils.isSurvival(player))
			{
				//default -> 4.5
				//5ブロック先(間に4ブロック)まで届くように
				player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeAllModifiers();
				player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(References.EXTRA_REACH, "Weapon modifier", 0.5D, 0));
			}
		}

		//速く飛べる
		if(Utils.isCreative(player) && player.isSprinting())
		{
			player.capabilities.setFlySpeed(0.05F/*default*/ * ModConfig.creativeFlySpeedMultiply);
		}
		//ブロック置くスピードと歩く速さを合わせる
		player.capabilities.setPlayerWalkSpeed(0.1F/*default*/ * ArithmeticUtils.percentToDecimal(116.0F));

		//PickUpWidely
		if(!world.isRemote && EventHelper.pickUpWidelyToggle)
		{
			float hor = ModConfig.horizontalRangePUW;
			float ver = ModConfig.verticalRangePUW;
			AxisAlignedBB aabb = new AxisAlignedBB(player.posX, player.posY, player.posZ, player.posX, player.posY, player.posZ).grow(hor, ver, hor);
			List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, aabb);
			List<EntityXPOrb> xpOrbs = world.getEntitiesWithinAABB(EntityXPOrb.class, aabb);
			List<EntityArrow> arrows = world.getEntitiesWithinAABB(EntityArrow.class, aabb);

			if(!items.isEmpty())
			{
				for(EntityItem item : items)
				{
					if(!item.isDead && !item.cannotPickup())
						item.onCollideWithPlayer(player);
				}
			}

			if(ModConfig.pickUpXpOrbPUW && !xpOrbs.isEmpty())
			{
				for(EntityXPOrb xpOrb : xpOrbs)
				{
					if(!xpOrb.isDead)
						xpOrb.onCollideWithPlayer(player);
				}
			}

			if(ModConfig.pickUpArrowPUW && !arrows.isEmpty())
			{
				for(EntityArrow arrow : arrows)
				{
					if(!arrow.isDead)
						arrow.onCollideWithPlayer(player);
				}
			}
		}
	}

	@SubscribeEvent
	public void initLakeGen(PopulateChunkEvent.Populate event)
	{
		//森林バイオームとかに溶岩湖が出来ないように。
		if(!ModCompat.COMPAT_WILDFIRE)
		{
			if(event.getType() == Populate.EventType.LAVA && event.getResult() == Result.DEFAULT)
			{
				BlockPos pos = new BlockPos(event.getChunkX() * 16 + 8, 64, event.getChunkZ() * 16 + 8);
				Biome biome = event.getWorld().getBiomeForCoordsBody(pos);
				if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE))
				{
					event.setResult(Result.DENY);
				}
			}
		}
		else
			PSV2Core.LOGGER.info("No More Forest Fire is loaded.");
	}

	@SubscribeEvent
	public void initFluid(DecorateBiomeEvent.Decorate event)
	{
		//森林バイオームとかに溶岩湖が出来ないように。
		if(!ModCompat.COMPAT_WILDFIRE)
		{
			if(event.getType() == Decorate.EventType.LAKE_LAVA && event.getResult() == Result.DEFAULT)
			{
				Biome biome = event.getWorld().getBiomeForCoordsBody(event.getPos());
				if(BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.DENSE))
				{
					event.setResult(Result.DENY);
				}
			}
		}
		else
			PSV2Core.LOGGER.info("No More Forest Fire is loaded.");
	}

	@SubscribeEvent
	public void onItemDied(ItemExpireEvent event)
	{
		EntityItem entity = event.getEntityItem();
		ItemStack stack = entity.getItem();
		Item item = stack.getItem();
		Block block = Block.getBlockFromItem(item);
		World world = entity.world;
		Random random = new Random();

		//アイテムの苗木が消えたとき植えられたら勝手に植わるように。
		//また、何個かスタックされてるときはスタックサイズが1、減って5秒経つと消えるアイテムをスポーンさせる。植えられた苗木にそのスポーンさせた苗木が重なったら消える。
		if(entity != null && !stack.isEmpty() && item != null)
		{
			if(ModConfig.plantSaplingWhenDespawn && !world.isRemote && block != null && block instanceof BlockSapling)
			{
				BlockSapling sapling = (BlockSapling) block;
				if(sapling.canPlaceBlockAt(world, entity.getPosition()))
				{
					world.setBlockState(entity.getPosition(), sapling.getStateFromMeta(item.getMetadata(stack)));
					if(stack.getCount() > 1)
					{
						PSV2Core.LOGGER.info("cloning_tree");
						EntityItem extraItem = entity.dropItem(item, stack.getCount() - 1);
						extraItem.lifespan = (int) ArithmeticUtils.secondToTick(5.0F);
						//						extraItem.motionX = 10;
						world.spawnEntity(extraItem);
						PSV2Core.LOGGER.info("cloned_tree");
					}
				}
				//				event.setCanceled(true);
			}

			//卵が消えるときに生まれるように。
			if(item instanceof ItemEgg)
			{
				if(!world.isRemote)
				{
					if(random.nextInt(64) == 0)
					{
						int i = 1;

						if(random.nextInt(32) == 0)
							i = 4;

						for(int j = 0; j < i; ++j)
						{
							EntityChicken chicken = new EntityChicken(world);
							chicken.setGrowingAge(-24000);
							chicken.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, 0.0F);
							world.spawnEntity(chicken);
						}
					}
					if(stack.getCount() > 1)
					{
						PSV2Core.LOGGER.info("cloning_chicken");
						EntityItem extraItem = entity.dropItem(item, stack.getCount() - 1);
						extraItem.lifespan = (int) ArithmeticUtils.secondToTick(5.0F);
						//						extraItem.motionX = 10;
						world.spawnEntity(extraItem);
						PSV2Core.LOGGER.info("cloned_chicken");
					}
				}
				else
				{
					for(int k = 0; k < 8; ++k)
					{
						world.spawnParticle(EnumParticleTypes.ITEM_CRACK, entity.posX, entity.posY, entity.posZ, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, ((double) random.nextFloat() - 0.5D) * 0.08D, new int[]
						{ Item.getIdFromItem(Items.EGG) });
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void itemToss(ItemTossEvent event)
	{
		EntityItem entity = event.getEntityItem();
		ItemStack stack = entity.getItem();
		Item item = stack.getItem();
		EntityPlayer player = event.getPlayer();
		World world = player.world;

		if(entity != null && player != null)
		{
			//			if(stack != null && item != null)
			//			{
			//				if(ModConfig.plantSaplingWhenDespawn && Block.getBlockFromItem(item) instanceof BlockSapling)
			//				{
			//					entity.lifespan = (int) Utils.secondToTick(5.0F);
			//					event.setResult(Result.ALLOW);
			//				}
			//				if(item instanceof ItemEgg)
			//				{
			//					entity.lifespan = (int) Utils.secondToTick(5.0F);
			//				}
			//			}
			entity.addTag("dropped_by_player");

			//TODO アイテム捨てたときにポコッて音出したい
			if(world.isRemote)
				world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.3F, 10.0F);

			player.swingArm(EnumHand.MAIN_HAND);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		Entity entity = event.getEntity();
		World world = event.getWorld();

		if(entity != null && !world.isRemote)
		{
			//鉱石辞書統一機能
			if(entity instanceof EntityItem)
			{
				EntityItem entityItem = (EntityItem) entity;
				ItemStack stack = entityItem.getItem();
				List<ItemStack> oredict = Utils.findOreDict(stack);
				if(oredict != null && oredict.size() > 0 /*&& !Comparator.UNIFY.compareDisallow(stack.getItem())*/)
				{
					// ドロップアイテムの書き換え
					for(ItemStack i : oredict)
					{
						Item replace = i.getItem();
						//						if(!Comparator.UNIFY.compareDisallow(replace))
						//						{
						ItemStack newItem = new ItemStack(replace, stack.getCount(), i.getMetadata(), i.getTagCompound());
						entityItem.setItem(newItem);
						break;
						//							}
					}
				}

				if(ModConfig.instantItemPickUp && !entityItem.getTags().contains("dropped_by_player"))
				{
					entityItem.setPickupDelay(0);
				}
			}

			if(entity instanceof EntityPig)
			{
				EntityPig pig = (EntityPig) entity;

				pig.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
			}
		}
	}

	@SubscribeEvent
	public void onCreateFluidSource(BlockEvent.CreateFluidSourceEvent event)
	{
		if(ModConfig.infinityLava && event.getState().getBlock() == Blocks.FLOWING_LAVA)
		{
			event.setResult(Result.ALLOW);
		}
	}

	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load event)
	{
		World world = event.getWorld();
		WorldInfo worldinfo = world.getWorldInfo();

		if(world.getWorldType() == WorldType.FLAT)
		{
			worldinfo.setCleanWeatherTime(NumericalConstant.INT_MAX);
			worldinfo.setRainTime(0);
			worldinfo.setThunderTime(0);
			worldinfo.setRaining(false);
			worldinfo.setThundering(false);
		}
	}

	//TODO
	@SubscribeEvent
	public void onDimensionLoad(WorldEvent.Load event)
	{
		WorldProvider provider = event.getWorld().provider;
		if(ModConfig.waterDontEvaporate && provider instanceof WorldProviderHell)
		{
			ObfuscationReflectionHelper.setPrivateValue(WorldProvider.class, provider, false, "field_76575_d", "isHellWorld");
		}
	}

	@SubscribeEvent
	public void addAnvilRecipe(AnvilUpdateEvent event)
	{
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		Utils.addChat("Hey!1");
		if(left.getItem() == Items.IRON_INGOT && right.getItem() == Items.DYE && right.getItemDamage() == EnumColor.YELLOW.getDyeNumber())
		{
			Utils.addChat("Hey!2");
			//			event.setCost(5);//経験値
			//			event.setMaterialCost(5);//右スロットのアイテム数
			//			event.setOutput(new ItemStack(Items.GOLD_INGOT, 1));
		}
		if(left.getItem() == Items.SPAWN_EGG /*&& (!left.hasTagCompound() || !left.getTagCompound().hasKey("EntityTag"))*/ && right == new ItemStack(Blocks.SKULL) && right.getItemDamage() == 2)
		{
			Utils.addChat("Hey!3");
			event.setCost(5);

			event.setOutput(Utils.getSpawnEggItemStack(EntityZombie.class, 1));
		}
	}

	@SubscribeEvent
	public void onTooltipRender(ItemTooltipEvent event)
	{
		List<String> tooltip = event.getToolTip();
		//isAdvancedになってるか、シフト押してる
		boolean flag = !event.getItemStack().isEmpty() && (event.getFlags().isAdvanced() || GuiScreen.isShiftKeyDown());

		if(flag)
		{
			//鉱石辞書の登録内容をF3+Hかシフトの状態なら見えるように
			int[] oreIDs = OreDictionary.getOreIDs(event.getItemStack());
			tooltip.add(TextFormatting.DARK_GRAY + "Ore Dictionary Entries" + ":");
			if(oreIDs.length > 0)
			{
				for(int oreID : oreIDs)
				{
					tooltip.add(TextFormatting.DARK_GRAY + " - " + OreDictionary.getOreName(oreID));
				}
			}
			else
				tooltip.add(TextFormatting.DARK_GRAY + " - There is No Ore Dictionary Entries.");
		}
	}

	//TODO
	@SubscribeEvent
	public void placePumpkinAnywhere(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		ItemStack held = event.getItemStack();
		World world = event.getWorld();
		BlockPos targetingPos = event.getPos();
		BlockPos placePos = targetingPos.offset(event.getFace());
		Block block = world.getBlockState(placePos).getBlock();
		Vec3d hitVec = event.getHitVec();

//		Utils.addChat(player, String.valueOf(world.isRemote));

		if(player != null && !held.isEmpty() && held.getItem() != null)
		{
			if(held.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN))
			{
				if(!world.isSideSolid(placePos.down(), EnumFacing.UP) && block.isReplaceable(world, placePos))
				{
					IBlockState state = Blocks.PUMPKIN.getStateForPlacement(world, placePos, event.getFace(), (float) hitVec.x, (float) hitVec.y, (float) hitVec.z, 0, player);
					SoundType soundtype = Blocks.PUMPKIN.getSoundType(state, world, placePos, player);
					world.playSound(player, targetingPos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					player.swingArm(event.getHand());

					if(!world.isRemote)
					{
						Utils.addChat(player, "wow");
						world.setBlockState(placePos, state);
						event.setUseItem(Result.ALLOW);
					}
				}
			}
		}

//		Utils.addChat(player, "held: " + String.valueOf(!held.isEmpty()));
//
//		if(event.getSide() == Side.CLIENT)
//			Utils.addChat(player, "client side");
//		else
//			Utils.addChat(player, "server side");
//
//		Utils.addChat(player, "-----------------");
	}

	@SubscribeEvent
	public void infinityWithoutArrow(ArrowNockEvent event)
	{
		ItemStack bow = event.getBow();
		boolean infinity = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;

		if(infinity)
		{
			event.getEntityPlayer().setActiveHand(event.getHand());
			event.setAction(new ActionResult<ItemStack>(EnumActionResult.SUCCESS, bow));
		}
	}

	//	@SubscribeEvent
	//	public void onEntityDied(LivingDeathEvent event)
	//	{
	//		Entity entity = event.getEntity();
	//		World world = entity.worldObj;
	//		DamageSource source = event.getSource();
	//
	//		if(entity instanceof EntityItem)
	//		{
	//			EntityItem entityItem = (EntityItem) entity;
	//			ItemStack stack = entityItem.getEntityItem();
	//			if(source == DamageSource.lava && stack != null && stack.getItem() != null && stack.getItem() == Items.ENDER_PEARL && !world.isRemote)
	//			{
	//				EntityItem newItem = new EntityItem(world, entityItem.posX, entityItem.posY + 2, entityItem.posZ, new ItemStack(Items.ENDER_EYE));
	//				newItem.motionX = entityItem.motionX;
	//				newItem.motionY = entityItem.motionY;
	//				newItem.motionZ = entityItem.motionZ;
	//				world.spawnEntityInWorld(newItem);
	//			}
	//		}
	//	}

	//	@SubscribeEvent
	//	public void onItemDied(ItemExpireEvent event)
	//	{
	//
	//		EntityItem entityItem = event.getEntityItem();
	//		ItemStack itemStack = entityItem.getEntityItem();
	//		World world = entityItem.worldObj;
	//		if(entityItem.isInLava() && itemStack != null && itemStack.getItem() != null && itemStack.getItem() == Items.ENDER_PEARL && !world.isRemote)
	//		{
	//			EntityItem newItem = new EntityItem(world, entityItem.posX, entityItem.posY + 1, entityItem.posZ, new ItemStack(Items.ENDER_EYE));
	////			newItem.motionX = entityItem.motionX;
	////			newItem.motionY = entityItem.motionY;
	////			newItem.motionZ = entityItem.motionZ;
	//			world.spawnEntityInWorld(newItem);
	//			//				Block.spawnAsEntity(entity.worldObj, entity.getPosition(), new ItemStack(Items.ENDER_EYE, 1));
	//		}
	//	}

	//	@SubscribeEvent
	//	public void onEntityAdded(ItemTossEvent event)
	//	{
	//
	//	}

	//	@SubscribeEvent
	//	public void onEntityAdded(EntityJoinWorldEvent event)
	//	{
	//		Entity entity = event.getEntity();
	//		World world = event.getWorld();
	//		if(entity != null && entity instanceof EntityItem)
	//		{
	//			EntityItem entityItem = (EntityItem) entity;
	//			ItemStack itemStack = entityItem.getEntityItem();
	//			if(/*entityItem.isInLava() && */itemStack != null && itemStack.getItem() != null && itemStack.getItem() == Items.ENDER_PEARL && !world.isRemote)
	//			{
	//				EntityItem newItem = new EntityItem(world, entityItem.posX, entityItem.posY, entityItem.posZ, new ItemStack(Items.ENDER_EYE, 1));
	//				newItem.motionX = entityItem.motionX;
	//				newItem.motionY = entityItem.motionY;
	//				newItem.motionZ = entityItem.motionZ;
	//				newItem.setDefaultPickupDelay();
	//				entityItem.setDead();
	//				world.spawnEntityInWorld(newItem);
	//			}
	//		}
	//	}

	//TODO ウィザーのスポーンエッグを使ったときに実績を解除するように
	//	@SubscribeEvent
	//	public void onRightClick(/*PlayerUseItemEvent*/PlayerInteractEvent event)
	//	{
	//		//			Block block;
	//		//			if((event.action == Action.RIGHT_CLICK_BLOCK) && (event.entityPlayer.getHeldItem() == new ItemStack(Register.itemTest)))
	//		//			{
	//		//				block = event.world.getBlock(event.x, event.y, event.z);
	//		//
	//		//				block.setResistance(10000F);
	//		//			}
	//
	//		EntityPlayer entityPlayer = event.entityPlayer;
	//		World world = event.world;
	//
	//		if(world.isRemote || event.action != Action.RIGHT_CLICK_BLOCK)
	//		{
	//			return;
	//		}
	//		if(entityPlayer.getHeldItem() != null || entityPlayer.getHeldItem().getItem() != Items.spawn_egg || entityPlayer.getHeldItem().getItemDamage() != 64 || entityPlayer.getHeldItem() == null)
	//		{
	//			return;
	//		}
	//		if(entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() == Items.spawn_egg && entityPlayer.getHeldItem().getItemDamage() == 64)
	//		{
	//			Utils.triggerAchievement(event.entityPlayer, AchievementList.field_150963_I);
	//		}
	//	}
}
