package ryoryo.polishedstone.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.Props;
import ryoryo.polishedlib.util.RegistryUtils;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.config.ModConfig;

public class BlockFaucet extends BlockModBase {
	protected static final AxisAlignedBB FAUCET_NORTH_AABB = Utils.creatAABB(7, 6.5, 0, 9, 15, 9);
	protected static final AxisAlignedBB FAUCET_SOUTH_AABB = Utils.creatAABB(7, 6.5, 7, 9, 15, 16);
	protected static final AxisAlignedBB FAUCET_WEST_AABB = Utils.creatAABB(0, 6.5, 7, 9, 15, 9);
	protected static final AxisAlignedBB FAUCET_EAST_AABB = Utils.creatAABB(7, 6.5, 7, 16, 15, 9);

	public static final PropertyDirection FACING = Props.HORIZONTAL_FACING;
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 1);
	public static final PropertyBool RUNNING = PropertyBool.create("running");

	public BlockFaucet() {
		super(Material.WOOD, "faucet");
		this.setHardness(0.4F);
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, 0).withProperty(RUNNING, Boolean.valueOf(false)));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	// @SideOnly(Side.CLIENT)
	// public BlockRenderLayer getBlockLayer()
	// {
	// return BlockRenderLayer.TRANSLUCENT;
	// }

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(TYPE, meta == 0 ? 0 : 1);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing facing = state.getValue(FACING);
		switch(facing) {
			case NORTH:
			default:
				return FAUCET_NORTH_AABB;
			case SOUTH:
				return FAUCET_SOUTH_AABB;
			case WEST:
				return FAUCET_WEST_AABB;
			case EAST:
				return FAUCET_EAST_AABB;
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE) == 0 ? 0 : 4;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		if(!world.isAirBlock(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())) || !world.isAirBlock(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())) || !world.isAirBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)) || !world.isAirBlock(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1))) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return FaucetOnOff(world, pos);
	}

	private boolean FaucetOnOff(World world, BlockPos pos) {
		if(world.isRemote) {
			return true;
		}
		IBlockState id = world.getBlockState(pos);

		if(!id.getValue(RUNNING).booleanValue())
			world.setBlockState(pos, id.withProperty(RUNNING, Boolean.valueOf(true)));
		else
			world.setBlockState(pos, id.withProperty(RUNNING, Boolean.valueOf(false)));

		world.playSound(Utils.getPlayer(), pos, SoundEvents.ENTITY_PLAYER_SWIM, SoundCategory.BLOCKS, 1.0F, 1.2F);

		boolean open = id.getValue(RUNNING).booleanValue() == true;
		IBlockState downId = world.getBlockState(pos.down());
		int ret;
		if(open) {
			ret = makeStream(world, pos);
		}
		else if(downId == Register.BLOCK_RUNNING_WATER) {
			world.setBlockToAir(pos.down());
		}
		else if((downId == Blocks.WATER || downId == Blocks.FLOWING_WATER) && ModConfig.waterOriginDelete) {
			world.setBlockToAir(pos.down());
		}
		return true;
	}

	@Override
	public int tickRate(World world) {
		return 1;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(!world.isRemote && world.isBlockPowered(pos)) {
			FaucetOnOff(world, pos);
			return;
		}
		if(!world.isRemote && world.getBlockState(pos.down()).getBlock() == Blocks.CAULDRON) {
			neighborChanged(state, world, pos, Blocks.CAULDRON, pos);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if(block != Blocks.AIR && block.canProvidePower(state)) {
			boolean flag = world.isBlockPowered(pos) || world.isBlockPowered(pos.up());
			if(flag) {
				world.notifyNeighborsOfStateChange(pos, Register.BLOCK_FAUCET, false);// TODO
				return;
			}
		}
		IBlockState id = world.getBlockState(pos);
		boolean open = id.getValue(RUNNING).equals(true);;

		IBlockState topId = world.getBlockState(pos.up());
		IBlockState downId = world.getBlockState(pos.down());
		int ret;
		if(open) {
			ret = makeStream(world, pos);
		}
		else if(downId == Register.BLOCK_RUNNING_WATER) {
			world.isAirBlock(pos.down());
		}
		else if(downId == Blocks.WATER && ModConfig.waterOriginDelete) {
			world.isAirBlock(pos.down());
		}
	}

	private int makeStream(World world, BlockPos pos) {
		int y = pos.getY();
		while(world.getBlockState(new BlockPos(pos.getX(), --y, pos.getZ())).getBlock() == Blocks.AIR && y >= 0) {
			world.setBlockState(new BlockPos(pos.getX(), y, pos.getZ()), Register.BLOCK_RUNNING_WATER.getDefaultState());
		}
		BlockPos posy = new BlockPos(pos.getX(), y, pos.getZ());
		IBlockState id2 = world.getBlockState(posy);
		if(id2 == Blocks.CAULDRON) {
			world.setBlockState(posy, Blocks.CAULDRON.getDefaultState().withProperty(BlockCauldron.LEVEL, 3));
			world.notifyNeighborsOfStateChange(posy.up(), world.getBlockState(posy.up()).getBlock()/*
																									 * ,
																									 * 50
																									 */, false);
			return 0;
		}
		if(id2 == Blocks.FLOWING_WATER || id2 == Blocks.WATER) {
			int meta2 = world.getBlockState(posy).getBlock().getMetaFromState(world.getBlockState(posy));
			if(meta2 != 0) {
				world.setBlockState(posy, Blocks.FLOWING_WATER.getDefaultState());
			}
			return 0;
		}
		if(world.getBlockState(posy.up()).getBlock() != Register.BLOCK_FAUCET) {
			world.setBlockState(posy.up(), Blocks.FLOWING_WATER.getDefaultState());
		}
		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		int newmeta = 0;
		int length = EnumFacing.values().length - 2;

		if(meta < length)
			newmeta = meta;
		else if(meta >= length && meta < length * 2)
			newmeta = meta - length;
		else if(meta >= length * 2 && meta < length * 3)
			newmeta = meta - length * 2;
		else if(meta >= length * 3)
			newmeta = meta - length * 3;

		return this.getDefaultState()
				.withProperty(FACING, EnumFacing.getHorizontal(newmeta))
				.withProperty(TYPE, meta < length * 2 ? 0 : 1)
				.withProperty(RUNNING, (meta >= length && meta < length * 2) || meta >= length * 3 ? Boolean.valueOf(true) : Boolean.valueOf(false));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumFacing facing = state.getValue(FACING);
		boolean flg = state.getValue(RUNNING).booleanValue();
		int index = facing.getHorizontalIndex();
		int length = EnumFacing.values().length - 2;

		switch(state.getValue(TYPE)) {
			case 0:
			default:
				if(flg)
					return index + length;
				else
					return index;
			case 1:
				if(flg)
					return index + (length * 3);
				else
					return index + (length * 2);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, TYPE, RUNNING });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		RegistryUtils.registerSubBlocks(this, 2, tab, list);
	}
}