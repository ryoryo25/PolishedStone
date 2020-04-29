package ryoryo.polishedstone.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPlacePumpkinAnywhere implements IMessage
{
	private int posX;
	private int posY;
	private int posZ;

	public PacketPlacePumpkinAnywhere(int posX, int posY, int posZ)
	{
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
	}

	public BlockPos getPos()
	{
		return new BlockPos(this.posX, this.posY, this.posZ);
	}

	public static class Handler implements IMessageHandler<PacketPlacePumpkinAnywhere, IMessage>
	{
		@Override
		public IMessage onMessage(PacketPlacePumpkinAnywhere message, MessageContext ctx)
		{
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
			{
				EntityPlayerMP player = ctx.getServerHandler().player;
				IBlockState state = Blocks.PUMPKIN.getDefaultState().withProperty(BlockPumpkin.FACING, player.getHorizontalFacing().getOpposite());
				player.world.setBlockState(message.getPos(), state);
			});
			return null;
		}
	}
}