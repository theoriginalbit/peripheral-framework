package com.theoriginalbit.minecraft.computercraft.peripheral.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that a TileEntity should be wrapped as a peripheral. It should provide a
 *
 * @author theoriginalbit
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaPeripheral {

    public String name();

}
