package ryoryo.polishedstone.block;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.tileenttiy.TileEntitySlimeRemovalTorch;

public class BlockSlimeRemovalTorch extends BlockTorch implements ITileEntityProvider {
	// 置かれたチャンクに沸かないように
	public BlockSlimeRemovalTorch() {
		this.setCreativeTab(PSV2Core.TAB_MOD);
		this.setUnlocalizedName("slime_removal_torch");
		this.setHardness(0.0F);
		this.setLightLevel(1.0F);
		this.setSoundType(SoundType.STONE);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySlimeRemovalTorch();
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
}