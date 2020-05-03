package ryoryo.polishedstone.block;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.block.BlockBaseSlab;
import ryoryo.polishedlib.util.RegistryUtils;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public abstract class BlockModStoneSlab extends BlockBaseSlab {
	public static final PropertyEnum<Type> TYPE = PropertyEnum.<Type> create("type", Type.class);

	public BlockModStoneSlab() {
		super(Material.ROCK, "stone_slab", PSV2Core.TAB_MOD, 2.0F, 10.0F, SoundType.STONE);
		IBlockState iblockstate = this.blockState.getBaseState();

		if(!this.isDouble())
			iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);

		this.setDefaultState(iblockstate.withProperty(TYPE, Type.POLISHED_STONE_CROSSED));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(Register.BLOCK_STONE_SLAB);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Register.BLOCK_STONE_SLAB, 1, ((Type) state.getValue(TYPE)).getMeta());
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName() + "_" + Type.byMeta(meta).getName();
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return TYPE;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return Type.byMeta(stack.getMetadata() & 7);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = this.getDefaultState().withProperty(TYPE, Type.byMeta(meta & 7));

		if(!this.isDouble())
			state = state.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta = meta | state.getValue(TYPE).getMeta();

		if(!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
			meta |= 8;

		return meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] { TYPE }) : new BlockStateContainer(this, new IProperty[] { TYPE, HALF });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(!this.isDouble())
			RegistryUtils.registerSubBlocks(this, Type.getLength(), tab, list);
	}

	public static class BlockModStoneSlabDouble extends BlockModStoneSlab {
		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public static class BlockModStoneSlabHalf extends BlockModStoneSlab {
		@Override
		public boolean isDouble() {
			return false;
		}
	}

	public static enum Type implements IStringSerializable
	{
		/*
		 ***************
		 * meta : Max 7
		 ***************
		 */
		POLISHED_STONE_CROSSED(0, "polished_stone_crossed"),
		POLISHED_STONE_BRICK(1, "polished_stone_brick"),
		POLISHED_STONE_BRICK_LARGE(2, "polished_stone_brick_large"),
		BLACK_QUARTZ(3, "black_quartz"),
		SMOOTH_GRANITE(4, "smooth_granite"),
		SMOOTH_DIORITE(5, "smooth_diorite"),
		SMOOTH_ANDESITE(6, "smooth_andesite"),
		SMOOTH_BASALT(7, "smooth_basalt"),;

		private static final Type[] META_LOOKUP = new Type[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private Type(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return this.meta;
		}

		public static int getLength() {
			return Type.values().length;
		}

		public static Type byMeta(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static {
			for(Type variant : values()) {
				META_LOOKUP[variant.getMeta()] = variant;
				NAMES[variant.getMeta()] = variant.getName();
			}
		}
	}
}