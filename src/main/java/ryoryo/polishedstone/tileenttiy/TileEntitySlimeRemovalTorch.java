package ryoryo.polishedstone.tileenttiy;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntitySlimeRemovalTorch extends TileEntity implements ITickable {
	private int tickCounter = 0;

	@Override
	public void update() {
		tickCounter++;

		if(tickCounter == 10) {
			int startX = (this.getPos().getX() / 16) * 16;
			int startZ = (this.getPos().getZ() / 16) * 16;
			AxisAlignedBB search = new AxisAlignedBB(startX, 0, startZ, startX + 16, 256, startZ + 16);
			this.world.getEntitiesWithinAABB(EntitySlime.class, search).stream().forEach(EntitySlime::setDead);

			// same meaning
			// this.world.getEntitiesWithinAABB(EntitySlime.class,
			// search).stream().forEach(target -> target.setDead());

			// List<EntitySlime> slimes =
			// this.world.getEntitiesWithinAABB(EntitySlime.class, search);
			// for(EntitySlime target : slimes)
			// {
			// target.setDead();
			// }

			tickCounter = 0;
		}
	}
}