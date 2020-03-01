package ryoryo.polishedstone.block;

import static ryoryo.polishedlib.util.Utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedlib.util.enums.EnumSimpleFacing;
import ryoryo.polishedstone.Register;

public class BlockStepladder extends BlockModBase
{
	protected static final AxisAlignedBB FIRST_AABB = creatAABB(0, 0, 0, 16, 16, 16);
//-----------------------------------------------------------------------------------------------------------------------------------------
	protected static final AxisAlignedBB FIRST_AABB_BOTTOM1_NORTH = creatAABB(0, 0, 0, 16, 7, 2);
	protected static final AxisAlignedBB FIRST_AABB_BOTTOM2_NORTH = creatAABB(0, 0, 14, 16, 7, 16);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD1_NORTH = creatAABB(0, 7, 0, 16, 8, 3);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD2_NORTH = creatAABB(0, 7, 13, 16, 8, 16);

	protected static final AxisAlignedBB FIRST_AABB_BOTTOM1_WEST = creatAABB(0, 0, 0, 2, 7, 16);
	protected static final AxisAlignedBB FIRST_AABB_BOTTOM2_WEST = creatAABB(14, 0, 0, 16, 7, 16);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD1_WEST = creatAABB(0, 7, 0, 3, 8, 16);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD2_WEST = creatAABB(13, 7, 0, 16, 8, 16);

	protected static final AxisAlignedBB FIRST_AABB_TOP1_NORTH = creatAABB(0, 8, 1, 16, 15, 3);
	protected static final AxisAlignedBB FIRST_AABB_TOP2_NORTH = creatAABB(0, 8, 13, 16, 15, 15);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD3_NORTH = creatAABB(0, 15, 1, 16, 16, 4);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD4_NORTH = creatAABB(0, 15, 12, 16, 16, 15);

	protected static final AxisAlignedBB FIRST_AABB_TOP1_WEST = creatAABB(1, 8, 0, 3, 15, 16);
	protected static final AxisAlignedBB FIRST_AABB_TOP2_WEST = creatAABB(13, 8, 0, 15, 15, 16);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD3_WEST = creatAABB(1, 15, 0, 4, 16, 16);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD4_WEST = creatAABB(12, 15, 0, 15, 16, 16);

	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD_NORTH = creatAABB(0, 14, 1, 16, 16, 15);
	protected static final AxisAlignedBB FIRST_AABB_SCAFOLD_WEST = creatAABB(1, 14, 0, 15, 16, 16);

//*****************************************************************************************************************************************

	protected static final AxisAlignedBB SECOND_AABB_NORTH = creatAABB(0, 0, 2, 16, 16, 14);
	protected static final AxisAlignedBB SECOND_AABB_WEST = creatAABB(2, 0, 0, 14, 16, 16);
//-----------------------------------------------------------------------------------------------------------------------------------------
	protected static final AxisAlignedBB SECOND_AABB_BOTTOM1_NORTH = creatAABB(0, 0, 2, 16, 7, 4);
	protected static final AxisAlignedBB SECOND_AABB_BOTTOM2_NORTH = creatAABB(0, 0, 12, 16, 7, 14);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD1_NORTH = creatAABB(0, 7, 2, 16, 8, 5);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD2_NORTH = creatAABB(0, 7, 11, 16, 8, 14);

	protected static final AxisAlignedBB SECOND_AABB_BOTTOM1_WEST = creatAABB(2, 0, 0, 4, 7, 16);
	protected static final AxisAlignedBB SECOND_AABB_BOTTOM2_WEST = creatAABB(12, 0, 0, 14, 7, 16);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD1_WEST = creatAABB(2, 7, 0, 5, 8, 16);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD2_WEST = creatAABB(11, 7, 0, 14, 8, 16);

	protected static final AxisAlignedBB SECOND_AABB_TOP1_NORTH = creatAABB(0, 8, 3, 16, 15, 5);
	protected static final AxisAlignedBB SECOND_AABB_TOP2_NORTH = creatAABB(0, 8, 11, 16, 15, 13);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD3_NORTH = creatAABB(0, 15, 3, 16, 16, 6);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD4_NORTH = creatAABB(0, 15, 10, 16, 16, 13);

	protected static final AxisAlignedBB SECOND_AABB_TOP1_WEST = creatAABB(3, 8, 0, 5, 15, 16);
	protected static final AxisAlignedBB SECOND_AABB_TOP2_WEST = creatAABB(11, 8, 0, 13, 15, 16);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD3_WEST = creatAABB(3, 15, 0, 6, 16, 16);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD4_WEST = creatAABB(10, 15, 0, 13, 16, 16);

	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD_NORTH = creatAABB(0, 14, 3, 16, 16, 13);
	protected static final AxisAlignedBB SECOND_AABB_SCAFOLD_WEST = creatAABB(3, 14, 0, 13, 16, 16);

//*****************************************************************************************************************************************

	protected static final AxisAlignedBB THIRD_AABB_NORTH = creatAABB(0, 0, 4, 16, 16, 12);
	protected static final AxisAlignedBB THIRD_AABB_WEST = creatAABB(4, 0, 0, 12, 16, 16);
//-----------------------------------------------------------------------------------------------------------------------------------------
	protected static final AxisAlignedBB THIRD_AABB_BOTTOM1_NORTH = creatAABB(0, 0, 4, 16, 7, 6);
	protected static final AxisAlignedBB THIRD_AABB_BOTTOM2_NORTH = creatAABB(0, 0, 10, 16, 7, 12);
	protected static final AxisAlignedBB THIRD_AABB_SCAFOLD1_NORTH = creatAABB(0, 7, 4, 16, 8, 7);
	protected static final AxisAlignedBB THIRD_AABB_SCAFOLD2_NORTH = creatAABB(0, 7, 9, 16, 8, 12);

	protected static final AxisAlignedBB THIRD_AABB_BOTTOM1_WEST = creatAABB(4, 0, 0, 6, 7, 16);
	protected static final AxisAlignedBB THIRD_AABB_BOTTOM2_WEST = creatAABB(10, 0, 0, 12, 7, 16);
	protected static final AxisAlignedBB THIRD_AABB_SCAFOLD1_WEST = creatAABB(4, 7, 0, 7, 8, 16);
	protected static final AxisAlignedBB THIRD_AABB_SCAFOLD2_WEST = creatAABB(9, 7, 0, 12, 8, 16);

	protected static final AxisAlignedBB THIRD_AABB_TOP1_NORTH = creatAABB(0, 8, 5, 16, 14, 7);
	protected static final AxisAlignedBB THIRD_AABB_TOP2_NORTH = creatAABB(0, 8, 9, 16, 14, 11);

	protected static final AxisAlignedBB THIRD_AABB_TOP1_WEST = creatAABB(5, 8, 0, 7, 14, 16);
	protected static final AxisAlignedBB THIRD_AABB_TOP2_WEST = creatAABB(9, 8, 0, 11, 14, 16);

	protected static final AxisAlignedBB THIRD_AABB_SCAFOLD_NORTH = creatAABB(0, 14, 5, 16, 16, 11);
	protected static final AxisAlignedBB THIRD_AABB_SCAFOLD_WEST = creatAABB(5, 14, 0, 11, 16, 16);

	public static final PropertyEnum<EnumSimpleFacing> FACING = Utils.SIMPLE_FACING;
	public static final PropertyEnum<EnumStage> STAGE = PropertyEnum.<EnumStage> create("stage", EnumStage.class);
	public static final PropertyBool TOP = Utils.TOP;

	public BlockStepladder()
	{
		super(Material.GROUND, "stepladder", SoundType.METAL);
		this.setHardness(0.5F);
		this.setResistance(2.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumSimpleFacing.NORTH).withProperty(STAGE, EnumStage.FIRST).withProperty(TOP, Boolean.valueOf(false)));
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
		EnumSimpleFacing facing = state.getValue(FACING);
		EnumStage stage = state.getValue(STAGE);
		boolean flag1 = stage == EnumStage.SECOND;

		if(stage == EnumStage.FIRST)
			return FIRST_AABB;
		else
		{
			switch(facing)
			{
			case NORTH:
			default:
				return flag1 ? SECOND_AABB_NORTH : THIRD_AABB_NORTH;
			case WEST:
				return flag1 ? SECOND_AABB_WEST : THIRD_AABB_WEST;
			}
		}
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
	{
		state = this.getActualState(state, world, pos);

		for(AxisAlignedBB aabb : getCollisionBoxList(state))
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb);
		}
	}

	//TODO
	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
	{
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();
		EnumStage stage = bstate.getValue(STAGE);
		EnumSimpleFacing facing = bstate.getValue(FACING);
		boolean isTop = bstate.getValue(TOP).booleanValue();
		switch(facing)
		{
		case NORTH:
		default:
			switch(stage)
			{
			case FIRST:
			default:
				list.add(FIRST_AABB_TOP1_NORTH);
				list.add(FIRST_AABB_TOP2_NORTH);
				list.add(FIRST_AABB_BOTTOM1_NORTH);
				list.add(FIRST_AABB_BOTTOM2_NORTH);
				list.add(FIRST_AABB_SCAFOLD1_NORTH);
				list.add(FIRST_AABB_SCAFOLD2_NORTH);
				list.add(FIRST_AABB_SCAFOLD3_NORTH);
				list.add(FIRST_AABB_SCAFOLD4_NORTH);
				if(isTop)
					list.add(FIRST_AABB_SCAFOLD_NORTH);
				break;
			case SECOND:
				list.add(SECOND_AABB_TOP1_NORTH);
				list.add(SECOND_AABB_TOP2_NORTH);
				list.add(SECOND_AABB_BOTTOM1_NORTH);
				list.add(SECOND_AABB_BOTTOM2_NORTH);
				list.add(SECOND_AABB_SCAFOLD1_NORTH);
				list.add(SECOND_AABB_SCAFOLD2_NORTH);
				list.add(SECOND_AABB_SCAFOLD3_NORTH);
				list.add(SECOND_AABB_SCAFOLD4_NORTH);
				if(isTop)
					list.add(SECOND_AABB_SCAFOLD_NORTH);
				break;
			case THIRD:
				list.add(THIRD_AABB_TOP1_NORTH);
				list.add(THIRD_AABB_TOP2_NORTH);
				list.add(THIRD_AABB_BOTTOM1_NORTH);
				list.add(THIRD_AABB_BOTTOM2_NORTH);
				list.add(THIRD_AABB_SCAFOLD1_NORTH);
				list.add(THIRD_AABB_SCAFOLD2_NORTH);
				if(isTop)
					list.add(THIRD_AABB_SCAFOLD_NORTH);
				break;
			}
			break;
		case WEST:
			switch(stage)
			{
			case FIRST:
			default:
				list.add(FIRST_AABB_TOP1_WEST);
				list.add(FIRST_AABB_TOP2_WEST);
				list.add(FIRST_AABB_BOTTOM1_WEST);
				list.add(FIRST_AABB_BOTTOM2_WEST);
				list.add(FIRST_AABB_SCAFOLD1_WEST);
				list.add(FIRST_AABB_SCAFOLD2_WEST);
				list.add(FIRST_AABB_SCAFOLD3_WEST);
				list.add(FIRST_AABB_SCAFOLD4_WEST);
				if(isTop)
					list.add(FIRST_AABB_SCAFOLD_WEST);
				break;
			case SECOND:
				list.add(SECOND_AABB_TOP1_WEST);
				list.add(SECOND_AABB_TOP2_WEST);
				list.add(SECOND_AABB_BOTTOM1_WEST);
				list.add(SECOND_AABB_BOTTOM2_WEST);
				list.add(SECOND_AABB_SCAFOLD1_WEST);
				list.add(SECOND_AABB_SCAFOLD2_WEST);
				list.add(SECOND_AABB_SCAFOLD3_WEST);
				list.add(SECOND_AABB_SCAFOLD4_WEST);
				if(isTop)
					list.add(SECOND_AABB_SCAFOLD_WEST);
				break;
			case THIRD:
				list.add(THIRD_AABB_TOP1_WEST);
				list.add(THIRD_AABB_TOP2_WEST);
				list.add(THIRD_AABB_BOTTOM1_WEST);
				list.add(THIRD_AABB_BOTTOM2_WEST);
				list.add(THIRD_AABB_SCAFOLD1_WEST);
				list.add(THIRD_AABB_SCAFOLD2_WEST);
				if(isTop)
					list.add(THIRD_AABB_SCAFOLD_WEST);
				break;
			}
			break;
		}

		return list;
	}

	@Override
	@Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
	{
		List<RayTraceResult> list = new ArrayList<RayTraceResult>();

		for(AxisAlignedBB aabb : getCollisionBoxList(this.getActualState(blockState, world, pos)))
		{
			list.add(this.rayTrace(pos, start, end, aabb));
		}

		RayTraceResult bestresult = null;
		double d1 = 0.0D;

		for(RayTraceResult result : list)
		{
			if(result != null)
			{
				double d0 = result.hitVec.squareDistanceTo(end);

				if(d0 > d1)
				{
					bestresult = result;
					d1 = d0;
				}
			}
		}

		return bestresult;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		world.setBlockState(pos.up(), this.getDefaultState().withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.convertToNormalFacing(placer.getHorizontalFacing())), 2);
		world.setBlockState(pos.up().up(), this.getDefaultState().withProperty(STAGE, EnumStage.THIRD).withProperty(FACING, EnumSimpleFacing.convertToNormalFacing(placer.getHorizontalFacing())), 2);
		return this.getDefaultState().withProperty(FACING, EnumSimpleFacing.convertToNormalFacing(placer.getHorizontalFacing()));
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		EnumStage stage = state.getValue(STAGE);
		return (stage == EnumStage.SECOND || stage == EnumStage.THIRD) ? 0 : 1;
	}

	//TODO
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if(meta < 2)
		{
			switch(meta)
			{
			case 0:
			default:
				return this.getDefaultState().withProperty(STAGE, EnumStage.FIRST).withProperty(FACING, EnumSimpleFacing.NORTH);
			case 1:
				return this.getDefaultState().withProperty(STAGE, EnumStage.FIRST).withProperty(FACING, EnumSimpleFacing.WEST);
			}
		}
		else if(meta > 1 && meta < 4)
		{
			switch(meta)
			{
			case 2:
			default:
				return this.getDefaultState().withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.NORTH);
			case 3:
				return this.getDefaultState().withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.WEST);
			}
		}
		else if(meta > 3 && meta < 6)
		{
			switch(meta)
			{
			case 4:
			default:
				return this.getDefaultState().withProperty(STAGE, EnumStage.THIRD).withProperty(FACING, EnumSimpleFacing.NORTH);
			case 5:
				return this.getDefaultState().withProperty(STAGE, EnumStage.THIRD).withProperty(FACING, EnumSimpleFacing.WEST);
			}
		}
		else
			return this.getDefaultState();
	}

	//TODO
	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumStage stage = state.getValue(STAGE);
		EnumSimpleFacing facing = state.getValue(FACING);

		switch(stage)
		{
		case FIRST:
		default:
			switch(facing)
			{
			case NORTH:
			default:
				return 0;
			case WEST:
				return 1;
			}
		case SECOND:
			switch(facing)
			{
			case NORTH:
			default:
				return 2;
			case WEST:
				return 3;
			}
		case THIRD:
			switch(facing)
			{
			case NORTH:
			default:
				return 4;
			case WEST:
				return 5;
			}
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(TOP, Boolean.valueOf(isTop(world, pos)));
	}

	private static boolean isTop(IBlockAccess world, BlockPos pos)
	{
		return (world.getBlockState(pos.up()).getBlock() != Register.BLOCK_STEPLADDER) ? true : false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		BlockPos posu = pos.up();
		BlockPos posuu = pos.up().up();
		Block blocku = world.getBlockState(posu).getBlock();
		Block blockuu = world.getBlockState(posuu).getBlock();

		if(itemstack.isEmpty() && world.getBlockState(pos).getValue(STAGE) == EnumStage.FIRST)
		{
			if(blocku == this && blockuu == this)
			{
				if(world.getBlockState(pos).getValue(FACING) == EnumSimpleFacing.NORTH)
				{
					world.setBlockState(pos, this.getDefaultState().withProperty(FACING, EnumSimpleFacing.WEST), 2);
					world.setBlockState(pos.up(), this.getDefaultState().withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.WEST), 2);
					world.setBlockState(pos.up().up(), this.getDefaultState().withProperty(STAGE, EnumStage.THIRD).withProperty(FACING, EnumSimpleFacing.WEST), 2);
				}
				else if(world.getBlockState(pos).getValue(FACING) == EnumSimpleFacing.WEST)
				{
					world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, EnumSimpleFacing.NORTH), 2);
					world.setBlockState(pos.up(), world.getBlockState(pos.up()).withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.NORTH), 2);
					world.setBlockState(pos.up().up(), world.getBlockState(pos.up().up()).withProperty(STAGE, EnumStage.THIRD).withProperty(FACING, EnumSimpleFacing.NORTH), 2);
				}
			}
			else if(blocku == this && blockuu != this)
			{
				if(world.getBlockState(pos).getValue(FACING) == EnumSimpleFacing.NORTH)
				{
					world.setBlockState(pos, this.getDefaultState().withProperty(FACING, EnumSimpleFacing.WEST), 2);
					world.setBlockState(pos.up(), this.getDefaultState().withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.WEST), 2);
				}
				else if(world.getBlockState(pos).getValue(FACING) == EnumSimpleFacing.WEST)
				{
					world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, EnumSimpleFacing.NORTH), 2);
					world.setBlockState(pos.up(), world.getBlockState(pos.up()).withProperty(STAGE, EnumStage.SECOND).withProperty(FACING, EnumSimpleFacing.NORTH), 2);
				}
			}
			else if(blocku != this && blockuu != this)
			{
				if(world.getBlockState(pos).getValue(FACING) == EnumSimpleFacing.NORTH)
					world.setBlockState(pos, this.getDefaultState().withProperty(FACING, EnumSimpleFacing.WEST), 2);
				else if(world.getBlockState(pos).getValue(FACING) == EnumSimpleFacing.WEST)
					world.setBlockState(pos, world.getBlockState(pos).withProperty(FACING, EnumSimpleFacing.NORTH), 2);
			}
			world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.4F, 1.8F);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return pos.getY() >= 254 ? false : world.isSideSolid(pos.down(), EnumFacing.UP) && world.isAirBlock(pos.up()) && world.isAirBlock(pos.up().up());
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		if(!world.isSideSolid(pos.down(), EnumFacing.UP) && world.getBlockState(pos.down()).getBlock() != this)
		{
			if(world.getBlockState(pos).getValue(STAGE) == EnumStage.FIRST)
				this.dropBlockAsItem(world, pos, this.getDefaultState(), 0);
			world.setBlockToAir(pos);
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ FACING, STAGE, TOP, });
	}

//	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
//	{
//		return true;
//	}

	public static enum EnumStage implements IStringSerializable
	{
		FIRST("first"),
		SECOND("second"),
		THIRD("third"),;

		private final String name;

		private EnumStage(String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return name;
		}
	}
}
