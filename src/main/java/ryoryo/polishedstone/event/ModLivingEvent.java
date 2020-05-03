package ryoryo.polishedstone.event;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.util.LibNBTTag;
import ryoryo.polishedstone.util.ModCompat;

public class ModLivingEvent {
	@SubscribeEvent
	public void onTrampleFarmland(FarmlandTrampleEvent event) {
		Entity entity = event.getEntity();

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			boolean hasArmor = Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_HELMET);
			hasArmor = hasArmor && Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_CHEST);
			hasArmor = hasArmor && Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_LEG);
			hasArmor = hasArmor && Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_BOOTS);

			if(hasArmor)
				event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public void onFall(LivingFallEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		World world = entity.world;

		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			// ハスの葉に落下した際、落下ダメージを無効化するように
			if(world.getBlockState(player.getPosition()).getBlock() == Blocks.WATERLILY)
				event.setDamageMultiplier(0.0F);
		}
	}

	@SubscribeEvent
	public void onSpawn(LivingSpawnEvent.CheckSpawn event) {
		EntityLivingBase living = event.getEntityLiving();
		World world = event.getWorld();
		float y = event.getY();

		// 敵対mobを、51以上80以下でまったくわかなく、40未満100以上で50%くらい、MaterialがWOODかLEAVESで全く、村の中にも湧かなくする。
		if(!ModCompat.COMPAT_CUSTOM_SPAWN) {
			if(living != null && living instanceof IMob) {
				if(y > 50.0F && y <= 80.0F) {
					event.setResult(Result.DENY);
				}
				else {
					boolean flag = true;
					if(y > 40.0F && y <= 100.0F && world.rand.nextFloat() < 0.5F) {
						flag = false;
					}
					IBlockState state;
					if(flag) {
						state = world.getBlockState(living.getPosition().down());
						if(state.getMaterial() == Material.WOOD || state.getMaterial() == Material.LEAVES) {
							flag = false;
						}
					}
					if(flag && world.getVillageCollection() != null) {
						for(Village vil : world.getVillageCollection().getVillageList()) {
							if(vil.getCenter().distanceSq(living.getPosition()) < 9216.0D) {
								flag = false;
							}
						}
					}
					if(!flag) {
						event.setResult(Result.DENY);
					}
				}
			}
		}
		else
			PSV2Core.LOGGER.info("Custom Spawn is loaded.");

		// フラットワールドの40以下でスライムがわかなくする。
		if(living != null && living instanceof EntitySlime && world != null && world.getWorldType() == WorldType.FLAT && event.getY() <= 40) {
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onLiving(LivingUpdateEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		if(living != null) {
			World world = living.world;

			// 村にいる敵対mobをデスポーンさせる。
			if(!ModCompat.COMPAT_CUSTOM_SPAWN) {
				if(living instanceof EntityZombie && world.getVillageCollection() != null) {
					for(Village vil : world.getVillageCollection().getVillageList()) {
						if(vil.getCenter().distanceSq(living.getPosition()) < 1024.0D) {
							living.setDead();
						}
					}
				}
			}
			else
				PSV2Core.LOGGER.info("Custom Spawn is loaded.");

			if(living instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) living;
				ItemStack plate = player.inventory.armorInventory.get(2);
				ItemStack belt = player.inventory.armorInventory.get(1);

				if(!plate.isEmpty() && plate.getItem() != null) {
					// 作業着を着ていたら梯子を早く登れるように。
					if(plate.getItem() == Register.ITEM_ARMOR_WORK_CLOTHES || plate.getItem() == Register.ITEM_ARMOR_INVINCIBLE_CHEST) {
						if(!player.onGround && player.isOnLadder()) {
							if(player.collidedHorizontally) {
								player.motionY *= 3;
								// player.motionY = 0.32D;
							}
						}
					}
					// ライフジャケットを着ていたら水、溶岩に沈まないように。
					else if(plate.getItem() == Register.ITEM_ARMOR_LIFE_JACKET) {
						if(player.isInWater() || player.isInLava()) {
							player.motionY += 0.038D;
						}
					}
				}

				// if(belt != null && belt.getItem() != null && belt.getItem()
				// == DCsIronChain.toolBag)
				// {
				// if(DCsIronChain.proxy.isGuiKeyDown())
				// {
				// if(!push)
				// {
				// PacketHandlerDC.INSTANCE.sendToServer(new
				// MessageGuiKey((byte) 1));
				// push = true;
				// }
				// }
				// else
				// {
				// push = false;
				// }
				// }

				// 安全ベルトをしていたら高い所から落ちても安心。
				if(!belt.isEmpty() && belt.getItem() != null && belt.getItem() == Register.ITEM_ARMOR_SAFETY_BELT) {
					boolean success = false;
					if(!world.isRemote) {
						NBTTagCompound nbt = belt.getTagCompound();
						boolean flag = false;
						double pX = 0.5D;
						double pY = 0.5D;
						double pZ = 0.5D;
						int dim = 0;

						if(nbt != null) {
							pX = nbt.getInteger(LibNBTTag.SAFETY_X) + 0.5D;
							pY = nbt.getInteger(LibNBTTag.SAFETY_Y) + 0.5D;
							pZ = nbt.getInteger(LibNBTTag.SAFETY_Z) + 0.5D;
							dim = nbt.getInteger(LibNBTTag.SAFETY_DIM);
							flag = nbt.hasKey(LibNBTTag.SAFETY_DIM);
						}

						if(player.onGround) {
							if(!player.isBeingRidden() && player.getRidingEntity() == null) {
								int newX = MathHelper.floor(player.posX);
								int newY = MathHelper.floor(player.posY);
								int newZ = MathHelper.floor(player.posZ);

								if(world.isSideSolid(new BlockPos(newX, newY - 1, newZ), EnumFacing.UP)) {
									if(nbt == null)
										nbt = new NBTTagCompound();
									nbt.setInteger(LibNBTTag.SAFETY_X, newX);
									nbt.setInteger(LibNBTTag.SAFETY_Y, newY);
									nbt.setInteger(LibNBTTag.SAFETY_Z, newZ);
									nbt.setInteger(LibNBTTag.SAFETY_DIM, MathHelper.floor(Utils.getDimensionId(world)));
									nbt.setString(LibNBTTag.SAFETY_DIM_NAME, Utils.getDimensionName(world));
									belt.setTagCompound(nbt);
								}
							}
						}

						if(flag && !player.onGround && !player.isInWater()) {
							if(player.isBeingRidden() || player.getRidingEntity() != null || Utils.isFlying(player)) {
								player.fallDistance = 0.0F;
								if(player.getRidingEntity() != null && !(player.getRidingEntity() instanceof EntityLivingBase))
									player.getRidingEntity().fallDistance = 0.0F;
								flag = false;
							}
							else if(Utils.getDimensionId(world) != dim) {
								flag = false;
							}

							if(flag && player.fallDistance > ModConfig.harnessCheckDistance) {
								success = true;
								player.setPositionAndUpdate(pX, pY, pZ);
								player.fallDistance = 0.0F;
							}
						}
					}

					if(success) {
						world.playSound(player, player.getPosition(), Register.SOUND_IRON_CHAIN, SoundCategory.PLAYERS, 1.0F, 0.7F);
						if(PSV2Core.isDebug)
							Utils.sendChat(player, "Fallen!");
					}
				}
			}

			if(!ModCompat.COMPAT_QUARK) {
				if(ModConfig.sunBurnsBabyZombies && living instanceof EntityZombie) {
					EntityZombie zombie = (EntityZombie) living;

					if(world.isDaytime() && !world.isRemote && zombie.isChild()) {
						float f = zombie.getBrightness();
						BlockPos posz = zombie.getRidingEntity() instanceof EntityBoat ? new BlockPos(zombie.posX, Math.round(zombie.posY), zombie.posZ).up() : new BlockPos(zombie.posX, Math.round(zombie.posY), zombie.posZ);

						if(f > 0.5F && world.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && world.canSeeSky(posz)) {
							boolean flag = true;
							ItemStack helm = zombie.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

							if(!helm.isEmpty()) {
								if(helm.isItemStackDamageable()) {
									helm.setItemDamage(helm.getItemDamage() + world.rand.nextInt(2));

									if(helm.getItemDamage() >= helm.getMaxDamage()) {
										zombie.renderBrokenItemStack(helm);
										zombie.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
									}
								}

								flag = false;
							}

							if(flag)
								zombie.setFire(8);
						}
					}
				}
			}
			else
				PSV2Core.LOGGER.info("Quark is loaded.");

			if(ModConfig.sunBurnsCreepers && living instanceof EntityCreeper) {
				EntityCreeper creeper = (EntityCreeper) living;

				if(world.isDaytime() && !world.isRemote) {
					float f = creeper.getBrightness();
					BlockPos posz = creeper.getRidingEntity() instanceof EntityBoat ? new BlockPos(creeper.posX, Math.round(creeper.posY), creeper.posZ).up() : new BlockPos(creeper.posX, Math.round(creeper.posY), creeper.posZ);

					if(f > 0.5F && world.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && world.canSeeSky(posz)) {
						creeper.setFire(8);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		DamageSource source = event.getSource();

		if(living != null) {
			World world = living.world;

			if(living instanceof EntityCreeper) {
				EntityCreeper creeper = (EntityCreeper) living;
				if(source == DamageSource.ON_FIRE) {
					float f = creeper.getPowered() ? 2.0F : 1.0F;
					world.createExplosion(creeper, creeper.posX, creeper.posY, creeper.posZ, (float) /*
																										 * creeper
																										 * .
																										 * explosionRadius
																										 */3 * f, ModConfig.deathExplosionDestroyTerrain);
				}
			}
		}
	}

	@SubscribeEvent
	public void onBabyBorn(BabyEntitySpawnEvent event) {
		// mobがたくさん子供を産むように
		EntityAgeable entity = event.getChild();
		EntityLiving parentA = event.getParentA();
		EntityLiving parentB = event.getParentB();
		World world = entity.getEntityWorld();

		if(!world.isRemote && parentA instanceof EntityAnimal && parentB instanceof EntityAnimal) {
			EntityAnimal mother = (EntityAnimal) parentA;
			EntityAnimal father = (EntityAnimal) parentB;
			int n = EventHelper.getBabyNum(entity);
			// TODO
			// if(n == 0)
			// event.setCanceled(true);
			for(int i = 0; i < n; i++) {
				EntityAgeable child = mother.createChild(father);

				if(child != null) {
					child.setGrowingAge(-24000);
					child.setPositionAndRotation(mother.posX, mother.posY, mother.posZ, 0.0F, 0.0F);
					world.spawnEntity(child);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
		// Fence Jumper機能
		EntityLivingBase entity = event.getEntityLiving();
		if(!ModCompat.COMPAT_FENCE_JUMPER && entity instanceof EntityPlayerSP) {
			EntityPlayerSP player = (EntityPlayerSP) entity;
			if(player.movementInput.jump && EventHelper.isPlayerNextToFence(player)) {
				player.motionY += 0.05D;
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttacked(LivingAttackEvent event) {
		// Invincible装備用にダメージを無効化
		EntityLivingBase entity = event.getEntityLiving();
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			boolean hasArmor = Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_HELMET);
			hasArmor = hasArmor && Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_CHEST);
			hasArmor = hasArmor && Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_LEG);
			hasArmor = hasArmor && Utils.isEquippedArmor(player, Register.ITEM_ARMOR_INVINCIBLE_BOOTS);

			if(hasArmor)
				event.setCanceled(true);
		}
	}
}