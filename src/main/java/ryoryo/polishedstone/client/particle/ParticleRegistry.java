package ryoryo.polishedstone.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.config.ModConfig;

public class ParticleRegistry
{
	public static final int PARTICLE_ID_DUMMY_BARRIER_DEFAULT = 50;

	public static final EnumParticleTypes DUMMY_BARRIER = Utils.registerParticleType("DUMMY_BARRIER", "dummyBarrier", ModConfig.particleIdDummyBarrier, false);

	public static void register()
	{
//		DUMMY_BARRIER = Utils.registerParticleType("DUMMY_BARRIER", "dummyBarrier", ModConfig.particleIdDummyBarrier, false);

		if(Utils.isClient())
		{
			Minecraft.getMinecraft().effectRenderer.registerParticle(ParticleRegistry.DUMMY_BARRIER.getParticleID(), new ParticleDummyBarrier.Factory());
		}
	}
}