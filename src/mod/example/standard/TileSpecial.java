package mod.example.standard;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Attach;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Detatch;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Again this is your standard TileEntity class, however this is where your peripheral fun can happen.
 *
 * This class is annotated with the LuaPeripheral annotation, this tells the PeripheralProvider that
 * you wish this TileEntity to be available to ComputerCraft as a peripheral, with the peripheral type
 * specified through name.
 *
 * This class must then define methods it wishes to be accessible in Lua with the LuaFunction annotation.
 *
 * If you wish your peripheral to know when a computer is attached or detached from your peripheral, you
 * must define a method with the Attach and Detach annotations respectively. Note: you may only define
 * one of each method!
 *
 * @author theoriginalbit
 */
@LuaPeripheral(name = "special")
public class TileSpecial extends TileEntity {

    /**
     * LuaFunction marks that you wish a method to be accessible in Lua.
     * The name it has in Lua will be the same name you define here.
     */
    @LuaFunction
    public void doSomething() {
        System.out.println("Something was done with the special peripheral");
    }

    /**
     * The Peripheral Framework will detect return types, as well as parameter types
     * and will convert them appropriately, it will also handle if the developer/user
     * has provided you with the wrong type, e.g. if they provide a number to this method
     * it will error to them stating "expected string, got number"
     */
    @LuaFunction
    public boolean foo(String bar) {
        return bar.equals("correct!");
    }

    /**
     * Lets assume, that for some reason you wish to have the method appear under a different
     * name in Lua you can provide a value to the LuaFunction; the only time you should really
     * be using this is in the event of method overloading like shown below.
     */
    @LuaFunction("bar")
    public boolean foo() {
        return foo("incorrect!");
    }

    // A list you can use to track all the computers attached to this peripheral
    private ArrayList<IComputerAccess> computers = Lists.newArrayList();

    /**
     * The method can be named anything you want, as long as it has the annotation it will
     * be invoked when a computer is attached to your peripheral
     */
    @Attach
    public void attach(IComputerAccess computer) {
        if (!computers.contains(computer)) {
            computers.add(computer);
        }
    }

    /**
     * The method can be named anything you want, as long as it has the annotation it will
     * be invoked when a computer is detached from your peripheral
     */
    @Detatch
    public void detach(IComputerAccess computer) {
        if (computers.contains(computer)) {
            computers.remove(computer);
        }
    }

}