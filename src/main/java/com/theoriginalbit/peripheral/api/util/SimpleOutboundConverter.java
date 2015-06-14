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
import com.theoriginalbit.peripheral.api.converter.ILuaTypeConverter;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public abstract class SimpleOutboundConverter<T> implements ILuaTypeConverter {
    private final Class<? super T> type = (new TypeToken<T>(getClass()) {
        // nothing to add
    }).getRawType();

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object toLua(IConversionRegistry registry, Object obj) throws TypeConversionException {
        return type.isInstance(obj) ? convert(registry, (T) obj) : null;
    }

    /**
     * Converts the supplied value from a Java value to valid Lua value
     *
     * @param registry the conversion registry for recursive calls
     * @param value    the value to convert
     * @return the converted value or {@code null} if it cannot be converted
     * @throws TypeConversionException if conversion failed
     */
    public abstract Object convert(IConversionRegistry registry, T value) throws TypeConversionException;
}