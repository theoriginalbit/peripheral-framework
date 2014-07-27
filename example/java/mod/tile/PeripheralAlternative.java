package mod.tile;

import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaFunction;
import com.theoriginalbit.minecraft.framework.peripheral.annotation.LuaPeripheral;

import mod.ExampleMod;
import mod.mounts.AlternativeMount;
import mod.mounts.SpecialMount;
import net.minecraft.item.ItemStack;

/**
 * Defining the peripherals as a separate class is allowed, sometimes it might
 * be cleaner for your code to do it this way. How you define Lua accessible
 * methods or attach/detach methods, is no different as shown in TileSpecial.
 * See TileSpecial for more information.
 *
 * What will be covered in this class:
 *  - How to define multiple virtual filesystem mounts
 *  - Using non-standard objects as return values
 *  - Using non-standard objects as arguments
 *  - Making methods only accessible when a mod is installed
 *  - Making methods only accessible when one of a set of mods is installed
 *
 * @author theoriginalbit
 */
@LuaPeripheral(value = "alternative", mounts = {SpecialMount.class, AlternativeMount.class})
public class PeripheralAlternative {

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

}
