package ryoryo.polishedstone.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.client.particle.ParticleRegistry;
import ryoryo.polishedstone.util.References;

public class BlockDummyBarrier extends BlockBarrier implements IModId
{
	private boolean isVisible = false;

	public BlockDummyBarrier()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("dummy_barrier");
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack held = player.getHeldItem(hand);

//		player.swingArm(hand);
		if(!world.isRemote && held.getItem() instanceof ItemPickaxe)
		{
			world.destroyBlock(pos, true);
			if(!Utils.isCreative(player))
			{
				Block.spawnAsEntity(world, pos, new ItemStack(Register.BLOCK_DUMMY_BARRIER));
				held.damageItem(1, player);
			}

			return true;
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + 0.5D;
		double d2 = (double) pos.getZ() + 0.5D;

		if(world.isRemote)
		{
			EntityPlayer player = Utils.getPlayer();
			if(player != null)
			{
				ItemStack[] held = Utils.getHeldItemStacks(player);

				for(ItemStack stack : held)
				{
					if(!stack.isEmpty())
					{
						this.isVisible = false;
						Item item = stack.getItem();
						if(item instanceof ItemBlock && Block.getBlockFromItem(item) == this)
						{
							this.isVisible = true;
							break;
						}
					}
					else
						this.isVisible = false;
				}
			}

			if(this.isVisible)
				world.spawnParticle(ParticleRegistry.DUMMY_BARRIER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
	{
		tooltip.add("To destroy this block, use a pickaxe and right-click.");
	}
}