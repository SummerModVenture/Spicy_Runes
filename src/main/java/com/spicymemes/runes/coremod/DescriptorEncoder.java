package com.spicymemes.runes.coremod;

import java.util.HashMap;

public class DescriptorEncoder {
	private static final HashMap<Class<?>, String> primitives = new HashMap<Class<?>, String>();
	static {
		primitives.put(byte.class, "B");
		primitives.put(char.class, "C");
		primitives.put(double.class, "D");
		primitives.put(float.class, "F");
		primitives.put(int.class, "I");
		primitives.put(long.class, "J");
		primitives.put(short.class, "S");
		primitives.put(boolean.class, "Z");
		primitives.put(void.class, "V");
	}
	
	public static String encodeType(Class<?> c) {
		if(primitives.containsKey(c)) {
			return primitives.get(c);
		}
		if(!c.isArray()) {
			return "L" + c.getName().replaceAll("\\.", "/") + ";";
		}
		return c.getName().replaceAll("\\.", "/") + ";";
	}
	
	public static String encodeField(java.lang.reflect.Field f) {
		return (f.getDeclaringClass().getName() + "/" + f.getName()).replaceAll("\\.", "/");
	}
	
	public static String encodeFieldType(java.lang.reflect.Field f) {
		return encodeType(f.getType());
	}

	public static String encodeMethodHeader(java.lang.reflect.Method f) {
		Class<?> ret = f.getReturnType();
		return encodeMethodHeader(ret, f.getParameterTypes());
	}

	public static String encodeMethodHeader(java.lang.reflect.Method f, int start) {
		Class<?> ret = f.getReturnType();
		Class[] args = f.getParameterTypes();
		Class[] a = new Class[args.length - start];
		for(int i = 0; i < a.length; i++){
			a[i] = args[i + start];
		}
		return encodeMethodHeader(ret, a);
	}
	
	public static String encodeFullMethodHeader(java.lang.reflect.Method f) {
		return (f.getDeclaringClass().getName() + "/" + f.getName()).replaceAll("\\.", "/") + encodeMethodHeader(f);
	}
	
	public static String encodeMethodHeader(Class<?> ret, Class<?> ... args) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(Class<?> c : args) {
			sb.append(encodeType(c));
		}
		sb.append(")");
		sb.append(encodeType(ret));
		return sb.toString();
	}
}
