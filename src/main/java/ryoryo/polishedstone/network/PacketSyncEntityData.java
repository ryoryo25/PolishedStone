package ryoryo.polishedstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.util.LibNBTTag;

public class PacketSyncEntityData implements IMessage
{
	private int tier;
	private boolean isUpdated;

	public PacketSyncEntityData()
	{
	}

	public PacketSyncEntityData(int tier, boolean isUpdated)
	{
		this.tier = tier;
		this.isUpdated = isUpdated;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.tier = buf.readInt();
		this.isUpdated = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.tier);
		buf.writeBoolean(this.isUpdated);
	}

	public static class Handler implements IMessageHandler<PacketSyncEntityData, IMessage>
	{
		@Override
		public IMessage onMessage(PacketSyncEntityData message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
			{
				EntityPlayerSP player = Minecraft.getMinecraft().player;
				NBTTagCompound entity_data = player.getEntityData();
				NBTTagCompound persisted_data = Utils.getTagCompound(entity_data, EntityPlayer.PERSISTED_NBT_TAG);
				persisted_data.setInteger(LibNBTTag.PLAYER_FLY_SPEED_TIER, message.tier);
				persisted_data.setBoolean(LibNBTTag.UPDATE_FLY_SPEED_TIER, message.isUpdated);
				entity_data.setTag(EntityPlayer.PERSISTED_NBT_TAG, persisted_data);
			});
			return null;
		}
	}
}