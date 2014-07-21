package mod.special;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.*;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Again this is your standard TileEntity class, however this is where your peripheral fun can happen.
 *
 * This class is annotated with the LuaPeripheral annotation, this tells the PeripheralProvider that
 * you wish this TileEntity to be available to ComputerCraft as a peripheral, with the peripheral type
 * specified.
 *
 * This class must then define methods it wishes to be accessible in Lua with the LuaFunction annotation.
 *
 * If you wish your peripheral to know when a computer is attached or detached from your peripheral, you
 * must define a method with the Attach and Detach annotations respectively. Note: you may only define
 * one of each method!
 *
 * It is suggested that you annotate your TileEntities with OpenPeripheral's Ignore annotation so that if
 * you implement anything such as IInventory on your TileEntity, OpenPeripheral will not attempt to claim
 * the peripheral as its own. Ignore annotation url:
 * https://github.com/OpenMods/OpenPeripheral/blob/master/src/main/java/openperipheral/api/Ignore.java
 *
 * @author theoriginalbit
 */
@LuaPeripheral("special")
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
     * If you want to error back to the user, you can do this just like you would with
     * the IPeripheral interface, simply throw an exception and the Peripheral-Framework
     * will pass this exception back to ComputerCraft
     */
    @LuaFunction
    public void rangeCheck(int num) throws Exception {
        if (num < 0 || num > 7) {
            throw new Exception("Number must be in range 1-6");
        }
    }

    /**
     * Lets assume, that for some reason you wish to have the method appear under a different
     * name in Lua you can provide a name to the LuaFunction; the only time you should really
     * be using this is in the event of method overloading like shown below.
     */
    @LuaFunction(name = "bar")
    public boolean foo() {
        return foo("incorrect!");
    }

    /**
     * You can also provide additional names for your methods using the Alias annotation,
     * your method will be accessible in Lua by both the native Java name, as well as the
     * name in the Alias annotation
     */
    @Alias("isColour")
    @LuaFunction
    public boolean isColor() {
        return true;
    }

    /**
     * You can even do multiple Aliases if you'd like
     */
    @Alias({"b", "c"})
    @LuaFunction
    public boolean a() {
        return true;
    }

    /**
     * What if you need a reference to the computer that is invoking the method?
     * Sure, that's fine too. You can also get the ILuaContext if you want it
     * as well.
     */
    @LuaFunction
    public void wait(IComputerAccess computer) {
        computer.queueEvent("done_waiting", new Object[0]);
    }

    /**
     * By default the Peripheral-Framework will convert return values of Object[] into a
     * {@link java.util.Map} so that it is a table in Lua
     */
    @LuaFunction
    public Object[] getSomething() {
        // this will return the table {[1] = "entry1", [2] = "entry2"}
        return new Object[]{ "entry1", "entry2" };
    }

    /**
     * However to do multi-returns we also must return an Object array. So that problems
     * don't arise we must therefore tell the Peripheral-Framework not to convert this
     * return value to a table, but instead return it as a multi-return; we do this
     * via the LuaFunction annotation like so.
     */
    @LuaFunction(isMultiReturn = true)
    public Object[] getSomethingElse() {
        return new Object[]{ true, "This is a multi-return" };
    }

    /**
     * You can specify if you want your method to only be accessible from Lua when certain
     * mods are installed.
     */
    @LuaFunction(modIds = "BuildCraft|Core")
    public void consumePower() {
        System.out.println("BuildCraft is installed, consuming its power");
    }

    /**
     * You can even specify a list of mods that can be installed for your method to be
     * enabled. The method below will only be enabled if BuildCraft or ThermalExpansion
     * are installed.
     */
    @LuaFunction(modIds = {"BuildCraft|Core", "ThermalExpansion"})
    public void consumeLotsOfPower() {
        System.out.println("BuildCraft and/or ThermalExpansion is installed, consuming all the power");
    }

    /**
     * Not all methods in a peripheral have to be registered, this is just a normal method
     * you can use, but it will not be accessible in Lua
     */
    public void fooBar() {
        System.out.println("Foo bar!");
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
    @Detach
    public void detach(IComputerAccess computer) {
        if (computers.contains(computer)) {
            computers.remove(computer);
        }
    }

}