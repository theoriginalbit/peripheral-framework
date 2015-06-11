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

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;
import com.theoriginalbit.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.peripheral.api.converter.IGenericJavaTypeConverter;
import com.theoriginalbit.peripheral.api.util.TypeConversionException;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterSetInbound implements IGenericJavaTypeConverter {
    private static final TypeVariable<?> TYPE_PARAM = Set.class.getTypeParameters()[0];

    @Override
    public Object toJava(IConversionRegistry registry, Object obj, Type expected) throws TypeConversionException {
        if (obj instanceof Map) {
            final TypeToken<?> type = TypeToken.of(expected);
            if (type.getRawType() == Set.class) {
                final Type valueType = type.resolveType(TYPE_PARAM).getType();

                Set<Object> result = Sets.newHashSet();

                for (Map.Entry<?, ?> e : ((Map<?, ?>) obj).entrySet()) {
                    final Object value = e.getKey();

                    Object marker = e.getValue();
                    if (isTruthful(marker)) {
                        Object converted = registry.toJava(value, valueType);
                        result.add(converted);
                    }
                }

                return result;
            }
        }

        return null;
    }

    private boolean isTruthful(Object v) {
        if (v == null) return false;
        if (v instanceof Boolean) return (Boolean) v;
        if (v instanceof Number) return ((Number) v).doubleValue() != 0;
        if (v instanceof String) return !Strings.isNullOrEmpty((String) v);
        if (v instanceof Map) return !((Map<?, ?>) v).isEmpty();
        return v instanceof Collection && !((Collection<?>) v).isEmpty();
    }
}
