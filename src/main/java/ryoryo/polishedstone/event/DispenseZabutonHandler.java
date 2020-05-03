package ryoryo.polishedstone.event;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ryoryo.polishedstone.entity.EntityZabuton;

public class DispenseZabutonHandler extends BehaviorProjectileDispense {
	protected ItemStack zabuton;

	@Override
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		// 色を識別するためにItemStackを確保
		zabuton = stack;
		return super.dispenseStack(source, stack);
	}

	@Override
	protected IProjectile getProjectileEntity(World world, IPosition position, ItemStack stack) {
		return new EntityZabuton(world, position.getX(), position.getY(), position.getZ(), (byte) zabuton.getItemDamage());
	}
}