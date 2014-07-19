package com.theoriginalbit.minecraft.computercraft.peripheral;

import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;
import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.Annotation;

/**
 * Created by theoriginalbit on 20/07/2014.
 */
public class Utils {

    public static final boolean isAnnotated(Object obj, Class<? extends Annotation> annotation) {
        return obj.getClass().isAnnotationPresent(annotation);
    }

}
