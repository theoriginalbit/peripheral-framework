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
package com.theoriginalbit.framework.peripheral.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public final class TypeUtil {
    public static final BiMap<Class<?>, Class<?>> PRIMATIVE_TYPE_MAP = ImmutableBiMap.<Class<?>, Class<?>>builder()
            .put(boolean.class, Boolean.class)
            .put(byte.class, Byte.class)
            .put(char.class, Character.class)
            .put(short.class, Short.class)
            .put(int.class, Integer.class)
            .put(long.class, Long.class)
            .put(float.class, Float.class)
            .put(double.class, Double.class)
            .put(void.class, Void.class)
            .build();

    public static boolean compareTypes(Class<?> lhs, Class<?> rhs) {
        if (lhs.isPrimitive()) lhs = PRIMATIVE_TYPE_MAP.get(lhs);
        if (rhs.isPrimitive()) rhs = PRIMATIVE_TYPE_MAP.get(rhs);
        return lhs.equals(rhs);
    }
}
