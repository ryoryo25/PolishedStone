package ryoryo.polishedstone.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import net.minecraft.client.Minecraft;

public class References
{
	public static final String MOD_ID = "polishedstone_v2";
	public static final String MOD_NAME = "PolishedStone2";

	public static final String MOD_VERSION_MAJOR = "GRADLE.VERSION_MAJOR";
	public static final String MOD_VERSION_MINOR = "GRADLE.VERSION_MINOR";
	public static final String MOD_VERSION_PATCH = "GRADLE.VERSION_PATCH";
	public static final String MOD_VERSION = MOD_VERSION_MAJOR + "." + MOD_VERSION_MINOR + "." + MOD_VERSION_PATCH;

	public static final String MOD_DEPENDENCIES = "required-after:forge@[14.23.5.2768,);"
//												+ "required-after:polishedlib@[1.0.1,);"
												+ "required-after:polishedlib;"
												+ "after:" + ModCompat.MOD_ID_QUARK + ";"
												+ "after:" + ModCompat.MOD_ID_CUSTOM_SPAWN + ";"
												+ "after:" + ModCompat.MOD_ID_WILDFIRE + ";"
												+ "after:" + ModCompat.MOD_ID_FENCE_JUMPER + ";";

	/** 起動出来るMinecraft本体のバージョン。記法はMavenのVersion Range Specificationを検索すること。 */
	public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.12.2]";
	public static final String MOD_GUI_FACTORY = "ryoryo.polishedstone.config.GuiModConfigFactory";

	public static final String PROXY_CLIENT = "ryoryo.polishedstone.proxy.ClientProxy";
	public static final String PROXY_COMMON = "ryoryo.polishedstone.proxy.CommonProxy";

	public static final String PREFIX = MOD_ID + ":";
	public static final String TEXTURE_FOLDER = PREFIX + "textures/";
	public static final String GUI_FOLDER = TEXTURE_FOLDER + "gui/";
	public static final String HUD_FOLDER = TEXTURE_FOLDER + "hud/";
	public static final String VILLAGER_FOLDER = TEXTURE_FOLDER + "villager/";
	public static final String TILEENTITY_FOLDER = TEXTURE_FOLDER + "tileentity/";
	public static final String MOB_TEXTURE_FOLDER = TEXTURE_FOLDER + "mob/";
	public static final String MODEL_TEXTURE_FOLDER = TEXTURE_FOLDER + "itemmodels/";
	public static final String ARMOR_TEXTURE_FOLDER = TEXTURE_FOLDER + "armor/";
	public static final String MODEL_FOLDER = PREFIX + "models/";

	public static final UUID EXTRA_REACH = UUID.fromString("6151b259-3a53-4788-8f42-7c89db9e3e28");

	public static String getVersion()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

		return Minecraft.getMinecraft().getVersion() + "-" + sdf.format(cal.getTime());
	}

	public static String getDependencies(String id)
	{
		return "after:" + id + ";";
	}

	//	static
	//	{
	//		MOD_VERSION = getVersion();
	//	}
}