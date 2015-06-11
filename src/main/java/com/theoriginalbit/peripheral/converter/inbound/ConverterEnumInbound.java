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

import java.util.Arrays;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterEnumInbound extends GenericInboundConverterAdapter {
    @Override
    protected Object toJava(IConversionRegistry registry, Object obj, Class<?> expected) throws TypeConversionException {
        if (expected.isEnum()) {
            Object[] constants = expected.getEnumConstants();
            if (obj instanceof String) {
                String value = (String) obj;
                for (Object o : constants) {
                    if (o.toString().equalsIgnoreCase(value)) return o;
                }
            } else if (obj instanceof Number) {
                int value = ((Number) obj).intValue() - 1;
                try {
                    return constants[value];
                } catch (IndexOutOfBoundsException e) {
                    // ignore, will fail anyway with more descriptive exception
                }
            }
            throw new TypeConversionException(String.format(
                    "'%s' is not valid enum value, must be %s or 1..%d",
                    obj, Arrays.toString(constants), constants.length
            ));
        }

        return null;
    }
}
