package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;
import ryoryo.polishedlib.util.enums.ToolType;

public class BlockPolishedStone extends BlockModBase {
	public static final PropertyEnum<EnumType> TYPE = PropertyEnum.<EnumType> create("type", EnumType.class);

	public BlockPolishedStone() {
		super(Material.GROUND, "polished_stone", SoundType.STONE);
		this.setHardness(0.5F);
		this.setResistance(10000.0F);
		this.setHarvestLevel(ToolType.PICKAXE.getToolClass(), ToolMaterial.WOOD.getHarvestLevel());
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.NORMAL));
	}

	@Override
	public int getLightValue(IBlockState state) {
		EnumType type = state.getValue(TYPE);
		float base = 15.0F;

		switch(type) {
			default:
				return (int) (base * 1.0F);
			case INVERTED:
				return (int) (base * 0.0F);
		}
	}

	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
		if(entity instanceof EntityPlayer)
			return true;
		else
			return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		IBlockState state = this.getDefaultState();
		if(meta == EnumType.PILLAR_Y.getMeta()) {
			switch(facing.getAxis()) {
				case Z:
					return state.withProperty(TYPE, EnumType.PILLAR_Z);
				case X:
					return state.withProperty(TYPE, EnumType.PILLAR_X);
				case Y:
					return state.withProperty(TYPE, EnumType.PILLAR_Y);
			}
		} else {
			switch(meta) {
				case 0:
				default:
					return state.withProperty(TYPE, EnumType.NORMAL);
				case 1:
					return state.withProperty(TYPE, EnumType.INVERTED);
				case 2:
					return state.withProperty(TYPE, EnumType.VERTICAL);
				case 3:
					return state.withProperty(TYPE, EnumType.CROSSED);
				case 4:
					return state.withProperty(TYPE, EnumType.BRICK);
				case 5:
					return state.withProperty(TYPE, EnumType.BRICK_LARGE);
				case 6:
					return state.withProperty(TYPE, EnumType.BRICK_CARVED);
			}
		}

		return state;
	}

	@Override
	public int damageDropped(IBlockState state) {
		EnumType type = state.getValue(TYPE);
		return type != EnumType.PILLAR_X && type != EnumType.PILLAR_Z ? type.getMeta() : EnumType.PILLAR_Y.getMeta();
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		EnumType type = state.getValue(TYPE);
		return type != EnumType.PILLAR_X && type != EnumType.PILLAR_Z ? super.getSilkTouchDrop(state) : new ItemStack(Item.getItemFromBlock(this), 1, EnumType.PILLAR_Y.getMeta());
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, EnumType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TYPE, });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
		RegistryUtils.registerSubBlocks(this, EnumType.getLength() - 2, tab, list);
	}

	public static enum EnumType implements IStringSerializable {
		NORMAL(0, "normal"),
		INVERTED(1, "inverted"),
		VERTICAL(2, "vertical"),
		CROSSED(3, "crossed"),
		BRICK(4, "brick"),
		BRICK_LARGE(5, "brick_large"),
		BRICK_CARVED(6, "brick_carved"),
		PILLAR_Y(7, "pillar_y", "pillar"),
		PILLAR_X(8, "pillar_x", "pillar"),
		PILLAR_Z(9, "pillar_z", "pillar"),;

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		private final int meta;
		private final String serializedName;
		private final String unlocalizedName;

		private EnumType(int meta, String name, String unlocalizedName) {
			this.meta = meta;
			this.serializedName = name;
			this.unlocalizedName = unlocalizedName;
		}

		private EnumType(int meta, String name) {
			this(meta, name, name);
		}

		@Override
		public String getName() {
			return this.serializedName;
		}

		public String toString() {
			return this.unlocalizedName;
		}

		public int getMeta() {
			return this.meta;
		}

		public static int getLength() {
			return EnumType.values().length;
		}

		public static EnumType byMeta(int meta) {
			if(meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static {
			for(EnumType enumtype : values()) {
				META_LOOKUP[enumtype.getMeta()] = enumtype;
			}
		}
	}
}