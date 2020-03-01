package ryoryo.polishedstone.util;

import net.minecraft.util.ResourceLocation;
import ryoryo.polishedlib.util.Utils;

public class ModUtils
{
	public static ResourceLocation makeModLocation(String name)
	{
		return Utils.makeModLocation(References.MOD_ID, name);
	}
}