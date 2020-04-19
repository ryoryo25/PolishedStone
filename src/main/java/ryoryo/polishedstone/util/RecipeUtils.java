package ryoryo.polishedstone.util;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ryoryo.polishedlib.util.enums.EnumColor;
import ryoryo.polishedlib.util.enums.EnumPlanks;
import ryoryo.polishedstone.PSV2Core;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.block.BlockLamp;

public class RecipeUtils
{
	public static void addRecipe(String name, ItemStack output, Object... params)
	{
		PSV2Core.REGISTER.addRecipe(name, output, params);
	}

	public static void addShapelessRecipe(String name, @Nonnull ItemStack output, Object... params)
	{
		PSV2Core.REGISTER.addShapelessRecipe(name, output, params);
	}

	public static void addRecipeWall(String name, Block output, ItemStack material)
	{
		PSV2Core.REGISTER.addRecipeWall(name, output, material);
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
		addRecipe("vending_machine_" + name, new ItemStack(output, 1), "Gd", "ii", "ii", 'G', "blockGlass", 'd', color.getDyeOreName(), 'i', "ingotIron");
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
		addRecipe("lattice_" + planks.getName(), new ItemStack(output, 6), "PP", "ss", "PP", 'P', EnumPlanks.getPlanksStack(1, planks), 's', Items.STICK);
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
		addRecipe("fence_slab_" + name, new ItemStack(output, 6), " M ", "MMM", 'M', material);
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
		addRecipe("glass_" + planks.getName() + "_door", new ItemStack(output, 3), "PP", "GG", "PP", 'P', EnumPlanks.getPlanksStack(1, planks), 'G', "paneGlass");
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
		addRecipe("lamp_" + output.getName(), new ItemStack(Register.BLOCK_LAMP, 1, output.getMeta()), "GTG", " m ", 'G', "blockGlass", 'T', Blocks.TORCH, 'm', material);
	}
}