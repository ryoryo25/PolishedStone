package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.util.RegistryUtils;

public class BlockVoidTeleporter extends BlockModBase
{
	public static final PropertyEnum<VoidType> TYPE = PropertyEnum.<VoidType> create("type", VoidType.class);

	public BlockVoidTeleporter()
	{
		super(Material.IRON, "void_teleporter", SoundType.METAL);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, VoidType.DAY));
	}

//	@Override
//	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//	{
//		MinecraftServer server = world.getMinecraftServer();
//
//		if(!world.isRemote && !player.isSneaking())
//		{
//			if(server != null)
//			{
//				PlayerList list = server.getPlayerList();
//				int currentDim = player.dimension;
//				int overworld = DimensionType.OVERWORLD.getId();
//
//				int id = overworld;
//				Teleporter teleporter;
//				BlockVoidTeleporter.VoidType type = state.getValue(TYPE);
//				switch(type)
//				{
//				case DAY:
//				default:
//					id = currentDim != DimensionRegistry.DAY_VOID.getId() ? DimensionRegistry.DAY_VOID.getId() : overworld;
//					teleporter = new TeleporterVoid(server.getWorld(id), DimensionRegistry.DAY_VOID);
//					break;
//				case NIGHT:
//					id = currentDim != DimensionRegistry.NIGHT_VOID.getId() ? DimensionRegistry.NIGHT_VOID.getId() : overworld;
//					teleporter = new TeleporterVoid(server.getWorld(id), DimensionRegistry.NIGHT_VOID);
//					break;
//				case PERFECT:
//					id = currentDim != DimensionRegistry.PERFECT_VOID.getId() ? DimensionRegistry.PERFECT_VOID.getId() : overworld;
//					teleporter = new TeleporterVoid(server.getWorld(id), DimensionRegistry.PERFECT_VOID);
//					break;
//				}
//
//				if(player instanceof EntityPlayerMP)
//				{
//					list.transferPlayerToDimension((EntityPlayerMP) player, id, teleporter);
//				}
//				else
//				{
//					int origin = player.dimension;
//					player.dimension = id;
//					world.removeEntityDangerously(player);
//
//					player.isDead = false;
//
//					list.transferEntityToWorld(player, origin, server.getWorld(origin), server.getWorld(id), teleporter);
//				}
//
//				return false;
//			}
//		}
//
//		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
//	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getValue(TYPE).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(TYPE, VoidType.byMeta(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(TYPE).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ TYPE });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		RegistryUtils.registerSubBlocks(this, VoidType.getLength(), tab, list);
	}

	public static enum VoidType implements IStringSerializable
	{
		DAY(0, "day"),
		NIGHT(1, "night"),
		PERFECT(2, "perfect"),;

		private static final VoidType[] META_LOOKUP = new VoidType[values().length];
		public static final String[] NAMES = new String[values().length];
		private final int meta;
		private final String name;

		private VoidType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}

		public int getMeta()
		{
			return this.meta;
		}

		public static int getLength()
		{
			return VoidType.values().length;
		}

		public static VoidType byMeta(int meta)
		{
			if(meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for(VoidType blocktype : values())
			{
				META_LOOKUP[blocktype.getMeta()] = blocktype;
				NAMES[blocktype.getMeta()] = blocktype.getName();
			}
		}
	}
}