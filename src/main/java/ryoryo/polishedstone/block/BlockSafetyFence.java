package ryoryo.polishedstone.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedlib.util.Utils;

public class BlockSafetyFence extends BlockModBase {
	public static final PropertyDirection FACING = Props.HORIZONTAL_FACING;
	public static final PropertyBool TOP = Props.TOP;
	public static final PropertyBool BOTTOM = Props.BOTTOM;

	public BlockSafetyFence() {
		super(Material.GROUND, "safety_fence", SoundType.METAL);
		this.setHardness(0.5F);
		this.setResistance(20.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TOP, Boolean.valueOf(false)).withProperty(BOTTOM, Boolean.valueOf(false)));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return getBounds(state, false);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, collidingBoxes, getBounds(state, true));
	}

	private static AxisAlignedBB getBounds(IBlockState state, boolean flag) {
		double d = flag ? 1.5F : 1.0F;
		EnumFacing facing = state.getValue(FACING);

		switch(facing) {
			case NORTH:
			default:
				return new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, d, 1.0D);
			case SOUTH:
				return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, d, 0.25D);
			case WEST:
				return new AxisAlignedBB(0.75D, 0.0D, 0.0D, 1.0D, d, 1.0D);
			case EAST:
				return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.25D, d, 1.0D);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if(enumfacing.getAxis() == EnumFacing.Axis.Y) {
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(TOP, Boolean.valueOf(getUpIsThis(world, pos))).withProperty(BOTTOM, Boolean.valueOf(getDownIsThis(world, pos)));
	}

	private static boolean getUpIsThis(IBlockAccess world, BlockPos pos) {
		if(Utils.isAirMaterial(world.getBlockState(pos.up()))/*
																 * .isSideSolid(
																 * world, pos,
																 * EnumFacing.
																 * DOWN)
																 */)
			return false;
		else
			return true;
	}

	private static boolean getDownIsThis(IBlockAccess world, BlockPos pos) {
		if(Utils.isAirMaterial(world.getBlockState(pos.down()))/*
																 * .isSideSolid(
																 * world, pos,
																 * EnumFacing.
																 * UP)
																 */)
			return false;
		else
			return true;
	}
	//
	// private static boolean getRightIsThis(IBlockState state, IBlockAccess
	// world, BlockPos pos)
	// {
	// EnumFacing facing = state.getValue(FACING);
	// if(world.getBlockState(Utils.getRightPos(facing, pos)).getBlock() ==
	// Register.blockSafetyFence)
	// return true;
	// else
	// return false;
	// }
	//
	// private static boolean getLeftIsThis(IBlockState state, IBlockAccess
	// world, BlockPos pos)
	// {
	// EnumFacing facing = state.getValue(FACING);
	// if(world.getBlockState(Utils.getLeftPos(facing, pos)).getBlock() ==
	// Register.blockSafetyFence)
	// return true;
	// else
	// return false;
	// }

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, TOP, BOTTOM, });
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}
}
