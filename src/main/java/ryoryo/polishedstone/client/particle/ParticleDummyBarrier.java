package ryoryo.polishedstone.client.particle;

import net.minecraft.client.particle.Barrier;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedstone.Register;

public class ParticleDummyBarrier extends Barrier {
	protected ParticleDummyBarrier(World world, double p_i46286_2_, double p_i46286_4_, double p_i46286_6_, Item p_i46286_8_) {
		super(world, p_i46286_2_, p_i46286_4_, p_i46286_6_, p_i46286_8_);
		this.particleRed = 255F / 255F;
		this.particleGreen = 0F / 255F;
		this.particleBlue = 0F / 255F;
	}

	@SideOnly(Side.CLIENT)
	public static class Factory implements IParticleFactory {
		public Particle createParticle(int particleID, World world, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... p_178902_15_) {
			return new ParticleDummyBarrier(world, xCoord, yCoord, zCoord, Item.getItemFromBlock(Register.BLOCK_DUMMY_BARRIER));
		}
	}
}
