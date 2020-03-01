package ryoryo.polishedstone;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ryoryo.polishedlib.util.ModLogger;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.proxy.IProxy;
import ryoryo.polishedstone.util.ArmorMaterials;
import ryoryo.polishedstone.util.ModCreativeTab;
import ryoryo.polishedstone.util.References;

@Mod(modid = References.MOD_ID, name = References.MOD_NAME, version = References.MOD_VERSION, dependencies = References.MOD_DEPENDENCIES, acceptedMinecraftVersions = References.MOD_ACCEPTED_MC_VERSIONS, useMetadata = true, guiFactory = References.MOD_GUI_FACTORY)
public class PSV2Core
{
	@Instance(References.MOD_ID)
	public static PSV2Core INSTANCE;

	@SidedProxy(clientSide=References.PROXY_CLIENT, serverSide=References.PROXY_COMMON)
	public static IProxy proxy;

	public static final CreativeTabs TAB_MOD = new ModCreativeTab(References.MOD_ID);
	public static ModConfig config;
	public static ModLogger logger = new ModLogger(References.MOD_ID);
	public static final boolean isDebug = false;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		INSTANCE = this;
		logger.addLog("Hello Minecraft!");
		logger.addLog("Start loading preInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		config = new ModConfig(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(config);
		//Registering Armor Materials
		ArmorMaterials.registerArmorMaterial();
		Register.preInit();
		Register.preInitClient();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.addLog("Start loading init!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Register.init();
		Register.initClient();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		logger.addLog("Start loading postInit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		Register.postInit();
		Register.postInitClient();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event)
	{
	}
}