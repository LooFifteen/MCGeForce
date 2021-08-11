package dev.decobr.mcgeforce.tweaker.transformer.impl;

import dev.decobr.mcgeforce.tweaker.transformer.ITransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class GuiIngameTransformer implements ITransformer {
    @Override
    public String[] getClassName() {
        return new String[]{
                "net.minecraft.client.gui.GuiIngame"
        };
    }

    @Override
    public void transform(ClassNode classNode, String name) {
        for (MethodNode methodNode : classNode.methods) {
            String methodName = mapMethodName(classNode, methodNode);

            if (methodName.equals("displayTitle") || methodName.equals("func_175178_a")) {
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), new MethodInsnNode(Opcodes.INVOKESTATIC, "dev/decobr/mcgeforce/tweaker/transformer/impl/GuiIngameTransformerImpl", "checkTitle", "(Ljava/lang/String;)V", false));
                methodNode.instructions.insertBefore(methodNode.instructions.getFirst(), new VarInsnNode(Opcodes.ALOAD, 1));
                break;
            }
        }
    }
}
