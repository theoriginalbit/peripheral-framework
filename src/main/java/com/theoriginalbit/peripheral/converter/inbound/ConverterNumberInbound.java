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

import com.theoriginalbit.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.peripheral.api.util.GenericInboundConverterAdapter;
import com.theoriginalbit.peripheral.api.util.TypeConversionException;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterNumberInbound extends GenericInboundConverterAdapter {
    @Override
    protected Object toJava(IConversionRegistry registry, Object obj, Class<?> expected) throws TypeConversionException {
        final Double d;
        if (obj instanceof Double) {
            d = (Double) obj;
        } else {
            return null;
        }

        if (expected == Double.class || expected == double.class) return d;
        if (expected == Integer.class || expected == int.class) return d.intValue();
        if (expected == Float.class || expected == float.class) return d.floatValue();
        if (expected == Long.class || expected == long.class) return d.longValue();
        if (expected == Short.class || expected == short.class) return d.shortValue();
        if (expected == Byte.class || expected == byte.class) return d.byteValue();
        if (expected == Boolean.class || expected == boolean.class) return d != 0;

        return null;
    }
}
