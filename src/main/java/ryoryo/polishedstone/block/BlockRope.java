package ryoryo.polishedstone.block;

import net.minecraft.block.SoundType;

public class BlockRope extends BlockIronChain {
	// 簡単に作れるけど上ったりするのが遅い
	public BlockRope() {
		this.setUnlocalizedName("rope");
		this.setSoundType(SoundType.CLOTH);
	}
}
