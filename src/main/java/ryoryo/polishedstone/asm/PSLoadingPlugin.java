package ryoryo.polishedstone.asm;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class PSLoadingPlugin implements IFMLLoadingPlugin {
	public static final Logger LOGGER = LogManager.getLogger("PSCore");
	public static boolean IN_MCP = false;

	@Override
	public String[] getASMTransformerClass() {
		return new String[] { BlockClassTransformer.class.getName(),
				ItemToolClassTransformer.class.getName(),
				TraceClassTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		IN_MCP = !((boolean) data.get("runtimeDeobfuscationEnabled"));
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	public static boolean isInMcp() {
		return IN_MCP;
	}

	public static String toSlash(String name) {
		return name.replace(".", "/");
	}
}