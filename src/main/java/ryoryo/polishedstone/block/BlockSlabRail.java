package ryoryo.polishedstone.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.util.References;

public class BlockSlabRail extends BlockRail implements IModId
{
	public BlockSlabRail()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("slab_rail");
		this.setResistance(0.7F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH));
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public Material getMaterial(IBlockState state)
    {
        return Material.IRON;
    }

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		Block blockd = world.getBlockState(pos.down()).getBlock();
		return blockd == Register.BLOCK_PAVING_STONE ? true : false;
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos)
	{
		return super.getRailMaxSpeed(world, cart, pos) * 1.5F;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(SHAPE, BlockRailBase.EnumRailDirection.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((BlockRailBase.EnumRailDirection) state.getValue(SHAPE)).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ SHAPE });
	}
}
