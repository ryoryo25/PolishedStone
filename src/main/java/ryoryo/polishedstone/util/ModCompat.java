package ryoryo.polishedstone.util;

import net.minecraftforge.fml.common.Loader;

public class ModCompat {
	// Mod Ids
	public static final String MOD_ID_QUARK = "quark";
	public static final String MOD_ID_CUSTOM_SPAWN = "dcs_spawn";
	public static final String MOD_ID_WILDFIRE = "dcs_wildfire";
	public static final String MOD_ID_FENCE_JUMPER = "fencejumper";

	// Bool of the mod loaded
	public static final boolean COMPAT_QUARK = Loader.isModLoaded(MOD_ID_QUARK);
	public static final boolean COMPAT_CUSTOM_SPAWN = Loader.isModLoaded(MOD_ID_CUSTOM_SPAWN);
	// public static final boolean COMPAT_BETTER_PLACEMENT =
	// Loader.isModLoaded("betterplacement");
	public static final boolean COMPAT_WILDFIRE = Loader.isModLoaded(MOD_ID_WILDFIRE);
	public static final boolean COMPAT_FENCE_JUMPER = Loader.isModLoaded(MOD_ID_FENCE_JUMPER);
}