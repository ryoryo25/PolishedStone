package ryoryo.polishedstone.config;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.config.ModConfig.EnumConfigCategory;
import ryoryo.polishedstone.util.References;

public class GuiModConfig extends GuiConfig
{
	public GuiModConfig(GuiScreen parentScreen)
	{
		super(parentScreen, getConfigElements(), References.MOD_ID, false, false, References.MOD_NAME + " Configurations");
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		for(EnumConfigCategory cat : EnumConfigCategory.values())
		{
			PSV2Core.config.getConfig().setCategoryComment(cat.name, cat.comment);
//			for(IConfigElement elem : new ConfigElement(PSV2Core.config.getCofig().getCategory(cat.name)).getChildElements())
//			{
//				list.add(elem);
//			}
			list.add(new ConfigElement(PSV2Core.config.getConfig().getCategory(cat.name)));
		}

		return list;
	}
}