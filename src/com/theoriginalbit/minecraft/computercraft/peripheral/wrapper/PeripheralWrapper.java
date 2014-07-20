package com.theoriginalbit.minecraft.computercraft.peripheral.wrapper;

import java.lang.reflect.Method;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Attach;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Detach;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;

import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;
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
     * TODO: method wrappers are created and stored, if there is an @Alias annotation
     * the method should also be stored under that name, as a result the List storing
     * the method wrappers should probably become a map
     */

    private final String peripheralType;
	private final Object instance;
	private final List<MethodWrapper> methods = Lists.newArrayList();
    private final Method attach;
    private final Method detach;
	private final String[] methodNames;
	
	public PeripheralWrapper(Object peripheral) {
		instance = peripheral;

        final Class<?> peripheralClass = peripheral.getClass();
        final LuaPeripheral peripheralLua = peripheralClass.getAnnotation(LuaPeripheral.class);

        // extract the peripheral type from the annotation
        peripheralType = peripheralLua.value();

		List<String> names = Lists.newArrayList();
        Method attachMethod = null;
        Method detachMethod = null;

        // for all the methods
		for (Method m : peripheralClass.getMethods()) {
            // if the method defines it to be an attach
            if (isAttachMethod(m)) {
                Preconditions.checkArgument(attachMethod == null, "Duplicate methods found annotated with Attach, a peripheral can only define one Attach method");
                Class<?>[] params = m.getParameterTypes();
                Preconditions.checkArgument(params.length == 1, "Attach methods should only have one argument; IComputerAccess");
                Preconditions.checkArgument(IComputerAccess.class.isAssignableFrom(params[0]), "Invalid argument on Attach method should be IComputerAccess");
                attachMethod = m;
            // if the method defines it to be a detach
            } else if (isDetachMethod(m)) {
                Preconditions.checkArgument(detachMethod == null, "Duplicate methods found annotated with Detach, a peripheral can only define one Detach method");
                Class<?>[] params = m.getParameterTypes();
                Preconditions.checkArgument(params.length == 1, "Detach methods should only have one argument; IComputerAccess");
                Preconditions.checkArgument(IComputerAccess.class.isAssignableFrom(params[0]), "Invalid argument on Detach method should be IComputerAccess");
                detachMethod = m;
            // if the method defines it to be a LuaFunction
            } else if (m.isAnnotationPresent(LuaFunction.class)) {
				MethodWrapper wrapper = new MethodWrapper(peripheral, m, m.getAnnotation(LuaFunction.class));
				methods.add(wrapper);
				names.add(wrapper.getLuaName());
			}
		}
        attach = attachMethod;
        detach = detachMethod;
		methodNames = names.toArray(new String[names.size()]);
	}

    private boolean isAttachMethod(Method method) {
        if (method.isAnnotationPresent(Attach.class)) {
            Preconditions.checkArgument(!method.isAnnotationPresent(Detach.class), "Attach method cannot also be a Detach method");
            Preconditions.checkArgument(!method.isAnnotationPresent(LuaFunction.class), "Attach method cannot also be a LuaFunction method");
            return true;
        }
        return false;
    }

    private boolean isDetachMethod(Method method) {
        if (method.isAnnotationPresent(Detach.class)) {
            Preconditions.checkArgument(!method.isAnnotationPresent(Attach.class), "Detach method cannot also be an Attach method");
            Preconditions.checkArgument(!method.isAnnotationPresent(LuaFunction.class), "Detach method cannot also be a LuaFunction method");
            return true;
        }
        return false;
    }

	@Override
	public String getType() {
		return peripheralType;
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
        try {
            attach.invoke(instance, computer);
        } catch (Exception e) {}
	}

	@Override
	public void detach(IComputerAccess computer) {
        try {
            detach.invoke(instance, computer);
        } catch (Exception e) {}
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

}
