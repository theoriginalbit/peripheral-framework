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
package com.theoriginalbit.framework.peripheral.api.converter;

import com.theoriginalbit.framework.peripheral.api.util.TypeConversionException;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IJavaTypeConverter {
    /**
     * Converts a value from a Lua type to a Java type or returns null if it cannot convert
     *
     * @param registry caller of this method for recursive value conversion
     * @param obj      object to be converted
     * @param expected type needed Java side, return should be the same type or subtype
     * @return converted value or null if conversion cannot occur
     * @throws TypeConversionException when the object can be converted but something happens during conversion
     */
    Object toJava(IConversionRegistry registry, Object obj, Class<?> expected) throws TypeConversionException;
}
