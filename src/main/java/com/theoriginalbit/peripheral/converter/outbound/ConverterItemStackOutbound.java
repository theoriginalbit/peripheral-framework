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
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class ConverterItemStackOutbound implements ILuaTypeConverter {
    @Override
    public Object toLua(IConversionRegistry registry, Object obj) throws TypeConversionException {
        return (obj instanceof ItemStack) ? fillBasicProperties((ItemStack) obj) : null;
    }

    private Map<String, Object> fillBasicProperties(ItemStack stack) throws TypeConversionException {
        final Map<String, Object> map = Maps.newHashMap();

        final GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(stack.getItem());

        if (id == null) {
            throw new TypeConversionException(String.format("Invalid item stack: %s", stack));
        }

        map.put("id", id.toString());
        map.put("name", id.name);
        map.put("mod_id", id.modId);
        map.put("display_name", getNameForItemStack(stack));
        map.put("raw_name", getRawNameForStack(stack));
        map.put("qty", stack.stackSize);
        map.put("dmg", stack.getItemDamage());
        map.put("max_dmg", stack.getMaxDamage());
        map.put("max_size", stack.getMaxStackSize());

        return map;
    }

    private String getNameForItemStack(ItemStack is) {
        try {
            return is.getDisplayName();
        } catch (Exception ignored) {
        }

        try {
            return is.getUnlocalizedName();
        } catch (Exception ignored) {
            return "unknown";
        }
    }

    private String getRawNameForStack(ItemStack is) {
        try {
            return is.getUnlocalizedName().toLowerCase();
        } catch (Exception ignored) {
            return "unknown";
        }
    }
}
