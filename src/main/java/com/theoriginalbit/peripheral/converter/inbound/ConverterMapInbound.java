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

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterMapInbound implements IGenericJavaTypeConverter {
    private static final TypeVariable<?> KEY;
    private static final TypeVariable<?> VALUE;

    static {
        TypeVariable<?>[] vars = Map.class.getTypeParameters();
        KEY = vars[0];
        VALUE = vars[1];
    }

    @Override
    public Object toJava(IConversionRegistry registry, Object obj, Type expected) throws TypeConversionException {
        if (obj instanceof Map) {
            final TypeToken<?> type = TypeToken.of(expected);
            if (type.getRawType() == Map.class) {
                final Type keyType = type.resolveType(KEY).getType();
                final Type valueType = type.resolveType(VALUE).getType();

                Map<Object, Object> result = Maps.newHashMap();

                for (Map.Entry<?, ?> e : ((Map<?, ?>) obj).entrySet()) {
                    Object key = registry.toJava(e.getKey(), keyType);
                    Object value = registry.toJava(e.getValue(), valueType);
                    result.put(key, value);
                }

                return result;
            }
        }

        return null;
    }
}
