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
package com.theoriginalbit.peripheral.api.converter;

import com.theoriginalbit.peripheral.api.util.TypeConversionException;

import java.lang.reflect.Type;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public interface IGenericJavaTypeConverter {
    /**
     * The same as {@link IJavaTypeConverter#toJava(IConversionRegistry, Object, Class)} except it supports
     * conversion to Java types that use generics
     *
     * @param registry caller of this method for recursive value conversion
     * @param obj      object to be converted
     * @param expected type needed Java side, return should be the same type or subtype
     * @return converted value or null if conversion cannot occur
     * @throws TypeConversionException when the object can be converted but something happens during conversion
     * @see IJavaTypeConverter#toJava(IConversionRegistry, Object, Class)
     */
    Object toJava(IConversionRegistry registry, Object obj, Type expected) throws TypeConversionException;
}
