package ryoryo.polishedstone.item.armor;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.EnumArmorType;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.util.ArmorMaterials;
import ryoryo.polishedstone.util.LibNBTTag;

public class ItemArmorSafetyClothes extends ItemArmor
{

	public ItemArmorSafetyClothes(EnumArmorType type)
	{
		super(ArmorMaterials.SAFETY_CLOTHES, type.getRenderIndex(), type.getSlot());
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("safety_clothes_" + type.getName());
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player)
	{
		super.onCreated(stack, world, player);
		if(stack.getItem() == Register.ITEM_ARMOR_SAFETY_MET)
		{
			stack.addEnchantment(Enchantments.BLAST_PROTECTION, 1);
		}
		if(stack.getItem() == Register.ITEM_ARMOR_SAFETY_BOOTS)
		{
			stack.addEnchantment(Enchantments.FEATHER_FALLING, 1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	//マウスオーバー時の表示情報
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);
		if(stack != null && stack.getItem() == Register.ITEM_ARMOR_SAFETY_BELT)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			String s = "None";
			if(nbt != null)
			{
				int X = nbt.getInteger(LibNBTTag.SAFETY_X);
				int Y = nbt.getInteger(LibNBTTag.SAFETY_Y);
				int Z = nbt.getInteger(LibNBTTag.SAFETY_Z);

				String dim = "Unknown";
				if(nbt.hasKey(LibNBTTag.SAFETY_DIM_NAME))
				{
					dim = nbt.getString(LibNBTTag.SAFETY_DIM_NAME);
				}

				s = X + ", " + Y + ", " + Z + " / " + dim;
			}
			tooltip.add(new String("Registered pos : " + s));
		}
	}
}
