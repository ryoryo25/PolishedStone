package ryoryo.polishedstone.block;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.interfaces.IModId;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.tileenttiy.TileEntitySlimeRemovalTorch;
import ryoryo.polishedstone.util.References;

public class BlockSlimeRemovalTorch extends BlockTorch implements ITileEntityProvider, IModId
{
	//置かれたチャンクに沸かないように
	public BlockSlimeRemovalTorch()
	{
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("slime_removal_torch");
		this.setHardness(0.0F);
		this.setLightLevel(1.0F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public String getModId()
	{
		return References.MOD_ID;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySlimeRemovalTorch();
	}
}