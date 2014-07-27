package mod.converter;

import com.google.common.collect.Maps;
import com.theoriginalbit.minecraft.framework.peripheral.converter.ITypeConverter;
import net.minecraft.item.ItemStack;

import java.util.Map;

/**
 * This is an example on how to implement a type converter,
 * you only need to do this if you plan on returning or
 * receiving a non-standard object. For this example it
 * will convert an ItemStack to and from Lua
 *
 * @author theoriginalbit
 */
public class ConverterItemStack implements ITypeConverter {

    /**
     * Convert from Lua to Java
     */
    @Override
    public Object fromLua(Object obj, Class<?> expected) {
        // we don't want to convert what we cannot convert
        if (expected == ItemStack.class && obj instanceof Map) {
            // convert the Lua table into an ItemStack
            Map<Object, Object> map = (Map<Object, Object>) obj;

            if (!map.containsKey("id")) {
                return null;
            }

            int id = ((Double) map.get("id")).intValue();
            int qty = 1;
            int dmg = 0;

            if (map.containsKey("qty")) {
                qty = ((Double) map.get("qty")).intValue();
            }
            if (map.containsKey("dmg")) {
                dmg = ((Double) map.get("dmg")).intValue();
            }

            return new ItemStack(id, qty, dmg);
        }
        // we couldn't convert it, lets say it's not ours
        return null;
    }

    /**
     * Convert from Java to Lua
     */
    @Override
    public Object toLua(Object obj) {
        // we don't want to convert what we cannot convert
        if (obj instanceof ItemStack) {
            // convert the ItemStack into a Lua table
            ItemStack stack = (ItemStack) obj;

            Map<String, Object> map = Maps.newHashMap();

            map.put("id", stack.itemID);
            map.put("name", getName(stack));
            map.put("rawName", stack.getUnlocalizedName().toLowerCase());
            map.put("qty", stack.stackSize);
            map.put("dmg", stack.getItemDamage());
            map.put("maxdmg", stack.getMaxDamage());
            map.put("maxqty", stack.getMaxStackSize());

            return map;
        }
        return null;
    }

    private static String getName(ItemStack stack) {
        String name = "Unknown";
        try {
            name = stack.getDisplayName();
        } catch (Exception e) {
            try {
                name = stack.getUnlocalizedName();
            } catch (Exception e2) {}
        }
        return name;
    }
}
