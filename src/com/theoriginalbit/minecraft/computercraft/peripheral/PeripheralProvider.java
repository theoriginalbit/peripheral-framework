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

	private final WeakHashMap<TileEntity, PeripheralWrapper> PERIPHERAL_CACHE = new WeakHashMap<TileEntity, PeripheralWrapper>();

	@Override
	public final IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);

        Class<?> tileClass = null;
        if (isAnnotated(tile, LuaPeripheral.class)) {
            tileClass = tile.getClass();
        } else if (tile instanceof ILuaPeripheralProvider) {
            tileClass = ((ILuaPeripheralProvider) tile).getPeripheral();
        }

		if (tileClass != null) {
			return new PeripheralWrapper(tileClass);
		}

		return null;
	}

    private final boolean isAnnotated(TileEntity tile, Class<?> annotation) {
        return tile.getClass().isAnnotationPresent(LuaPeripheral.class);
    }

}