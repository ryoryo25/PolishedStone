package ryoryo.polishedstone.event;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.ArithmeticUtils;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.network.PacketHandler;
import ryoryo.polishedstone.network.PacketSyncFlySpeed;
import ryoryo.polishedstone.util.LibKey;
import ryoryo.polishedstone.util.LibNBTTag;

public class PlayerMoveSpeedHandler
{
	private static final float BASE_FLY_SPEED = 0.05F;
	private static final float[] FLY_SPEEDS = new float[] { 0.023F/*0.022F*/, BASE_FLY_SPEED, BASE_FLY_SPEED * 2.0F, BASE_FLY_SPEED * 3.0F, BASE_FLY_SPEED * 4.0F, BASE_FLY_SPEED * 8.0F };
	/** 5 */
	private static final int MAX_TIER = FLY_SPEEDS.length - 1;
	/** 0 */
	private static final int MIN_TIER = 0;

	@SubscribeEvent
	public void onPlayerLogIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;

		/**
		 * ブロック置くスピードと歩く速さを合わせる
		 * 0.1(default value) * 116%
		 *
		 * AttributeでsetBaseValueするのと同じ
		 * Ref: EntityPlayer#582
		 */
		float newSpeed = 0.1F * ArithmeticUtils.percentToDecimal(116.0F);
		float currentSpeed = player.capabilities.getWalkSpeed();
		if(currentSpeed != newSpeed)
			player.capabilities.setPlayerWalkSpeed(newSpeed);

		/**
		 * NBT structure
		 *
		 * "entity_data"
		 * {
		 *   "persisted_data"
		 *   PlayerPersisted:
		 *   {
		 *     updateFlySpeedTier: false,
		 *     playerFlySpeedTier: 1
		 *   }
		 * }
		 */
		NBTTagCompound entity_data = player.getEntityData();
		NBTTagCompound persisted_data = Utils.getTagCompound(entity_data, EntityPlayer.PERSISTED_NBT_TAG);

		if(!persisted_data.hasKey(LibNBTTag.PLAYER_FLY_SPEED_TIER))
		{
			persisted_data.setInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER, 1);
			entity_data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted_data);
		}
		if(!persisted_data.hasKey(LibNBTTag.UPDATE_FLY_SPEED_TIER))
		{
			persisted_data.setBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER, false);
			entity_data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted_data);
		}

		PacketHandler.INSTANCE.sendTo(new PacketSyncFlySpeed(persisted_data.getInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER),
																persisted_data.getBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER)),
										(EntityPlayerMP) player);
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();

		if(target instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) target;
			NBTTagCompound entity_data = player.getEntityData();
			NBTTagCompound persisted_data = Utils.getTagCompound(entity_data, EntityPlayer.PERSISTED_NBT_TAG);

			if(Utils.isCreative(player) && (persisted_data.getBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER)/* || getFlySpeed(player) == 0.05F*/))
			{
				setFlySpeed(player, FLY_SPEEDS[persisted_data.getInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER)]);
				persisted_data.setBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER, false);
				entity_data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted_data);
			}

			if(!Utils.isCreative(player) && getFlySpeed(player) != 0.05F)
			{
				setFlySpeed(player, 0.05F);
			}
		}
	}

	private float getFlySpeed(EntityPlayer player)
	{
		return player.capabilities.getFlySpeed();
	}

	private void setFlySpeed(EntityPlayer player, float speed)
	{
		player.capabilities.setFlySpeed(speed);
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;

		//cancel inertia
		if (player.moveForward == 0 && player.moveStrafing == 0 && player.capabilities.isFlying)
		{
			player.motionX *= 0.5;
			player.motionZ *= 0.5;
		}
	}

	@SideOnly(Side.CLIENT)
	public static class Client
	{
		@SubscribeEvent
		@SideOnly(Side.CLIENT)
		public void onKeyBinding(InputEvent.KeyInputEvent event)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;

			if(!GuiScreen.isShiftKeyDown() && LibKey.KEY_FLY_SPEED.isPressed())
			{
				upFlySpeed(player);
				return;
			}

			if(GuiScreen.isShiftKeyDown() && LibKey.KEY_FLY_SPEED.isPressed())
			{
				downFlySpeed(player);
				return;
			}
		}

		@SideOnly(Side.CLIENT)
		public void upFlySpeed(EntityPlayer player)
		{
			NBTTagCompound entity_data = player.getEntityData();
			NBTTagCompound persisted_data = Utils.getTagCompound(entity_data, EntityPlayer.PERSISTED_NBT_TAG);
			int tier = persisted_data.getInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER);

			if(tier < MAX_TIER)
			{
				int newTier = tier + 1;
				persisted_data.setInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER, newTier);
				persisted_data.setBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER, true);
				entity_data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted_data);
				PacketHandler.INSTANCE.sendToServer(new PacketSyncFlySpeed(newTier, true));

				Utils.sendChat(player, "Set fly speed to Tier " + (newTier));
				player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, (newTier+1) * 0.2F);
				return;
			}
			else if(tier == MAX_TIER)
			{
				Utils.sendChat(player, "Now at max tier!");
				return;
			}
		}

		@SideOnly(Side.CLIENT)
		public void downFlySpeed(EntityPlayer player)
		{
			NBTTagCompound entity_data = player.getEntityData();
			NBTTagCompound persisted_data = Utils.getTagCompound(entity_data, EntityPlayer.PERSISTED_NBT_TAG);
			int tier = persisted_data.getInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER);

			if(tier > MIN_TIER)
			{
				int newTier = tier - 1;
				persisted_data.setInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER, newTier);
				persisted_data.setBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER, true);
				entity_data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted_data);
				PacketHandler.INSTANCE.sendToServer(new PacketSyncFlySpeed(newTier, true));

				Utils.sendChat(player, "Set fly speed to Tier " + newTier);
				player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, (newTier+1) * 0.2F);
				return;
			}
			else if(tier == MIN_TIER)
			{
				Utils.sendChat(player, "Now at min tier!");
				return;
			}
		}
	}
}