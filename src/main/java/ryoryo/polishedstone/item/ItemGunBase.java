package ryoryo.polishedstone.item;

// import net.minecraft.entity.Entity;
// import net.minecraft.entity.EntityLivingBase;
// import net.minecraft.entity.player.EntityPlayer;
// import net.minecraft.item.EnumAction;
// import net.minecraft.item.Item;
// import net.minecraft.item.ItemBow;
// import net.minecraft.item.ItemStack;
// import net.minecraft.nbt.NBTTagCompound;
// import net.minecraft.util.ActionResult;
// import net.minecraft.util.EnumActionResult;
// import net.minecraft.util.EnumHand;
// import net.minecraft.util.ResourceLocation;
// import net.minecraft.util.SoundCategory;
// import net.minecraft.util.SoundEvent;
// import net.minecraft.util.math.MathHelper;
// import net.minecraft.world.World;
// import ryoryo.util.Utils;
//
// public class ItemGunBase extends ItemBow
// {
//
// public static final String Tag_State = "State";
// public static final String Tag_MaxLoad = "MaxLoad";
// public static final String Tag_Magazin = "Magazin";
// public static final String Tag_Burst = "Burst";
// public static final String Tag_Cycle = "Cycle";
// public static final String Tag_ReloadTime = "ReloadTime";
// public static final String Tag_BurstCount = "BurstCount";
// public static final String Tag_CycleCount = "CycleCount";
// public static final String Tag_Efficiency = "Efficiency";
// public static final String Tag_Stability = "Stability";
// public static final String Tag_StabilityY = "StabilityY";
// public static final String Tag_StabilityYO = "StabilityYO";
// public static final String Tag_StabilityP = "StabilityP";
// public static final String Tag_StabilityPO = "StabilityPO";
// public static final String Tag_Accuracy = "Accuracy";
//
// protected static byte State_Ready = 0x00;
// protected static byte State_Empty = 0x10;
// protected static byte State_Reload = 0x20;
// protected static byte State_ReloadTac = 0x30;
// protected static byte State_ReloadCre = 0x40;
// protected static byte State_ReleseMag = 0x50;
// protected static byte State_ReloadEnd = 0x60;
//
// /** 空打ちした時の音 */
// public SoundEvent soundEmpty;
// /** マガジンを外した時（リロード開始時）の音 */
// public SoundEvent soundRelease;
// /** マガジンを入れた時（リロード完了時）の音 */
// public SoundEvent soundReload;
// /** ボリューム */
// public float volume;
//
// /** リロードに掛る時間 */
// public int reloadTime;
// /** 最大点射数 */
// public int burstCount;
// /** 発射インターバル */
// public short cycleCount;
//
// /** 弾速のエネルギー効率 */
// public float efficiency;
// /** 銃身の安定性 */
// public float stability;
// /** 発射時の腕の動き左右、乱数 */
// public float stabilityYaw;
// /** 発射時の腕の動き左右、固定値 */
// public float stabilityYawOffset;
// /** 発射時の腕の動き上下、乱数 */
// public float stabilityPitch;
// /** 発射時の腕の動き上下、固定値 */
// public float stabilityPitchOffset;
// /** 集弾性 */
// public float accuracy;
// /** 使用可能弾薬名 */
// public String[] bullets;
// /** アイコン名称 */
// public String[] iconNames;
//
// protected Item[] ammos;
//
// public ItemGunBase()
// {
// maxStackSize = 1;
// setFull3D();
//
// volume = 0.5F;
// reloadTime = 40;
// burstCount = 0;
// cycleCount = 2;
// efficiency = 1.0F;
// stability = 1.0F;
// stabilityPitch = 5.0F;
// stabilityPitchOffset = 5.0F;
// stabilityYaw = 3.0F;
// stabilityYawOffset = 0F;
// accuracy = 1.0F;
//
// iconNames = new String[]
// { "", "", "" };
// bullets = new String[]
// { "" };
//
// GunsBase.appendItem(this);
// }
//
// public void init()
// {
// ammos = new Item[bullets.length];
// for(int li = 0; li < bullets.length; li++)
// {
// ammos[li] = (Item) Item.REGISTRY.getObject(new
// ResourceLocation(bullets[li]));
// }
// }
//
// public void playSoundEmpty(World world, EntityPlayer player, ItemStack gun)
// {
// world.playSound(player, player.posX, player.posY, player.posZ, soundEmpty,
// SoundCategory.PLAYERS, volume, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
// }
//
// public void playSoundRelease(World world, EntityPlayer player, ItemStack gun)
// {
// world.playSound(player, player.posX, player.posY, player.posZ, soundRelease,
// SoundCategory.PLAYERS, volume, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
// }
//
// public void playSoundReload(World world, EntityPlayer player, ItemStack gun)
// {
// world.playSound(player, player.posX, player.posY, player.posZ, soundReload,
// SoundCategory.PLAYERS, volume, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
// }
//
// public int getReloadTime(ItemStack gun)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_ReloadTime))
// {
// return gun.getTagCompound().getInteger(Tag_ReloadTime);
// }
//
// return reloadTime;
// }
//
// public int getBurstCount(ItemStack gun)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_BurstCount))
// {
// return gun.getTagCompound().getInteger(Tag_BurstCount);
// }
//
// return burstCount;
// }
//
// public short getCycleCount(ItemStack gun)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_CycleCount))
// {
// return gun.getTagCompound().getShort(Tag_CycleCount);
// }
//
// return cycleCount;
// }
//
// public float getEfficiency(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_Efficiency))
// {
// return gun.getTagCompound().getFloat(Tag_Efficiency);
// }
//
// return efficiency;
// }
//
// public float getStability(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_Stability))
// {
// return gun.getTagCompound().getFloat(Tag_Stability);
// }
//
// return stability;
// }
//
// public float getStabilityY(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_StabilityY))
// {
// return gun.getTagCompound().getFloat(Tag_StabilityY);
// }
//
// return stabilityYaw;
// }
//
// public float getStabilityYO(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_StabilityYO))
// {
// return gun.getTagCompound().getFloat(Tag_StabilityYO);
// }
//
// return stabilityYawOffset;
// }
//
// public float getStabilityP(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_StabilityP))
// {
// return gun.getTagCompound().getFloat(Tag_StabilityP);
// }
//
// return stabilityPitch;
// }
//
// public float getStabilityPO(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_StabilityPO))
// {
// return gun.getTagCompound().getFloat(Tag_StabilityPO);
// }
//
// return stabilityPitchOffset;
// }
//
// public float getAccuracy(ItemStack gun, EntityPlayer player, int useCount)
// {
// if(gun.hasTagCompound() && gun.getTagCompound().hasKey(Tag_Accuracy))
// {
// return gun.getTagCompound().getFloat(Tag_Accuracy);
// }
//
// return accuracy;
// }
//
// @Override
// public int getMaxDamage(ItemStack stack)
// {
// // TODO 取り敢えず付けた
// NBTTagCompound ltag = getTagCompound(stack);
// if(ltag.hasKey(Tag_MaxLoad))
// {
// return ltag.getInteger(Tag_MaxLoad);
// }
//
// return super.getMaxDamage(stack);
// }
//
// protected NBTTagCompound getTagCompound(ItemStack gun)
// {
// if(!gun.hasTagCompound())
// {
// gun.setTagCompound(new NBTTagCompound());
// }
//
// return gun.getTagCompound();
// }
//
// /**
// * マガジンのからスロットをスキップするかどうか
// * @return
// */
// public boolean isSkipBlank()
// {
// return true;
// }
//
// /**
// * 連射するかどうか、falseの時は通常の弓と同じ。
// * @param pGun
// * @return
// */
// public boolean isBurst(ItemStack gun)
// {
// return getBurstCount(gun) > 0;
// }
//
// @Override
// public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer
// player, EnumHand hand)
// {
// // TODO Forgeのイベントハンドラどうする？
// ItemStack stack = player.getHeldItem(hand);
// int li = getState(stack);
// if(player.isSwingInProgress)
// {
// setState(stack, State_ReloadTac);
// }
// else
// {
// if(isBurst(stack))
// {
// // 連射
// if(li >= State_Empty && li < State_Reload)
// {
// if(hasAmmo(stack, world, player))
// {
// // リロード
// setState(stack, State_Reload);
// }
// else
// {
// // 空打ち
// playSoundEmpty(world, player, stack);
// }
// }
// else if(li < State_Empty)
// {
// // 発射可能
// resetBolt(stack);
// resetBurst(stack);
// }
// }
// else
// {
// // 単発
// if(isAmmoEmpty(stack) && li < State_Reload)
// {
// if(hasAmmo(stack, world, player))
// {
// setState(stack, State_Reload);
// }
// }
// }
// }
// // 撃つ
// Utils.setUncheckedItemStack(stack, player);
// player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
//
// return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
// }
//
// @Override
// public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
// {
// int li = getState(stack);
// if(li == State_Reload)
// {
// setState(stack, State_ReleseMag);
// releaseMagazin(stack, player.world, player);
// Utils.setUncheckedItemStack(stack, player);
// }
// if(li == State_ReloadTac)
// {
// setState(stack, State_ReleseMag);
// releaseMagazin(stack, player.world, player);
// Utils.setUncheckedItemStack(stack, player);
// }
// onFireTick(stack, player.world, player, count, li);
// }
//
// /**
// * タイマー処理の独自記述はこちらに書くと良し
// * @param pGun
// * @param pWorld
// * @param pPlayer
// * @param count
// * @param pState
// */
// public void onFireTick(ItemStack gun, World world, EntityPlayer player, int
// count, int state)
// {
// if(isBurst(gun))
// {
// if(state == State_Ready && !isAmmoEmpty(gun))
// {
// if(checkBolt(gun) && decBurst(gun) > 0)
// {
// // 発射
// if(fireBullet(gun, world, player, count) <= 0)
// {
// setState(gun, State_Empty);
// }
// }
// Utils.setUncheckedItemStack(gun, player);
// }
// }
// }
//
// @Override
// public void onPlayerStoppedUsing(ItemStack stack, World world,
// EntityLivingBase entityLiving, int timeLeft)
// {
// int li = getState(stack);
// if(li == State_ReloadEnd)
// {
// if(isAmmoEmpty(stack))
// {
// setState(stack, State_Empty);
// }
// else
// {
// setState(stack, State_Ready);
// }
// }
// else if(!isBurst(stack))
// {
// if(!isAmmoEmpty(stack))
// {
// // 弾があるので発射
// if(fireBullet(stack, world, entityLiving, timeLeft) <= 0)
// {
// // 撃ち尽くした
// setState(stack, State_Empty);
// }
// }
// else if(li < State_Reload)
// {
// // 弾がないので空打ち
// playSoundEmpty(world, entityLiving, stack);
// }
// else
// {
// // setState(par1ItemStack, State_Empty);
// }
// }
// }
//
// @Override
// public int getMaxItemUseDuration(ItemStack stack)
// {
// // 射撃時と装填時で挙動を変える
// int li = getState(stack);
// if(li >= State_Empty && li < State_ReloadEnd)
// {
// return getReloadTime(stack);
// }
// return 72000;
// }
//
// @Override
// public EnumAction getItemUseAction(ItemStack stack)
// {
// int li = getState(stack);
// if(li < State_ReloadTac)
// {
// return EnumAction.BOW;
// }
// return EnumAction.BLOCK;
// }
//
// /**
// * 弾薬を所持しているか？
// * @param stack
// * @param world
// * @param player
// * @return
// */
// public boolean hasAmmo(ItemStack stack, World world, EntityPlayer player)
// {
// return Utils.isCreative(player) || (getAmmoIndex(player) > -1);
// }
//
// /**
// * 弾体の発射
// * @param gun
// * @param pWorld
// * @param pPlayer
// * @param pUseCount
// * @return 射撃後の残弾数
// */
// public int fireBullet(ItemStack gun, World world, EntityPlayer player, int
// useCount)
// {
// int ldamage = getDamage(gun);
// int lmdamage = getMaxDamage(gun);
// ItemStack lbullet;
// do
// {
// // 弾薬を取り出す
// lbullet = getBullet(gun, ldamage);
// ldamage++;
// // 残弾数を減らす
// setDamage(gun, ldamage);
// if(lbullet != null)
// break;
// }
// while(!isSkipBlank() && ldamage < lmdamage);
// // 戻り値として再設定、残弾数を返す。
// ldamage = lmdamage - ldamage;
// if(lbullet == null)
// return ldamage;
//
// ItemBulletBase libullet = null;
// if(lbullet.getItem() instanceof ItemBulletBase)
// {
// libullet = (ItemBulletBase) lbullet.getItem();
// }
// // 発射音、弾薬ごとに音声を設定
// if(libullet != null)
// {
// libullet.playSoundFire(world, player, gun, lbullet);
// }
//
// // 弾体を発生させる
// if(!world.isRemote)
// {
// Entity lentity;
// if(libullet != null)
// {
// lentity = libullet.getBulletEntity(gun, lbullet, world, player, 72000 -
// useCount);
// world.spawnEntity(lentity);
// // onRecoile(pGun, pWorld, pPlayer, 72000 - pUseCount);
// }
// }
// if(libullet != null)
// {
// onRecoile(gun, lbullet, world, player, 72000 - useCount);
// }
// return ldamage;
// }
//
// /**
// * 発射時のリコイル動作を記述
// * @param pGun
// * @param pWorld
// * @param pPlayer
// * @param pUseCount
// */
// public void onRecoile(ItemStack gun, ItemStack bullet, World world,
// EntityPlayer player, int useCount)
// {
// // しゃがみの時は少し早く照準が安定する
// float lsn = player.isSneaking() ? 0.5F : 1.0F;
// lsn *= ((ItemBulletBase) bullet.getItem()).getReaction(bullet);
// // 腕の動き
// player.rotationPitch -= (player.getRNG().nextFloat() * getStabilityP(gun,
// player, useCount)
// + getStabilityPO(gun, player, useCount)) * lsn;
// player.rotationYaw += (player.getRNG().nextFloat() * getStabilityY(gun,
// player, useCount)
// + getStabilityYO(gun, player, useCount)) * lsn;
// // 後ろに吹っ飛ぶ
// lsn *= getStability(gun, player, useCount);
// player.motionX += MathHelper.sin(player.rotationYawHead * 0.01745329252F) *
// lsn;
// player.motionZ -= MathHelper.cos(player.rotationYawHead * 0.01745329252F) *
// lsn;
// }
//
// /**
// * 装填されている弾を返す。
// * @param pGun
// * @param pIndex
// * @return
// */
// public ItemStack getBullet(ItemStack gun, int index)
// {
// if(gun.hasTagCompound())
// {
// NBTTagCompound ltag = gun.getTagCompound();
// NBTTagCompound lbullet = ltag.getCompoundTag(Tag_Magazin);
// String ls = String.format("%04d", index);
// if(lbullet.hasKey(ls))
// {
// return ItemStack.loadItemStackFromNBT(lbullet.getCompoundTag(ls));
// }
// }
// return null;
// }
//
// public void setBullet(ItemStack gun, int index, ItemStack bullet)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// NBTTagCompound lmagazin = ltag.getCompoundTag(Tag_Magazin);
// ltag.setTag(Tag_Magazin, lmagazin);
// String ls = String.format("%04d", index);
// if(bullet == null)
// {
// lmagazin.removeTag(ls);
// }
// else
// {
// NBTTagCompound lbullet = ltag.getCompoundTag(ls);
// lmagazin.setTag(ls, lbullet);
// bullet.writeToNBT(lbullet);
// }
// }
//
// /**
// * 弾を装填する、装填する際にはスタックから減らす。
// * @param pGun
// * @param pIndex
// * @param pBullet
// */
// public void loadBullet(ItemStack gun, ItemStack bullet)
// {
// int li = getDamage(gun);
// while(li > 0)
// {
// li--;
// ItemStack lis = getBullet(gun, li);
// gun.setItemDamage(li);
// if(lis == null)
// {
// setBullet(gun, li, bullet.splitStack(1));
// break;
// }
// }
// }
//
// /**
// * 残弾確認
// * @param pGun
// * @return
// */
// public boolean isAmmoEmpty(ItemStack gun)
// {
// return getDamage(gun) >= getMaxDamage(gun);
// }
//
// /**
// * 使用可能な弾薬を判定する
// * @param pItemStack
// * @return
// */
// public boolean checkAmmo(ItemStack stack)
// {
// Item litem = stack.getItem();
// for(Item li : ammos)
// {
// if(litem == li)
// return true;
// }
// return false;
// }
//
// /**
// * インベントリから弾薬を検索する
// * @param pPlayer
// * @return
// */
// public int getAmmoIndex(EntityPlayer player)
// {
// for(int li = 0; li < player.inventory.getSizeInventory(); li++)
// {
// ItemStack lis = player.inventory.getStackInSlot(li);
// if(lis != null && checkAmmo(lis))
// {
// return li;
// }
// }
// return -1;
// }
//
// /**
// * マガジンを取り外す。
// * @param pGun
// * @param pWorld
// * @param pPlayer
// */
// public void releaseMagazin(ItemStack gun, World world, EntityPlayer player)
// {
// playSoundRelease(world, player, gun);
// if(!Utils.isCreative(player))
// {
// // マガジンから使用済みのカートを取り出す（Creativeの時はマガジンの内容が変わらない）
// for(int li = 0; li < getDamage(gun); li++)
// {
// setBullet(gun, li, null);
// }
// }
//
// for(int li = 0; li < getMaxDamage(gun); li++)
// {
// ItemStack lis = getBullet(gun, li);
// }
//
// setDamage(gun, getMaxDamage(gun));
// }
//
// /**
// * リロード完了
// * @param stack
// * @param player
// */
// public void reloadMagazin(ItemStack gun, World world, EntityPlayer player)
// {
// while(getDamage(gun) > 0)
// {
// int li = getAmmoIndex(player);
// if(li == -1)
// {
// break;
// }
// ItemStack lis = player.inventory.getStackInSlot(li);
// loadBullet(gun, lis);
// if(lis.getCount() <= 0)
// {
// player.inventory.setInventorySlotContents(li, null);
// }
// }
// playSoundReload(world, player, gun);
// }
//
// // 状態ステータス
//
// public void setState(ItemStack gun, byte state)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// ltag.setByte(Tag_State, state);
// }
//
// public byte getState(ItemStack gun)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// return ltag.getByte(Tag_State);
// }
//
// // 発射間隔の算出
// public boolean checkBolt(ItemStack gun)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// short lval = ltag.getShort(Tag_Cycle);
// if(--lval <= 0)
// {
// ltag.setShort(Tag_Cycle, getCycleCount(gun));
// return true;
// }
// ltag.setShort(Tag_Cycle, lval);
// return false;
// }
//
// public void resetBolt(ItemStack gun)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// ltag.setShort(Tag_Cycle, getCycleCount(gun));
// }
//
// // 連射カウント
//
// public int decBurst(ItemStack gun)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// int lburst = ltag.getInteger(Tag_Burst);
// if(lburst > 0)
// {
// ltag.setInteger(Tag_Burst, lburst - 1);
// }
// return lburst;
// }
//
// public void resetBurst(ItemStack gun)
// {
// NBTTagCompound ltag = getTagCompound(gun);
// ltag.setInteger(Tag_Burst, getBurstCount(gun));
// }
// }