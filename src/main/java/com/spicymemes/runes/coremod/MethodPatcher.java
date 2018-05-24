package com.spicymemes.runes.coremod;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.tree.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Spencer on 5/22/18.
 */

//TODO add different types of patches, add ability to create new methods, replace methods, duplicate methods, rename methods
public class MethodPatcher implements IClassTransformer{
    private static ArrayList<Patch> patches = new ArrayList<Patch>();//IBlockState
    static {
        try {
            /*Method m = World.class.getMethod("canSeeSky", BlockPos.class);
            Method c = WorldPatches.class.getMethod("seeSkyCondition", BlockPos.class);
            addPatch(m, c, c);*///quantityDroppedsee
            Method c = WorldPatches.class.getMethod("isOpaqueCube", Object.class, IBlockState.class, int.class, Random.class);
            Method s = WorldPatches.class.getMethod("seeSkyCondition", Object.class, BlockPos.class);
            Method b = WorldPatches.class.getMethod("seeBottle", Object.class, BlockPos.class);
            Method r = WorldPatches.class.getMethod("quantityDropped", Object.class, IBlockState.class, int.class, Random.class);
            addPatch("net.minecraft.block.Block", "quantityDropped", c, r);
            addPatch("net.minecraft.world.World", "canSeeSky", b, s);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    private static InsnList genPatch(Method conditional, Method ret){
        InsnList patch = new InsnList();
        LabelNode els = new LabelNode(new Label());

        String desc = DescriptorEncoder.encodeMethodHeader(conditional);
        String retDesc = DescriptorEncoder.encodeMethodHeader(ret);

        loadMethodHeaderOnStack(conditional, patch);
        patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC, conditional.getDeclaringClass().getCanonicalName().replace('.', '/'), conditional.getName(), desc, false));


        Object[] ls = getStackArray(ret.getParameterTypes());
        Object[] ss = getStackArray(new Class[]{ret.getReturnType()});
        //patch.add(new FrameNode(Opcodes.F_NEW, ls.length, ls, ss.length, ss));

        patch.add(new JumpInsnNode(Opcodes.IFEQ, els));

        loadMethodHeaderOnStack(conditional, patch);
        patch.add(new MethodInsnNode(Opcodes.INVOKESTATIC, ret.getDeclaringClass().getCanonicalName().replace('.', '/'), ret.getName(), retDesc, false));
        Class rt = ret.getReturnType();
        for(int i = 0; i < 1; i++) {
            if (rt == int.class || rt == byte.class || rt == boolean.class || rt == short.class || rt == char.class) {
                patch.add(new InsnNode(Opcodes.IRETURN));
            } else if (rt == float.class) {
                patch.add(new InsnNode(Opcodes.FRETURN));
            } else if (rt == void.class) {
                patch.add(new InsnNode(Opcodes.RETURN));
            } else if (rt == long.class) {
                patch.add(new InsnNode(Opcodes.LRETURN));
            } else if (rt == double.class) {
                patch.add(new InsnNode(Opcodes.DRETURN));
            } else {
                patch.add(new InsnNode(Opcodes.ARETURN));
            }
        }
        patch.add(new FrameNode(Opcodes.F_SAME, ls.length, ls, ss.length, ss));
        patch.add(els);
        //System.out.println(Arrays.toString(patch.toArray()));
        return patch;
    }

    private static Object[] getStackArray(Class[] types){
        if(types.length == 1 && types[0] == void.class){
            return new Object[0];
        }
        Object[] o = new Object[types.length];
        for(int i = 0; i < o.length; i++){
            Class type = types[i];
            if(type == int.class || type == byte.class || type == boolean.class || type == short.class || type == char.class){
                o[i] = Opcodes.INTEGER;
            }
            else if(type == float.class){
                o[i] = Opcodes.FLOAT;
            }
            else if(type == long.class){
                o[i] = Opcodes.LONG;
            }
            else if(type == double.class){
                o[i] = Opcodes.DOUBLE;
            }
            else{
                o[i] = type.getCanonicalName().replace('.', '/');
            }
        }
        return o;
    }

    private static void loadMethodHeaderOnStack(Method m, InsnList l){
        Class[] c = m.getParameterTypes();
        for(int i = 0; i < m.getParameterCount(); i++){
            int opcode = -1;
            if(c[i].equals(int.class)){
                opcode = Opcodes.ILOAD;
            }
            else if(c[i].equals(boolean.class)){
                opcode = Opcodes.ILOAD;
            }
            else if(c[i].equals(char.class)){
                opcode = Opcodes.ILOAD;
            }
            else if(c[i].equals(short.class)){
                opcode = Opcodes.ILOAD;
            }
            else if(c[i].equals(float.class)){
                opcode = Opcodes.FLOAD;
            }
            else if(c[i].equals(double.class)){
                opcode = Opcodes.DLOAD;
            }
            else if(c[i].equals(long.class)){
                opcode = Opcodes.LLOAD;
            }
            else if(c[i].equals(byte.class)){
                opcode = Opcodes.FLOAD;
            }
            else {
                opcode = Opcodes.ALOAD;
            }
            l.add(new VarInsnNode(opcode, i));
        }
    }

    public static void patchBefore(MethodNode toPatch, Method condition, Method ret){
        toPatch.instructions.insert(genPatch(condition, ret));
    }

    private static void addPatch(String cn, String mn, Method cond, Method ret){
        patches.add(new Patch(cn, mn, cond, ret));
        System.out.println("Adding patch for class " + cn + "." + mn);
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
                    if(method.name.equals(p.m) && method.desc.equals(DescriptorEncoder.encodeMethodHeader(p.ret, 1))){
                        System.out.println("YAY!!!");
                        System.out.println(name);
                        System.out.println(method.desc);
                        System.out.println(DescriptorEncoder.encodeMethodHeader(p.ret, 1));
                        patchBefore(method, p.cond, p.ret);
                        wasPatched = true;
                    }
                    //System.out.println(method.name);
                }
            }
        }
        ClassWriter cw = new ClassWriter(cr, 0);
        //ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classNode.accept(cw);
        return cw.toByteArray();
    }

    private static class Patch{
        public final Method cond, ret;
        String c, m;
        public Patch(String cn, String mn, Method cd, Method rt){
            c = cn;
            m = mn;
            cond = cd;
            ret = rt;
        }

        public boolean matchesClass(String name){
            return c.equals(name);
        }
    }

}
