package ryoryo.polishedstone.itemblock;

import ryoryo.polishedlib.itemblock.ItemBlockMeta;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.block.BlockPolishedStone.EnumType;

public class ItemBlockPolishedStone extends ItemBlockMeta {
	public static final String[] NAMES = new String[] {
			EnumType.NORMAL.getName(),
			EnumType.INVERTED.getName(),
			EnumType.VERTICAL.getName(),
			EnumType.CROSSED.getName(),
			EnumType.BRICK.getName(),
			EnumType.BRICK_LARGE.getName(),
			EnumType.BRICK_CARVED.getName(),
			EnumType.PILLAR_Y.toString(),
	};

	public ItemBlockPolishedStone() {
		super(Register.BLOCK_POLISHED_STONE, NAMES);
	}
}