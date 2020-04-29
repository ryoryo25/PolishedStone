package ryoryo.polishedstone.event;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockGlowstone;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.oredict.OreDictionary;
import ryoryo.polishedlib.util.ArithmeticUtils;
import ryoryo.polishedlib.util.NumericalConstant;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.util.LibNBTTag;
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
				Utils.getHeldItemStacks(player).stream()
				.filter(stack -> stack.isItemEqual(new ItemStack(Blocks.TORCH)))
				.forEach(stack -> event.setNewSpeed(original * 20.0F));
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

		Utils.sendChat(player, TextFormatting.BLUE + "" + TextFormatting.BOLD + "[PolishedStone V2]:" + TextFormatting.RESET + " " + player.getName() + ", Hello World!");
		Utils.sendChat(player, TextFormatting.BLUE + "" + TextFormatting.BOLD + "[PolishedStone V2]:" + TextFormatting.RESET + " " + "VERSION: " + References.getVersion());

		//TODO metaとかnbtとか
		NBTTagCompound tag = event.player.getEntityData();
		NBTTagCompound data = Utils.getTagCompound(tag, EntityPlayer.PERSISTED_NBT_TAG);

		if(!data.getBoolean(LibNBTTag.STARTING_INVENTORY) && !ModConfig.startingInventory.isEmpty())
		{
			ModConfig.startingInventory.stream()
			.map(location -> Item.REGISTRY.getObject(new ResourceLocation(location)))
			.forEach(item -> Utils.giveItemToPlayer(player, new ItemStack(item, 1)));

			data.setBoolean(LibNBTTag.STARTING_INVENTORY, true);
			tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();

		if(target instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) target;
			World world = player.world;

			//プレイヤーのリーチを調整。
			if(ModConfig.extendReach)
			{
				double reachDistance = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

				//5.0(default value) + extra reach
				if(Utils.isCreative(player) && reachDistance != 10.0D)
				{
					//default -> 10.0
					//10ブロック先(間に9ブロック)まで届くように
					player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(References.EXTRA_REACH);
					player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(References.EXTRA_REACH, "Reach distance modifier", 5.0D, 0));
				}

				if(Utils.isSurvival(player) && reachDistance != 5.5D)
				{
					//default -> 5.5
					//5ブロック先(間に4ブロック)まで届くように
					player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(References.EXTRA_REACH);
					player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(new AttributeModifier(References.EXTRA_REACH, "Reach distance modifier", 0.5D, 0));
				}
			}

			//速く飛べる
			//0.05(default value) * factor
			if(Utils.isCreative(player) && player.capabilities.getFlySpeed() != 0.05F * ModConfig.creativeFlySpeedMultiply)
			{
				player.capabilities.setFlySpeed(0.05F * ModConfig.creativeFlySpeedMultiply);
			}
			if(!Utils.isCreative(player) && player.capabilities.getFlySpeed() != 0.05F)
			{
				player.capabilities.setFlySpeed(0.05F);
			}

			//cancel inertia
			if (player.moveForward == 0 && player.moveStrafing == 0 && player.capabilities.isFlying)
			{
				player.motionX *= 0.5;
				player.motionZ *= 0.5;
			}
//			player.capabilities.setFlySpeed(0.022F);

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
					items.stream()
					.filter(item -> (!item.isDead && !item.cannotPickup()))
					.forEach(item -> item.onCollideWithPlayer(player));
				}

				if(ModConfig.pickUpXpOrbPUW && !xpOrbs.isEmpty())
				{
					xpOrbs.stream()
					.filter(xpOrb -> !xpOrb.isDead)
					.forEach(xpOrb -> xpOrb.onCollideWithPlayer(player));
				}

				if(ModConfig.pickUpArrowPUW && !arrows.isEmpty())
				{
					arrows.stream()
					.filter(arrow -> !arrow.isDead)
					.forEach(arrow -> arrow.onCollideWithPlayer(player));
				}
			}
		}
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
						ItemStack newItem = new ItemStack(i.getItem(), stack.getCount(), i.getMetadata(), i.getTagCompound());
						entityItem.setItem(newItem);
						break;
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
		Utils.sendChat("Hey!1");
		if(left.getItem() == Items.IRON_INGOT && right.getItem() == Items.DYE && right.getItemDamage() == EnumColor.YELLOW.getDyeNumber())
		{
			Utils.sendChat("Hey!2");
			//			event.setCost(5);//経験値
			//			event.setMaterialCost(5);//右スロットのアイテム数
			//			event.setOutput(new ItemStack(Items.GOLD_INGOT, 1));
		}
		if(left.getItem() == Items.SPAWN_EGG /*&& (!left.hasTagCompound() || !left.getTagCompound().hasKey("EntityTag"))*/ && right == new ItemStack(Blocks.SKULL) && right.getItemDamage() == 2)
		{
			Utils.sendChat("Hey!3");
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
				//Stream.of(oreIDs)がなぜかint[]のstreamになっちゃう
				//プリミティブ配列だとStream<int[]>になっちゃうみたい
				//プリミティブ型の場合はIntStreamとか個別の物が使われてるから
				//Ref: https://www.codeflow.site/ja/article/java8__java-how-to-convert-array-to-stream
				Arrays.stream(oreIDs).forEach(oreID ->
				tooltip.add(TextFormatting.DARK_GRAY + " - " + OreDictionary.getOreName(oreID)));
			}
			else
				tooltip.add(TextFormatting.DARK_GRAY + " - There is No Ore Dictionary Entries.");
		}
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
//	public void test(PlayerInteractEvent.RightClickBlock event)
//	{
//		EntityPlayer player = event.getEntityPlayer();
//		ItemStack held = event.getItemStack();
//		BlockPos targetPos = event.getPos().up();
//		World world = event.getWorld();
//
//		if(!held.isEmpty() && held.getItem() == Items.STICK)
//		{
//			world.setBlockState(targetPos, Blocks.DIAMOND_BLOCK.getDefaultState());
//			world.setBlockState(Utils.getRightPos(targetPos, player.getHorizontalFacing()), Blocks.DIAMOND_BLOCK.getDefaultState());
//			world.setBlockState(Utils.getLeftPos(targetPos, player.getHorizontalFacing()), Blocks.DIAMOND_BLOCK.getDefaultState());
//		}
//	}

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
