package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;

public class BlockIronLadder extends BlockModBase
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
	protected static final AxisAlignedBB LAD_AABB_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);/*Utils.creatNewAABB(new double[] { 0.0D, 1.0D }, new double[] { 0.0D, 0.125D }, new double[] { 0.875D, 1.0D });*/
	protected static final AxisAlignedBB LAD_AABB_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
	protected static final AxisAlignedBB LAD_AABB_WEST = new AxisAlignedBB(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB LAD_AABB_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);

	public static final PropertyDirection FACING = Props.HORIZONTAL_FACING;
	public static final PropertyBool TOP = Props.TOP;
	public static final PropertyBool PROP = PropertyBool.create("prop");

	public BlockIronLadder()
	{
		super(Material.CLAY, "iron_ladder", SoundType.METAL);
		this.setHardness(0.5F);
		this.setResistance(20.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TOP, Boolean.valueOf(false)).withProperty(PROP, Boolean.valueOf(false)));
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);
		switch(facing)
		{
		case NORTH:
		default:
			return LAD_AABB_NORTH;
		case SOUTH:
			return LAD_AABB_SOUTH;
		case WEST:
			return LAD_AABB_WEST;
		case EAST:
			return LAD_AABB_EAST;
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
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(TOP, Boolean.valueOf(getUp(world, pos))).withProperty(PROP, Boolean.valueOf(getProp(world, pos)));
	}

	private static boolean getUp(IBlockAccess world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		BlockPos posu = pos.up();

		if(world.getBlockState(pos.up()).getBlock() != Register.BLOCK_IRON_LADDER && world.getBlockState(posu).getMaterial() == Material.AIR)
		{
			EnumFacing facing = state.getValue(FACING);
			switch(facing)
			{
			case NORTH:
			default:
				return world.getBlockState(posu.south()).getMaterial() == Material.AIR ? true : false;
			case SOUTH:
				return world.getBlockState(posu.north()).getMaterial() == Material.AIR ? true : false;
			case WEST:
				return world.getBlockState(posu.east()).getMaterial() == Material.AIR ? true : false;
			case EAST:
				return world.getBlockState(posu.west()).getMaterial() == Material.AIR ? true : false;
			}
		}
		else
			return false;
	}

	private static boolean getProp(IBlockAccess world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		EnumFacing facing = state.getValue(FACING);
		switch(facing)
		{
		case NORTH:
		default:
			return world.getBlockState(pos.south()).getMaterial() != Material.AIR ? true : false;
		case SOUTH:
			return world.getBlockState(pos.north()).getMaterial() != Material.AIR ? true : false;
		case WEST:
			return world.getBlockState(pos.east()).getMaterial() != Material.AIR ? true : false;
		case EAST:
			return world.getBlockState(pos.west()).getMaterial() != Material.AIR ? true : false;
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ FACING, TOP, PROP, });
	}

	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		if(stack.isEmpty() || stack.getItem() != Item.getItemFromBlock(this))
		{
			return false;
		}
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(state);
		int l = 0;
		l = this.thisLadderLength(world, pos);
		BlockPos posu = pos.up(l);
		if(l > 0 && world.isAirBlock(posu))
		{
			world.playSound(player, pos, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if(!world.isRemote)
			{
				world.setBlockState(posu, this.getStateFromMeta(meta));
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

	private int thisLadderLength(World world, BlockPos pos)
	{
		Block block = world.getBlockState(pos).getBlock();
		if(block != this)
		{
			return 0;
		}
		else
		{
			boolean b = true;
			int i = 0;
			while(b == true)
			{
				Block target = world.getBlockState(pos.up(i)).getBlock();
				b = target == this;
				if(b)
				{
					i++;
				}
				if(i > 16 || pos.getY() + i > 255)
				{
					b = false;
				}
			}
			return i;
		}
	}
}
