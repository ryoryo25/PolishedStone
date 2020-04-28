package ryoryo.polishedstone.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.PSV2Core;

public class BlockInvisibleButton extends BlockButton
{
	public static final PropertyBool VISIBLE = PropertyBool.create("visible");
	private boolean isVisible = false;

	public BlockInvisibleButton()
	{
		super(false);
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("invisible_botton");
		this.setHardness(0.5F);
		this.setSoundType(SoundType.METAL);
		//		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.valueOf(false)).withProperty(VISIBLE, Boolean.valueOf(false)));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		if(((Boolean) state.getValue(VISIBLE)).booleanValue())
		{
			return Blocks.STONE_BUTTON.getBoundingBox(state, source, pos);
		}
		return Utils.ZERO_AABB;
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
	{
		return Blocks.STONE_BUTTON.getCollisionBoundingBox(blockState, world, pos);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return super.onBlockActivated(world, pos, state.withProperty(VISIBLE, Boolean.valueOf(this.isVisible)), player, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing;

		switch(meta & 7)
		{
		case 0:
			facing = EnumFacing.DOWN;
			break;
		case 1:
			facing = EnumFacing.EAST;
			break;
		case 2:
			facing = EnumFacing.WEST;
			break;
		case 3:
			facing = EnumFacing.SOUTH;
			break;
		case 4:
			facing = EnumFacing.NORTH;
			break;
		case 5:
		default:
			facing = EnumFacing.UP;
		}

		return this.getDefaultState().withProperty(FACING, facing).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i;

		switch((EnumFacing) state.getValue(FACING))
		{
		case EAST:
			i = 1;
			break;
		case WEST:
			i = 2;
			break;
		case SOUTH:
			i = 3;
			break;
		case NORTH:
			i = 4;
			break;
		case UP:
		default:
			i = 5;
			break;
		case DOWN:
			i = 0;
		}

		if(((Boolean) state.getValue(POWERED)).booleanValue())
		{
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ FACING, POWERED, VISIBLE });
	}

	private void setVisible(IBlockState state, World world, BlockPos pos)
	{
		if(world.isRemote)
		{
			EntityPlayer player = Utils.getPlayer();
			if(player != null)
			{
				List<ItemStack> held = Utils.getHeldItemStacks(player);

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
			if(this.isVisible != state.getValue(VISIBLE).booleanValue())
			{
				world.setBlockState(pos, state.withProperty(VISIBLE, Boolean.valueOf(this.isVisible)));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		setVisible(state, world, pos);
	}

	@Override
	protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos)
	{
		worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
	}

	@Override
	protected void playReleaseSound(World worldIn, BlockPos pos)
	{
		worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
	}
}
