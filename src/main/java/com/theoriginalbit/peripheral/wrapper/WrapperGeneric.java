/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.peripheral.wrapper;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.theoriginalbit.peripheral.api.event.Attach;
import com.theoriginalbit.peripheral.api.event.ComputerList;
import com.theoriginalbit.peripheral.api.event.Detach;
import com.theoriginalbit.peripheral.api.lua.Alias;
import com.theoriginalbit.peripheral.api.lua.Function;
import com.theoriginalbit.peripheral.api.peripheral.Peripheral;
import com.theoriginalbit.peripheral.util.Validation;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author theoriginalbit
 * @since 8/11/14
 */
class WrapperGeneric implements IPeripheral {
    protected final Object instance;
    protected final String peripheralType;
    protected final LinkedHashMap<String, WrapperMethod> methods = Maps.newLinkedHashMap();
    protected final String[] methodNames;
    protected final Method methodAttach;
    protected final Method methodDetach;
    protected final ArrayList<IComputerAccess> computers = Lists.newArrayList();

    public WrapperGeneric(Object peripheral) {
        final Class<?> peripheralClass = peripheral.getClass();
        final Peripheral peripheralLua = peripheralClass.getAnnotation(Peripheral.class);

        // validate the peripheral type
        final String pname = peripheralLua.value().trim();
        Preconditions.checkArgument(!pname.isEmpty(), "Peripheral name cannot be an empty string");

        Method attach = null, detach = null;
        for (Method m : peripheralClass.getMethods()) {
            if (Validation.isEnabled(m)) {
                wrapMethod(peripheral, m);
            } else if (m.isAnnotationPresent(Alias.class)) {
                throw new RuntimeException("Alias annotations should only occur on LuaFunction annotated methods");
            }
            if (m.isAnnotationPresent(Attach.class)) {
                attach = m;
            }
            if (m.isAnnotationPresent(Detach.class)) {
                detach = m;
            }
        }

        // check for the @Computer fields and assign them to this instances computer list
        for (Field f : peripheralClass.getDeclaredFields()) {
            if (f.isAnnotationPresent(ComputerList.class)) {
                try {
                    f.set(peripheral, computers);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        instance = peripheral;
        peripheralType = pname;
        methodAttach = checkEventMethod(attach, "@Computers.Attach");
        methodDetach = checkEventMethod(detach, "@Computers.Detach");
        Set<String> keys = methods.keySet();
        methodNames = keys.toArray(new String[keys.size()]);
    }

    public final Object getInstance() {
        return instance;
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
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int methodIdx, Object[] arguments)
            throws LuaException, InterruptedException {
        if (instance instanceof TileEntity) {
            if (((TileEntity) instance).isInvalid()) {
                throw new LuaException("peripheral no longer exists");
            }
        }
        final String name = methodNames[methodIdx];
        final WrapperMethod method = methods.get(name);
        return method.invoke(computer, context, arguments);
    }

    @Override
    public void attach(IComputerAccess computer) {
        if (!computers.contains(computer)) {
            computers.add(computer);
        }
        if (methodAttach != null) {
            try {
                methodAttach.invoke(instance, computer);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void detach(IComputerAccess computer) {
        if (computers.contains(computer)) {
            computers.remove(computer);
        }
        if (methodDetach != null) {
            try {
                methodDetach.invoke(instance, computer);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * dan200, why do we have to do this? why can't we just use Java's native equals?
     */
    @Override
    public boolean equals(IPeripheral other) {
        return super.equals(other);
    }

    private void wrapMethod(Object peripheral, Method method) {
        final Function annotation = method.getAnnotation(Function.class);
        // extract the method name either from the annotation or the actual name
        final String name = annotation.value().trim().isEmpty() ? method.getName() : annotation.value().trim();
        // make sure it doesn't already exist
        Preconditions.checkArgument(!methods.containsKey(name), "Duplicate method found " + name + ". Either make use of the name in the LuaFunction annotation, or if these methods do the same purpose use the Alias annotation instead.");
        // wrap and store the method
        final WrapperMethod wrapper = new WrapperMethod(peripheral, method);
        methods.put(name, wrapper);
        // add Alias references too
        if (method.isAnnotationPresent(Alias.class)) {
            for (String alias : method.getAnnotation(Alias.class).value()) {
                Preconditions.checkArgument(!methods.containsKey(alias), "Duplicate method found while attempting to apply Alias " + alias);
                methods.put(alias, wrapper);
            }
        }
    }

    private Method checkEventMethod(final Method m, String type) {
        if (m == null) return null;
        final Class<?>[] params = m.getParameterTypes();
        if (params.length == 0) return m;
        final boolean valid = params.length == 1 && IComputerAccess.class.isAssignableFrom(params[0]);
        Preconditions.checkArgument(valid, type + " method can only have one parameters of type IComputerAccess");
        return m;
    }
}
