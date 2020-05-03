package ryoryo.polishedstone.item;

import net.minecraft.creativetab.CreativeTabs;
import ryoryo.polishedlib.item.ItemBaseFood;
import ryoryo.polishedstone.PSV2Core;

public class ItemModBaseFood extends ItemBaseFood {
	public ItemModBaseFood(int hunger, float hiddenHunger, boolean wolfCanEat, String unlocalizeName, CreativeTabs tab, int eatSpeed) {
		super(hunger, hiddenHunger, wolfCanEat, unlocalizeName, tab);
	}

	public ItemModBaseFood(int hunger, float hiddenHunger, boolean wolfCanEat, String unlocalizeName, int eatSpeed) {
		this(hunger, hiddenHunger, wolfCanEat, unlocalizeName, PSV2Core.TAB_MOD);
	}

	public ItemModBaseFood(int hunger, float hiddenHunger, boolean wolfCanEat, String unlocalizeName, CreativeTabs tab) {
		this(hunger, hiddenHunger, wolfCanEat, unlocalizeName, tab, 32);
	}

	public ItemModBaseFood(int hunger, float hiddenHunger, boolean wolfCanEat, String unlocalizeName) {
		this(hunger, hiddenHunger, wolfCanEat, unlocalizeName, PSV2Core.TAB_MOD);
	}
}