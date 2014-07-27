package mod;

import com.theoriginalbit.minecraft.framework.peripheral.LuaType;
import com.theoriginalbit.minecraft.framework.peripheral.PeripheralProvider;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dan200.computercraft.api.ComputerCraftAPI;
import mod.tile.TileAlternative;
import mod.tile.TileBasic;
import mod.converter.ConverterItemStack;
import mod.tile.TileSpecial;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * This is your main mod class.
 *
 * Other than registering the few things with the Peripheral-Framework or ComputerCraft,
 * this is a basic mod class, if you don't know this leave now and learn to mod for
 * Minecraft first, I'm not creating this example to show you how to mod.
 *
 * @author theoriginalbit
 */
@Mod(modid = "ExampleMod", name = "ExampleMod", version = "1.0", dependencies = "required-after:ComputerCraft;")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class ExampleMod {

    public static Block blockBasic;
    public static Block blockSpecial;
    public static Block blockAlternative;

    public static String MOUNT_PATH = "/examplemod/lua/";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        // setup basic block
        blockBasic = new BlockExample(1410, "Basic") {
            @Override
            public TileEntity createNewTileEntity(World world) {
                return new TileBasic();
            }
        };

        // setup special block
        blockSpecial = new BlockExample(1411, "Special") {
            @Override
            public TileEntity createNewTileEntity(World world) {
                return new TileSpecial();
            }
        };

        // setup alternative block
        blockAlternative = new BlockExample(1412, "Alternative") {
            @Override
            public TileEntity createNewTileEntity(World world) {
                return new TileAlternative();
            }
        };

        registerLuaTypes();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        // We have to register the peripheral provider that is supplied in the
        // Peripheral-Framework so that ComputerCraft may use your peripherals
        ComputerCraftAPI.registerPeripheralProvider(new PeripheralProvider());
    }

    private void registerLuaTypes() {
        // We have methods in PeripheralAlternative that return or want ItemStacks,
        // we have to register a converter for these
        LuaType.registerTypeConverter(new ConverterItemStack());

        // we have a custom converter, so that this doesn't appear as a '?' in Lua-side
        // error messages we provide a name mapping for it
        LuaType.registerClassToNameMapping(ItemStack.class, "item");
    }

}
