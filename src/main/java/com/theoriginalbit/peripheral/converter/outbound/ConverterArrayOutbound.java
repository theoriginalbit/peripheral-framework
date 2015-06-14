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
package com.theoriginalbit.peripheral.converter.outbound;

import com.google.common.collect.Maps;
import com.theoriginalbit.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.peripheral.api.converter.ILuaTypeConverter;
import com.theoriginalbit.peripheral.api.util.TypeConversionException;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * Converts an array to/from Lua
 *
 * @author theoriginalbit
 */
public class ConverterArrayOutbound implements ILuaTypeConverter {
    @Override
    public Object toLua(IConversionRegistry registry, Object obj) throws TypeConversionException {
        if (obj.getClass().isArray()) {
            Map<Object, Object> map = Maps.newHashMap();
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                map.put(i + 1, registry.toLua(Array.get(obj, i)));
            }
            return map;
        }
        return null;
    }
}