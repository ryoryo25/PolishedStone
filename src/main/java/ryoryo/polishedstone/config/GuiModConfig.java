package ryoryo.polishedstone.config;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		return Stream.of(EnumConfigCategory.values())
		.map(cat ->
		{
			PSV2Core.config.getConfig().setCategoryComment(cat.getDisplayName(), cat.getComment());
			return new ConfigElement(PSV2Core.config.getConfig().getCategory(cat.getDisplayName()));
		})
		.collect(Collectors.toList());
	}
}