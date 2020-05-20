package ryoryo.polishedstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.event.PlayerMoveSpeedHandler;
import ryoryo.polishedstone.util.LibNBTTag;

public class PacketSyncFlySpeed implements IMessage {
	private int tier;

	public PacketSyncFlySpeed() {
	}

	public PacketSyncFlySpeed(int tier) {
		this.tier = tier;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.tier = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.tier);
	}

	public static class ServerHandler implements IMessageHandler<PacketSyncFlySpeed, IMessage> {
		@Override
		public IMessage onMessage(PacketSyncFlySpeed message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
				EntityPlayerMP player = ctx.getServerHandler().player;
				NBTTagCompound entityData = player.getEntityData();
				NBTTagCompound persistedData = Utils.getTagCompound(entityData, EntityPlayer.PERSISTED_NBT_TAG);

				persistedData.setInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER, message.tier);
				entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistedData);

				PlayerMoveSpeedHandler.setFlySpeed(player, PlayerMoveSpeedHandler.FLY_SPEEDS[message.tier]);
			});

			return null;
		}
	}

	public static class ClientHandler implements IMessageHandler<PacketSyncFlySpeed, IMessage> {
		@Override
		public IMessage onMessage(PacketSyncFlySpeed message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> {
				EntityPlayerSP player = Minecraft.getMinecraft().player;
				NBTTagCompound entityData = player.getEntityData();
				NBTTagCompound persistedData = Utils.getTagCompound(entityData, EntityPlayer.PERSISTED_NBT_TAG);

				persistedData.setInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER, message.tier);
				entityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistedData);
			});

			return null;
		}
	}
}