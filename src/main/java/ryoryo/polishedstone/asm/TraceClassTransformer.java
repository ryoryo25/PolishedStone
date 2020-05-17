package ryoryo.polishedstone.asm;

import org.objectweb.asm.Opcodes;

import net.minecraft.launchwrapper.IClassTransformer;

public class TraceClassTransformer implements IClassTransformer, Opcodes {

	private static final String ASM_HOOKS = PSLoadingPlugin.toSlash(PSHooks.class.getName());

	private static final String TARGET_CLASS = "net.minecraft.item.ItemSword";

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		//		if(!transformedName.equals(TARGET_CLASS))
		//			return basicClass;
		//
		//		ClassReader cr = new ClassReader(basicClass);
		//		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		//
		//		PrintWriter pw = null;
		//		try {
		//			pw = new PrintWriter("D:/Desktop/trace.txt");
		//		}
		//		catch(FileNotFoundException e) {
		//			e.printStackTrace();
		//		}
		//		TraceClassVisitor cv = new TraceClassVisitor(cw, pw);
		//		cr.accept(cv, ClassReader.EXPAND_FRAMES);
		//
		//		basicClass = cw.toByteArray();

		return basicClass;
	}
}