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
package com.theoriginalbit.peripheral.converter.inbound;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.theoriginalbit.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.peripheral.api.converter.IGenericJavaTypeConverter;
import com.theoriginalbit.peripheral.api.util.TypeConversionException;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterArrayInbound implements IGenericJavaTypeConverter {
    @Override
    public Object toJava(IConversionRegistry registry, Object obj, Type expected) throws TypeConversionException {
        if (obj instanceof Map) {
            final TypeToken<?> type = TypeToken.of(expected);
            if (type.isArray()) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> m = (Map<Object, Object>) obj;

                final TypeToken<?> component = type.getComponentType();

                if (component == null) return null;

                final Class<?> rawComponent = component.getRawType();

                final Type genericComponent = component.getType();

                if (m.isEmpty()) return Array.newInstance(rawComponent, 0);

                int indexMin = Integer.MAX_VALUE;
                int indexMax = Integer.MIN_VALUE;

                Map<Integer, Object> tmp = Maps.newHashMap();
                for (Map.Entry<Object, Object> e : m.entrySet()) {
                    Object k = e.getKey();
                    if (!(k instanceof Number)) return null;
                    int index = ((Number) k).intValue();
                    if (index < indexMin) indexMin = index;
                    if (index > indexMax) indexMax = index;
                    tmp.put(index, e.getValue());
                }

                int size = indexMax - indexMin + 1;
                if (indexMin != 0 && indexMin != 1) return null;

                Object result = Array.newInstance(rawComponent, size);
                for (int i = 0, index = indexMin; i < size; i++, index++) {
                    Object in = tmp.get(index);
                    if (in == null) continue;
                    Object out = registry.toJava(in, genericComponent);
                    Array.set(result, i, out);
                }

                return result;
            }
        }

        return null;
    }
}
