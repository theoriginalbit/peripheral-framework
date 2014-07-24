package mod;

import com.theoriginalbit.minecraft.computercraft.peripheral.LuaType;
import com.theoriginalbit.minecraft.computercraft.peripheral.PeripheralProvider;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.ComputerCraftAPI;
import mod.alternative.BlockAlternative;
import mod.alternative.TileAlternative;
import mod.converter.ConverterItemStack;
import mod.special.BlockSpecial;
import mod.special.TileSpecial;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

/**
 * This is your main mod class.
 *
 * @author theoriginalbit
 */
@Mod(modid = "ExampleMod", name = "ExampleMod", version = "1.0", dependencies = "required-after:ComputerCraft;")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ExampleMod {

    public static Block blockSpecial;
    public static Block blockAlternative;

    public static ExampleMount mount = new ExampleMount();

    public static String MOUNT_PATH = "/examplemod/lua/";

    /**
     * This is basic mod creation stuff, if you don't know this leave now
     * and learn to mod for Minecraft first
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // This block details the standard way you would define a peripheral
        blockSpecial = new BlockSpecial();
        GameRegistry.registerBlock(blockSpecial, "Special Block");
        GameRegistry.registerTileEntity(TileSpecial.class, "Special Tile");

        // This block details another way you could define your peripherals
        blockAlternative = new BlockAlternative();
        GameRegistry.registerBlock(blockAlternative, "Alternative Block");
        GameRegistry.registerTileEntity(TileAlternative.class, "Alternative Tile");

        // We have methods in PeripheralAlternative that return or want ItemStacks,
        // we have to register a converter for these
        LuaType.registerTypeConverter(new ConverterItemStack());

        // we have a custom converter, so that this doesn't appear as a '?' in Lua-side
        // error messages we provide a name mapping for it
        LuaType.registerClassToNameMapping(ItemStack.class, "item");
    }

    /**
     * We have to register the peripheral provider that is supplied in the
     * Peripheral Framework so that ComputerCraft may use your peripherals
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

}
