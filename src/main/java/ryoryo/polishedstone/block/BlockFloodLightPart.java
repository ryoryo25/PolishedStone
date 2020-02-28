package ryoryo.polishedstone.block;
//package ryoryo.block;
//
//import java.util.Random;
//
//import net.minecraft.block.Block;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.properties.IProperty;
//import net.minecraft.block.properties.PropertyBool;
//import net.minecraft.block.state.BlockStateContainer;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.BlockRenderLayer;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import ryoryo.Register;
//import ryoryo.util.Utils;
//
//public class BlockFloodLightPart extends Block
//{
//	public static final PropertyBool VISIBLE = PropertyBool.create("visible");
//	private boolean isVisible = false;
//
//	public BlockFloodLightPart()
//	{
//		super(Material.CIRCUITS);
//		this.setUnlocalizedName("flood_light_part");
//		this.setHardness(0.1F);
//		this.setResistance(0.0F);
//		this.setSoundType(SoundType.STONE);
//		this.setLightLevel(1.0F);
//		this.setTickRandomly(true);
//	}
//
//	@Override
//	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
//	{
//		return NULL_AABB;
//	}
//
//	public boolean isOpaqueCube(IBlockState state)
//	{
//		return false;
//	}
//
//	public boolean isFullCube(IBlockState state)
//	{
//		return false;
//	}
//
//	@SideOnly(Side.CLIENT)
//	public BlockRenderLayer getBlockLayer()
//	{
//		return BlockRenderLayer.CUTOUT;
//	}
//
//	@Override
//	public int tickRate(World world)
//	{
//		return 10;
//	}
//
//	@Override
//	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
//	{
//		return true;
//	}
//
//	@Override
//	public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos)
//	{
//		return true;
//	}
//
//	@Override
//	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
//	{
//		if(!world.isRemote)
//		{
//			if(!this.canBlockStay(world, pos))
//			{
//				world.setBlockToAir(pos);
//			}
//		}
//
//	}
//
//	@Override
//	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
//	{
//		if(!this.canBlockStay(world, pos))
//		{
//			world.setBlockToAir(pos);
//		}
//		else if(pos.getY() - 2 > 0 && world.isAirBlock(pos.down()) && world.getBlockState(pos.down(2)).getBlock() != this)
//		{
//			world.setBlockState(pos.down(), this.getDefaultState(), 2);
//			world.setBlockToAir(pos);
//		}
//	}
//
//	private boolean canBlockStay(World world, BlockPos pos)
//	{
//		return this.checkFloodLight(world, pos);
//	}
//
//	private boolean checkFloodLight(World world, BlockPos pos)
//	{
//		boolean end = false;
//
//		for(int i = 0; i < 21; i++)
//		{
//			Block ID = world.getBlockState(BlockFloodLight.FACING, pos.up(i), 3);
//
//			if(ID == Register.blockFloodLight && pos.up(i).getY() < 255 && !end)
//			{
//				int l = world.getBlockMetadata((X + getOffsetX(meta)), (Y + i), (Z + getOffsetZ(meta)));
//				if(l == meta)
//					end = true;
//			}
//		}
//
//		return end;
//	}
//
//	@Override
//	public IBlockState getStateFromMeta(int meta)
//	{
//		return this.getDefaultState();
//	}
//
//	@Override
//	public int getMetaFromState(IBlockState state)
//	{
//		return 0;
//	}
//
//	@Override
//	protected BlockStateContainer createBlockState()
//	{
//		return new BlockStateContainer(this, new IProperty[]
//		{ VISIBLE, });
//	}
//
//	private void setVisible(IBlockState state, World world, BlockPos pos)
//	{
//		if(world.isRemote)
//		{
//			EntityPlayer player = Utils.getPlayer();
//			if(player != null)
//			{
//				ItemStack[] held = /*player.inventory.getCurrentItem();*/
//				{ Utils.getHeldItemStackOffhand(player), Utils.getHeldItemStackMainhand(player) };
//
//				for(ItemStack is : held)
//				{
//					if(is != null)
//					{
//						this.isVisible = false;
//						Item item = is.getItem();
//						if(item instanceof ItemBlock && Block.getBlockFromItem(item) == Register.blockFloodLight)
//						{
//							this.isVisible = true;
//							break;
//						}
//					}
//					else
//						this.isVisible = false;
//				}
//			}
//			if(this.isVisible != ((Boolean) state.getValue(VISIBLE)).booleanValue())
//			{
//				world.setBlockState(pos, state.withProperty(VISIBLE, Boolean.valueOf(this.isVisible)));
//			}
//		}
//	}
//
//	@SideOnly(Side.CLIENT)
//	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
//	{
//		setVisible(state, world, pos);
//	}
//}