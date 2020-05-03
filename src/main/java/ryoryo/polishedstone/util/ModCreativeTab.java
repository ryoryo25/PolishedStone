package ryoryo.polishedstone.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedstone.Register;

public class ModCreativeTab extends CreativeTabs {
	public ModCreativeTab(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return References.MOD_NAME;
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Register.BLOCK_POLISHED_STONE, 1, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		list.add(new ItemStack(Items.SPAWN_EGG));
		super.displayAllRelevantItems(list);
	}
}