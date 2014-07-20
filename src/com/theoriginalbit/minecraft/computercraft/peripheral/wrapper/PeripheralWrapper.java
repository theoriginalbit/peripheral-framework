package com.theoriginalbit.minecraft.computercraft.peripheral.wrapper;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

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
 * This wraps the object annotated with LuaPeripheral that is supplied to
 * it from the Peripheral Provider, it will then wrap any methods annotated
 * with LuaFunction and retain references of methods annotated with Attach
 * and Detach so that your peripheral will function with ComputerCraft's
 * expected IPeripheral interface.
 *
 * IMPORTANT:
 * This is a backend class, you should never need to use this, and
 * modifying this may have unexpected results.
 *
 * @author theoriginalbit
 */
public class PeripheralWrapper implements IPeripheral {

    /*
     * TODO: fix the wrapper to check for, and make use of, the new annotation system
     */

	private final Object instance;
	private final List<MethodWrapper> methods = Lists.newArrayList();
	private final String[] methodNames;
	
	public PeripheralWrapper(Object peripheral) {
		instance = peripheral;
		
		List<String> names = Lists.newArrayList();
		for (Method m : peripheral.getClass().getMethods()) {
			if (m.isAnnotationPresent(LuaFunction.class)) {
				MethodWrapper wrapper = new MethodWrapper(peripheral, m, m.getAnnotation(LuaFunction.class));
				methods.add(wrapper);
				names.add(wrapper.getLuaName());
			}
		}
		methodNames = names.toArray(new String[names.size()]);
	}

	@Override
	public String getType() {
		return instance.getType();
	}

	@Override
	public String[] getMethodNames() {
		return methodNames;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception {
		return methods.get(method).invoke(computer, context, arguments);
	}

	@Override
	public void attach(IComputerAccess computer) {
		instance.attach(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		instance.detach(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

}
