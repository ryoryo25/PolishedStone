package ryoryo.polishedstone.client.render;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class ThirdPersonCameraController
{
	private static final ThirdPersonCameraController instance = new ThirdPersonCameraController();
	private int cnt = 2;

	private static final String[] fieldNames =
	{
			"thirdPersonDistance", "field_78490_B",
	};

	private ThirdPersonCameraController()
	{
	}

	public void turn()
	{
		cnt++;
		if(8 < cnt)
		{
			cnt = 2;
		}
		try
		{
			setThirdPersonDistance(Minecraft.getMinecraft().entityRenderer, cnt * cnt, new HashSet<EntityRenderer>());
		}
		catch(Exception e)
		{
			PSV2Core.LOGGER.error("ThirdPersonCameraController#turn", e);
		}
	}

	public static ThirdPersonCameraController getInstance()
	{
		return instance;
	}

	private static void setThirdPersonDistance(EntityRenderer renderer, float distance, Set<EntityRenderer> visited) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		if(visited.contains(renderer))
		{
			return;
		}
		else
		{
			visited.add(renderer);
			// entityRenderer に入ってる EntityRenderer を直接書き換えてみる
			Utils.addChat("Wow!");
			ObfuscationReflectionHelper.setPrivateValue(EntityRenderer.class, renderer, distance, fieldNames);
			// entityRenderer が入れ子になっていたら、それらも書き換えてみる
//			for(EntityRenderer obj2 : ObfuscationReflectionHelper.getPrivateValue(EntityRenderer.class, renderer, fieldNames))
//			{
//				setThirdPersonDistance(obj2, distance, visited);
//			}
		}
	}
}