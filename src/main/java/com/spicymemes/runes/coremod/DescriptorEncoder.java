package com.spicymemes.runes.coremod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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

	public static char[] getMethodArgs(String s){
		ArrayList<Character> out = new ArrayList<>();
		int start = 1;
		while(s.charAt(start) != ')'){
			switch (s.charAt(start)){
				case 'L':case '[':
					out.add('O');
					while(s.charAt(start) != ';'){
						start++;
					} break;
				default: out.add(s.charAt(start));
			}
			start++;
		}
		Object[] o = out.toArray();
		char[] outChars = new char[o.length];
		for(int i = 0; i < o.length; i++){
			outChars[i] = (char)o[i];
		}
		return outChars;
	}

	public static char getMethodRet(String s){
		char out = s.charAt(s.lastIndexOf(')') + 1);
		if(out == 'L' || out == '['){
			out = 'O';
		}
		return out;
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
	
	public static String encodeMethodHeader(Object ret, Object ... args) {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for(Object c : args) {
			if(c instanceof Class){
				sb.append(encodeType((Class)c));
			}
			else if(c instanceof String){
				sb.append("L" + ((String)c).replaceAll("\\.", "/") + ";");
			}
		}
		sb.append(")");
		if(ret instanceof Class){
			sb.append(encodeType((Class)ret));
		}
		else if(ret instanceof String){
			sb.append("L" + ((String)ret).replaceAll("\\.", "/") + ";");
		}
		return sb.toString();
	}
}
