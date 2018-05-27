package com.spicymemes.runes.coremod;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.math.BlockPos;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Spencer on 5/22/18.
 */

//TODO add different types of patches, add ability to create new methods, replace methods, duplicate methods, rename methods
public class MethodPatcher implements IClassTransformer{
    private static ArrayList<Patch> patches = new ArrayList<Patch>();//IBlockState
    static {
        addPatch("net.minecraft.block.Block.quantityDropped", "com.spicymemes.runes.coremod.WorldPatches.isOpaqueCube", "com.spicymemes.runes.coremod.WorldPatches.quantityDropped", DescriptorEncoder.encodeMethodHeader(int.class, IBlockState.class, int.class, Random.class));
        addPatch("net.minecraft.world.World.canSeeSky", "com.spicymemes.runes.coremod.WorldPatches.seeBottle", "com.spicymemes.runes.coremod.WorldPatches.seeSkyCondition", DescriptorEncoder.encodeMethodHeader(boolean.class, BlockPos.class));
        //addPatch("net.minecraft.block.BlockFarmland.hasWater", "com.spicymemes.runes.coremod.WorldPatches.hasWaterCondition", "com.spicymemes.runes.coremod.WorldPatches.hasWater", DescriptorEncoder.encodeMethodHeader(boolean.class, "net.minecraft.world.World", BlockPos.class));
    }
    private static InsnList genPatch(String conditional, String ret, String desc, boolean virtual, String operatingClass){
        InsnList patch = new InsnList();
        LabelNode els = new LabelNode(new Label());

        loadMethodHeaderOnStack(DescriptorEncoder.getMethodArgs(desc), patch, virtual);
        String newDesc = desc;
        if(virtual){
            newDesc = "(L" + operatingClass.replace('.', '/') + ";" + newDesc.substring(1);
        }
        System.out.println("newDesc: " + newDesc);
        patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC, conditional.replace('.', '/').substring(0, conditional.lastIndexOf('.')), conditional.substring(conditional.lastIndexOf('.') + 1), newDesc.substring(0, newDesc.indexOf(')') + 1) + "Z", false));


        patch.add(new JumpInsnNode(Opcodes.IFEQ, els));

        loadMethodHeaderOnStack(DescriptorEncoder.getMethodArgs(desc), patch, virtual);
        patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ret.replace('.', '/').substring(0, ret.lastIndexOf('.')), ret.substring(ret.lastIndexOf('.') + 1), newDesc, false));
        addReturn(desc, patch);
        patch.add(new FrameNode(Opcodes.F_SAME, 0, null, 0, null));
        patch.add(els);
        return patch;
    }

    private static void addReturn(String desc, InsnList patch){
        char rt = DescriptorEncoder.getMethodRet(desc);
        for(int i = 0; i < 1; i++) {
            if (rt == 'I' || rt == 'B' || rt == 'Z' || rt == 'S' || rt == 'C') {
                patch.add(new InsnNode(Opcodes.IRETURN));
            } else if (rt == 'F') {
                patch.add(new InsnNode(Opcodes.FRETURN));
            } else if (rt == 'V') {
                patch.add(new InsnNode(Opcodes.RETURN));
            } else if (rt == 'J') {
                patch.add(new InsnNode(Opcodes.LRETURN));
            } else if (rt == 'D') {
                patch.add(new InsnNode(Opcodes.DRETURN));
            } else {
                patch.add(new InsnNode(Opcodes.ARETURN));
            }
        }
    }

    private static void loadMethodHeaderOnStack(char[] m, InsnList l, boolean virtual){
        if(virtual){
            l.add(new VarInsnNode(Opcodes.ALOAD, 0));
        }
        int off = virtual ? 1 : 0;
        for(int i = 0; i < m.length; i++){
            int opcode;
            char c = m[i];
            if(c == 'I'){
                opcode = Opcodes.ILOAD;
            }
            else if(c == 'Z'){
                opcode = Opcodes.ILOAD;
            }
            else if(c == 'C'){
                opcode = Opcodes.ILOAD;
            }
            else if(c == 'S'){
                opcode = Opcodes.ILOAD;
            }
            else if(c == 'F'){
                opcode = Opcodes.FLOAD;
            }
            else if(c == 'D'){
                opcode = Opcodes.DLOAD;
            }
            else if(c == 'J'){
                opcode = Opcodes.LLOAD;
            }
            else if(c == 'B'){
                opcode = Opcodes.FLOAD;
            }
            else {
                opcode = Opcodes.ALOAD;
            }
            l.add(new VarInsnNode(opcode, i + off));
        }
    }

    public static void patchBefore(MethodNode toPatch, String condition, String ret, String patchClass){
        System.out.println(toPatch);
        System.out.println(toPatch.parameters);
        System.out.println(toPatch.desc);

        System.out.println(toPatch.access & Opcodes.ACC_STATIC);
        InsnList patch = genPatch(condition, ret, toPatch.desc, (toPatch.access & Opcodes.ACC_STATIC) == 0, patchClass);

        loadMethodHeaderOnStack(DescriptorEncoder.getMethodArgs(toPatch.desc), patch, (toPatch.access & Opcodes.ACC_STATIC) == 0);
        int invokeCode = ((toPatch.access & Opcodes.ACC_STATIC) == 0) ? Opcodes.INVOKEVIRTUAL : Opcodes.INVOKESTATIC;
        patch.add(new MethodInsnNode(invokeCode, patchClass.replace('.', '/'), toPatch.name + "_dup", toPatch.desc, false));
        addReturn(toPatch.desc, patch);
        toPatch.instructions = patch;
        //toPatch.instructions.insert(genPatch(condition, ret, toPatch.desc, (toPatch.access & Opcodes.ACC_STATIC) == 0, patchClass));
    }

    private static void addPatch(String toPatch, String cond, String ret, String desc){
        patches.add(new Patch(toPatch, cond, ret, desc));
        System.out.println("Adding patch for class " + toPatch);
    }

    private static MethodNode addMethod(ClassNode cn, int accessFlags, String name, String desc, String[] exceptions){
        MethodNode mn = new MethodNode(accessFlags, name, desc, null, exceptions);
        cn.methods.add(mn);
        return mn;
    }

    private static MethodNode duplicateMethod(ClassNode cn, MethodNode mn, String newName){
        List<String> exceptions = mn.exceptions;
        int size = (exceptions == null) ? 0 : exceptions.size();
        String[] ex = new String[size];
        for(int i = 0; i < size; i++){
            ex[i] = exceptions.get(i);
        }
        MethodNode newNode = addMethod(cn, mn.access, newName, mn.desc, ex);
        newNode.localVariables = mn.localVariables;
        newNode.attrs = mn.attrs;
        newNode.parameters = mn.parameters;
        newNode.annotationDefault = mn.annotationDefault;
        newNode.maxLocals = mn.maxLocals;
        newNode.maxStack = mn.maxStack;
        InsnList code = new InsnList();
        HashMap<LabelNode, LabelNode> newLabels = new HashMap<>();
        for(int i = 0; i < mn.instructions.size(); i++){
            AbstractInsnNode node = mn.instructions.get(i);
            if(node instanceof LabelNode){
                LabelNode newLabel = new LabelNode();
                newLabels.put((LabelNode)node, newLabel);
            }
        }
        for(int i = 0; i < mn.instructions.size(); i++){
            AbstractInsnNode node = mn.instructions.get(i);

            if(node instanceof LabelNode){
                code.add(newLabels.get(node));
            }
            else if(node instanceof FrameNode){
                AbstractInsnNode clone = node.clone(newLabels);
                System.out.println("Cloned framenode " + node + " as " + clone);
                code.add(clone);
            }
            else{
                AbstractInsnNode clone = node.clone(newLabels);
                if(clone == null){
                    System.err.println(node + "'s clone is null");
                }
                code.add(clone);
            }
        }
        newNode.instructions = code;
        return newNode;
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        ClassReader cr = new ClassReader(basicClass);
        ClassNode classNode = new ClassNode();
        cr.accept(classNode, ClassReader.EXPAND_FRAMES);
        //System.out.println(name);
        boolean wasPatched = false;
        for(Patch p : patches){
            if(p.matchesClass(transformedName)){
                System.out.println("YAY");
                for (MethodNode method : classNode.methods) {
                    if(p.matchesFunction(method)){
                        duplicateMethod(classNode, method, method.name + "_dup");
                        System.out.println("YAY!!!");
                        System.out.println(name);
                        patchBefore(method, p.condition, p.ret, transformedName);
                        wasPatched = true;
                        break;
                    }
                    //System.out.println(method.name);
                }
            }
        }
        ClassWriter cw = new ClassWriter(cr, 0);
        //ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(cw);
        if(wasPatched){
            File f = new File("patched.class");
            try {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(cw.toByteArray());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cw.toByteArray();
    }

    private static class Patch{
        String toPatch, condition, ret, des;
        public Patch(String tp, String cd, String rt, String desc){
            toPatch = tp;
            condition = cd;
            ret = rt;
            des = desc;
        }

        public boolean matchesClass(String name){
            return name.equals(toPatch.substring(0, toPatch.lastIndexOf('.')));
        }

        public boolean matchesFunction(MethodNode m){
            if(m.name.equals(toPatch.substring(toPatch.lastIndexOf('.') + 1))){
                if(des == null || des.equals(m.desc)){
                    return true;
                }
            }
            return false;
        }

        public void patch(){

        }
    }

}
