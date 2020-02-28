package ryoryo.polishedstone.itemblock;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import ryoryo.polishedstone.Register;

public class ItemBlockFaucet extends ItemBlock
{
	public ItemBlockFaucet()
	{
		super(Register.BLOCK_FAUCET);
		this.setHasSubtypes(true);
	}

	//ItemStackのdamage値からmetadataの値を返す。
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return getUnlocalizedName() + "_" + itemStack.getItemDamage();
	}
}
