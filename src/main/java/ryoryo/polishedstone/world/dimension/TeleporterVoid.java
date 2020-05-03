package ryoryo.polishedstone.world.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterVoid extends Teleporter {
	protected final WorldServer world;
	protected final DimensionType type;

	public TeleporterVoid(WorldServer world, DimensionType type) {
		super(world);
		this.world = world;
		this.type = type;
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {
		if(world.provider.getDimension() != this.type.getId() && entity instanceof EntityPlayer) {
			boolean foundBlock = false;
			BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(pos.getX(), 0, pos.getZ());
			for(int y = 0; y < 256; y++) {
				mutableBlockPos.setY(y);
				if(world.getBlockState(mutableBlockPos).getBlock() == SimpleVoidWorld.portal) {
					pos = new BlockPos(pos.getX(), y + 1, pos.getZ());
					foundBlock = true;
					break;
				}
			}

			if(!foundBlock) {
				pos = ((EntityPlayer) entity).getBedLocation(world.provider.getDimension());
				if(pos == null) {
					pos = world.provider.getRandomizedSpawnPoint();
				}
			}

		}

		if(world.provider.getDimension() == this.type.getId()) {
			// TODO look around for a free space so it doesnt place in a base?
			pos = new BlockPos(pos.getX(), 64, pos.getZ());
			if(world.getBlockState(pos).getBlock() != SimpleVoidWorld.portal) {
				int color = world.rand.nextInt(15);
				for(int x = -3; x < 4; x++) {
					for(int z = -3; z < 4; z++) {
						if(world.isAirBlock(pos.add(x, 0, z))) {
							world.setBlockState(pos.add(x, 0, z), Blocks.STAINED_HARDENED_CLAY.getStateFromMeta(color));
						}

					}
				}
				world.setBlockState(pos, SimpleVoidWorld.portal.getDefaultState());
				for(EnumFacing facing : EnumFacing.HORIZONTALS) {
					world.setBlockState(pos.up().offset(facing), Blocks.TORCH.getDefaultState());
				}

			}
		}
	}
}