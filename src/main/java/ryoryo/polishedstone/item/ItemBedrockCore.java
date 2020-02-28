package ryoryo.polishedstone.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;

public class ItemBedrockCore extends ItemModBase
{
	public ItemBedrockCore()
	{
		super("bedrock_core");
		this.setMaxStackSize(1);
		this.setMaxDamage(1024);
		this.setContainerItem(Register.ITEM_BEDROCK_CORE);
		this.setNoRepair();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	@Override
	public boolean hasContainerItem()
	{
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		if(!itemStack.isEmpty() && itemStack.getItem() == this)
		{
			if(itemStack.getItemDamage() == itemStack.getMaxDamage())
				return null;

			return Utils.damageStack(itemStack, 1);
		}

		return itemStack;
	}
}
