package ryoryo.polishedstone.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ryoryo.polishedlib.util.EnumColor;
import ryoryo.polishedlib.util.EnumPlanks;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.block.BlockLamp;

public class RecipeUtils
{
	/**
	 * 小さい階段レシピ登録
	 * 名前の前に"small_stairs_"と足される
	 * @param name
	 * @param output
	 * @param material
	 */
	public static void addRecipeSmallStairs(String name, Block output, Object material)
	{
		int quantity = 6;
		if(ModCompat.COMPAT_QUARK)
			quantity = 9;
		else
			Utils.addInfo("Quark isn't loaded.");

		Utils.addRecipe("small_stairs_" + name, new ItemStack(output, quantity), "M ", "MM", 'M', material);
	}

	/**
	 * 自動販売機レシピ登録
	 * 名前の前に"vending_machine_"と足される
	 * @param name
	 * @param output
	 * @param dyeOre
	 */
	public static void addRecipeVendingMachine(String name, Block output, EnumColor color)
	{
		Utils.addRecipe("vending_machine_" + name, new ItemStack(output, 1), "Gd", "ii", "ii", 'G', "blockGlass", 'd', color.getDyeOreName(), 'i', "ingotIron");
	}

	/**
	 * Latticeのレシピ登録
	 * Planks用
	 * 名前の前に"lattice_"と足される
	 * @param output
	 * @param material
	 */
	public static void addRecipeLatticePlanks(Block output, EnumPlanks planks)
	{
		Utils.addRecipe("lattice_" + planks.getName(), new ItemStack(output, 6), "PP", "ss", "PP", 'P', EnumPlanks.getPlanksStack(1, planks), 's', Items.STICK);
	}

	/**
	 * FenceSlabのレシピ登録
	 * 名前の前に"fence_slab_"と足される
	 * @param name
	 * @param output
	 * @param material
	 */
	public static void addRecipeFenceSlab(String name, Block output, ItemStack material)
	{
		Utils.addRecipe("fence_slab_" + name, new ItemStack(output, 6), " M ", "MMM", 'M', material);
	}

	/**
	 * FenceSlabのレシピ登録
	 * Planks用
	 * 名前の前に"fence_slab_"と足される
	 * @param output
	 * @param planks
	 */
	public static void addRecipeFenceSlab(Block output, EnumPlanks planks)
	{
		addRecipeFenceSlab(planks.getName(), output, EnumPlanks.getPlanksStack(1, planks));
	}

	/**
	 * GlassDoorのレシピ登録
	 * Planks用
	 * 名前に"glass_" + planks.getName() + "_door"と足される
	 * @param output
	 * @param planks
	 */
	public static void addRecipeGlassDoorPlanks(Block output, EnumPlanks planks)
	{
		Utils.addRecipe("glass_" + planks.getName() + "_door", new ItemStack(output, 3), "PP", "GG", "PP", 'P', EnumPlanks.getPlanksStack(1, planks), 'G', "paneGlass");
	}

	/**
	 * Lampのレシピ登録
	 * 名前の前に"lamp_"と足される
	 * @param name
	 * @param output
	 * @param material
	 */
	public static void addRecipeLamp(BlockLamp.MaterialType output, Object material)
	{
		Utils.addRecipe("lamp_" + output.getName(), new ItemStack(Register.BLOCK_LAMP, 1, output.getMeta()), "GTG", " m ", 'G', "blockGlass", 'T', Blocks.TORCH, 'm', material);
	}
}