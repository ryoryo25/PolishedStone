package ryoryo.polishedstone.util;

import static ryoryo.polishedlib.util.Utils.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import ryoryo.polishedstone.Register;

public enum EnumCropType
{
	ONION("onion", Register.ITEM_SEED_ONION, Register.ITEM_ONION, Register.BLOCK_CROP_ONION, Blocks.FARMLAND, Lib.CROP_ONION_AABB),
	JAPANESE_RADISH("japanese_radish", Register.ITEM_SEED_JAPANESE_RADISH, Register.ITEM_JAPANESE_RADISH, Register.BLOCK_CROP_JAPANESE_RADISH, Blocks.FARMLAND, Lib.CROP_ONION_AABB),
	CABBAGE("cabbage", Register.ITEM_SEED_CABBAGE, Register.ITEM_CABBAGE, Register.BLOCK_CROP_CABBAGE, Blocks.FARMLAND, Lib.CROP_CABBAGE_AABB),
	STICKY_RICE("sticky_rice", Register.ITEM_STICKY_RICE, Register.ITEM_STICKY_RICE, Register.BLOCK_CROP_STICKY_RICE, Blocks.WATER, Lib.CROP_STICKY_RICE_AABB),
	TOMATO("tomato", Register.ITEM_SEED_TOMATO, Register.ITEM_TOMATO, Register.BLOCK_CROP_TOMATO, Blocks.FARMLAND, Lib.CROP_ONION_AABB),
	EGGPLANT("eggplant", Register.ITEM_SEED_EGGPLANT, Register.ITEM_EGGPLANT, Register.BLOCK_CROP_EGGPLANT, Blocks.FARMLAND, Lib.CROP_ONION_AABB),;

	private final String name;
	private final Item seed;
	private final Item crop;
	private final Block cropBlock;
	private final Block soil;
	private final AxisAlignedBB[] aabbs;

	private EnumCropType(String name, Item seed, Item crop, Block cropBlock, Block soil, AxisAlignedBB[] aabbs) {
		this.name = name;
		this.seed = seed;
		this.crop = crop;
		this.cropBlock = cropBlock;
		this.soil = soil;
		this.aabbs = aabbs;
	}

	public String getName() {
		return this.name;
	}

	public Item getSeed() {
		return this.seed;
	}

	public Item getCrop() {
		return this.crop;
	}

	public Block getCropBlock() {
		return this.cropBlock;
	}

	public Block getSoil() {
		return this.soil;
	}

	public AxisAlignedBB[] getAABBs() {
		return this.aabbs;
	}

	public static class Lib {
		private static final AxisAlignedBB[] DEFAULT_CROP_AABB = new AxisAlignedBB[] { creatAABB(0, 0, 0, 16, 2, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 6, 16), creatAABB(0, 0, 0, 16, 8, 16), creatAABB(0, 0, 0, 16, 10, 16), creatAABB(0, 0, 0, 16, 12, 16), creatAABB(0, 0, 0, 16, 14, 16), creatAABB(0, 0, 0, 16, 16, 16) };
		private static final AxisAlignedBB[] CROP_ONION_AABB = new AxisAlignedBB[] { creatAABB(2.5, 0, 2.5, 13.5, 4, 13.5), creatAABB(2.5, 0, 2.5, 13.5, 4, 13.5), creatAABB(2.5, 0, 2.5, 13.5, 4, 13.5), creatAABB(2.5, 0, 2.5, 13.5, 4, 13.5), creatAABB(2.5, 0, 2.5, 13.5, 4, 13.5), creatAABB(2.5, 0, 2.5, 13.5, 16, 13.5), creatAABB(2.5, 0, 2.5, 13.5, 16, 13.5),
				creatAABB(2.5, 0, 2.5, 13.5, 16, 13.5) };
		private static final AxisAlignedBB[] CROP_CABBAGE_AABB = new AxisAlignedBB[] { creatAABB(0, 0, 0, 16, 1, 16), creatAABB(0, 0, 0, 16, 1, 16), creatAABB(0, 0, 0, 16, 1.5, 16), creatAABB(0, 0, 0, 16, 3, 16), creatAABB(0, 0, 0, 16, 4.5, 16), creatAABB(0, 0, 0, 16, 6, 16), creatAABB(0, 0, 0, 16, 7.5, 16), creatAABB(0, 0, 0, 16, 7.5, 16) };
		private static final AxisAlignedBB[] CROP_STICKY_RICE_AABB = new AxisAlignedBB[] { creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16), creatAABB(0, 0, 0, 16, 4, 16) };
	}
}