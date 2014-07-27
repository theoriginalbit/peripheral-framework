package mod.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.Alias;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;
import net.minecraft.tileentity.TileEntity;

/**
 * This is your standard TileEntity class, however this is where your peripheral fun can happen.
 *
 * This class is annotated with the LuaPeripheral annotation, this tells the PeripheralProvider that
 * you wish this TileEntity to be available to ComputerCraft as a peripheral, with the peripheral type
 * specified.
 *
 * This class must then define methods it wishes to be accessible in Lua with the LuaFunction annotation.
 *
 * If you wish your peripheral to know when a computer is attached or detached from your peripheral, you
 * must define a method with the OnAttach and OnDetach annotations respectively. Note: you may only define
 * one of each method!
 *
 * It is suggested that you annotate your TileEntities with OpenPeripheral's Ignore annotation so that if
 * you implement anything such as IInventory on your TileEntity, OpenPeripheral will not attempt to claim
 * the peripheral as its own. Ignore annotation url:
 * https://github.com/OpenMods/OpenPeripheral/blob/master/src/main/java/openperipheral/api/Ignore.java
 *
 * What will be covered in this class:
 *  - How to define a Lua accessible method
 *  - How to change the name of the method shown Lua side
 *  - How to setup multiple names for a method in Lua side
 *  - Non-accessible Lua methods
 *
 * @author theoriginalbit
 */
@LuaPeripheral("basic")
public class TileBasic extends TileEntity {

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
     * You can  even do multiple Aliases if you'd like
     */
    @Alias({"b", "c"})
    @LuaFunction
    public boolean a() {
        return true;
    }

    /**
     * Not all methods in a peripheral have to be registered, this is just a normal method
     * you can use in your TileEntity, but it will not be accessible in Lua
     */
    public void fooBar() {
        System.out.println("Foo bar!");
    }

}
