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
package com.theoriginalbit.framework.peripheral.converter.inbound;

import com.theoriginalbit.framework.peripheral.api.converter.IConversionRegistry;
import com.theoriginalbit.framework.peripheral.api.util.GenericInboundConverterAdapter;
import com.theoriginalbit.framework.peripheral.api.util.TypeConversionException;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterItemStackInbound extends GenericInboundConverterAdapter {
    @Override
    protected Object toJava(IConversionRegistry registry, Object obj, Class<?> expected) throws TypeConversionException {
        if (expected == ItemStack.class && obj instanceof Map) {
            Map<?, ?> m = (Map<?, ?>) obj;

            if (!m.containsKey("id")) {
                throw new TypeConversionException("expected id for item");
            }
            String[] parts = ((String) m.get("id")).split(":");
            if (parts.length != 2) {
                throw new TypeConversionException("invalid item id should be modId:blockName");
            }

            Item item = GameRegistry.findItem(parts[0], parts[1]);

            if (item == null) {
                throw new TypeConversionException("cannot find item for " + m.get("id"));
            }

            int quantity = getIntValue(m, "qty", 1);
            int dmg = getIntValue(m, "dmg", 0);

            return new ItemStack(item, quantity, dmg);
        }
        return null;
    }

    private int getIntValue(Map<?, ?> map, String key, int _default) {
        final Object value = map.get(key);
        return value instanceof Number ? ((Number) value).intValue() : _default;
    }
}
