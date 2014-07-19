package com.theoriginalbit.minecraft.computercraft.peripheral;

import java.util.WeakHashMap;

import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;
import com.theoriginalbit.minecraft.computercraft.peripheral.wrapper.PeripheralWrapper;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;

/**
 * Peripheral provider for ComputerCraft
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