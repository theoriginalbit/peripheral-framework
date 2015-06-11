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
package com.theoriginalbit.framework.peripheral.converter.outbound;

import com.google.common.collect.Maps;
import com.theoriginalbit.framework.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.framework.peripheral.api.util.SimpleOutboundConverter;
import com.theoriginalbit.framework.peripheral.api.util.TypeConversionException;

import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterMapOutbound extends SimpleOutboundConverter<Map<?, ?>> {
    @Override
    protected Object convert(IConversionRegistry registry, Map<?, ?> value) throws TypeConversionException {
        Map<Object, Object> result = Maps.newHashMap();
        for (Map.Entry<?, ?> e : value.entrySet()) {
            Object k = registry.toLua(e.getKey());
            Object v = registry.toLua(e.getValue());
            result.put(k, v);
        }
        return result;
    }
}
