package ryoryo.polishedstone.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ryoryo.polishedstone.util.References;

public class PacketHandler
{
	private static int packetId = 0;
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(References.MOD_ID);

	private static int nextId()
	{
		return packetId ++;
	}

	public static void init()
	{
//		INSTANCE.registerMessage(PacketTest.Handler.class, PacketTest.class, nextId(), Side.SERVER);
		INSTANCE.registerMessage(PacketSyncFlySpeed.ServerHandler.class, PacketSyncFlySpeed.class, nextId(), Side.SERVER);
		INSTANCE.registerMessage(PacketSyncFlySpeed.ClientHandler.class, PacketSyncFlySpeed.class, nextId(), Side.CLIENT);
	}
}