package ryoryo.polishedstone.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Props;

public class BlockIronPlateStairs extends BlockModBase {
	/**
	 * B: xx T: .. B: xx T: ..
	 */
	protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	/**
	 * B: .. T: x. B: .. T: x.
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
	/**
	 * B: .. T: .x B: .. T: .x
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	/**
	 * B: .. T: xx B: .. T: ..
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
	/**
	 * B: .. T: .. B: .. T: xx
	 */
	protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);

	public static final PropertyDirection FACING = Props.HORIZONTAL_FACING;
	public static final PropertyBool FLOATING = PropertyBool.create("floating");

	public BlockIronPlateStairs() {
		super(Material.IRON, "iron_plate_stairs", SoundType.METAL);
		this.setHardness(0.2F);
		this.setResistance(15.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(FLOATING, Boolean.valueOf(false)));
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
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
		getCollisionBoxList(this.getActualState(state, world, pos))
				.forEach(aabb -> addCollisionBoxToList(pos, entityBox, collidingBoxes, aabb));
	}

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate) {
		List<AxisAlignedBB> list = new ArrayList<AxisAlignedBB>();
		EnumFacing facing = bstate.getValue(FACING);
		list.add(AABB_SLAB_BOTTOM);

		switch(facing) {
			case NORTH:
			default:
				list.add(AABB_QTR_TOP_NORTH);
				break;
			case SOUTH:
				list.add(AABB_QTR_TOP_SOUTH);
				break;
			case WEST:
				list.add(AABB_QTR_TOP_WEST);
				break;
			case EAST:
				list.add(AABB_QTR_TOP_EAST);
				break;
		}

		return list;
	}

	@Override
	@Nullable
	public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end) {
		return getCollisionBoxList(this.getActualState(blockState, world, pos)).stream()
				.map(aabb -> this.rayTrace(pos, start, end, aabb))
				.filter(Objects::nonNull)
				.max((result1, result2) -> (int) (result1.hitVec.squareDistanceTo(end) - result2.hitVec.squareDistanceTo(end)))
				.orElse(null);
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
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(FLOATING, Boolean.valueOf(getDownIsAir(world, pos)));
	}

	private static boolean getDownIsAir(IBlockAccess world, BlockPos pos) {
		return (world.getBlockState(pos.down()).getMaterial() == Material.AIR) ? true : false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, FLOATING });
	}

	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}
}
