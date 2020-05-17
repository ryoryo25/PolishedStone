package ryoryo.polishedstone.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;

public class PSClassTransformer implements IClassTransformer, Opcodes {

	private static final String ASM_HOOKS = PSLoadingPlugin.toSlash(PSHooks.class.getName());

	private static final String TARGET_CLASS = "net.minecraft.block.Block";
//	private static final String TARGET_CLASS = "ryoryo.polishedstone.block.BlockTest";

	public PSClassTransformer() {
		PSLoadingPlugin.LOGGER.info("Starting Class Transformation");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if(!TARGET_CLASS.equals(transformedName))
			return basicClass;

		ClassReader cr = new ClassReader(basicClass);
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter("D:/Desktop/trace.txt");
//		}
//		catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		TraceClassVisitor cv = new TraceClassVisitor(cw, pw);
//		cr.accept(cv, ClassReader.EXPAND_FRAMES);

		cr.accept(new CustomVisitor(name, cw), ClassReader.EXPAND_FRAMES);
		basicClass = cw.toByteArray();

		return basicClass;
	}

	class CustomVisitor extends ClassVisitor {
		private static final String TARGET_METHOD = "canSustainPlant";
		private static final String TARGET_METHOD_DEOBF = "canSustainPlant";
		private String owner;

		public CustomVisitor(String owner, ClassVisitor cv) {
			super(ASM4, cv);
			this.owner = owner;
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//			MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

			if(TARGET_METHOD.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(this.owner, name, desc))
					|| TARGET_METHOD_DEOBF.equals(FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(this.owner, name, desc))
					|| name.equals(TARGET_METHOD)
					|| name.equals(TARGET_METHOD_DEOBF)) {
				PSLoadingPlugin.LOGGER.info("Found: " + name + "; start transforming");
//				return new CustomMethodNode(access, name, desc, signature, exceptions, mv);
				return new CustomMethodVisitor(this.api, super.visitMethod(access, name, desc, signature, exceptions));
			}

//			return mv;
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
	}

	class CustomMethodVisitor extends MethodVisitor {
		private final MethodVisitor visitor;

		public CustomMethodVisitor(int api, MethodVisitor mv) {
			super(api, null);// 今回はメソッド内の処理まるまる置き換えなので，nullを渡す．
			this.visitor = mv;
		}

		@Override
		public void visitCode() {
			PSLoadingPlugin.LOGGER.info("Replacing...");
			visitor.visitCode();
			visitor.visitVarInsn(ALOAD, 0);
			visitor.visitVarInsn(ALOAD, 1);
			visitor.visitVarInsn(ALOAD, 2);
			visitor.visitVarInsn(ALOAD, 3);
			visitor.visitVarInsn(ALOAD, 4);
			visitor.visitVarInsn(ALOAD, 5);
			visitor.visitMethodInsn(INVOKESTATIC,
									ASM_HOOKS,
									"canSustainPlant",
									Type.getMethodDescriptor(Type.BOOLEAN_TYPE,
																Type.getObjectType(PSLoadingPlugin.toSlash("net.minecraft.block.Block")),
																Type.getObjectType(PSLoadingPlugin.toSlash("net.minecraft.block.state.IBlockState")),
																Type.getObjectType(PSLoadingPlugin.toSlash("net.minecraft.world.IBlockAccess")),
																Type.getObjectType(PSLoadingPlugin.toSlash("net.minecraft.util.math.BlockPos")),
																Type.getObjectType(PSLoadingPlugin.toSlash("net.minecraft.util.EnumFacing")),
																Type.getObjectType(PSLoadingPlugin.toSlash("net.minecraftforge.common.IPlantable"))),
									false);
			visitor.visitInsn(IRETURN);
			visitor.visitMaxs(6, 6);
			visitor.visitEnd();
		}
	}

	/**
	 *
	 * @author Toyota
	 *
	 */
//	class CustomMethodNode extends MethodNode {
//		public CustomMethodNode(int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv) {
//			super(ASM4, access, name, desc, signature, exceptions);
//			this.mv = mv;
//		}
//
//		@Override
//		public void visitEnd() {
//			Iterator<AbstractInsnNode> iterator = instructions.iterator();
//
//			while(iterator.hasNext()) {
//				AbstractInsnNode node = iterator.next();// ALOAD 5
//				if(node.getOpcode() != ALOAD || ((VarInsnNode) node).var != 5)
//					continue;
//
//				AbstractInsnNode next1 = node.getNext();// ALOAD 2
//				if(next1 == null || next1.getOpcode() != ALOAD || ((VarInsnNode) next1).var != 2)
//					continue;
//
//				AbstractInsnNode next2 = next1.getNext();// ALOAD 3
//				if(next2 == null || next2.getOpcode() != ALOAD || ((VarInsnNode) next2).var != 3)
//					continue;
//
//				AbstractInsnNode next3 = next2.getNext();// ALOAD 4
//				if(next3 == null || next3.getOpcode() != ALOAD || ((VarInsnNode) next3).var != 4)
//					continue;
//
//				AbstractInsnNode next4 = next3.getNext();// INVOKEVIRTUAL
//				if(next4 == null || next4.getOpcode() != INVOKEVIRTUAL)
//					continue;
//				MethodInsnNode next4_1 = (MethodInsnNode) next4;
//				if(!next4_1.owner.equals("net/minecraft/util/math/BlockPos") || !next4_1.name.equals("offset") || !next4_1.desc.equals("(Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;"))
//					continue;
//
//				AbstractInsnNode next5 = next4.getNext();// INVOKEINTERFACE
//				if(next5 == null || next5.getOpcode() != INVOKEINTERFACE)
//					continue;
//				MethodInsnNode next5_1 = (MethodInsnNode) next5;
//				if(!next5_1.owner.equals("net/minecraftforge/common/IPlantable") || !next5_1.name.equals("getPlantType") || !next5_1.desc.equals("(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraftforge/common/EnumPlantType;"))
//					continue;
//
//				AbstractInsnNode next6 = next5.getNext();// ASTORE 7
//				if(next6 == null || next6.getOpcode() != ASTORE || ((VarInsnNode) next6).var != 7)
//					continue;
//
//				AbstractInsnNode next7 = next6.getNext();// L2
//				if(next7 != null) {
//					PSLoadingPlugin.LOGGER.info("Found " + node.toString());
//					LabelNode next7_1 = (LabelNode) next7;
//					InsnList toInsert = new InsnList();
//
//					toInsert.add(new VarInsnNode(ALOAD, 6));
//					toInsert.add(new MethodInsnNode(INVOKESTATIC,
//													ASM_HOOKS,
//													"isCactus",
//													Type.getMethodDescriptor(Type.BOOLEAN_TYPE,
//																			Type.getObjectType("net/minecraft/block/state/IBlockState")),
//													false));
//					toInsert.add(new JumpInsnNode(IFEQ, next7_1));
//
//					toInsert.add(new VarInsnNode(ALOAD, 0));
//					toInsert.add(new VarInsnNode(ALOAD, 1));
//					toInsert.add(new MethodInsnNode(INVOKESTATIC,
//													ASM_HOOKS,
//													"canSustainPlant",
//													Type.getMethodDescriptor(Type.BOOLEAN_TYPE,
//																				Type.getObjectType("net/minecraft/block/Block"),
//																				Type.getObjectType("net/minecraft/block/state/IBlockState")),
//													false));
//					toInsert.add(new InsnNode(IRETURN));
//
//					instructions.insertBefore(next7_1, toInsert);
//					maxStack = Math.max(6, maxStack);
//					break;
//				}
//			}
//			accept(mv);
//		}
//	}
}