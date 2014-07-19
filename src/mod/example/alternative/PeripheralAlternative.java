package mod.example.alternative;

import com.google.common.collect.Lists;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Attach;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.Detatch;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;
import dan200.computercraft.api.peripheral.IComputerAccess;

import java.util.ArrayList;

/**
 * Defining the peripherals as a separate class is allowed, sometimes it might
 * be cleaner for your code to do it this way. How you define Lua accessible
 * methods or attach/detach methods, is no different as shown in TileSpecial.
 * See TileSpecial for more information.
 *
 * @author theoriginalbit
 */
@LuaPeripheral(name = "alternative")
public class PeripheralAlternative {

    @LuaFunction
    public void doSomething() {
        System.out.println("Something was done with the alternative peripheral");
    }

}