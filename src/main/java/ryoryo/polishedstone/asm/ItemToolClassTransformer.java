package ryoryo.polishedstone.asm;

import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class ItemToolClassTransformer implements IClassTransformer, Opcodes {

	private static final String ASM_HOOKS = PSLoadingPlugin.toSlash(PSHooks.class.getName());

	private static final String TARGET_CLASS1 = "net.minecraft.item.ItemTool";
	private static final String TARGET_CLASS2 = "net.minecraft.item.ItemSword";

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(transformedName.equals(TARGET_CLASS1))
			return patchItemTool(basicClass);

		if(transformedName.equals(TARGET_CLASS2))
			return patchItemSword(basicClass);

		return basicClass;
	}

	/**
	 * public hitEntity(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/entity/EntityLivingBase;)Z
	 * L0
	 *  LINENUMBER 73 L0
	 *  ALOAD 1
	 *  ICONST_2 -> ICONST_1
	 *  ALOAD 3
	 *  INVOKEVIRTUAL net/minecraft/item/ItemStack.damageItem (ILnet/minecraft/entity/EntityLivingBase;)V
	 * 略...
	 *
	 * @param basicClass
	 * @return
	 */
	private byte[] patchItemTool(byte[] basicClass) {
		/*private static */final String TARGET_METHOD = "hitEntity";
		/*private static */final String TARGET_METHOD_DEOBF = "func_77644_a";

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		PSLoadingPlugin.LOGGER.info("==================================================");
		PSLoadingPlugin.LOGGER.info("-Found: " + classNode.name + "; start transforming");

		MethodNode hitEntity = null;
		for(MethodNode mn : classNode.methods) {
			if(TARGET_METHOD.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, mn.name, mn.desc))
					|| TARGET_METHOD_DEOBF.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, mn.name, mn.desc))
					|| mn.name.equals(TARGET_METHOD)
					|| mn.name.equals(TARGET_METHOD_DEOBF)) {
				hitEntity = mn;
				break;
			}
		}

		if(hitEntity != null) {
			PSLoadingPlugin.LOGGER.info("--Found: " + hitEntity.name + "; start transforming");
			InsnList instructions = hitEntity.instructions;
			Iterator<AbstractInsnNode> iterator = instructions.iterator();

			while(iterator.hasNext()) {
				AbstractInsnNode node = iterator.next();// ALOAD 1
				if(node.getOpcode() != ALOAD || ((VarInsnNode) node).var != 1)
					continue;

				AbstractInsnNode next1 = node.getNext();// ICONST_2
				if(next1 == null || next1.getOpcode() != ICONST_2)
					continue;

				AbstractInsnNode next2 = next1.getNext();// ALOAD 3
				if(next2 == null || next2.getOpcode() != ALOAD || ((VarInsnNode) next2).var != 3)
					continue;

				AbstractInsnNode next3 = next2.getNext();// INVOKEVIRTUAL
				if(next3 == null || next3.getOpcode() != INVOKEVIRTUAL)
					continue;
				MethodInsnNode next3_1 = (MethodInsnNode) next3;
				if(!next3_1.owner.equals("net/minecraft/item/ItemStack") || !next3_1.name.equals("damageItem") || !next3_1.desc.equals("(ILnet/minecraft/entity/EntityLivingBase;)V"))
					continue;

				PSLoadingPlugin.LOGGER.info("--Replacing...");
				instructions.remove(next1);
				instructions.insert(node, new InsnNode(ICONST_1));
			}

		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		PSLoadingPlugin.LOGGER.info("-Finish transforming");
		PSLoadingPlugin.LOGGER.info("==================================================");

		return writer.toByteArray();
	}

	/**
	 * public onBlockDestroyed(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/EntityLivingBase;)Z
	 * 略...
	 * L2
	 *  LINENUMBER 72 L2
	 *  ALOAD 1
	 *  ICONST_2 -> ICONST_1
	 *  ALOAD 5
	 *  INVOKEVIRTUAL net/minecraft/item/ItemStack.damageItem (ILnet/minecraft/entity/EntityLivingBase;)V
	 * 略...
	 *
	 * @param basicClass
	 * @return
	 */
	private byte[] patchItemSword(byte[] basicClass) {
		/*private static */final String TARGET_METHOD = "onBlockDestroyed";
		/*private static */final String TARGET_METHOD_DEOBF = "func_179218_a";

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(basicClass);
		classReader.accept(classNode, 0);

		PSLoadingPlugin.LOGGER.info("==================================================");
		PSLoadingPlugin.LOGGER.info("-Found: " + classNode.name + "; start transforming");

		MethodNode hitEntity = null;
		for(MethodNode mn : classNode.methods) {
			if(TARGET_METHOD.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, mn.name, mn.desc))
					|| TARGET_METHOD_DEOBF.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(classNode.name, mn.name, mn.desc))
					|| mn.name.equals(TARGET_METHOD)
					|| mn.name.equals(TARGET_METHOD_DEOBF)) {
				hitEntity = mn;
				break;
			}
		}

		if(hitEntity != null) {
			PSLoadingPlugin.LOGGER.info("--Found: " + hitEntity.name + "; start transforming");
			InsnList instructions = hitEntity.instructions;
			Iterator<AbstractInsnNode> iterator = instructions.iterator();

			while(iterator.hasNext()) {
				AbstractInsnNode node = iterator.next();// ALOAD 1
				if(node.getOpcode() != ALOAD || ((VarInsnNode) node).var != 1)
					continue;

				AbstractInsnNode next1 = node.getNext();// ICONST_2
				if(next1 == null || next1.getOpcode() != ICONST_2)
					continue;

				AbstractInsnNode next2 = next1.getNext();// ALOAD 3
				if(next2 == null || next2.getOpcode() != ALOAD || ((VarInsnNode) next2).var != 5)
					continue;

				AbstractInsnNode next3 = next2.getNext();// INVOKEVIRTUAL
				if(next3 == null || next3.getOpcode() != INVOKEVIRTUAL)
					continue;
				MethodInsnNode next3_1 = (MethodInsnNode) next3;
				if(!next3_1.owner.equals("net/minecraft/item/ItemStack") || !next3_1.name.equals("damageItem") || !next3_1.desc.equals("(ILnet/minecraft/entity/EntityLivingBase;)V"))
					continue;

				PSLoadingPlugin.LOGGER.info("--Replacing...");
				instructions.remove(next1);
				instructions.insert(node, new InsnNode(ICONST_1));
			}

		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);

		PSLoadingPlugin.LOGGER.info("-Finish transforming");
		PSLoadingPlugin.LOGGER.info("==================================================");

		return writer.toByteArray();
	}
}