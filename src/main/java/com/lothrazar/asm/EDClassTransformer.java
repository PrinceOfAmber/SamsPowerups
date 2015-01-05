package com.lothrazar.asm;

import static org.objectweb.asm.Opcodes.FDIV;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.tree.AbstractInsnNode.METHOD_INSN;
import java.util.Iterator;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class EDClassTransformer implements IClassTransformer
{

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) 
	{
		// from tutorial here : http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571568-tutorial-1-6-2-changing-vanilla-without-editing
		
		//Bytecode manipulations goes here
		//i think agw is explosion, looking at joined.srg
		//also from https://raw.githubusercontent.com/MinecraftForge/FML/master/conf/joined.srg
		//also http://www.minecraftforge.net/wiki/Using_Access_Transformers
		
		String Explosion_obf = "agw";
		String Explosion = "net.minecraft.world.Explosion";
		
		String doExplosionB_srg = "func_77279_a";
		String doExplosionB_obf = "a";
		
		if (arg0.equals(Explosion_obf)) {
			System.out.println("********* INSIDE OBFUSCATED EXPLOSION TRANSFORMER ABOUT TO PATCH: " + arg0);
			return patchClassASM(arg0, arg2, true);
			}

			if (arg0.equals(Explosion)) {
			System.out.println("********* INSIDE EXPLOSION TRANSFORMER ABOUT TO PATCH: " + arg0);
			return patchClassASM(arg0, arg2, false);
			}
		
		
		//for now this does nothing to alter it
		return arg2;
	}
	
	
	public byte[] patchClassASM(String name, byte[] bytes, boolean obfuscated)
	{

		String doExplosionB = "doExplosionB";
		String doExplosionB_srg = "func_77279_a";
		String doExplosionB_obf = "a";
		
		
		
		String targetMethodName = "";

		// Our target method
		if (obfuscated == true)
			targetMethodName = doExplosionB_obf;
		else
			targetMethodName = doExplosionB;

		// set up ASM class manipulation stuff. Consult the ASM docs for details
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		// Now we loop over all of the methods declared inside the Explosion
		// class until we get to the targetMethodName "doExplosionB"

		Iterator<MethodNode> methods = classNode.methods.iterator();
		while (methods.hasNext())
		{
			MethodNode m = methods.next();
			int fdiv_index = -1;

			// Check if this is doExplosionB and it's method signature is (Z)V
			// which means that it accepts a boolean (Z) and returns a void (V)
			if ((m.name.equals(targetMethodName) && m.desc.equals("(Z)V"))) // from the MCP mapping viewer
			{
				System.out.println("********* Inside target method!");

				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				@SuppressWarnings("unchecked")
				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				int index = -1;

				// Loop over the instruction set and find the instruction FDIV
				// which does the division of 1/explosionSize
				while (iter.hasNext())
				{
					index++;
					currentNode = iter.next();

					// Found it! save the index location of instruction FDIV and
					// the node for this instruction
					if (currentNode.getOpcode() == FDIV)
					{
						targetNode = currentNode;
						fdiv_index = index;
					}
				}

				// now we want the save nods that load the variable
				// explosionSize and the division instruction:

				/*
				 * mv.visitInsn(FCONST_1); mv.visitVarInsn(ALOAD, 0);
				 * mv.visitFieldInsn(GETFIELD, "net/minecraft/src/Explosion",
				 * "explosionSize", "F"); mv.visitInsn(FDIV);
				 * mv.visitInsn(ICONST_0); mv.visitMethodInsn(INVOKEVIRTUAL,
				 * "net/minecraft/src/Block", "dropBlockAsItemWithChance",
				 * "(Lnet/minecraft/src/World;IIIIFI)V");
				 */

				AbstractInsnNode remNode1 = m.instructions.get(fdiv_index - 2); // mv.visitVarInsn(ALOAD,
																				// 0);
				AbstractInsnNode remNode2 = m.instructions.get(fdiv_index - 1); // mv.visitFieldInsn(GETFIELD,
																				// "net/minecraft/src/Explosion",
																				// "explosionSize",
																				// "F");
				AbstractInsnNode remNode3 = m.instructions.get(fdiv_index); // mv.visitInsn(FDIV);

				// just remove these nodes from the instruction set, this will
				// prevent the instruction FCONST_1 to be divided.

				m.instructions.remove(remNode1);
				m.instructions.remove(remNode2);
				m.instructions.remove(remNode3);

				// in this section, i'll just illustrate how to inject a call to
				// a static method if your instruction is a little more advanced
				// than just removing a couple of instruction:

				/*
				//To add new instructions, such as calling a static method can be done like so:

				// make new instruction list
				InsnList toInject = new InsnList();

				//add your own instruction lists: *USE THE ASM JAVADOC AS REFERENCE*
				toInject.add(new VarInsnNode(ALOAD, 0));
				toInject.add(new MethodInsnNode(INVOKESTATIC, "mod/culegooner/MyStaticClass", "myStaticMethod", "()V"));

				// add the added code to the nstruction list
				// You can also choose if you want to add the code before or after the target node, check the ASM Javadoc (insertBefore)
				m.instructions.insert(targetNode, toInject);
				*/

				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				System.out.println("Patching Complete!");
				break;
			}
		}

		// ASM specific for cleaning up and returning the final bytes for JVM
		// processing.
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}
}
