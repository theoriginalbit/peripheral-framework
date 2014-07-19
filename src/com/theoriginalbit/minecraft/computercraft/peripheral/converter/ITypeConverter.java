package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

public interface ITypeConverter {
	public Object fromLua(Object obj, Class<?> expected);
	public Object toLua(Object obj);
}