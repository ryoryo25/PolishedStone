package ryoryo.polishedstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ryoryo.polishedlib.util.Utils;

public class PacketTest implements IMessage
{
	private String toSend;

	// A default constructor is always required
	public PacketTest()
	{
	}

	public PacketTest(String toSend)
	{
		this.toSend = toSend;
	}

	/**
	 * https://bedrockminer.jimdofree.com/modding-tutorials/advanced-modding/packet-handler/
	 *
	 * 引数がある場合は絶対読み書き必要
	 */
	@Override
	public void fromBytes(ByteBuf buf)
	{
		// Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		this.toSend = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeUTF8String(buf, this.toSend);
	}

	public static class Handler implements IMessageHandler<PacketTest, IMessage>
	{
		@Override
		public IMessage onMessage(PacketTest message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			Utils.sendChat(player, message.toSend);
//			player.getServerWorld().addScheduledTask(() ->
//			{
//				Utils.sendChat(player, message.toSend);
//			});
			return null;
		}
	}
}