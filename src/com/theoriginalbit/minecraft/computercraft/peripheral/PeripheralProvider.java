package com.theoriginalbit.minecraft.computercraft.peripheral;

import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.wrapper.PeripheralWrapper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

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
 * This is the Peripheral Provider that will wrap valid TileEntities
 * and return them to ComputerCraft for usage. See IPeripheralProvider
 * for more information
 *
 * IMPORTANT:
 * This is a backend class that is very important for operation of this
 * framework, modifying it may have unexpected results.
 *
 * @author theoriginalbit
 */
public final class PeripheralProvider implements IPeripheralProvider {

	@Override
	public final IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);

        Object target = null;
        if (Utils.isAnnotated(tile, LuaPeripheral.class)) {
            target = tile;
        } else if (tile instanceof ILuaPeripheralProvider) {
            target = ((ILuaPeripheralProvider) tile).getPeripheral();
        }

		if (target != null) {
			return new PeripheralWrapper(target);
		}

		return null;
	}

}