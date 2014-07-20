package com.theoriginalbit.minecraft.computercraft.peripheral;

import java.util.Deque;
import java.util.Map;

import net.minecraftforge.common.ForgeDirection;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterArray;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterDefault;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterForgeDirection;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterList;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterMap;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterNumber;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterSet;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ConverterString;
import com.theoriginalbit.minecraft.computercraft.peripheral.converter.ITypeConverter;

import dan200.computercraft.api.lua.ILuaObject;

/**
 * Peripheral Framework is an open-source framework that has the aim of
 * allowing developers to implement their ComputerCraft peripherals faster,
 * easier, and cleaner; allowing them to focus more on developing their
 * content.
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

/**
 * An Enum to specify the mapping between Java classes and Lua types
 * and perform data type conversion between both languages.
 *
 * If you have another ITypeConverter to register with this system
 * you can do it in one of your mods initialisation phases. See
 * the registerTypeConverter method for more information.
 *
 * IMPORTANT:
 * This is a backend class, you should never need to use this, and
 * modifying this may have unexpected results.
 * 
 * @author theoriginalbit
 */
public enum LuaType {

	TABLE("table", Map.class),
	NUMBER("number", Double.class),
	STRING("string", String.class),
	NIL("nil", void.class),
	BOOLEAN("boolean", Boolean.class),
	FORGEDIRECTION("direction", ForgeDirection.class),
	OBJECT("?", Object.class);

    /**
     * A list of all the converters available
     */
	private static final Deque<ITypeConverter> CONVERTERS = Lists.newLinkedList();

	private final String luaName;
	private final Class<?> javaType;

	private LuaType(String name, Class<?> type) {
		luaName = name;
		javaType = type;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public String getLuaName() {
		return luaName;
	}

	public static String findName(Class<?> clazz) {
		if (clazz == null) {
			return "nil";
		}
		for (LuaType t : values()) {
			if (clazz.isAssignableFrom(t.getJavaType())) {
				return t.getLuaName();
			}
		}
		return "?";
	}

	public static Object fromLua(Object obj, Class<?> expected) {
		for (ITypeConverter converter : CONVERTERS) {
			Object response = converter.fromLua(obj, expected);
			if (response != null) { return response; }
		}
		return null;
	}
	
	public static Object toLua(Object obj) {
		if (obj == null || obj instanceof ILuaObject) { return obj; }
		
		for (ITypeConverter converter : CONVERTERS) {
			Object response = converter.toLua(obj);
			if (response != null) { return response; }
		}
		
		// a catch-all of a catch-all? sure :P
		throw new IllegalStateException("Conversion failed on " + obj);
	}

    /**
     * Registers the supplied converter to the conversion system.
     * Use this to supply converters for custom classes, such as
     * ItemStack or the likes, so that these objects can be converted
     * to and from Lua.
     *
     * NOTE: it adds it to the start because order is important!
     */
    public static void registerTypeConverter(ITypeConverter converter) {
        if (!CONVERTERS.contains(converter)) {
            CONVERTERS.addFirst(converter);
        }
    }
	
	static {
		CONVERTERS.add(new ConverterForgeDirection());
		// Order is important from here on!
		CONVERTERS.add(new ConverterArray());
		CONVERTERS.add(new ConverterList());
		CONVERTERS.add(new ConverterMap());
		CONVERTERS.add(new ConverterSet());
		CONVERTERS.add(new ConverterDefault());
		CONVERTERS.add(new ConverterNumber());
		CONVERTERS.add(new ConverterString()); // catch-all
	}

}