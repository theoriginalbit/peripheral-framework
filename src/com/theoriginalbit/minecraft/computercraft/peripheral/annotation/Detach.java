package com.theoriginalbit.minecraft.computercraft.peripheral.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated method will handle the {@link dan200.computercraft.api.peripheral.IPeripheral#detach(dan200.computercraft.api.peripheral.IComputerAccess)}
 * method on your {@link com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral} annotated {@link net.minecraft.tileentity.TileEntity}
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Detach {}
