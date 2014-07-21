package mod.alternative;

import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.computercraft.peripheral.annotation.LuaPeripheral;

import mod.ExampleMod;
import net.minecraft.item.ItemStack;

/**
 * Defining the peripherals as a separate class is allowed, sometimes it might
 * be cleaner for your code to do it this way. How you define Lua accessible
 * methods or attach/detach methods, is no different as shown in TileSpecial.
 * See TileSpecial for more information.
 *
 * @author theoriginalbit
 */
@LuaPeripheral("alternative")
public class PeripheralAlternative {

    @LuaFunction
    public void doSomething() {
        System.out.println("Something was done with the alternative peripheral");
    }

    /**
     * This demonstrates that you can return non-standard objects, as long as
     * you register a converter for them. See {@link mod.converter.ConverterItemStack}
     */
    @LuaFunction
    public ItemStack getItem() {
        return new ItemStack(ExampleMod.blockAlternative, 1);
    }

    /**
     * This demonstrates that you can accept non-standard objects, as long as
     * you register a converter for them. See {@link mod.converter.ConverterItemStack}
     */
    @LuaFunction
    public void useItem(ItemStack stack) {
        System.out.println("Using up the item stack");
    }

}