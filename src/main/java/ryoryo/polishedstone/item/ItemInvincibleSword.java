package ryoryo.polishedstone.item;

import java.util.List;

import com.google.common.collect.Multimap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.NumericalConstant;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.util.ArmorMaterials;
import ryoryo.polishedstone.util.References;

public class ItemInvincibleSword extends ItemSword implements IModId
{
	public ItemInvincibleSword()
	{
		super(ArmorMaterials.INVINCIBLE_SWORD);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("invincible_sword");
		this.setMaxDamage(0);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(!attacker.world.isRemote && attacker != null && attacker instanceof EntityPlayer && target != null)
		{
			World world = attacker.world;
			EntityPlayer player = (EntityPlayer) attacker;

			if(player.isSneaking())
			{
				List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(target, new AxisAlignedBB(
					target.posX - ModConfig.invincibleSwordAOERudius, target.posY - ModConfig.invincibleSwordAOERudius, target.posZ - ModConfig.invincibleSwordAOERudius,
					target.posX + ModConfig.invincibleSwordAOERudius, target.posY + ModConfig.invincibleSwordAOERudius, target.posZ + ModConfig.invincibleSwordAOERudius));
				for(Entity entity : entities)
				{
					if(entity instanceof EntityLivingBase)
						entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), NumericalConstant.FLOAT_MAX);
				}

				return true;
			}
			else
				target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor(), NumericalConstant.FLOAT_MAX);

			return true;
		}

		return true;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
	{
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if(slot == EntityEquipmentSlot.MAINHAND)
		{
			Utils.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 1.0D);
			Utils.replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, -10D);
//			modifiers.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(LibMisc.INVINCIBLE_REACH, "Weapon modifier", 8.0D, 0));
		}

		return modifiers;
	}

//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
//	{
//		if(!player.worldObj.isRemote && entity != null && entity instanceof EntityLivingBase)
//		{
//			EntityLivingBase living = (EntityLivingBase) entity;
//			living.attackEntityFrom(DamageSource.causePlayerDamage(player), Utils.FLOAT_MAX);
//
//			return super.onLeftClickEntity(stack, player, entity);
//		}
//
//		return super.onLeftClickEntity(stack, player, entity);
//	}
}
