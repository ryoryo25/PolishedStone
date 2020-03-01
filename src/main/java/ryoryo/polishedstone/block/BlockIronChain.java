package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
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
import ryoryo.polishedstone.Register;

public class BlockIronChain extends BlockModBase
{
	protected static final AxisAlignedBB CHAIN_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	public static final PropertyBool TOP = Utils.TOP;
	public static final PropertyBool BOTTOM = Utils.BOTTOM;

	public BlockIronChain()
	{
		super(Material.IRON, "iron_chain", SoundType.METAL);
		this.setHardness(0.5F);
		this.setResistance(2.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TOP, Boolean.valueOf(false)).withProperty(BOTTOM, Boolean.valueOf(false)));
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return CHAIN_AABB;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.inventory.getCurrentItem();

		int l = 0;
		l = this.thisChainLength(world, pos);

		if(stack.isEmpty())
		{
			if(l < 1)
				return false;
			else if(l < 65)
			{
				Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_IRON_CHAIN, l));
				world.playSound(player, pos, Register.SOUND_IRON_CHAIN, SoundCategory.BLOCKS, 1.0F, 0.7F);
				for(int i = 1; i < (l + 1); i++)
				{
					BlockPos posa = pos.down(l -/*+*/ i);
					world.setBlockToAir(posa);
				}
				return true;
			}
			else
				return false;
		}
		else if(this.canPlace(stack))
		{
			Item place = stack.getItem();
			Block placeID;
			int placeMeta = stack.getItemDamage();

			if(place instanceof ItemBlock)
				placeID = Block.getBlockFromItem(place);
			else
				return false;

			if(l < 0)
				return false;
			else if(l < 65 && world.isAirBlock(pos.down(l)))
			{
				world.setBlockState(pos.down(l), placeID.getStateFromMeta(placeMeta));
				world.playSound(player, pos, Register.SOUND_IRON_CHAIN, SoundCategory.BLOCKS, 1.0F, 0.7F);
				if(!Utils.isCreative(player))
				{
					stack.shrink(1);
					if(stack.getCount() <= 0)
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					return true;
				}
				return true;
			}
			return true;
		}
		else
		{
			Item place = stack.getItem();
			Block block;

			if(place instanceof ItemBlock)
				block = Block.getBlockFromItem(place);
			else
				return false;

			if(l < 0)
				return false;
			else if(l < 65 && world.isAirBlock(pos.down(l)))
			{
				if(block != null)
				{
					if(block.canPlaceBlockAt(world, pos.down(l)))
					{
						BlockFalling.fallInstantly = true;
						world.setBlockState(pos.down(l), block.getStateFromMeta(stack.getItemDamage()));
						BlockFalling.fallInstantly = false;
						world.playSound(player, pos, Register.SOUND_IRON_CHAIN, SoundCategory.BLOCKS, 1.0F, 0.7F);
						if(!Utils.isCreative(player))
						{
							stack.shrink(1);
							if(stack.getCount() <= 0)
								player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
							return true;
						}
						return true;
					}
					return true;
				}
				return false;
			}
			return false;
		}
	}

	public boolean canPlace(ItemStack itemstack)
	{
		if(itemstack.getItem() == Item.getItemFromBlock(this))
			return true;
		else
			return false;
	}

	private int thisChainLength(World world, BlockPos pos)
	{
		int l = 0;
		boolean end = false;

		for(int h = 0; h < 65; h++)
		{
			BlockPos posc = pos.down(h);
			if(world.getBlockState(posc).getBlock() == Register.BLOCK_IRON_CHAIN && posc.getY() > 0 && !end)
				++l;
			else
				end = true;
		}

		return l;
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		BlockPos posu = pos.up();
		if(world.getBlockState(posu).getBlock() != Register.BLOCK_IRON_CHAIN && world.getBlockState(posu).getBlock() != Register.BLOCK_ANCHOR_BOLT && !world.isSideSolid(posu, EnumFacing.DOWN))
		{
			this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		BlockPos posu = pos.up();
		if(world.isAirBlock(posu) || world.getBlockState(posu) == null)
			return false;
		return(world.getBlockState(posu).getBlock() == Register.BLOCK_IRON_CHAIN || world.getBlockState(posu) == Register.BLOCK_ANCHOR_BOLT || world.isSideSolid(posu, EnumFacing.DOWN));
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState();
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(TOP, Boolean.valueOf(getTopIsIronChain(world, pos))).withProperty(BOTTOM, Boolean.valueOf(getBottomIsIronChain(world, pos)));
	}

	private static boolean getTopIsIronChain(IBlockAccess world, BlockPos pos)
	{
		BlockPos posu = pos.up();
		if(world.getBlockState(posu).getBlock() != Register.BLOCK_IRON_CHAIN)
			return true;
		else
			return false;
	}

	private static boolean getBottomIsIronChain(IBlockAccess world, BlockPos pos)
	{
		BlockPos posd = pos.down();
		if(world.getBlockState(posd).getBlock() != Register.BLOCK_IRON_CHAIN)
			return true;
		else
			return false;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TOP, BOTTOM });
	}
}
