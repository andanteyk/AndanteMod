package andmod.ExTools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;

public class Block_Pane extends BlockPane {

	public Block_Pane( int blockID, String mainTexture, String sideTexture, Material material, boolean canDropItself ) {
		super( blockID, mainTexture, sideTexture, material, canDropItself );
	}
	
	
	public boolean canPaneConnectTo( IBlockAccess access, int x, int y, int z, ForgeDirection dir ) {
		int bid = access.getBlockId( x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ );
        return canThisPaneConnectToThisBlockID( bid ) || access.isBlockSolidOnSide( x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir.getOpposite(), false ) ||
        		Block.blocksList[bid] instanceof Block_Glass || Block.blocksList[bid] instanceof Block_Pane ;
    }

}
