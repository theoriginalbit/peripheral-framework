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
package com.theoriginalbit.peripheral.api.util;

import com.google.common.reflect.TypeToken;
import com.theoriginalbit.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.peripheral.api.converter.IGenericJavaTypeConverter;

import java.lang.reflect.Type;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public abstract class GenericInboundConverterAdapter implements IGenericJavaTypeConverter {
    @Override
    public Object toJava(IConversionRegistry registry, Object obj, Type expected) throws TypeConversionException {
        TypeToken<?> type = TypeToken.of(expected);
        return toJava(registry, obj, type.getRawType());
    }

    public abstract Object toJava(IConversionRegistry registry, Object obj, Class<?> expected) throws TypeConversionException;
}
