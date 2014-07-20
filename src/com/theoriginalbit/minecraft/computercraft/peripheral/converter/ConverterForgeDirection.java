package com.theoriginalbit.minecraft.computercraft.peripheral.converter;

import net.minecraftforge.common.ForgeDirection;

/**
 * Peripheral Framework is an open-source framework that has the aim of
 * allowing developers to implement their ComputerCraft peripherals faster,
 * easier, and cleaner; allowing them to focus more on developing their
 * content.
 *
 * Copyright (C) 2014  Joshua Asbury (@theoriginalbit)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

/**
 * Converts a {@link net.minecraftforge.common.ForgeDirection} to from Lua
 *
 * @author theoriginalbit
 */
public class ConverterForgeDirection implements ITypeConverter {
	@Override
	public Object fromLua(Object obj, Class<?> expected) {
		if (expected == ForgeDirection.class && obj instanceof String) {
			ForgeDirection dir = ForgeDirection.valueOf((String) obj);
			return dir != null ? dir : ForgeDirection.UNKNOWN;
		}
		return null;
	}

	@Override
	public Object toLua(Object obj) {
		if (obj instanceof ForgeDirection) {
			return ((ForgeDirection) obj).name().toLowerCase();
		}
		return null;
	}

}
