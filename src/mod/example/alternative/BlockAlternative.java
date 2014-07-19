package mod.example.alternative;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * This is your basic Minecraft block, again if you don't know what you're doing here,
 * leave now and learn to mod for Minecraft first
 *
 * @author theoriginalbit
 */
public class BlockAlternative extends BlockContainer {

    public BlockAlternative() {
            super(1451, Material.rock);
        }

    @Override
    public TileEntity createNewTileEntity(World world) {
            return new TileAlternative();
        }

}
