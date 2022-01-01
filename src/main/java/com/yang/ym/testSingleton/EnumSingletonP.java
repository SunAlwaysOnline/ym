//// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
//// Jad home page: http://kpdus.tripod.com/jad.html
//// Decompiler options: packimports(3) fieldsfirst ansi space
//// Source File Name:   EnumSingleton.java
//
//
//public final class EnumSingleton extends Enum
//{
//
//	public static final EnumSingleton INSTANCE;
//	private Object data;
//	private static final EnumSingleton $VALUES[];
//
//	public static EnumSingleton[] values()
//	{
//		return (EnumSingleton[])$VALUES.clone();
//	}
//
//	public static EnumSingleton valueOf(String s)
//	{
//		return (EnumSingleton)Enum.valueOf(EnumSingleton, s);
//	}
//
//	private EnumSingleton(String s, int i, Object obj)
//	{
//		super(s, i);
//		data = obj;
//	}
//
//	public Object getData()
//	{
//		return data;
//	}
//
//	static
//	{
//		INSTANCE = new EnumSingleton("INSTANCE", 0, new Object());
//		$VALUES = (new EnumSingleton[] {
//			INSTANCE
//		});
//	}
//}
