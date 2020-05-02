package ryoryo.polishedstone.event;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class TooltipHandler
{
	@SubscribeEvent
	public void onTooltipDraw(ItemTooltipEvent event)
	{
		List<String> tooltip = event.getToolTip();
		ItemStack held = event.getItemStack();
		//isAdvancedになってるか、シフト押してる
		boolean flag = event.getFlags().isAdvanced() || GuiScreen.isShiftKeyDown();

		if(!held.isEmpty() && flag)
		{
			//鉱石辞書の登録内容をF3+Hかシフトの状態なら見えるように
			int[] oreIDs = OreDictionary.getOreIDs(held);
			tooltip.add(TextFormatting.DARK_GRAY + "Ore Dictionary Entries" + ":");
			if(oreIDs.length > 0)
			{
				//Stream.of(oreIDs)がなぜかint[]のstreamになっちゃう
				//プリミティブ配列だとStream<int[]>になっちゃうみたい
				//プリミティブ型の場合はIntStreamとか個別の物が使われてるから
				//Ref: https://www.codeflow.site/ja/article/java8__java-how-to-convert-array-to-stream
				Arrays.stream(oreIDs).forEach(oreID ->
				tooltip.add(TextFormatting.DARK_GRAY + " - " + OreDictionary.getOreName(oreID)));
			}
			else
				tooltip.add(TextFormatting.DARK_GRAY + " - There is No Ore Dictionary Entry.");

			//NBTがあれば表示
			if(held.hasTagCompound())
			{
				NBTTagCompound tag = held.getTagCompound();
				tooltip.add(TextFormatting.DARK_GRAY + "NBT Entries:");
				for(String key : tag.getKeySet())
				{
					String toAdd = "";
					toAdd += TextFormatting.DARK_GRAY + " - " + key + ":";
					toAdd += tag.getTag(key).toString();
					tooltip.add(toAdd);
				}
			}
		}
	}
}