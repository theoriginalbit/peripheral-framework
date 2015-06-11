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

import com.theoriginalbit.framework.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.framework.peripheral.api.converter.ILuaTypeConverter;
import com.theoriginalbit.framework.peripheral.api.util.MultiReturn;
import com.theoriginalbit.framework.peripheral.api.util.TypeConversionException;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterMultiReturnOutbound implements ILuaTypeConverter {
    @Override
    public Object toLua(IConversionRegistry registry, Object obj) throws TypeConversionException {
        if (!(obj instanceof MultiReturn)) return null;
        Object[] result = ((MultiReturn) obj).getValues();
        for (int i = 0; i < result.length; ++i) {
            result[i] = registry.toLua(result[i]);
        }
        return result;
    }
}
