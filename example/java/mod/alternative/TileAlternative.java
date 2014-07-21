package mod.alternative;

import com.theoriginalbit.minecraft.computercraft.peripheral.ILuaPeripheralProvider;
import net.minecraft.tileentity.TileEntity;

/**
 * This TileEntity behaves a little different to TileSpecial, this one defines an external
 * peripheral which it provides to the PeripheralProvider through the use of the
 * ILuaPeripheralProvider annotation. Any annotations (LuaPeripheral, LuaFunction, OnAttach,
 * OnDetach) within this TileEntity are ignored so you must define them within the peripheral.
 *
 * @author theoriginalbit
 */
public class TileAlternative extends TileEntity implements ILuaPeripheralProvider {

    private final PeripheralAlternative peripheral = new PeripheralAlternative();

    @Override
    public Object getPeripheral() {
        return peripheral;
    }
}
