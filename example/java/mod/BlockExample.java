package mod;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

/**
 * This is your basic Minecraft block, there's nothing related to the
 * Peripheral-Framework in here, it is just needed to provide a working
 * example; you may leave now.
 *
 * @author theoriginalbit
 */
public abstract class BlockExample extends BlockContainer {

    public BlockExample(int id, String name) {
        super(id, Material.rock);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("examplemod." + name.toLowerCase());
        GameRegistry.registerBlock(this, name + " Block");
        GameRegistry.registerTileEntity(createNewTileEntity(null).getClass(), name + " Tile");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister registry) {
        blockIcon = registry.registerIcon("examplemod:block");
    }

}
