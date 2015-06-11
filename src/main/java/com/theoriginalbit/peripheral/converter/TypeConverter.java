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
package com.theoriginalbit.peripheral.converter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.theoriginalbit.peripheral.api.converter.*;
import com.theoriginalbit.peripheral.api.util.JavaTypeConverterAdapter;
import com.theoriginalbit.peripheral.api.util.TypeConversionException;
import com.theoriginalbit.peripheral.util.Log;
import com.theoriginalbit.peripheral.converter.inbound.*;
import com.theoriginalbit.peripheral.converter.outbound.*;

import java.lang.reflect.Type;
import java.util.Deque;
import java.util.List;
import java.util.Set;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class TypeConverter implements IConverterRegistry, IConversionRegistry {
    protected final Deque<IGenericJavaTypeConverter> inbound = Lists.newLinkedList();
    protected final Deque<ILuaTypeConverter> outbound = Lists.newLinkedList();

    private final Set<Class<?>> directlyIgnored = Sets.newHashSet();
    private final List<Class<?>> subclassIgnored = Lists.newArrayList();

    public TypeConverter() {
        inbound.add(new ConverterRaw());

        inbound.add(new ConverterItemStackInbound());

        inbound.add(new ConverterNumberInbound());
        inbound.add(new ConverterEnumInbound());
        inbound.add(new ConverterStringInbound());

        inbound.add(new ConverterArrayInbound());
        inbound.add(new ConverterListInbound());
        inbound.add(new ConverterMapInbound());
        inbound.add(new ConverterSetInbound());

        inbound.add(new ConverterPrimative());

        outbound.add(new ConverterMultiReturnOutbound());
        outbound.add(new ConverterBooleanOutbound());
        outbound.add(new ConverterNumberOutbound());
        outbound.add(new ConverterEnumOutbound());

        outbound.add(new ConverterArrayOutbound());
        outbound.add(new ConverterListOutbound());
        outbound.add(new ConverterMapOutbound());
        outbound.add(new ConverterSetOutbound());

        outbound.add(new ConverterItemStackOutbound());

        outbound.add(new ConverterStringOutbound());
    }

    @Override
    public void register(IJavaTypeConverter converter) {
        Log.trace("Registering Java type converter %s", converter);
        inbound.addFirst(new JavaTypeConverterAdapter(converter));
    }

    @Override
    public void register(IGenericJavaTypeConverter converter) {
        Log.trace("Registering generic Java type converter %s", converter);
        inbound.addFirst(converter);
    }

    @Override
    public void register(ILuaTypeConverter converter) {
        Log.trace("Registering Lua type converter %s", converter);
        outbound.addFirst(converter);
    }

    @Override
    public void ignore(Class<?> ignored, boolean includeSubclasses) {
        (includeSubclasses ? subclassIgnored : directlyIgnored).add(ignored);
    }

    @Override
    public Object toJava(Object obj, Type expected) throws TypeConversionException {
        if (obj == null) {
            final TypeToken<?> type = TypeToken.of(expected);
            if (!type.isPrimitive()) {
                throw new TypeConversionException("expected %s, got nil", type);
            }
            return null;
        }

        for (IGenericJavaTypeConverter converter : inbound) {
            Object response = converter.toJava(this, obj, expected);
            if (response != null)
                return response;
        }

        final TypeToken<?> type = TypeToken.of(expected);
        throw new TypeConversionException("Failed to convert value %s to %s", obj, type.getRawType().getSimpleName());
    }

    @Override
    public <T> T toJava(Object obj, Class<? extends T> clazz) throws TypeConversionException {
        Object result = toJava(obj, (Type) clazz);
        if (!clazz.isInstance(result)) {
            throw new TypeConversionException("Conversion of %s to type %s failed", obj, clazz);
        }
        return clazz.cast(result);
    }

    @Override
    public Object toLua(Object obj) throws TypeConversionException {
        if (obj == null || isIgnored(obj.getClass())) return obj;

        for (ILuaTypeConverter converter : outbound) {
            Object response = converter.toLua(this, obj);
            if (response != null)
                return response;
        }

        // should never get here, since ConverterString is catch-all
        throw new TypeConversionException("Conversion failed on value %s", obj);
    }

    private boolean isIgnored(Class<?> clazz) {
        if (directlyIgnored.contains(clazz)) return true;
        for (Class<?> ignored : subclassIgnored)
            if (ignored.isAssignableFrom(clazz)) return true;
        return false;
    }
}
