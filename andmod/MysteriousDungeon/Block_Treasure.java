package andmod.MysteriousDungeon;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Block_Treasure extends BlockContainer {

	public Block_Treasure( int blockID ) {
		super( blockID, Material.iron );
	}

	@Override
	public TileEntity createNewTileEntity( World world ) {
		return new TileEntity_Treasure();
	}

}
