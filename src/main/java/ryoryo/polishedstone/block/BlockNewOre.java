package ryoryo.polishedstone.block;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;
import ryoryo.polishedlib.util.enums.ToolType;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;

public class BlockNewOre extends BlockOre {
	public static final PropertyEnum<MaterialType> TYPE = PropertyEnum.<MaterialType> create("type", MaterialType.class);

	public BlockNewOre() {
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("new_ore");
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel(ToolType.PICKAXE.getToolClass(), ToolMaterial.WOOD.getHarvestLevel());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, MaterialType.COOKIE));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		MaterialType type = state.getValue(TYPE);

		// return ((MaterialType) type).getDrop();
		switch(type) {
			case COOKIE:
			default:
				return Items.COOKIE;
			case QUARTZ_BLACK:
				return Register.ITEM_MATERIAL;
		}
	}

	@Override
	public int quantityDropped(Random random) {
		// TODO
		return 4 + random.nextInt(5);
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : new Random();
		MaterialType type = state.getValue(TYPE);

		return MathHelper.getInt(rand, type.getExpMin(), type.getExpMax());
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(TYPE).getMeta());
	}

	// @Override
	// public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world,
	// BlockPos pos, IBlockState state, int fortune)
	// {
	// Random rand = world instanceof World ? ((World) world).rand : RANDOM;
	//
	// int count = this.quantityDropped(state, fortune, rand);
	// for(int i = 0; i < count; i++)
	// {
	// drops.add(state.getValue(TYPE).getDrop());
	// }
	// }

	@Override
	public int damageDropped(IBlockState state) {
		MaterialType type = state.getValue(TYPE);

		if(type != null)
			return type.getDropMeta();

		return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, MaterialType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta();
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

	public static enum MaterialType implements IStringSerializable {
		// if (this == Blocks.COAL_ORE)
		// i = MathHelper.getInt(rand, 0, 2);
		// else if (this == Blocks.DIAMOND_ORE)
		// i = MathHelper.getInt(rand, 3, 7);
		// else if (this == Blocks.EMERALD_ORE)
		// i = MathHelper.getInt(rand, 3, 7);
		// else if (this == Blocks.LAPIS_ORE)
		// i = MathHelper.getInt(rand, 2, 5);
		// else if (this == Blocks.QUARTZ_ORE)
		// i = MathHelper.getInt(rand, 2, 5);
		COOKIE(0, "cookie", Items.COOKIE, 0, 0, 2),
		QUARTZ_BLACK(1, "quartz_black", Register.ITEM_MATERIAL, 18, 2, 5);

		private static final MaterialType[] META_LOOKUP = new MaterialType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;
		private final Item drop;
		private final int dropMeta;
		private final int expMin;
		private final int expMax;

		private MaterialType(int meta, String name, Item drop, int dropMeta, int expMin, int expMax) {
			this.meta = meta;
			this.name = name;
			this.drop = drop;
			this.dropMeta = dropMeta;
			this.expMin = expMin;
			this.expMax = expMax;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getMeta() {
			return this.meta;
		}

		public Item getDrop() {
			return this.drop;
		}

		public int getDropMeta() {
			return this.dropMeta;
		}

		public int getExpMin() {
			return this.expMin;
		}

		public int getExpMax() {
			return this.expMax;
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