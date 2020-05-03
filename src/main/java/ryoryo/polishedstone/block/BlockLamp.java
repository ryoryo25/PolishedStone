package ryoryo.polishedstone.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.NumericalConstant;
import ryoryo.polishedlib.util.RegistryUtils;

public class BlockLamp extends BlockModBase {
	public static final PropertyEnum<MaterialType> TYPE = PropertyEnum.<MaterialType> create("type", MaterialType.class);

	public BlockLamp() {
		super(Material.GLASS, "lamp");
		this.setLightLevel(1.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, MaterialType.STONE));
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		double min = state.getValue(TYPE).getAABBMin();
		double max = 1 - min;

		return new AxisAlignedBB(min, 0, min, max, 1, max);
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
		return NULL_AABB;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		double d0 = (double) pos.getX() + 0.5D;
		double d1 = (double) pos.getY() + state.getValue(TYPE).getParticlePos();
		double d2 = (double) pos.getZ() + 0.5D;

		world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, ((MaterialType) state.getValue(TYPE)).getMeta());
	}

	// @Override
	// public int damageDropped(IBlockState state)
	// {
	// return state.getValue(TYPE).getMeta();
	// }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, MaterialType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return (Integer) state.getValue(TYPE).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		RegistryUtils.registerSubBlocks(this, MaterialType.getLength(), tab, list);
	}

	public static enum MaterialType implements IStringSerializable
	{
		STONE(0, "stone", NumericalConstant.D2_0, NumericalConstant.D9_0),
		BRICK(1, "brick", NumericalConstant.D3_0, NumericalConstant.D7_0),
		IRON(2, "iron", NumericalConstant.D3_0, NumericalConstant.D6_0),
		LAPIS(3, "lapis", NumericalConstant.D4_0, NumericalConstant.D10_0),;

		private static final MaterialType[] META_LOOKUP = new MaterialType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;
		private final double aabbMin;
		private final double particlePos;

		private MaterialType(int meta, String name, double aabbMin, double particlePos) {
			this.meta = meta;
			this.name = name;
			this.aabbMin = aabbMin;
			this.particlePos = particlePos;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return this.meta;
		}

		public double getAABBMin() {
			return this.aabbMin;
		}

		public double getParticlePos() {
			return this.particlePos;
		}

		public static int getLength() {
			return MaterialType.values().length;
		}

		public static MaterialType byMeta(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static {
			for(MaterialType materialtype : values()) {
				META_LOOKUP[materialtype.getMeta()] = materialtype;
				NAMES[materialtype.getMeta()] = materialtype.getName();
			}
		}
	}
}
