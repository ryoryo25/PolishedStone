package ryoryo.polishedstone.world.dimension;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
	public static DimensionType DAY_VOID;
	public static DimensionType NIGHT_VOID;
	public static DimensionType PERFECT_VOID;

	public static void register() {
		DAY_VOID = DimensionType.register("Day Void", "_day_void", DimensionManager.getNextFreeDimId(), WorldProviderDayVoid.class, false);
		DimensionManager.registerDimension(DAY_VOID.getId(), DAY_VOID);

		NIGHT_VOID = DimensionType.register("Night Void", "_night_void", DimensionManager.getNextFreeDimId(), WorldProviderNightVoid.class, false);
		DimensionManager.registerDimension(NIGHT_VOID.getId(), NIGHT_VOID);

		PERFECT_VOID = DimensionType.register("Perfect Void", "_perfect_void", DimensionManager.getNextFreeDimId(), WorldProviderPerfectVoid.class, false);
		DimensionManager.registerDimension(PERFECT_VOID.getId(), PERFECT_VOID);
	}
}