package com.theoriginalbit.minecraft.computercraft.peripheral.wrapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.common.base.Preconditions;
import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Alias;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;

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
 * This wraps the method supplied from the LuaPeripheral, it provides
 * a simple invocation which converts the Lua arguments supplied to
 * the Java type expected of the LuaFunction, as well as converting
 * the return value back to Lua types. It will also provide user friendly
 * errors when the method is invoked incorrectly, such as 'expected string
 * got number' or 'expected 4 arguments, got 3'
 *
 * IMPORTANT:
 * This is a backend class, you should never need to use this, and
 * modifying this may have unexpected results.
 *
 * @author theoriginalbit
 */
public class MethodWrapper {

	private final String name;
	private final Method method;
	private final Class<?>[] javaParams;
	private final TilePeripheral instance;
	
	public MethodWrapper(TilePeripheral peripheral, Method method, LuaFunction function) {
		Preconditions.checkArgument(method.isAnnotationPresent(LuaFunction.class));
		
		instance = peripheral;
		this.method = method;
		// get the name either from the @Alias or the Java name
		name = method.isAnnotationPresent(Alias.class) ? method.getAnnotation(Alias.class).value() : method.getName();
		
		javaParams = method.getParameterTypes();
	}
	
	public String getLuaName() {
		return name;
	}
	
	public Object[] invoke(IComputerAccess access, ILuaContext context, Object[] arguments) throws Exception {
        /*
         * TODO: validate number of args provided and throw an error when not enough are supplied,
         * it must also allow for methods to define IComputerAccess as an argument type and not
         * throw a # args error when it's not provided, since, you know, Lua-side cannot supply
         * this, it instead must be provided from here.
         *
         * TODO: allow for multi-returns, so basically if I'm understanding correctly, if the method
         * returns an Object[] then just loop through it, convert all it's members, and return it,
         * otherwise convert the return value and wrap in an Object[]
         *
         * TODO: I'm sure there's lots more things that can be improved in this
         */
		Object[] args = new Object[javaParams.length];
		
		for (int i = 0; i < args.length; ++i) {
			if (arguments[i] == null) {
				throw new Exception(String.format("expected %s, got nil", LuaType.findName(javaParams[i])));
			} else if (IComputerAccess.class.isAssignableFrom(javaParams[i])) {
				args[i] = access;
			} else if (ILuaContext.class.isAssignableFrom(javaParams[i])) {
				args[i] = context;
			} else {
				Object convert = LuaType.fromLua(arguments[i], javaParams[i]);
				Preconditions.checkArgument(convert != null, "expected %s, got %s", LuaType.findName(javaParams[i]), LuaType.findName(arguments[i].getClass()));
				args[i] = convert;
			}
		}
		
		try {
			return new Object[] { LuaType.toLua(method.invoke(instance, args)) };
		} catch (IllegalAccessException e) {
			throw new Exception("Developer problem, IllegalAccessException " + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new Exception("Developer problem, IllegalArgumentException " + e.getMessage());
		} catch (InvocationTargetException e) {
			String message = null;
			Throwable cause = e;
			while ((message = cause.getMessage()) == null
					&& (cause = cause.getCause()) != null) {}
			throw new Exception(message);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}