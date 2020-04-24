package ryoryo.polishedstone.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ryoryo.polishedlib.util.Utils;
import ryoryo.polishedstone.Register;
import ryoryo.polishedstone.config.ModConfig;
import ryoryo.polishedstone.util.LibUnlocalizedString;

public class BlockGlowstoneGenerator extends BlockModBase
{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 7);
	private Random random = new Random();
	private int power;
	private int GENERATION_NUM;

	public BlockGlowstoneGenerator()
	{
		super(Material.ROCK, "glowstone_generator");
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, 0));
		this.GENERATION_NUM = ModConfig.glowstoneGen;
		this.power = 0;
	}

	@Override
	public int getLightValue(IBlockState state)
	{
		float base = 15F;
		switch(this.getMetaFromState(state))
		{
		case 0:
			return (int) (base * 0F);
		case 1:
			return (int) (base * 0.1F);
		case 2:
			return (int) (base * 0.3F);
		case 3:
			return (int) (base * 0.4F);
		case 4:
			return (int) (base * 0.5F);
		case 5:
			return (int) (base * 0.7F);
		case 6:
			return (int) (base * 0.9F);
		case 7:
			return (int) (base * 1F);
		default:
			return 15;
		}
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		long ti = world.getWorldTime();
		long ti2 = world.getWorldTime() % 24000L;
		if(ti2 >= 14500L && ti2 <= 23000L)
		{
			int meta = state.getBlock().getMetaFromState(world.getBlockState(pos));
			if(meta >= 7)
			{
				if(GenerationGlowstone(world, pos))
				{
					this.power = 0;
					world.setBlockState(pos, Register.BLOCK_GLOWSTONE_GENERATOR.getStateFromMeta(this.power));
				}
			}
		}
		else if(ti2 > 0L && ti2 <= 12000L)
		{
			if(!world.isRemote && world.getLight(pos.up()) >= 15 && this.random.nextInt(5) == 0)
			{
				int meta = state.getBlock().getMetaFromState(world.getBlockState(pos));
				this.power = meta;
				if(meta < 7)
				{
					this.power += 1;
				}
				if(meta == 7)
					return;
				world.playSound(Utils.getPlayer(), pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.5F, 1.2F);
				Utils.sendChat(LibUnlocalizedString.CHAT_GG_POWER_UP + String.valueOf(this.power));
				world.setBlockState(pos, Register.BLOCK_GLOWSTONE_GENERATOR.getStateFromMeta(this.power), 2);
			}
		}
	}

	private boolean GenerationGlowstone(World world, BlockPos pos)
	{
		boolean flg = false;
		int hieght = this.random.nextInt(this.GENERATION_NUM) + 1;
		for(int h = 1; h <= hieght; h++)
		{
			BlockPos posg = pos.down(h)/*new BlockPos(pos.getX(), pos.getY() - h, pos.getZ())*/;
			if(world.getBlockState(posg).getMaterial() != Material.AIR)
			{
				break;
			}
			world.playSound(Utils.getPlayer(), pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.2F);
			if(h == 1)
			{
				Utils.sendChat(LibUnlocalizedString.CHAT_GG_GENERATED);
			}
			world.setBlockState(posg, Blocks.GLOWSTONE.getDefaultState());
			flg = true;
		}
		return flg;
	}

	//ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	//IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(STAGE);
	}

	//初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[]
		{ STAGE });
	}
}