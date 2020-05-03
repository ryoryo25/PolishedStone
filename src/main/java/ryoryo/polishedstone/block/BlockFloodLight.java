package ryoryo.polishedstone.block;

// package ryoryo.block;
//
// import java.util.Random;
//
// import javax.annotation.Nullable;
//
// import net.minecraft.block.Block;
// import net.minecraft.block.BlockHorizontal;
// import net.minecraft.block.SoundType;
// import net.minecraft.block.material.Material;
// import net.minecraft.block.properties.IProperty;
// import net.minecraft.block.properties.PropertyDirection;
// import net.minecraft.block.state.BlockStateContainer;
// import net.minecraft.block.state.IBlockState;
// import net.minecraft.entity.EntityLivingBase;
// import net.minecraft.entity.player.EntityPlayer;
// import net.minecraft.init.SoundEvents;
// import net.minecraft.item.ItemStack;
// import net.minecraft.util.BlockRenderLayer;
// import net.minecraft.util.EnumFacing;
// import net.minecraft.util.EnumHand;
// import net.minecraft.util.SoundCategory;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.world.World;
// import net.minecraftforge.fml.relauncher.Side;
// import net.minecraftforge.fml.relauncher.SideOnly;
// import ryoryo.PSV2Core;
// import ryoryo.Register;
// import ryoryo.util.ModConfig;
// import ryoryo.util.Utils;
//
// public class BlockFloodLight extends Block
// {
// public static final PropertyDirection FACING = BlockHorizontal.FACING;
//
// public BlockFloodLight()
// {
// super(Material.GRASS);
// this.setCreativeTab(PSV2Core.tabMod);
// this.setUnlocalizedName("flood_light");
// this.setHardness(0.5F);
// this.setResistance(2.0F);
// this.setSoundType(SoundType.STONE);
// this.setLightLevel(1.0F);
// this.setTickRandomly(true);
// this.setDefaultState(this.blockState.getBaseState().withProperty(FACING,
// EnumFacing.NORTH));
// }
//
// @Override
// public boolean isOpaqueCube(IBlockState state)
// {
// return false;
// }
//
// @Override
// public boolean isFullCube(IBlockState state)
// {
// return false;
// }
//
// @SideOnly(Side.CLIENT)
// public BlockRenderLayer getBlockLayer()
// {
// return BlockRenderLayer.CUTOUT;
// }
//
// @Override
// public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing
// facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase
// placer)
// {
// int h = this.setLight(world, pos, placer.getHorizontalFacing());
// if(h > 0 && h < 20)
// {
// world.setBlockState(Utils.getFront(facing, pos.down(h), 3),
// Register.blockFloodLightPart.getDefaultState(), 2);
// }
//
// return this.getDefaultState().withProperty(FACING,
// placer.getHorizontalFacing().getOpposite());
// }
//
// @Override
// public int tickRate(World world)
// {
// return 5;
// }
//
// @Override
// public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
// EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing
// side, float hitX, float hitY, float hitZ)
// {
// ItemStack itemstack = player.inventory.getCurrentItem();
//
// if(itemstack == null)
// {
// EnumFacing facing = state.getValue(FACING);
// this.eraseLight(world, pos, facing);
// world.playSound(Utils.getPlayer(), pos, SoundEvents.ENTITY_ITEM_PICKUP,
// SoundCategory.BLOCKS, 0.4F, 1.8F); //for debug
//
//// world.setBlockMetadataWithNotify(pos, next[meta], 3);
// int h = this.setLight(world, pos, Utils.getRightFacing(facing));
// if(h > 0 && h < 20)
// {
// world.setBlockState(Utils.getFront(Utils.getRightFacing(facing), pos.down(h),
// 3), Register.blockFloodLightPart.getDefaultState()/*, next[meta]*/, 2);
// }
// return true;
// }
// else
// return false;
// }
//
// @Override
// public void updateTick(World world, BlockPos pos, IBlockState state, Random
// rand)
// {
// if(!world.isRemote)
// {
// EnumFacing facing = state.getValue(FACING);
// int h = this.setLight(world, pos, facing);
// if(h > 0 && h < 20)
// {
// world.setBlockState(Utils.getFront(facing, pos.down(h), 3),
// Register.blockFloodLightPart.getDefaultState()/*, meta*/, 2);
// if(PSV2Core.isDebug)
// world.playSound(Utils.getPlayer(), pos, SoundEvents.UI_BUTTON_CLICK,
// SoundCategory.BLOCKS, 0.3F, 0.5F); //for debug
// }
//// world.scheduleBlockUpdate(pos, this, 5, 1);
// }
// }
//
// @Override
// public void neighborChanged(IBlockState state, World world, BlockPos pos,
// Block block)
// {
// super.neighborChanged(state, world, pos, this);
// if(!world.isRemote)
// {
// EnumFacing facing = state.getValue(FACING);
// int h = this.setLight(world, pos, facing);
// if(h > 0 && h < 20)
// {
// world.setBlockState(Utils.getFront(facing, pos.down(h), 3),
// Register.blockFloodLightPart.getDefaultState()/*, meta*/, 2);
// if(PSV2Core.isDebug)
// world.playSound(Utils.getPlayer(), pos, SoundEvents.UI_BUTTON_CLICK,
// SoundCategory.BLOCKS, 0.3F, 0.5F); //for debug
// }
//// world.notifyBlockUpdate(pos, state,
// Register.blockFloodLightPart.getDefaultState(), 2);
// }
// }
//
// @Override
// public void breakBlock(World world, BlockPos pos, IBlockState state)
// {
// this.eraseLight(world, pos, state.getValue(FACING));
// }
//
// private int setLight(World world, BlockPos pos, EnumFacing facing)
// {
// int height = -1;
// boolean end = false;
//// boolean success = false;
//
// for(int i = 0; i < 21; i++)
// {
// BlockPos posn = Utils.getFront(facing, pos.down(i), 3);
// if(world.isAirBlock(posn) && pos.down(i).getY() > 0 && !end)
// {
// height++;
// }
// else
// {
// end = true;
// }
// }
//
// if(end && (height > 0) && (height < 20))
// {
// Block ID = world.getBlockState(Utils.getFront(facing, pos.down(height - 1),
// 3)).getBlock();
// if(ID == Register.blockFloodLightPart || ModConfig.notUseLight)
// {
// height = -1;
// }
// }
//
// return height;
// }
//
// private void eraseLight(World world, BlockPos pos, EnumFacing facing)
// {
// for(int i = 0; i < 21; i++)
// {
// BlockPos posn = Utils.getFront(facing, pos.down(i), 3);
// Block ID = world.getBlockState(posn).getBlock();
// if(ID == Register.blockFloodLightPart)
// {
// world.setBlockToAir(posn);
// }
// }
// }
//
// @Override
// public IBlockState getStateFromMeta(int meta)
// {
// EnumFacing enumfacing = EnumFacing.getFront(meta);
//
// if(enumfacing.getAxis() == EnumFacing.Axis.Y)
// {
// enumfacing = EnumFacing.NORTH;
// }
//
// return this.getDefaultState().withProperty(FACING, enumfacing);
// }
//
// @Override
// public int getMetaFromState(IBlockState state)
// {
// return ((EnumFacing) state.getValue(FACING)).getIndex();
// }
//
// @Override
// protected BlockStateContainer createBlockState()
// {
// return new BlockStateContainer(this, new IProperty[]
// { FACING, });
// }
// }