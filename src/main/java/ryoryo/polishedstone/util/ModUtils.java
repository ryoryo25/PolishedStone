package ryoryo.polishedstone.util;

import net.minecraft.util.ResourceLocation;

public class ModUtils
{
	public static ResourceLocation makeModLocation(String name)
	{
		return new ResourceLocation(References.MOD_ID, name);
	}
}