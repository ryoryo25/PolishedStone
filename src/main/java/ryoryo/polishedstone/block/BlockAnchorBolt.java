package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.util.References;

public class BlockAnchorBolt extends BlockModBase
{
	/**
	 *         (NORTH)
	 *            |
	 *            |
	 * (WEST) ----|---->+x (EAST)
	 *            |
	 *            |
	 *            V
	 *            +z (SOUTH)
	 */
	protected static final AxisAlignedBB BOLT_AABB_NORTH = new AxisAlignedBB(0.425D, 0.0D, 0.375D, 0.575D, 0.125D, 1.0D);
	protected static final AxisAlignedBB BOLT_AABB_SOUTH = new AxisAlignedBB(0.425D, 0.0D, 0.0D, 0.575D, 0.125D, 0.625D);
	protected static final AxisAlignedBB BOLT_AABB_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.425D, 0.625D, 0.125D, 0.575D);
	protected static final AxisAlignedBB BOLT_AABB_EAST = new AxisAlignedBB(0.375D, 0.0D, 0.425D, 1.0D, 0.125D, 0.575D);

	public static final PropertyDirection FACING = Utils.HORIZONTAL_FACING;

	public BlockAnchorBolt()
	{
		super(Material.GROUND, "anchor_bolt", SoundType.METAL);
		this.setHardness(0.5F);
		this.setResistance(2.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}


	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);
		switch(facing)
		{
		case SOUTH:
		default:
			return BOLT_AABB_NORTH;
		case NORTH:
			return BOLT_AABB_SOUTH;
		case WEST:
			return BOLT_AABB_WEST;
		case EAST:
			return BOLT_AABB_EAST;
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return world.isSideSolid(pos.west(), EnumFacing.EAST) || world.isSideSolid(pos.east(), EnumFacing.WEST) || world.isSideSolid(pos.north(), EnumFacing.SOUTH) || world.isSideSolid(pos.south(), EnumFacing.NORTH);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		int meta = this.getMetaFromState(state);
		boolean flag = false;

		//NORTH
		if(meta == 2 && world.isSideSolid(pos.north(), EnumFacing.SOUTH))
		{
			flag = true;
		}
		//SOUTH
		if(meta == 3 && world.isSideSolid(pos.south(), EnumFacing.NORTH))
		{
			flag = true;
		}
		//WEST
		if(meta == 4 && world.isSideSolid(pos.west(), EnumFacing.EAST))
		{
			flag = true;
		}
		//EAST
		if(meta == 5 && world.isSideSolid(pos.east(), EnumFacing.WEST))
		{
			flag = true;
		}

		if(!flag)
		{
			this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack =player.inventory.getCurrentItem();
		int l = 0;
		l = this.thisChainLength(world, pos);

		if(stack.isEmpty())
		{
			if(l < 0)
				return false;
			else if(l < 65)
			{
				Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_IRON_CHAIN, l));
				Utils.giveItemToPlayer(player, new ItemStack(Register.BLOCK_ANCHOR_BOLT, 1));
				if(l > 0)
					world.playSound(player, pos, Register.SOUND_IRON_CHAIN, SoundCategory.BLOCKS, 1.0F, 0.7F);
				else
					world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.4F, 1.9F);

				for(int i = 0; i < (l + 1); i++)
				{
					BlockPos posn = pos.down(l -/*+*/ i);
					world.setBlockToAir(posn);
				}
			}
			return false;
		}
		else if(this.canPlace(stack))
		{
			Block placeID = Block.getBlockFromItem(stack.getItem());
			if(placeID == null)
				return false;
			int placeMeta = stack.getItemDamage();

			if(l < 65 && world.isAirBlock(pos.down(l +/*-*/ 1)))
			{
				world.setBlockState(pos.down(l +/*-*/ 1), placeID.getStateFromMeta(placeMeta));
				world.playSound(player, pos, Register.SOUND_IRON_CHAIN, SoundCategory.BLOCKS,  1.0F, 0.7F);
				if(!Utils.isCreative(player))
				{
					stack.shrink(1);
					if(stack.getCount() <= 0)
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					return true;
				}
				return true;
			}
			return false;
		}
		return false;
	}

	private boolean canPlace(ItemStack itemstack)
	{
		if(itemstack.getItem() == Item.getItemFromBlock(Register.BLOCK_IRON_CHAIN))
			return true;
		else
			return false;
	}

	private int thisChainLength(World world, BlockPos pos)
	{
		int l = 0;
		boolean end = false;

		for(int i = 1; i < 66; i++)
		{
			BlockPos posn = pos.down(i);
			if(world.getBlockState(posn).getBlock() == Register.BLOCK_IRON_CHAIN && posn.getY() > 0 && !end)
				++l;
			else
				end = true;
		}

		return l;
	}

	private boolean canPlaceOn(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if(state.isSideSolid(world, pos, EnumFacing.UP))
		{
			return true;
		}
		else
		{
			return state.getBlock().canPlaceTorchOnTop(state, world, pos);
		}
	}

	private boolean canPlaceAt(World world, BlockPos pos, EnumFacing facing)
	{
		BlockPos blockpos = pos.offset(facing);
		boolean flag = facing.getAxis().isHorizontal();
		return flag && world.isSideSolid(blockpos, facing, true) || facing.equals(EnumFacing.UP) && this.canPlaceOn(world, blockpos);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		if(this.canPlaceAt(world, pos, facing))
		{
			return this.getDefaultState().withProperty(FACING, facing);
		}
		else
		{
			for(EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
			{
				if(world.isSideSolid(pos.offset(enumfacing), enumfacing, true))
				{
					return this.getDefaultState().withProperty(FACING, enumfacing);
				}
			}

			return this.getDefaultState();
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ FACING });
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}
}
