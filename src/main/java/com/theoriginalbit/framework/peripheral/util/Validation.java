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

import com.theoriginalbit.framework.peripheral.api.lua.Function;
import com.theoriginalbit.framework.peripheral.api.require.RequireAll;
import com.theoriginalbit.framework.peripheral.api.require.RequireOne;
import cpw.mods.fml.common.Loader;

import java.lang.reflect.Method;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public final class Validation {
    public static boolean isEnabled(Class<?> peripheral) {
        // if there is no annotation, we ignore it
        if (!peripheral.isAnnotationPresent(Function.class)) {
            return false;
        }

        // check if any of the mods from the RequireOne annotation mod ids are loaded
        if (peripheral.isAnnotationPresent(RequireOne.class)) {
            for (final String id : peripheral.getAnnotation(RequireOne.class).value()) {
                if (Loader.isModLoaded(id)) {
                    return true; // one was present
                }
            }
            // RequireAll can still enable this, we dont return false yet...
        }

        // check if all mods from the RequireAll annotation mod ids are loaded
        if (peripheral.isAnnotationPresent(RequireAll.class)) {
            for (final String id : peripheral.getAnnotation(RequireAll.class).value()) {
                if (!Loader.isModLoaded(id)) {
                    return false; // something was missing
                }
            }
            return true; // everything was present
        }

        // if there was no RequireOne this function can be enabled
        return !peripheral.isAnnotationPresent(RequireOne.class);
    }

    public static boolean isEnabled(Method method) {
        // if there is no annotation, we ignore it
        if (!method.isAnnotationPresent(Function.class)) {
            return false;
        }

        // check if any of the mods from the RequireOne annotation mod ids are loaded
        if (method.isAnnotationPresent(RequireOne.class)) {
            for (final String id : method.getAnnotation(RequireOne.class).value()) {
                if (Loader.isModLoaded(id)) {
                    return true; // one was present
                }
            }
            // RequireAll can still enable this, we dont return false yet...
        }

        // check if all mods from the RequireAll annotation mod ids are loaded
        if (method.isAnnotationPresent(RequireAll.class)) {
            for (final String id : method.getAnnotation(RequireAll.class).value()) {
                if (!Loader.isModLoaded(id)) {
                    return false; // something was missing
                }
            }
            return true; // everything was present
        }

        // if there was no RequireOne this function can be enabled
        return !method.isAnnotationPresent(RequireOne.class);
    }
}
