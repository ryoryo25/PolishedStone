package ryoryo.polishedstone.event;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.config.ModConfig;

public class ModLivingDropsEvent {
	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event) {
		Random random = new Random();
		EntityLivingBase entity = event.getEntityLiving();
		World world = entity.world;
		DamageSource source = event.getSource();
		int lootingLevel = event.getLootingLevel();
		boolean isRecentlyHit = event.isRecentlyHit();
		boolean killedByPlayer = source.getTrueSource() instanceof EntityPlayer && isRecentlyHit;
		boolean dropEgg = killedByPlayer && random.nextInt(2000) == 0;
		boolean dropHead = random.nextFloat() * 100.0F < lootingLevel * 5 + 10;
		boolean hasLoot = lootingLevel > 1;

		if(!world.isRemote) {
			// スポーンエッグ
			if(entity instanceof EntityLiving) {
				EntityLiving living = (EntityLiving) entity;
				if(dropEgg && Utils.hasSpawnEgg(living))
					entity.entityDropItem(Utils.getSpawnEggItemStack(living.getClass(), 1), 1);
			}

			// 敵
			if(entity instanceof EntityZombie || entity instanceof EntityPigZombie) {
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.zombieBonesMax, ModConfig.zombieBonesMin));
				if(entity instanceof EntityZombie && dropHead)
					entity.entityDropItem(new ItemStack(Items.SKULL, 1, 2), 1);
			}

			if(entity instanceof EntityWitch)
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.witchBonesMax, ModConfig.witchBonesMin));

			if(entity instanceof EntitySkeleton) {
				EntityMob mob = (EntityMob) entity;

				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.skeletonBonesMax, ModConfig.skeletonBonesMin));

				if(mob.getHeldItemMainhand().getItem() instanceof ItemBow)
					entity.dropItem(Items.ARROW, EventHelper.getRandom(ModConfig.bowEnemiesArrowMax, ModConfig.bowEnemiesArrowMin));

				if(dropHead)
					entity.entityDropItem(new ItemStack(Items.SKULL, 1, 0), 1);
			}

			if(entity instanceof EntitySpider || entity instanceof EntityCaveSpider)
				entity.dropItem(Items.STRING, EventHelper.getRandom(ModConfig.spiderStringMax, ModConfig.spiderStringMin));

			if(entity instanceof EntityCreeper && dropHead)
				entity.entityDropItem(new ItemStack(Items.SKULL, 1, 4), 1);

			if(entity instanceof EntitySilverfish && isRecentlyHit && Utils.isOreDictLoaded("nuggetSilver"))
				entity.entityDropItem(Utils.getItemFromOreDict("nuggetSilver"), EventHelper.getRandom(ModConfig.silverfishSilverMax, ModConfig.silverfishSilverMin));

			if(entity instanceof EntityBlaze) {
				entity.dropItem(Items.FIRE_CHARGE, EventHelper.getRandom(ModConfig.blazeFireChargeMax, ModConfig.blazeFireChargeMin));
				entity.dropItem(Items.BLAZE_ROD, EventHelper.getRandom(ModConfig.blazeRodMax, ModConfig.blazeRodMin));
			}

			if(entity instanceof EntityWither) {
				entity.dropItem(Items.COAL, EventHelper.getRandom(ModConfig.witherCoalMax, ModConfig.witherCoalMin));
				if(dropHead)
					entity.entityDropItem(new ItemStack(Items.SKULL, 1, 1), 1);
			}

			if(entity instanceof EntityDragon && dropHead)
				entity.entityDropItem(new ItemStack(Items.SKULL, 1, 5), 1);

			// 友好
			if(entity instanceof EntityPig) {
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.pigBoneMax, ModConfig.pigBoneMin));
				entity.dropItem(Items.PORKCHOP, EventHelper.getRandom(ModConfig.pigMeatMax, ModConfig.pigMeatMin));
			}

			if(entity instanceof EntityCow) {
				entity.dropItem(Items.LEATHER, EventHelper.getRandom(ModConfig.cowLeatherMax, ModConfig.cowLeatherMin));
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.cowBoneMax, ModConfig.cowBoneMin));
				entity.dropItem(Items.BEEF, EventHelper.getRandom(ModConfig.cowMeatMax, ModConfig.cowMeatMin));
			}

			if(entity instanceof EntitySheep) {
				entity.dropItem(Items.LEATHER, EventHelper.getRandom(ModConfig.sheepLeatherMax, ModConfig.sheepLeatherMin));
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.sheepBoneMax, ModConfig.sheepBoneMin));
				entity.dropItem(Items.MUTTON, EventHelper.getRandom(ModConfig.sheepMeatMax, ModConfig.sheepMeatMin));
			}

			if(entity instanceof EntityChicken) {
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.chickenBoneMax, ModConfig.chickenBoneMin));
				entity.dropItem(Items.FEATHER, EventHelper.getRandom(ModConfig.chickenFeatherMax, ModConfig.chickenFeatherMin));
				entity.dropItem(Items.CHICKEN, EventHelper.getRandom(ModConfig.chickenMeatMax, ModConfig.chickenMeatMin));
			}

			if(entity instanceof EntityHorse) {
				// if(hasLoot)
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.horseBoneMax, ModConfig.horseBoneMin));
				entity.dropItem(Items.LEATHER, EventHelper.getRandom(ModConfig.horseLeatherMax, ModConfig.horseLeatherMin));
			}

			if(entity instanceof EntityRabbit) {
				entity.dropItem(Items.RABBIT, EventHelper.getRandom(ModConfig.rabbitMeatMax, ModConfig.rabbitMeatMin));
				entity.dropItem(Items.RABBIT_FOOT, EventHelper.getRandom(ModConfig.rabbitFootMax, ModConfig.rabbitFootMin));
				entity.dropItem(Items.RABBIT_HIDE, EventHelper.getRandom(ModConfig.rabbitHideMax, ModConfig.rabbitHideMin));
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.rabbitBoneMax, ModConfig.rabbitBoneMin));
			}

			if(entity instanceof EntityVillager) {
				entity.dropItem(Items.BONE, EventHelper.getRandom(ModConfig.villagerBoneMax, ModConfig.villagerBoneMin));
				entity.dropItem(Items.EMERALD, EventHelper.getRandom(ModConfig.villagerEmeraldMax, ModConfig.villagerEmeraldMin));
			}

			if(entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				// if(dropHead)
				entity.entityDropItem(Utils.getPlayerHead(player, 1), 1);
			}

			/*
			 * //TODO レアドロップ if(((event.source.getEntity() instanceof
			 * EntityPlayer)) && (event.recentlyHit)) { EntityOcelot mob =
			 * (EntityOcelot) event.entity;
			 * 
			 * if(event.entityLiving instanceof EntityOcelot) { if
			 * ((!mob.isTamed()) || (mob.getTameSkin() != 1) || (mob.bi())) {
			 * return; } if (random.nextInt(100) < event.lootingLevel * 10 + 5)
			 * { event.entityLiving.entityDropItem(new ItemStack(
			 * ItemsCore.mobDrop, 1, 3), 1); } }
			 * 
			 * EntityChicken mob = (EntityChicken) event.entity;
			 * 
			 * if(event.entityLiving instanceof EntityChicken) { BiomeGenHell
			 * biome = mob.k.a((int)mob.o, (int)mob.q); if ((biome != abn.j) ||
			 * (!mob.T()) || (mob.bi())) { return; } if
			 * (!mob.v_().equals("texture/crow.png")) { return; } if
			 * (random.nextInt(100) < event.lootingLevel * 3 + 1) {
			 * event.entityLiving.entityDropItem(new ItemStack(
			 * ItemsCore.mobDrop, 1, 4), 1); } } } }
			 */
			/*
			 * public boolean onEntityLivingUpdate(acq entity) { if ((entity
			 * instanceof rd)) { ady nbt = entity.getEntityData(); if
			 * (nbt.c("texture")) { String tex = nbt.j("texture"); if
			 * (!entity.v_().equals(tex)) { CE.setEntityTexture(entity, tex); }
			 * } } return false; }
			 */
		}
	}
}