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
package com.theoriginalbit.framework.peripheral.converter.inbound;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.theoriginalbit.framework.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.framework.peripheral.api.converter.IGenericJavaTypeConverter;
import com.theoriginalbit.framework.peripheral.api.util.TypeConversionException;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterListInbound implements IGenericJavaTypeConverter {
    private static final TypeVariable<?> TYPE_PARAM = List.class.getTypeParameters()[0];

    @Override
    public Object toJava(IConversionRegistry registry, Object obj, Type expected) throws TypeConversionException {
        if (obj instanceof Map) {
            final TypeToken<?> type = TypeToken.of(expected);
            if (type.getRawType() == List.class) {
                final Type valueType = type.resolveType(TYPE_PARAM).getType();

                final Map<?, ?> m = (Map<?, ?>) obj;

                if (m.isEmpty()) return ImmutableList.of();

                int indexMin = Integer.MAX_VALUE;
                int indexMax = Integer.MIN_VALUE;

                Map<Integer, Object> tmp = Maps.newHashMap();
                for (Map.Entry<?, ?> e : m.entrySet()) {
                    Object k = e.getKey();
                    if (!(k instanceof Number)) return null;
                    int index = ((Number) k).intValue();
                    if (index < indexMin) indexMin = index;
                    if (index > indexMax) indexMax = index;
                    tmp.put(index, e.getValue());
                }

                if (indexMin != 0 && indexMin != 1) return null;

                List<Object> result = Lists.newArrayList();

                for (int index = indexMin; index <= indexMax; index++) {
                    Object o = tmp.get(index);
                    final Object converted = registry.toJava(o, valueType);
                    result.add(converted);
                }

                return result;
            }
        }

        return null;
    }
}
