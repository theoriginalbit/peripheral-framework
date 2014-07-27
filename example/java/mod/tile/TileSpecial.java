package mod.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.*;
import dan200.computercraft.api.peripheral.IComputerAccess;
import mod.mounts.SpecialMount;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Please read the {@link mod.tile.TileBasic} before reading this
 * to get an understanding of the Peripheral-Framework syntax.
 *
 * What will be covered in this class:
 *  - Specifying your peripheral to mount a virtual file system to the computer
 *  - Throwing an error back to the program that called your method
 *  - Returning multiple values from your method.
 *  - Queueing events to the invoking computer
 *  - Queueing events to all the connected computers
 *
 *  More areas of using the Peripheral-Framework are covered in the {@link mod.tile.TileBasic}
 *  and {@link mod.tile.TileAlternative} classes.
 *
 * @author theoriginalbit
 */

/**
 * To have your peripheral mount virtual file systems you can use the
 * mounts value on the LuaPeripheral annotation. When this peripheral
 * is attached to a computer, and there are no other peripherals of
 * this kind attached, the mount will be instantiated and mounted to
 * the computer. When all peripherals of this kind are removed from
 * the computer this mount will be unmounted from the computer.
 */
@LuaPeripheral(value = "special", mounts = SpecialMount.class)
public class TileSpecial extends TileEntity {

    /**
     * If you want to error back to the user, you can do this just like you would with
     * the IPeripheral interface, simply throw an exception and the Peripheral-Framework
     * will pass this exception back to ComputerCraft
     */
    @LuaFunction
    public boolean rangeCheck(int num) throws Exception {
        if (num < 0 || num > 7) {
            throw new Exception("Number must be in range 1-6");
        }
        return true;
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
     * What if you need a reference to the computer that is invoking the method?
     * Sure, that's fine too. You can also get the ILuaContext if you want it
     * as well.
     */
    @LuaFunction
    public void wait(IComputerAccess computer) {
        computer.queueEvent("done_waiting", new Object[0]);
    }

    /**
     * This specifies a list that will contain all the possible computers attached to this
     * peripheral, these attached computers are maintained by the Peripheral-Framework, thus
     * you do not even need to assign this variable to anything.
     */
    @ComputerList
    public ArrayList<IComputerAccess> computers;

    /**
     * An example method that iterates all the computers and queues an event to them
     */
    @LuaFunction
    public void queuer(String s) {
        for (IComputerAccess c : computers) {
            c.queueEvent(s, new Object[0]);
        }
    }

}