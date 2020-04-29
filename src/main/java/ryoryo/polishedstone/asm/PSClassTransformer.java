package ryoryo.polishedstone.asm;

import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;

public class PSClassTransformer implements IClassTransformer, Opcodes
{
	public PSClassTransformer()
	{
		PSLoadingPlugin.LOGGER.info("Starting Class Transformation");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		return basicClass;
	}
}